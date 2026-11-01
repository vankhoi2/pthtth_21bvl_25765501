package bai2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculateServiceImpl extends UnicastRemoteObject implements CalculateService {
    
    public CalculateServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}