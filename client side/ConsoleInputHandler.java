import java.util.ArrayList;
import java.util.Scanner;


public class ConsoleInputHandler implements Runnable {
     static ArrayList<Sms> sms1 = new ArrayList<>();

    public void run() {
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        while (running) {
            System.out.println("Type a command (or 'shutdown' to stop the server):");
            String input = scanner.nextLine();
            if ("shutdown".equalsIgnoreCase(input)) {
                running = false;
                System.out.println("Shutting down server...");
                break;
            } else {
                System.out.println("Unknown command.");
            }
        }
        scanner.close();
    }
}
