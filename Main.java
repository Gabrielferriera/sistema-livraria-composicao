import java.util.ArrayList;
import java.util.List;

// ============================================================================
// CENÁRIO 1: HERANÇA (ACOPLAMENTO FORTE - O jeito "ruim/rígido")
// Problema: Se quisermos um Livro Digital que também é Tributável, 
// teríamos que criar uma classe 'LivroDigitalTributavel', duplicando lógica.
// ============================================================================

// Classe Base
class LivroHeranca {
    protected String titulo;
    protected double valorBase;

    public LivroHeranca(String titulo, double valorBase) {
        this.titulo = titulo;
        this.valorBase = valorBase;
    }

    public double calcularPrecoFinal() {
        return valorBase;
    }
}

// Herança Nível 1
class LivroFisico extends LivroHeranca {
    public LivroFisico(String titulo, double valorBase) {
        super(titulo, valorBase);
    }
    @Override
    public double calcularPrecoFinal() {
        return valorBase + 15.00; // Custo de frete/impressão fixo
    }
}

// Herança Nível 1 - Outro ramo
class LivroDigital extends LivroHeranca {
    public LivroDigital(String titulo, double valorBase) {
        super(titulo, valorBase);
    }
    // Não tem frete
}

// Herança Nível 2 - Começa o problema. 
// E se eu quiser um LivroFisico que tem imposto? Tenho que estender LivroFisico.
class LivroFisicoComImposto extends LivroFisico {
    public LivroFisicoComImposto(String titulo, double valorBase) {
        super(titulo, valorBase);
    }
    @Override
    public double calcularPrecoFinal() {
        return super.calcularPrecoFinal() * 1.10; // 10% de imposto
    }
}

// E se eu quiser um LivroDigital com imposto? Tenho que duplicar a lógica do imposto!
class LivroDigitalComImposto extends LivroDigital {
    public LivroDigitalComImposto(String titulo, double valorBase) {
        super(titulo, valorBase);
    }
    @Override
    public double calcularPrecoFinal() {
        return super.calcularPrecoFinal() * 1.10; // Lógica duplicada! Acoplamento ruim.
    }
}

// ============================================================================
// CENÁRIO 2: COMPOSIÇÃO (BAIXO ACOPLAMENTO - O jeito "flexível")
// Solução: O Livro "TEM UMA" regra de imposto e "TEM UM" custo de formato.
// ============================================================================

// Interface para comportamento de Imposto (Strategy Pattern)
interface RegraDeImposto {
    double aplicar(double valor);
}

class ImpostoPadrao implements RegraDeImposto {
    public double aplicar(double valor) { return valor * 1.10; } // 10%
}

class IsentoDeImposto implements RegraDeImposto {
    public double aplicar(double valor) { return valor; } // Sem alteração
}

// Interface para comportamento de Formato/Frete
interface CustoFormato {
    double custoAdicional();
}

class FormatoFisico implements CustoFormato {
    public double custoAdicional() { return 15.00; }
}

class FormatoDigital implements CustoFormato {
    public double custoAdicional() { return 0.0; }
}

// Classe Principal usando Composição
class LivroComposicao {
    private String titulo;
    private double valorBase;
    
    // COMPOSIÇÃO: O livro é composto por comportamentos, não herda deles.
    private RegraDeImposto regraDeImposto;
    private CustoFormato custoFormato;

    public LivroComposicao(String titulo, double valorBase, RegraDeImposto regra, CustoFormato formato) {
        this.titulo = titulo;
        this.valorBase = valorBase;
        this.regraDeImposto = regra;
        this.custoFormato = formato;
    }

    public double calcularPrecoFinal() {
        double valorComFrete = valorBase + custoFormato.custoAdicional();
        return regraDeImposto.aplicar(valorComFrete);
    }
    
    public String getTitulo() { return titulo; }
}


// ============================================================================
// CLASSE MAIN PARA TESTAR E COMPARAR
// ============================================================================
public class Main {
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE LIVRARIA: HERANÇA VS COMPOSIÇÃO ===\n");

        // --- Testando Herança ---
        System.out.println("1. Abordagem com Herança (Rígida):");
        LivroFisicoComImposto livroH1 = new LivroFisicoComImposto("Java Herança", 100.00);
        // (100 + 15 frete) * 1.10 imposto = 126.50
        System.out.println("Livro: " + "Java Herança" + " | Preço Final: " + livroH1.calcularPrecoFinal());
        
        LivroDigitalComImposto livroH2 = new LivroDigitalComImposto("Ebook Herança", 100.00);
        // (100) * 1.10 imposto = 110.00
        System.out.println("Livro: " + "Ebook Herança" + " | Preço Final: " + livroH2.calcularPrecoFinal());


        System.out.println("\n--------------------------------------------------\n");

        // --- Testando Composição ---
        System.out.println("2. Abordagem com Composição (Flexível):");
        
        // Criamos comportamentos reutilizáveis
        RegraDeImposto comImposto = new ImpostoPadrao();
        RegraDeImposto semImposto = new IsentoDeImposto();
        CustoFormato fisico = new FormatoFisico();
        CustoFormato digital = new FormatoDigital();

        // Combinamos como quisermos sem criar novas classes!
        
        // Livro Físico com Imposto
        LivroComposicao lc1 = new LivroComposicao("Java Composição", 100.00, comImposto, fisico);
        System.out.println("Livro: " + lc1.getTitulo() + " | Preço Final: " + lc1.calcularPrecoFinal());

        // Livro Digital com Imposto
        LivroComposicao lc2 = new LivroComposicao("Ebook Composição", 100.00, comImposto, digital);
        System.out.println("Livro: " + lc2.getTitulo() + " | Preço Final: " + lc2.calcularPrecoFinal());
        
        // MUDANÇA EM TEMPO DE EXECUÇÃO (Impossível com Herança):
        // Imagine um livro físico isento de impostos (ex: livro didático)
        LivroComposicao lc3 = new LivroComposicao("Livro Didático", 100.00, semImposto, fisico);
        System.out.println("Livro: " + lc3.getTitulo() + " | Preço Final: " + lc3.calcularPrecoFinal());
    }
}