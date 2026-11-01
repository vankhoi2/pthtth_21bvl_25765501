package bai5;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ChatServiceImpl extends UnicastRemoteObject implements ChatService {

    public ChatServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public String sendMessage(String message) throws RemoteException {
        System.out.println("\n[Client gửi]: " + message);
        
        // Server nhập phản hồi trực tiếp từ bàn phím máy chủ
        System.out.print("[Server phản hồi]: ");
        Scanner serverScanner = new Scanner(System.in);
        String reply = serverScanner.nextLine();
        
        return reply;
    }
}