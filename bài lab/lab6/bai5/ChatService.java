package bai5;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatService extends Remote {
    // Client gửi tin nhắn lên server, đồng thời nhận về chuỗi phản hồi từ server
    String sendMessage(String message) throws RemoteException;
}