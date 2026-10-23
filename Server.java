import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Porta 12345 aberta!");
            System.out.println("Aguardando conexão do cliente...");

            while (true) {
                Socket cliente = serverSocket.accept();

                System.out.println(
                    "Cliente conectado! " +
                    cliente.getInetAddress().getHostAddress()
                );

                TratamentoCliente tratamento = new TratamentoCliente(cliente);
                Thread t = new Thread(tratamento);
                t.start();
            }
        }   
    }
} 

class TratamentoCliente implements Runnable {
    private Socket cliente; 

    public TratamentoCliente(Socket cliente) {
        this.cliente = cliente;
    }

    @Override 
    public void run() {
        try (DataInputStream in = new DataInputStream(new BufferedInputStream(cliente.getInputStream()));
             DataOutputStream out = new DataOutputStream(cliente.getOutputStream())) {

            int quantidadeNumeros = in.readInt();
            int[] numeros = new int [quantidadeNumeros];

            long inicio = System.nanoTime();

            for (int i = 0; i < quantidadeNumeros; i++) {
                numeros[i] = in.readInt();
            }

            int soma = 0;
            for (int numero : numeros) {
                soma += numero;
            }

            long fim = System.nanoTime();
            double tempoCalculoMs = (fim - inicio) / 1000000.0;

            System.out.println("Soma: " + soma);
            System.out.println("Tempo de processamento: " + tempoCalculoMs + " ms");

            out.writeInt(soma);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}