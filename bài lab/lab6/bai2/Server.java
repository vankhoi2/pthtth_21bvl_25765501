package bai2;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            // Tạo Registry tại cổng 1099
            Registry registry = LocateRegistry.createRegistry(1099);
            
            // Khởi tạo dịch vụ
            CalculateService service = new CalculateServiceImpl();
            
            // Đăng ký dịch vụ với tên "CalculateService"
            registry.rebind("CalculateService", service);
            
            System.out.println("RMI Server (Tính tổng) đã sẵn sàng...");
        } catch (Exception e) {
            System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}