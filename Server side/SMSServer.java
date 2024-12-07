import java.io.*;
import java.net.*;
import java.util.ArrayList;
public class SMSServer {
    static ArrayList<Sms> sms1 = new ArrayList<>();
    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server is listening on port 5000");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
               // initializeMessages();
                //clientHandler handler = new clientHandler(socket);
                //handler.start();
                new clientHandler(socket).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void initializeMessages() {
        SMSServer.sms1.add(new SpanishSms("Hola, cómo estás?"));
        SMSServer.sms1.add(new FrenchSms("Bonjour, comment ça va?"));
        SMSServer.sms1.add(new ArabicSms("مرحبا، كيف حالك؟"));
    }
}
