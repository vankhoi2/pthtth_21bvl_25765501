package bai5;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            // Tạo Registry tại cổng 1099
            Registry registry = LocateRegistry.createRegistry(1099);
            
            // Khởi tạo dịch vụ Chat
            ChatService service = new ChatServiceImpl();
            
            // Đăng ký dịch vụ với tên "ChatService"
            registry.rebind("ChatService", service);
            
            System.out.println("RMI Server (Chat 1-1) đã sẵn sàng, đang chờ tin nhắn từ Client...");
        } catch (Exception e) {
            System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}