package bai1;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            // Tạo RMI Registry tại cổng 1099
            Registry registry = LocateRegistry.createRegistry(1099);
            
            // Khởi tạo đối tượng từ xa
            HelloService service = new HelloServiceImpl();
            
            // Đăng ký dịch vụ với tên "HelloService"
            registry.rebind("HelloService", service);
            
            System.out.println("RMI Server đã sẵn sàng và đang chạy...");
        } catch (Exception e) {
            System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}