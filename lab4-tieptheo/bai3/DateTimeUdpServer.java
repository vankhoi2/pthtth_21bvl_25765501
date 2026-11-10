package bai3;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUdpServer {
    public static void main(String[] args) {
        int port = 9998;
        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("UDP DateTime Server đang chạy trên cổng " + port + "...");
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String request = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8).trim().toUpperCase();
                System.out.println("Nhận yêu cầu '" + request + "' từ " + packet.getAddress() + ":" + packet.getPort());

                LocalDateTime now = LocalDateTime.now();
                String response;
                switch (request) {
                    case "DATE":
                        response = now.format(DateTimeFormatter.ofPattern("dd MM yyyy"));
                        break;
                    case "TIME":
                        response = now.format(DateTimeFormatter.ofPattern("HH mm ss"));
                        break;
                    case "DATETIME":
                        response = now.format(DateTimeFormatter.ofPattern("dd MM yyyy HH mm ss"));
                        break;
                    default:
                        response = "ERR UNKNOWN_COMMAND";
                        break;
                }

                byte[] sendData = response.getBytes(StandardCharsets.UTF_8);
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
                socket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}