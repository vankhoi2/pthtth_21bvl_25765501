package lab4;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostInspector {
    public static void main(String[] args) {
        // 1. Kiểm tra tham số truyền vào
        if (args.length != 1) {
            System.out.println("Usage: java lab4.HostInspector <hostname>");
            return;
        }

        String host = args[0];

        try {
            // 2. Phân giải danh sách IP từ hostname
            InetAddress[] addresses = InetAddress.getAllByName(host);
            System.out.println("Host: " + host);

            for (InetAddress address : addresses) {
                System.out.println("- IP: " + address.getHostAddress());

                // Bổ sung: Phân biệt IPv4 hay IPv6
                if (address instanceof Inet4Address) {
                    System.out.println("  Type: IPv4");
                } else if (address instanceof Inet6Address) {
                    System.out.println("  Type: IPv6");
                } else {
                    System.out.println("  Type: Unknown");
                }

                System.out.println("  Canonical: " + address.getCanonicalHostName());
                System.out.println("  Loopback: " + address.isLoopbackAddress());
                System.out.println("  Site local: " + address.isSiteLocalAddress());
            }
        } catch (UnknownHostException e) {
            // Thông báo lỗi rõ ràng, không in stack trace
            System.err.println("Không phân giải được host: " + host);
        }
    }
}