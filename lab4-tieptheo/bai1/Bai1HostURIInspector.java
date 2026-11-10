package bai1;

import java.net.InetAddress;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public class Bai1HostURIInspector {
    public static void main(String[] args) {
        // 1. Kiểm tra thiếu tham số (Yêu cầu nhận 2 tham số riêng biệt qua args)
        if (args.length < 2) {
            System.err.println("Lỗi: Thiếu tham số!");
            System.out.println("Cú pháp đúng: java bai1.Bai1HostURIInspector <hostname> <URI>");
            return;
        }

        String hostInput = args[0];
        String uriInput = args[1];

        System.out.println("=== KẾT QUẢ KIỂM TRA HOST VÀ URI ===");
        System.out.println("1. HOSTNAME: " + hostInput);
        System.out.println("2. URI STRING: " + uriInput);
        System.out.println("------------------------------------");

        // Phần 1: Phân giải Hostname và IP
        inspectHost(hostInput);

        System.out.println("------------------------------------");

        // Phần 2: Phân tích URI bằng java.net.URI
        inspectUri(uriInput);
    }

    private static void inspectHost(String hostname) {
        System.out.println("[Phân giải Hostname]");
        try {
            InetAddress[] addresses = InetAddress.getAllByName(hostname);
            for (InetAddress address : addresses) {
                System.out.println(" - IP: " + address.getHostAddress());
                if (address instanceof Inet4Address) {
                    System.out.println("   Type: IPv4");
                } else if (address instanceof Inet6Address) {
                    System.out.println("   Type: IPv6");
                } else {
                    System.out.println("   Type: Unknown");
                }
                System.out.println("   Loopback  : " + address.isLoopbackAddress());
                System.out.println("   Site local: " + address.isSiteLocalAddress());
            }
        } catch (UnknownHostException e) {
            System.err.println("Không phân giải được host: " + hostname);
        }
    }

    private static void inspectUri(String uriString) {
        System.out.println("[Phân tích URI]");
        try {
            URI uri = new URI(uriString);
            System.out.println(" - Scheme   : " + (uri.getScheme() != null ? uri.getScheme() : "N/A"));
            System.out.println(" - Host     : " + (uri.getHost() != null ? uri.getHost() : "N/A"));
            System.out.println(" - Port     : " + (uri.getPort() != -1 ? uri.getPort() : "N/A (Mặc định)"));
            System.out.println(" - Path     : " + (uri.getPath() != null ? uri.getPath() : "N/A"));
            System.out.println(" - Query    : " + (uri.getQuery() != null ? uri.getQuery() : "N/A"));
            System.out.println(" - Fragment : " + (uri.getFragment() != null ? uri.getFragment() : "N/A"));
        } catch (URISyntaxException e) {
            System.err.println("Lỗi cú pháp URI: " + e.getMessage());
        }
    }
}