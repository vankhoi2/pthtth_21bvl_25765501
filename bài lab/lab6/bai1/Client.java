package bai1;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try {
            // Kết nối tới Registry trên máy cục bộ (localhost) cổng 1099
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            
            // Tìm kiếm đối tượng từ xa
            HelloService service = (HelloService) registry.lookup("HelloService");
            
            // Gọi phương thức từ xa và nhận kết quả
            String response = service.sayHello();
            
            System.out.println("Kết quả nhận từ Server: " + response);
        } catch (Exception e) {
            System.err.println("Lỗi Client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}