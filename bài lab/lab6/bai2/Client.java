package bai2;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // Kết nối tới Registry
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            
            // Tìm kiếm dịch vụ từ xa
            CalculateService service = (CalculateService) registry.lookup("CalculateService");
            
            // Nhập 2 số từ bàn phím
            Scanner scanner = new Scanner(System.in);
            System.out.print("Nhập số nguyên a: ");
            int a = scanner.nextInt();
            System.out.print("Nhập số nguyên b: ");
            int b = scanner.nextInt();
            
            // Gọi phương thức từ xa để tính tổng
            int result = service.add(a, b);
            
            System.out.println("Kết quả từ Server: " + a + " + " + b + " = " + result);
        } catch (Exception e) {
            System.err.println("Lỗi Client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}