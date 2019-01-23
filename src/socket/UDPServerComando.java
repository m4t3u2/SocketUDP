package socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServerComando extends Thread {

    @Override
    public void run() {
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket(6789);
            while (true) {
                byte[] buffer = new byte[1000];
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);
                String temp = new String(request.getData());
                UDPServerComando server = new UDPServerComando();
                temp = comandoRecebido(temp);

                byte[] novoBuffer = new byte[1000];
                novoBuffer = server.executarComando(temp);

                DatagramPacket reply = new DatagramPacket(novoBuffer, 0, novoBuffer.length, request.getAddress(), request.getPort());
                aSocket.send(reply);
            }

        } catch (SocketException e) {
            System.out.println("UDPServerComando Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("UDPServerComando IO: " + e.getMessage());
        } finally {
            if (aSocket != null) {
                aSocket.close();
            }
        }

    }

    public static String comandoRecebido(String recebida) {
        if (recebida.contains("listas")) {
            recebida = "ls /home/mateus/servidor/listasExistentes";
        }
        if (recebida.contains("inicia")) {
            String[] array = recebida.split("#");
            recebida = "aircrack-ng -w /home/mateus/servidor/listasExistentes/" + array[1] + " /home/mateus/servidor/pacotesRecebidos/" + array[2];
        }
        return recebida;
    }

    public byte[] executarComando(String comando) throws IOException {
        Process p = Runtime.getRuntime().exec(comando);
        java.util.Scanner s = new java.util.Scanner(p.getInputStream());
        int i = 0;
        String resp = new String();

        while (s.hasNextLine()) {
            resp = resp + (++i) + ": ";
            resp = resp + s.nextLine() + "\n";
        }
        System.out.println("\nEnviado lista para um cliente.\n");
        int r = resp.getBytes().length;
        byte[] m = new byte[r];
        m = resp.getBytes();

        return m;
    }
}
