import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Cliente {
    private static final int QUANTIDADE_NUMEROS = 1000000;

    public static void main(String[] args) throws Exception {
        int quantidadeClientes = args.length > 0
                ? Integer.parseInt(args[0])
                : 1;

        if (quantidadeClientes < 1) {
            throw new IllegalArgumentException("Informe pelo menos 1 cliente.");
        }

        ExecutorService executor =
                Executors.newFixedThreadPool(quantidadeClientes);

        CountDownLatch prontos = new CountDownLatch(quantidadeClientes);
        CountDownLatch iniciar = new CountDownLatch(1);
        List<Future<Integer>> resultados = new ArrayList<>();

        try {
            for (int cliente = 0; cliente < quantidadeClientes; cliente++) {

                resultados.add(executor.submit(() -> {
                    int[] numeros = new int[QUANTIDADE_NUMEROS];
                    Random random = new Random();

                    for (int i = 0; i < numeros.length; i++) {
                        numeros[i] = random.nextInt(1001);
                    }

                    prontos.countDown();
                    iniciar.await();

                    try (Socket socket = new Socket("127.0.0.1", 12345);
                         DataOutputStream out = new DataOutputStream(
                                 new BufferedOutputStream(socket.getOutputStream()));
                         DataInputStream in = new DataInputStream(
                                 new BufferedInputStream(socket.getInputStream()))) {

                        out.writeInt(QUANTIDADE_NUMEROS);

                        for (int numero : numeros) {
                            out.writeInt(numero);
                        }

                        out.flush();
                        return in.readInt();
                    }
                }));
            }

            prontos.await();

            long inicio = System.nanoTime();
            iniciar.countDown();

            for (int i = 0; i < resultados.size(); i++) {
                int soma = resultados.get(i).get();
                System.out.println(
                        "Cliente " + (i + 1) + " recebeu a soma: " + soma);
            }

            long fim = System.nanoTime();
            double tempoTotalMs = (fim - inicio) / 1000000.0;

            System.out.printf(
                    "Rodada com %d cliente(s): %.3f ms%n",
                    quantidadeClientes,
                    tempoTotalMs
            );
        } finally {
            executor.shutdownNow();
        }
    }
}