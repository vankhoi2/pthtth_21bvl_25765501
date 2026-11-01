package bai9;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class AuctionServiceImpl extends UnicastRemoteObject implements AuctionService {
    // Lưu trữ danh sách sản phẩm đấu giá: <Tên sản phẩm, Đối tượng Product>
    private Map<String, Product> products;

    public AuctionServiceImpl() throws RemoteException {
        super();
        products = new HashMap<>();
        // Thêm một số sản phẩm mẫu ban đầu
        products.put("Laptop Gaming", new Product("Laptop Gaming", 15000000));
        products.put("iPhone 15", new Product("iPhone 15", 20000000));
    }

    @Override
    public synchronized void addProduct(String name, double startPrice) throws RemoteException {
        if (!products.containsKey(name)) {
            products.put(name, new Product(name, startPrice));
            System.out.println("Server: Đã thêm sản phẩm đấu giá mới -> " + name + " (Giá khởi điểm: " + startPrice + ")");
        } else {
            System.out.println("Server: Sản phẩm " + name + " đã tồn tại.");
        }
    }

    @Override
    public synchronized Map<String, Product> getProducts() throws RemoteException {
        System.out.println("Server: Đã cung cấp danh sách sản phẩm cho Client.");
        return products;
    }

    @Override
    public synchronized boolean placeBid(String productName, double bidAmount, String bidderName) throws RemoteException {
        Product product = products.get(productName);
        if (product != null) {
            // Kiểm tra xem giá thầu mới có cao hơn giá hiện tại không
            if (bidAmount > product.getCurrentPrice()) {
                product.setCurrentPrice(bidAmount);
                product.setHighestBidder(bidderName);
                System.out.println("Server: [ĐẤU GIÁ THÀNH CÔNG] " + bidderName + " đã đặt giá " + bidAmount + " cho sản phẩm '" + productName + "'");
                return true;
            } else {
                System.out.println("Server: [ĐẤU GIÁ THẤT BẠI] " + bidderName + " đặt giá " + bidAmount + " thấp hơn giá hiện tại (" + product.getCurrentPrice() + ")");
            }
        }
        return false;
    }
}