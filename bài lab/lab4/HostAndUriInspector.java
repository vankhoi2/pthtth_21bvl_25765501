package lab4;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public class HostAndUriInspector {

    public static void main(String[] args) {
        // 1. Xử lý thiếu tham số (Yêu cầu nhận 2 tham số riêng biệt qua args)
        if (args.length < 2) {
            System.err.println("Lỗi: Thiếu tham số!");
            System.out.println("Cú pháp đúng: java lab4.HostAndUriInspector <hostname> <URI>");
            return;
        }

        String hostname = args[0];
        String uriString = args[1];

        System.out.println("=== KẾT QUẢ KIỂM TRA HOST VÀ URI ===");
        System.out.println("1. HOSTNAME: " + hostname);
        System.out.println("2. URI STRING: " + uriString);
        System.out.println("------------------------------------");

        // 2. PHẦN 1: Kiểm tra Hostname và phân giải IP
        inspectHost(hostname);

        System.out.println("------------------------------------");

        // 3. PHẦN 2: Phân tích các thành phần của URI bằng java.net.URI
        inspectUri(uriString);
    }

    private static void inspectHost(String hostname) {
        System.out.println("[Phân giải Hostname]");
        try {
            InetAddress[] addresses = InetAddress.getAllByName(hostname);
            for (InetAddress addr : addresses) {
                System.out.println("- IP: " + addr.getHostAddress());

                // Phân biệt IPv4 hay IPv6
                if (addr instanceof Inet4Address) {
                    System.out.println("  Type: IPv4");
                } else if (addr instanceof Inet6Address) {
                    System.out.println("  Type: IPv6");
                } else {
                    System.out.println("  Type: Unknown");
                }

                // In thông tin Loopback và Site Local
                System.out.println("  Loopback: " + addr.isLoopbackAddress());
                System.out.println("  Site local: " + addr.isSiteLocalAddress());
            }
        } catch (UnknownHostException e) {
            System.err.println("Lỗi: Hostname không phân giải được (" + hostname + ")");
        }
    }

    private static void inspectUri(String uriString) {
        System.out.println("[Phân tích URI]");
        try {
            URI uri = new URI(uriString);
            System.out.println("- Scheme   : " + (uri.getScheme() != null ? uri.getScheme() : "N/A"));
            System.out.println("- Host     : " + (uri.getHost() != null ? uri.getHost() : "N/A"));
            System.out.println("- Port     : " + (uri.getPort() != -1 ? uri.getPort() : "N/A (Mặc định)"));
            System.out.println("- Path     : " + (uri.getPath() != null && !uri.getPath().isEmpty() ? uri.getPath() : "N/A"));
            System.out.println("- Query    : " + (uri.getQuery() != null ? uri.getQuery() : "N/A"));
            System.out.println("- Fragment : " + (uri.getFragment() != null ? uri.getFragment() : "N/A"));
        } catch (URISyntaxException e) {
            System.err.println("Lỗi: Cú pháp URI không hợp lệ (" + e.getMessage() + ")");
        }
    }
}