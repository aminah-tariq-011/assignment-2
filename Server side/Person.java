public class Person {
    private String Name;
    private String Contact;
    private String Email;
    public Person(String name, String contact, String email) {
        this.Name = name;
        this.Contact = contact;
        this.Email = email;
    }
    public String getName() {
        return Name;
    }
    public String getContact() {
        return Contact;
    }
    public void setName(String name) {
        this.Name = name;
    }
    public void setContact(String contact) {
        this.Contact = contact;
    }
    public void setEmail(String email) {
        this.Email = email;
    }
    public String getEmail() {
        return Email;
    }
    public boolean isValidContactNumber(String number) {
        return number.length() == 11;
    }
    @Override
    public String toString() {
        return "Name: " + Name + "\nContact: " + Contact + "\nEmail: " + Email;
    }
}