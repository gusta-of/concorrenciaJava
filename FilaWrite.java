package concorrencia;

import java.util.Base64;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class FilaWrite {

    private static final ReentrantLock lock = new ReentrantLock();
    private static final LinkedBlockingQueue<Usuario> filaDeUsuarios = new LinkedBlockingQueue<>();

    private static void salvarArquivo(Usuario usuario) {
        System.out.println("[ESCRITA] : " + usuario.toString());
    }

    private static void lerArquivo(Usuario usuario) {
         System.out.println("[LEITURA] : " + usuario.toString());
    }

    public static void executarOperacaoEscrita(Usuario usuario) {
        filaDeUsuarios.offer(usuario);
        lock.lock(); // Bloqueia o acesso a partir daqui
        try {
            // Aguarde até que seja a vez do usuário atual
            while (!filaDeUsuarios.peek().equals(usuario)) {
                try {
                    lock.unlock();
                    Thread.sleep(100); // Aguarda um pouco e tenta novamente
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    lock.lock();
                }
            }

            try {
                
                salvarArquivo(usuario);
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        

            // Após a conclusão, remova o usuário da fila
            filaDeUsuarios.poll();
        } finally {
            lock.unlock(); // Libera o bloqueio após a operação
        }
    }


    public static String executarOperacaoLeitura(Usuario usuario) {
        filaDeUsuarios.offer(usuario);
        lock.lock(); // Bloqueia o acesso a partir daqui

        String retorno = "";
        try {
            // Aguarde até que seja a vez do usuário atual
            while (!filaDeUsuarios.peek().equals(usuario)) {
                try {
                    lock.unlock();
                    Thread.sleep(100); // Aguarda um pouco e tenta novamente
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    lock.lock();
                }
            }

            try {
                lerArquivo(usuario);
                retorno = "LEITURA EXECUTADA COM SUCESSO " + Base64.getEncoder().encodeToString(("Conteúdo do Usuário " + usuario.getId()).getBytes());
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        

            // Após a conclusão, remova o usuário da fila
            filaDeUsuarios.poll();
        } finally {
            lock.unlock(); // Libera o bloqueio após a operação
        }

        return retorno;
    }

    // Outros métodos da sua aplicação
}
