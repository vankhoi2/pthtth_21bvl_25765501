package bai4;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ContactServiceImpl extends UnicastRemoteObject implements ContactService {
    // Sử dụng HashMap để lưu trữ danh bạ trên server
    private Map<String, String> contacts;

    public ContactServiceImpl() throws RemoteException {
        super();
        contacts = new HashMap<>();
        // Thêm vài dữ liệu mẫu ban đầu (nếu muốn)
        contacts.put("Nguyen Van A", "0901234567");
        contacts.put("Tran Thi B", "0987654321");
    }

    @Override
    public void addContact(String name, String phone) throws RemoteException {
        contacts.put(name, phone);
        System.out.println("Server: Đã thêm/cập nhật liên hệ -> Tên: " + name + ", SĐT: " + phone);
    }

    @Override
    public String findContact(String name) throws RemoteException {
        System.out.println("Server: Đang tìm kiếm liên hệ cho tên: " + name);
        return contacts.get(name); // Trả về số điện thoại hoặc null nếu không thấy
    }

    @Override
    public boolean deleteContact(String name) throws RemoteException {
        if (contacts.containsKey(name)) {
            contacts.remove(name);
            System.out.println("Server: Đã xóa liên hệ -> " + name);
            return true;
        }
        System.out.println("Server: Không tìm thấy tên '" + name + "' để xóa.");
        return false;
    }
}