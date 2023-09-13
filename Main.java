package concorrencia;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        byte[] conteudo1 = ("Conteúdo do Usuário " + 1).getBytes();
        Usuario usuario1 = new Usuario("arquivo.pdf", conteudo1, 1, "W".toCharArray()[0]);

        Usuario usuario2 = new Usuario("arquivo.pdf", null, 2, "R".toCharArray()[0]);

        byte[] conteudo3 = ("Conteúdo do Usuário " + 3).getBytes();
        Usuario usuario3 = new Usuario("arquivo.pdf", conteudo3, 3, "W".toCharArray()[0]);

        Usuario usuario4 = new Usuario("arquivo.pdf", null, 4, "R".toCharArray()[0]);

        Usuario usuario5 = new Usuario("arquivo.pdf", null, 5, "R".toCharArray()[0]);

        List<Usuario> users = new ArrayList<>();
        users.add(usuario1);
        users.add(usuario2);
        users.add(usuario3);
        users.add(usuario4);
        users.add(usuario5);

        for (Usuario usuario : users) {
            executorService.execute(() -> {

                System.out.println("Usuário " + usuario.toString() + " está na fila de " + (usuario.getTypeExecution()== "W".toCharArray()[0] ? "[escrita]" : "[leitura]") + ".");

                if(usuario.getTypeExecution() == "W".toCharArray()[0]) {
                    FilaWrite.executarOperacaoEscrita(usuario);
                    System.out.println("_______________________________________________________________________________________");
                } else {
                    String retorno = FilaWrite.executarOperacaoLeitura(usuario);
                    System.out.println("RETORNO: " + retorno);
                    System.out.println("_______________________________________________________________________________________");
                }

                
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}