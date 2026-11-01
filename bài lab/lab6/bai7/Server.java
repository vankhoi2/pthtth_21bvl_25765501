package bai7;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            // Tạo Registry tại cổng 1099
            Registry registry = LocateRegistry.createRegistry(1099);
            
            // Khởi tạo dịch vụ tính diện tích hình học
            ShapeService service = new ShapeServiceImpl();
            
            // Đăng ký dịch vụ với tên "ShapeService"
            registry.rebind("ShapeService", service);
            
            System.out.println("RMI Server (Tính diện tích hình học) đã sẵn sàng...");
        } catch (Exception e) {
            System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}