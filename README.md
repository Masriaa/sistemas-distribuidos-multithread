# Sistemas Distribuidos: Terceira Prática - Multithread

[link para o vídeo explicativo e evidências de execução](https://drive.google.com/drive/folders/1SHYsw2VLO4jITgBY3JTZgc4sMf9XJsyi?usp=sharing)

## Objetivo:
O cliente é responsável por enviar 1000000 números 
aleatórios (entre 0 e 1000) para o servidor, que realiza a soma de todos os números e retorna a 
resposta para o cliente.

## Requisitos

- JDK 8 ou superior (Java Development Kit), necessário para compilar e executar o programa.
- A porta TCP `12345` disponível na máquina.

## Como executar

Abra um terminal na pasta do projeto e compile os arquivos:

```bash
javac Server.java Cliente.java
```

1. No primeiro terminal, inicie o servidor:

   ```bash
   java Server
   ```

2. Em outro terminal, na mesma pasta, inicie o cliente:

   ```bash
   java Cliente
   ```

   Em seguida, informe a quantidade desejada de clientes, por exemplo:

   ```bash
   java Cliente 4
   ```
3. Para encerrar o servidor, use `Ctrl+C` no terminal em que ele está sendo executado.

# Resultados Obtidos: 
| Quantidade de Clientes | Tempo Total - Cliente (Ms) | Tempo de processamento (Ms)|
|--- | ---|---|
| 1 | 139| 82|
|4| 347 | 180 - 258|
|8| 505 | 184 - 332|
|16| 1360| 119 - 302 | 
