package bai8;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            // Tạo Registry tại cổng 1099
            Registry registry = LocateRegistry.createRegistry(1099);
            
            // Khởi tạo dịch vụ Đặt vé máy bay
            FlightService service = new FlightServiceImpl();
            
            // Đăng ký dịch vụ với tên "FlightService"
            registry.rebind("FlightService", service);
            
            System.out.println("RMI Server (Hệ thống đặt vé máy bay) đã sẵn sàng...");
        } catch (Exception e) {
            System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}