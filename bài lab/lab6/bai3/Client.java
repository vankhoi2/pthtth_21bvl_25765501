package bai3;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // Kết nối tới Registry
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            
            // Tìm kiếm dịch vụ từ xa
            PrimeService service = (PrimeService) registry.lookup("PrimeService");
            
            // Nhập số cần kiểm tra từ bàn phím
            Scanner scanner = new Scanner(System.in);
            System.out.print("Nhập một số nguyên cần kiểm tra: ");
            int n = scanner.nextInt();
            
            // Gọi phương thức từ xa
            boolean result = service.isPrime(n);
            
            // In kết quả
            if (result) {
                System.out.println(n + " LÀ số nguyên tố.");
            } else {
                System.out.println(n + " KHÔNG PHẢI là số nguyên tố.");
            }
        } catch (Exception e) {
            System.err.println("Lỗi Client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}