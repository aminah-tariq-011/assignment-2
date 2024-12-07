import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

class clientHandler extends Thread {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
   // private static ArrayList<Sms> sms = new ArrayList<>();
    public clientHandler(Socket socket) {
        this.socket = socket;
    }
    public void run() {
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
           // output = new ObjectOutputStream(socket.getOutputStream());
            output.writeObject("Welcome to the SMS Server!");
output.flush();
            Thread receiveThread = new Thread(() -> {
                try {
                    while (true) {
                        String message = (String) input.readObject();
                        System.out.println("Aminah: " + message);
                        //String content = (String) input.readObject();
                        Sms sms = new Sms(message);
                       // sms.SmsDisplay();
                        SMSServer.sms1.add(sms);
                        output.writeObject("Message received and stored.");

                     /*   if ("send".equalsIgnoreCase(message)) {
                            String content = (String) input.readObject();
                            Sms sms = new Sms(content);
                            sms.SmsDisplay();
                            SMSServer.sms1.add(sms);
                            output.writeObject("Message received and stored.");
                        }*/
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    System.out.println("Client disconnected.");
                }
            });
            receiveThread.start();
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("1. Send SMS");
                System.out.println("2. delete  ");
                System.out.println("3. display" );
                System.out.println("4. sort by time");
                System.out.println("5. receive message");
                System.out.println("6. Exit");
                System.out.print("Enter option: \n");
                String sms = scanner.nextLine();

    switch (sms) {
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
                    SMSServer.sms1.add(smss);
                    System.out.println("Message sent");
                    break;
                    case "2":
                        output.writeObject("sending message");
                        System.out.print("Enter message \n");
                        String sms3 = scanner.nextLine();
                        Sms smsw = new SpanishSms(sms3);
                        SMSServer.sms1.add(smsw);
                        System.out.println("Message sent");
                        break;
                        case "3":
                            output.writeObject("sending message");
                            System.out.print("Enter message \n");
                            String sms9 = scanner.nextLine();
                            Sms smsq = new ArabicSms(sms9);
                            SMSServer.sms1.add(smsq);
                            System.out.println("Message sent");
                            break;
                            default:
                                System.out.println("Invalid option");
                                break;
            }
            break;
        case "2":
            System.out.print("Enter message you want to delete: ");
            String message1 = scanner.nextLine();
            delete(message1);

            break;
        case "3":
            System.out.println("choose your option:");
            System.out.println("1. display all sms");
            System.out.println("2. display by status");
            System.out.println("3. display by languages");
            String sing = scanner.nextLine();
            switch (sing) {
                case "1":
                    for(Sms smss1 : SMSServer.sms1) {
                        smss1.SmsDisplay();
                    }
                    break;
                case "2":
                    System.out.println("how do you want to display by status");
                    System.out.println("1. display unread");
                    System.out.println("2. display read");
                    String sing1 = scanner.nextLine();
                    switch (sing1) {
                        case "1":
                            ArrayList<Sms> smsr2 = getUnreadMessages();
                            for (Sms sms6 : smsr2) {
                                sms6.SmsDisplay();
                            }
                            break;
                        case "2":
                            ArrayList<Sms> sms3 =  getReadMessages();
                            for (Sms smst : sms3) {
                                smst.SmsDisplay();
                            }
                            break;
                        default:
                            System.out.println("Invalid option");
                            break;
                    }
                    break;
                case "3":
                    System.out.println("which language do you want to display:");
                    System.out.println("1. French");
                    System.out.println("2. spanish");
                    System.out.println("3. Arabic");
                    String sing3 = scanner.nextLine();
                    ArrayList<Sms> sms4= new ArrayList<>();
                    switch (sing3) {
                        case "1":
                             sms4 =  displayspanishsms();
                            for (Sms sms5 : sms4) {
                                sms5.SmsDisplay();
                            }
                            break;
                        case "2":
                            sms4 =  displayfrenchsms();
                            for (Sms sms5 : sms4) {
                                sms5.SmsDisplay();
                            }
                            break;
                        case "3":
                             sms4 =  displayarabicsms();
                            for (Sms sms5 : sms4) {
                                sms5.SmsDisplay();
                            }
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

            output.writeObject("Messages sorted by time.");
            break;
            case "5":
                for(Sms smss1 : SMSServer.sms1) {
                    smss1.SmsDisplay();
                }
                System.out.print("\nEnter message you want to delete: ");
                String message18 = scanner.nextLine();
                receivesms(message18);
                break;
        case "6":
            output.writeObject("Disconnecting...");
            System.out.println("Client disconnected.");
            socket.close();
            return;
        default:
            output.writeObject("Invalid command.");
            break;

    }
        if ("exit".equalsIgnoreCase(sms)) {
            System.out.println("Disconnecting client...");
            output.writeObject("Server disconnecting...");
            socket.close();
            break;
        }

     //   output.writeObject(message);
        output.flush();
    }
} catch (IOException e) {
            throw new RuntimeException(e);
        }
}
    public static void delete(String message) {
        Sms toDelete = null;
        for (Sms msg : SMSServer.sms1) {
            if (msg.getContent().equals(message)) {
                toDelete = msg;
                break;
            }
        }
        if (toDelete != null) {
            SMSServer.sms1.remove(toDelete);
        }// return toDelete;
    }
    public static void display(){
        for (Sms msg: SMSServer.sms1){
            msg.SmsDisplay();
        }
    }
    private static ArrayList<Sms> getUnreadMessages() {
        ArrayList<Sms> unreadMessages = new ArrayList<>();
        for (Sms msg : SMSServer.sms1) {
            if (!msg.getStatus()) unreadMessages.add(msg);
        }
        return unreadMessages;
    }

    private static ArrayList<Sms> getReadMessages() {
        ArrayList<Sms> readMessages = new ArrayList<>();
        for (Sms msg : SMSServer.sms1) {
            if (msg.getStatus()) readMessages.add(msg);
        }
        return readMessages;
    }

    private static void sortMessagesByTime() {
        if (SMSServer.sms1 != null) {
            SMSServer.sms1.sort(Comparator.comparing(Sms::getTimeStamp));
            for (Sms msg : SMSServer.sms1) {
                msg.SmsDisplay();
            }
        } else {
            System.out.println("No messages to sort.");
        }
    }
    private static ArrayList<Sms> displayfrenchsms(){
        ArrayList<Sms> french = new ArrayList<>();
        for(Sms sms : SMSServer.sms1){
            if(sms instanceof FrenchSms){
                // System.out.println(sms);
                french.add(sms);
            }
        }
        return french;
    }
    private static ArrayList<Sms> displayspanishsms(){
        ArrayList<Sms> spanish = new ArrayList<>();
        for(Sms sms : SMSServer.sms1){
            if(sms instanceof SpanishSms){
                // System.out.println(sms);
                spanish.add(sms);
            }
        }
        return spanish;
    }
    private static ArrayList<Sms> displayarabicsms(){
        ArrayList<Sms> arabic = new ArrayList<>();
        for(Sms sms : SMSServer.sms1){
            if(sms instanceof ArabicSms){
                //System.out.println(sms);
                arabic.add(sms);
            }
        }
        return arabic;
    }
    private static void receivesms(String message) {
        for (Sms msg : SMSServer.sms1) {
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