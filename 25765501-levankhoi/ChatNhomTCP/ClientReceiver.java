
import java.io.BufferedReader;
import java.io.IOException;

public class ClientReceiver implements Runnable {
    private final BufferedReader input;

    public ClientReceiver(BufferedReader input) {
        this.input = input;
    }

    @Override
    public void run() {
        try {
            String message;

            while ((message = input.readLine()) != null) {
                System.out.println("\n" + message);
                System.out.print("> ");
            }
        } catch (IOException e) {
            System.out.println("\nĐã ngắt kết nối với server.");
        }
    }
}
