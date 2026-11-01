package bai8;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class FlightServiceImpl extends UnicastRemoteObject implements FlightService {
    // Lưu trữ danh sách chuyến bay: <Mã chuyến bay, Số ghế trống>
    private Map<String, Integer> flightSeats;

    public FlightServiceImpl() throws RemoteException {
        super();
        flightSeats = new HashMap<>();
        // Thêm một số chuyến bay mẫu ban đầu
        flightSeats.put("VN123", 50);
        flightSeats.put("VJ456", 20);
        flightSeats.put("QH789", 5);
    }

    @Override
    public boolean bookTicket(String flightNumber, int seats) throws RemoteException {
        if (flightSeats.containsKey(flightNumber)) {
            int currentSeats = flightSeats.get(flightNumber);
            if (currentSeats >= seats && seats > 0) {
                flightSeats.put(flightNumber, currentSeats - seats);
                System.out.println("Server: Đặt vé thành công chuyến " + flightNumber + " số lượng: " + seats + " ghế. Còn lại: " + flightSeats.get(flightNumber));
                return true;
            }
        }
        System.out.println("Server: Đặt vé thất bại chuyến " + flightNumber + " (Không đủ ghế hoặc sai thông tin).");
        return false;
    }

    @Override
    public int availableSeats(String flightNumber) throws RemoteException {
        if (flightSeats.containsKey(flightNumber)) {
            int seats = flightSeats.get(flightNumber);
            System.out.println("Server: Kiểm tra ghế chuyến " + flightNumber + " -> Số ghế trống: " + seats);
            return seats;
        }
        System.out.println("Server: Không tìm thấy chuyến bay " + flightNumber);
        return -1; // Trả về -1 nếu không tồn tại chuyến bay
    }

    @Override
    public boolean cancelBooking(String flightNumber, int seats) throws RemoteException {
        if (flightSeats.containsKey(flightNumber) && seats > 0) {
            int currentSeats = flightSeats.get(flightNumber);
            flightSeats.put(flightNumber, currentSeats + seats);
            System.out.println("Server: Hủy vé thành công chuyến " + flightNumber + " hoàn lại: " + seats + " ghế. Tổng ghế trống: " + flightSeats.get(flightNumber));
            return true;
        }
        System.out.println("Server: Hủy vé thất bại cho chuyến " + flightNumber);
        return false;
    }
}