package socket;

public class UDPServer {

    public static void main(String args[]) {
        UDPServerComando iniciaServidorComando = new UDPServerComando();
        ServerPacote iniciaServidorPacote = new ServerPacote();
        iniciaServidorComando.start();
        iniciaServidorPacote.start();
    }
}
