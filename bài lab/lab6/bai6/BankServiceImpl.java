package bai6;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BankServiceImpl extends UnicastRemoteObject implements BankService {
    // Khởi tạo số dư ban đầu cho tài khoản ngân hàng (ví dụ: 1,000,000 VNĐ)
    private double balance;

    public BankServiceImpl() throws RemoteException {
        super();
        this.balance = 1000000.0; 
    }

    @Override
    public double getBalance() throws RemoteException {
        System.out.println("Server: Đã kiểm tra số dư. Số dư hiện tại = " + balance);
        return balance;
    }

    @Override
    public void deposit(double amount) throws RemoteException {
        if (amount > 0) {
            balance += amount;
            System.out.println("Server: Đã nạp " + amount + " | Số dư mới = " + balance);
        } else {
            System.out.println("Server: Số tiền nạp không hợp lệ!");
        }
    }

    @Override
    public boolean withdraw(double amount) throws RemoteException {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Server: Đã rút " + amount + " | Số dư còn lại = " + balance);
            return true;
        }
        System.out.println("Server: Rút tiền thất bại (Số tiền vượt quá số dư hoặc không hợp lệ).");
        return false;
    }
}
