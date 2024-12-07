import java.sql.DataTruncation;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Sms {
    private final String Id;
    private final Person Sender= new Person("aminah", "03701690844", "aminahtariq@gmail.com");
    private final Person Receiver= new Person("adina", "03771690844", "adina@gmail.com");;
    private String Content;
    private boolean Status;
    String receivedTime;
String sendtime;
    static int counter = 0;
LocalDateTime timeStamp = LocalDateTime.now();
DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss");
String mydate=timeStamp.format(df);
    /*public Sms() {
        this.Id = String.format("%03d", ++counter);
    }*/



    public Sms( String content) {
        this.Id = String.format("%03d", ++counter);
        //this.Sender = sender;
        //this.Receiver = receiver;
        this.Content = content;
        this.Status = false;
        this.timeStamp = LocalDateTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss");
        String mydate=timeStamp.format(df);
        this.sendtime=mydate;
    }

    private void setReceivedTime(String receivedTime) {
        this.receivedTime = receivedTime;
    }

    public String getId() {
        return Id;
    }

    public Person getSender() {
        return Sender;
    }
    public Person getReceiver() {
        return Receiver;
    }
    public String getContent() {
        return Content;
    }
    public boolean getStatus() {

        return Status;
    }
    public void setStatus(boolean status) {
        LocalDateTime receive = null;
        if (status) {
            receive = LocalDateTime.now();
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss");
            String rectime=timeStamp.format(df);
            setReceivedTime(rectime);
        }
        this.Status = status;
    }

    public String getTimeStamp() {
        return sendtime;
    }


   /* public void setReceiver(Person receiver) {
        this.Receiver = receiver;
    }*/
    public void setContent(String content) {
        this.Content = content;
    }

    public void statusIs() {
        if(this.Status){
            System.out.println("seen");
        }else{
            System.out.println("not seen");}
    }



    public void SmsDisplay(){
        System.out.println("ID: " + this.Id);
        System.out.println("sending time: "+getTimeStamp());
        System.out.println(Sender);
        System.out.println(Receiver);
        System.out.println("Message: " + this.Content);
        if(getStatus()) {
            System.out.println("receiving time: "+receivedTime);
        }
        System.out.print("Status: ");
        statusIs();
        System.out.println("\n");

    }




}
