# Atividade Prática 2 - Composição vs. Herança

Este repositório contém a implementação prática solicitada na Atividade Prática 2 da disciplina de Programação Orientada a Objetos.

O objetivo do projeto é demonstrar, através de um sistema de livraria simplificado, como a **Composição** pode ser uma alternativa superior à **Herança** para evitar acoplamento forte e explosão de classes.

## 📁 Estrutura do Projeto

O código está dividido em dois cenários dentro do pacote padrão:

1.  **Cenário de Herança (Acoplamento Forte):**
    * Demonstra a rigidez ao tentar criar livros com diferentes formatos (Físico/Digital) e regras de imposto usando apenas herança.
    * Classes: `LivroHeranca`, `LivroFisico`, `LivroDigital`, `LivroFisicoComImposto`, etc.

2.  **Cenário de Composição (Flexível):**
    * Demonstra o uso de interfaces para injetar comportamentos dinamicamente.
    * Classes: `LivroComposicao`.
    * Interfaces: `RegraDeImposto`, `CustoFormato`.

## 🚀 Como Executar o Projeto

Para rodar este projeto, você precisa ter o **JDK (Java Development Kit)** instalado.

### Passo 1: Compilar
Abra o terminal (ou cmd) na pasta onde estão os arquivos `.java` e execute:

```bash
javac *.java

📊 Resultados Esperados
O console exibirá os preços calculados para o mesmo tipo de livro (Físico com Imposto, Digital com Imposto) usando as duas abordagens. A seção de Composição mostrará a flexibilidade ao criar combinações (ex: Livro Didático Físico Isento de Imposto) sem a necessidade de criar uma nova classe.

👤 Autor
Gabriel Jonathan Queiroz Ferreira 
Análise e Desenvolvimento de Sistemas 
Centro Universitario Celso Lisboa 
