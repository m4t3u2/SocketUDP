package socket;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class UDPClient {

    public static void main(String args[]) throws Exception {

    }

    public static void enviaMensagem(String msg) {
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket();
            String mensagem = msg;
            int l = mensagem.getBytes().length;
            byte[] m = new byte[l];
            m = mensagem.getBytes();
            InetAddress aHost = InetAddress.getByName("127.0.0.1");
            int serverPort = 6789;
            DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort);
            aSocket.send(request);

            byte[] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(reply);
            String respostaRecebida = new String(reply.getData());
            System.out.println("\nResposta: \n" + respostaRecebida);

        } catch (SocketException e) {
            System.out.println("UDPCliente Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("UDPCliente IO: " + e.getMessage());
        } finally {
            if (aSocket != null) {
                aSocket.close();
            }
        }
    }

    public static void passaCaminho() throws Exception {
        System.out.println("Especifique o caminho local do pacote: ");
        Scanner caminho = new Scanner(System.in);
        String enviarCaminho = caminho.next();
        enviaPacote(enviarCaminho);
    }

    public static void enviaPacote(String caminhoRecebido) throws Exception {
        String caminhoPacote = caminhoRecebido;
        File pacote = new File(caminhoPacote);
        FileInputStream in = new FileInputStream(pacote);
        Socket socket = new Socket("127.0.0.1", 6770);
        OutputStream out = socket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(out);
        BufferedWriter writer = new BufferedWriter(osw);
        writer.write(pacote.getName() + "\n");
        writer.flush();
        System.out.print("Aguarde até que seja enviado!");
        int c;
        while ((c = in.read()) != -1) {
            out.write(c);
        }
        System.out.println("\nEnviado.");
    }
}
