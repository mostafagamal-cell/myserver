package sample;



import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;



class DAO {
 public     Connection con;
    public DAO() throws SQLException {
        try {
            DriverManager.registerDriver(new Driver());
            String mycon = "jdbc:mysql://localhost:3306/players";
            con = DriverManager.getConnection(mycon, "root", "root");
        }catch (Exception e) {
            throw e;
        }

    }

    public synchronized void signup(String name, String pass , String email) throws Exception {
        try {
            checkindatabase(email,name);
            insert(name,pass,email);
        }catch (Exception exception){

            throw exception;
        }
    }
    public synchronized void signin(String name, String pass) throws Exception {
        try {
            check(pass,name);
        }catch (Exception exception){
            throw exception;
        }
    }
    public synchronized void insert(String user, String pass, String email) throws Exception {
        try {
            con.createStatement().executeUpdate("Insert INTO players VALUES ('"+user+"','"+pass+"','"+email+"',"+0+")");

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw  new Exception("user name or email exists");
        }
    }
    public synchronized void addscore(String user) throws Exception {
        try {
            String updateScoreSQL = "UPDATE players SET score = score + 1 WHERE username ='"+user+"'";
            int st=    con.createStatement().executeUpdate(updateScoreSQL);
            if (st==0)throw new Exception("Not found");
        }catch (Exception e)
        {
            System.out.println("bbbbbbbbbbbbb");

            throw e;
        }
    }
     public synchronized int getscore(String user) throws Exception {
         try {
             String updateScoreSQL = "SELECT score FROM players   WHERE username ='"+user+"'";
             ResultSet set=con.createStatement().executeQuery(updateScoreSQL);
             if (set.next())
                 return set.getInt(1);
         }catch (Exception e)
         {
             System.out.println("hhhhhhhhhhhhhhhhhhhhhh");
             throw e;
         }
         return -1;
     }

    public synchronized void checkindatabase(String email,String user) throws Exception {
        try {
            String query = "SELECT COUNT(*) FROM players WHERE email = ? OR username = ?";
            PreparedStatement preparedStatement= con.prepareStatement(query);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,user);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);

                if (count > 0) {
                    System.out.println(count);
                    throw new Exception("email or user exists");
                }
            }
        }catch (Exception e){
            throw e;
        }
    }

    public synchronized void check(String pass,String user) throws Exception {
        try {
            checkuser(user);
            checkpass(pass, user);
        }catch (Exception e){
            throw e;
        }
    }


    private void checkpass(String pass, String user) throws Exception {
        String query2 = "SELECT COUNT(*) FROM players WHERE  username = ? AND password = ?";
        PreparedStatement preparedStatement2= con.prepareStatement(query2);
        preparedStatement2.setString(1, user);
        preparedStatement2.setString(2, pass);
        ResultSet resultSet2 = preparedStatement2.executeQuery();
        // Check if the email and username exist
        if (resultSet2.next()) {
            int count = resultSet2.getInt(1);
            if (count <= 0) {
                throw new Exception("password  is not correct");
            }
        }
    }

    private void checkuser(String user) throws Exception {
        String query = "SELECT COUNT(*) FROM players WHERE  username = ?";
        PreparedStatement preparedStatement= con.prepareStatement(query);
        preparedStatement.setString(1, user);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            if (count <= 0) {
                throw new Exception("username  does not exists");
            }
        }
    }
    public   synchronized ArrayList<User> getallsuers() throws Exception {
        String query = "SELECT * FROM players";
        PreparedStatement preparedStatement= con.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<User>uSers=new ArrayList<>();
        while (resultSet.next()){
            User n= new User();
            n.name=resultSet.getString(1);
            n.email=resultSet.getString(3);
            n.Score=resultSet.getInt(4);
            uSers.add(n);
        }
        return uSers;
    }
    public synchronized Integer numberofpalyers() {
        try {
            String query = "SELECT COUNT(*) FROM players ";
            PreparedStatement preparedStatement= con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            int count =0;
            resultSet.next();
            count = resultSet.getInt(1);
            System.out.println("cout" +count);
            return count;
        }catch (Exception e){
            return 0;

        }
    }

}
