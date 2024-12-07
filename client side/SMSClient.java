import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;


public class SMSClient {
    public static void main(String[] args) {

        ConsoleInputHandler.sms1.add(new SpanishSms("Hola, cómo estás?"));
        ConsoleInputHandler.sms1.add(new FrenchSms("Bonjour, comment ça va?"));
        ConsoleInputHandler.sms1.add(new ArabicSms("مرحبا، كيف حالك؟"));

        try (Socket socket = new Socket("192.168.10.8", 5000);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in))
        {


            System.out.println("Connected to the server.");
            System.out.println((String) input.readObject());
            Thread receiveThread = new Thread(() -> {
                try {
                    while (true) {
                        String message = (String) input.readObject();
                        System.out.println("Adina: " + message);
                        Sms sms = new Sms(message);
                        ConsoleInputHandler.sms1.add(sms);
                        System.out.println("message received and stored");
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    System.out.println("Connection closed.");
                }
            });
            receiveThread.start();


            while (true) {
                System.out.println("1. Send SMS");
                System.out.println("2. delete  ");
                System.out.println("3. display");
                System.out.println("4. sort by time");
                System.out.println("5. receive message");
                System.out.println("5. Exit");
                System.out.print("Enter option: \n");
                String command = scanner.nextLine();

                switch (command) {
                    case "1":
                        System.out.println("choose language in which you want to send message:");
                        System.out.println("1. French");
                        System.out.println("2. spanish");
                        System.out.println("3. Arabic");
                        String sing4 = scanner.nextLine();
                        switch (sing4) {
                            case "1":
                                output.writeObject("sending message");
                                System.out.print("Enter message \n");
                                String sms2 = scanner.nextLine();
                                Sms smss = new FrenchSms(sms2);
                                ConsoleInputHandler.sms1.add(smss);
                                System.out.println("Message sent");
                                break;
                            case "2":
                                output.writeObject("sending message");
                                System.out.print("Enter message \n");
                                String sms3 = scanner.nextLine();
                                Sms smsw = new SpanishSms(sms3);
                                ConsoleInputHandler.sms1.add(smsw);
                                System.out.println("Message sent");
                                break;
                            case "3":
                                output.writeObject("sending message");
                                System.out.print("Enter message \n");
                                String sms9 = scanner.nextLine();
                                Sms smsq = new ArabicSms(sms9);
                                ConsoleInputHandler.sms1.add(smsq);
                                System.out.println("Message sent");
                                break;
                            default:
                                System.out.println("Invalid option");
                                break;
                        }

                        break;


                    case "2":
                        for (Sms smss1 : ConsoleInputHandler.sms1) {
                            smss1.SmsDisplay();
                        }
                        System.out.print("\nEnter message you want to delete: ");
                        String message = scanner.nextLine();
                        delete(message);
                        System.out.println("Message deleted.");
                        break;
                    case "3":
                        System.out.println("choose your option:");
                        System.out.println("1. display all sms");
                        System.out.println("2. display by status");
                        System.out.println("3. display by languages");
                        String sing = scanner.nextLine();
                        switch (sing) {
                            case "1":
                                for (Sms smss1 : ConsoleInputHandler.sms1) {
                                    smss1.SmsDisplay();
                                }
                                break;
                            case "2":
                                System.out.println("how do you want to display by status");
                                System.out.println("1. display unread");
                                System.out.println("2. display read");
                                String sing12 = scanner.nextLine();
                                switch (sing12) {
                                    case "1":
                                        ArrayList<Sms> smsrw2 = getUnreadMessages();
                                        for (Sms sms6 : smsrw2) {
                                            sms6.SmsDisplay();
                                        }

                                        break;
                                    case "2":
                                        getReadMessages();
                                                  /* for (Sms smst : sms3) {
                                                       smst.SmsDisplay();
                                                   }*/
                                        break;
                                    default:
                                        System.out.println("Invalid option");
                                        break;
                                }
                                break;
                            case "3":
                                System.out.println("which language do you want to display:");
                                System.out.println("1. Spanish");
                                System.out.println("2. French");
                                System.out.println("3. Arabic");
                                String sing3 = scanner.nextLine();
                                ArrayList<Sms> sms4 = new ArrayList<>();
                                switch (sing3) {
                                    case "1":
                                        displayspanishsms();

                                        break;
                                    case "2":
                                        displayfrenchsms();
                                                   /*for (Sms sms5 : sms4) {
                                                       sms5.SmsDisplay();
                                                   }*/
                                        break;
                                    case "3":
                                        displayarabicsms();

                                        break;
                                    default:
                                        break;
                                }

                                break;
                            default:
                                break;
                        }
                    case "4":
                        sortMessagesByTime();
                        break;
                    case "5":
                        for (Sms smss1 : ConsoleInputHandler.sms1) {
                            smss1.SmsDisplay();
                        }
                        System.out.print("\nEnter message you want to receive: ");
                        String message1 = scanner.nextLine();
                        receivesms(message1);
                        break;
                    case "6":
                        System.out.println("Disconnecting from server...");
                        output.flush();
                        break;
                    default:
                        System.out.println("Invalid option.");
                        break;

                }


            }

        }
    catch(IOException | ClassNotFoundException ex)
    {
        ex.printStackTrace();
    }
}

    public static Sms delete(String message) {
        Sms toDelete = null;

        for (Sms msg : ConsoleInputHandler.sms1) {
            if (msg.getContent().equals(message)) {
                toDelete = msg;
                break;
            }
        }

        if (toDelete != null) {
            ConsoleInputHandler.sms1.remove(toDelete);
        }

        return toDelete;
    }

    public static void display(){
        for (Sms msg: ConsoleInputHandler.sms1){
            msg.SmsDisplay();
        }
    }

    private static ArrayList<Sms> getUnreadMessages() {
        ArrayList<Sms> unreadMessages = new ArrayList<>();
        for (Sms msg : ConsoleInputHandler.sms1) {
            if (!msg.getStatus()) unreadMessages.add(msg);
        }
        return unreadMessages;
    }

    private static void getReadMessages() {
      //  ArrayList<Sms> readMessages = new ArrayList<>();
        for (Sms msg : ConsoleInputHandler.sms1) {
                if(msg.getStatus()){
                   msg.SmsDisplay();
                   // System.out.println("SMS received");
                }
                }

    }

    private static void sortMessagesByTime() {
        if (ConsoleInputHandler.sms1 != null) {
            ConsoleInputHandler.sms1.sort(Comparator.comparing(Sms::getTimeStamp));
            for (Sms msg : ConsoleInputHandler.sms1) {
                msg.SmsDisplay();
            }
        } else {
            System.out.println("No messages to sort.");
        }
    }
       /* private static ArrayList<Sms> displayfrenchsms(){
            ArrayList<Sms> french = new ArrayList<>();
            for(Sms sms : ConsoleInputHandler.sms1){
                if(sms instanceof FrenchSms){

                    french.add(sms);
                }
            }
            return french;
        }*/

    private static void displayfrenchsms() {
        ArrayList<Sms> french = new ArrayList<>();
        for (Sms sms : ConsoleInputHandler.sms1) {
            if (sms instanceof FrenchSms) {
                sms.SmsDisplay();
            }
        }

    }

    private static void displayspanishsms(){
          //  ArrayList<Sms> spanish = new ArrayList<>();
            for(Sms sms : ConsoleInputHandler.sms1){
                if(sms instanceof SpanishSms){
                    sms.SmsDisplay();
                }
            }
        }
        private static void displayarabicsms(){
          //  ArrayList<Sms> arabic = new ArrayList<>();
            for(Sms sms : ConsoleInputHandler.sms1){
                if(sms instanceof ArabicSms){
                    sms.SmsDisplay();
                }
            }

        }

        private static void receivesms(String message) {
            for (Sms msg : ConsoleInputHandler.sms1) {
                if (msg.getContent().equals(message)) {
                    if(!msg.getStatus()){
                        msg.setStatus(true);
                        System.out.println("SMS received");
                    } else {
                        System.out.println("message has already been received.");
                    }
                }
                }


        }
    }



