package bai7;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ShapeServiceImpl extends UnicastRemoteObject implements ShapeService {

    public ShapeServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public double rectangleArea(double width, double height) throws RemoteException {
        double area = width * height;
        System.out.println("Server: Tính diện tích Hình chữ nhật (Rộng: " + width + ", Cao: " + height + ") = " + area);
        return area;
    }

    @Override
    public double circleArea(double radius) throws RemoteException {
        double area = Math.PI * radius * radius;
        System.out.println("Server: Tính diện tích Hình tròn (Bán kính: " + radius + ") = " + area);
        return area;
    }

    @Override
    public double triangleArea(double base, double height) throws RemoteException {
        double area = 0.5 * base * height;
        System.out.println("Server: Tính diện tích Hình tam giác (Đáy: " + base + ", Cao: " + height + ") = " + area);
        return area;
    }
}