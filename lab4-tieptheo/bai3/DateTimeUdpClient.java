package bai3;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class DateTimeUdpClient {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {
            InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
            int serverPort = 9998;

            System.out.println("UDP Client đã sẵn sàng. Nhập lệnh (DATE, TIME, DATETIME):");
            while (true) {
                System.out.print("Client nhập: ");
                String cmd = scanner.nextLine();

                if ("QUIT".equalsIgnoreCase(cmd.trim())) {
                    break;
                }

                byte[] sendData = cmd.getBytes(StandardCharsets.UTF_8);
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
                socket.send(sendPacket);

                byte[] buffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(receivePacket);

                String response = new String(receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);
                System.out.println("Server trả về: " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}