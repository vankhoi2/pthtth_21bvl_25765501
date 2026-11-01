package bai5;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // Kết nối tới Registry
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            
            // Tìm kiếm dịch vụ từ xa
            ChatService service = (ChatService) registry.lookup("ChatService");
            
            Scanner scanner = new Scanner(System.in);
            System.out.println("--- BẮT ĐẦU CHAT VỚI SERVER (Gõ 'exit' để thoát) ---");
            
            while (true) {
                System.out.print("Bạn (Client): ");
                String message = scanner.nextLine();
                
                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("Đã ngắt kết nối chat.");
                    break;
                }
                
                // Gửi tin nhắn lên server và nhận lại phản hồi
                String response = service.sendMessage(message);
                System.out.println("Server phản hồi: " + response);
            }
            
        } catch (Exception e) {
            System.err.println("Lỗi Client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}