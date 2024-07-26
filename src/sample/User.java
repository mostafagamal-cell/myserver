package sample;

 class User {
    public   String name;
    public String email;
    public  Integer Status=-1;
    public String Oppentment;
    boolean online;
    Integer Score;

    @Override
    public String toString() {
       return "User{" +
               "name='" + name + '\'' +
               ", email='" + email + '\'' +
               ", Status=" + Status +
               ", Oppentment='" + Oppentment + '\'' +
               ", online=" + online +
               ", Score=" + Score +
               '}';
    }
 }
