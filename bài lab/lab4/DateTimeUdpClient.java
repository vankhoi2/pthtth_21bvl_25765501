package lab4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

public class DateTimeUdpClient {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 5003;

        try (DatagramSocket socket = new DatagramSocket();
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {

            socket.setSoTimeout(3000); // Thiết lập Timeout 3 giây tránh treo vô hạn khi Server dừng
            InetAddress address = InetAddress.getByName(host);

            System.out.println("Sẵn sàng gửi UDP packet. Nhập lệnh (DATE, TIME, DATETIME, EXIT):");
            String request;
            while ((request = console.readLine()) != null) {
                if ("EXIT".equalsIgnoreCase(request.trim())) break;

                byte[] sendData = request.getBytes(StandardCharsets.UTF_8);
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
                socket.send(sendPacket);

                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                
                try {
                    socket.receive(receivePacket);
                    String response = new String(receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);
                    System.out.println("Server: " + response);
                } catch (SocketTimeoutException e) {
                    System.err.println("Lỗi: Không nhận được phản hồi từ UDP Server (Timeout).");
                }
            }
        } catch (IOException e) {
            System.err.println("Lỗi UDP Client: " + e.getMessage());
        }
    }
}