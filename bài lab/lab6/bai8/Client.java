package bai8;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // Kết nối tới Registry
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            
            // Tìm kiếm dịch vụ từ xa
            FlightService service = (FlightService) registry.lookup("FlightService");
            
            Scanner scanner = new Scanner(System.in);
            int choice = 0;
            
            do {
                System.out.println("\n--- HỆ THỐNG ĐẶT VÉ MÁY BAY RMI ---");
                System.out.println("1. Kiểm tra số ghế trống");
                System.out.println("2. Đặt vé máy bay");
                System.out.println("3. Hủy vé máy bay");
                System.out.println("4. Thoát");
                System.out.print("Chọn chức năng (1-4): ");
                
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Đọc bỏ dòng thừa
                } else {
                    System.out.println("Vui lòng nhập một số hợp lệ!");
                    scanner.next();
                    continue;
                }
                
                switch (choice) {
                    case 1:
                        System.out.print("Nhập mã chuyến bay (ví dụ: VN123, VJ456, QH789): ");
                        String fNumCheck = scanner.nextLine();
                        int seats = service.availableSeats(fNumCheck);
                        if (seats != -1) {
                            System.out.println("-> Số ghế trống của chuyến " + fNumCheck + " là: " + seats);
                        } else {
                            System.out.println("-> Không tìm thấy mã chuyến bay này.");
                        }
                        break;
                        
                    case 2:
                        System.out.print("Nhập mã chuyến bay muốn đặt: ");
                        String fNumBook = scanner.nextLine();
                        System.out.print("Nhập số lượng ghế cần đặt: ");
                        int bookSeats = scanner.nextInt();
                        scanner.nextLine();
                        
                        boolean booked = service.bookTicket(fNumBook, bookSeats);
                        if (booked) {
                            System.out.println("-> Đặt vé thành công!");
                        } else {
                            System.out.println("-> Đặt vé thất bại! (Không đủ ghế trống hoặc sai mã chuyến).");
                        }
                        break;
                        
                    case 3:
                        System.out.print("Nhập mã chuyến bay muốn hủy vé: ");
                        String fNumCancel = scanner.nextLine();
                        System.out.print("Nhập số lượng ghế muốn hoàn trả: ");
                        int cancelSeats = scanner.nextInt();
                        scanner.nextLine();
                        
                        boolean canceled = service.cancelBooking(fNumCancel, cancelSeats);
                        if (canceled) {
                            System.out.println("-> Hủy vé và hoàn trả ghế thành công!");
                        } else {
                            System.out.println("-> Hủy vé thất bại!");
                        }
                        break;
                        
                    case 4:
                        System.out.println("Thoát chương trình đặt vé.");
                        break;
                        
                    default:
                        System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn từ 1 đến 4.");
                }
            } while (choice != 4);
            
        } catch (Exception e) {
            System.err.println("Lỗi Client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}