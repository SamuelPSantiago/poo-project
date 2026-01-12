# Sistema de Gerenciamento de Estacionamento Inteligente

## Descrição

Sistema desenvolvido em Java para controlar de forma eficiente a entrada, permanência e saída de veículos em um estacionamento. O sistema automatiza o registro de veículos, a ocupação de vagas e o cálculo de tarifas.

## Tecnologias Utilizadas

- **Linguagem:** Java 17+
- **Interface Gráfica:** Java Swing (JFrame)

## Pré-requisitos

- JDK 17 ou superior instalado

## Estrutura do Projeto

```
poo-project/
├── src/
│   ├── model/              # Classes de domínio
│   │   ├── Veiculo.java
│   │   ├── Carro.java
│   │   ├── Moto.java
│   │   ├── Caminhao.java
│   │   ├── Vaga.java
│   │   └── Ticket.java
│   ├── service/            # Lógica de negócio
│   │   └── Estacionamento.java
│   ├── view/               # Interface gráfica (Swing)
│   │   ├── TelaPrincipal.java
│   │   ├── TelaEntrada.java
│   │   ├── TelaSaida.java
│   │   ├── TelaConsulta.java
│   │   └── TelaRelatorio.java
│   ├── utils/              # Utilitários
│   │   └── ComponenteUtils.java
│   └── Main.java           # Ponto de entrada
├── bin/                    # Classes compiladas
├── run.bat                 # Script para compilar e executar
└── README.md
```

## Como Executar

### Opcao 1: Usando run.bat (Windows)
1. Clone ou baixe o projeto
2. Execute o arquivo `run.bat`

### Opcao 2: Manualmente
1. Clone ou baixe o projeto
2. Abra o terminal na pasta do projeto
3. Compile o projeto:
   ```bash
   javac -d bin src/Main.java src/model/*.java src/service/*.java src/utils/*.java src/view/*.java
   ```
4. Execute:
   ```bash
   java -cp bin Main
   ```

## Funcionalidades

- Cadastro de veículos (Carro, Moto, Caminhão)
- Controle de entrada e saída
- Cálculo automático de tarifas
- Emissão de tickets
- Liberação automática de vagas
- Consulta de veículo por placa
- Validação de formato de placa (antigo e Mercosul)

## Conceitos de POO Aplicados

| Conceito | Aplicação no Projeto |
|----------|---------------------|
| **Encapsulamento** | Atributos privados com getters/setters |
| **Herança** | Carro, Moto e Caminhao herdam de Veiculo |
| **Polimorfismo** | Cálculo de tarifa específico por tipo |
| **Abstração** | Classe abstrata Veículo |
| **Composição** | Estacionamento contem Vagas e Tickets |

---

**Autores:** Samuel Pinheiro, Marina Gomes, André Ary

Trabalho acadêmico – POO
