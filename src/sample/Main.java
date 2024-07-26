package sample;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {
  public  static   DAO dao;

    static {
        try {
            dao = new DAO();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

   public static MainServer s;

    @Override
    public void start(Stage primaryStage) throws Exception{


        Parent root = new View(dao);
        primaryStage.setTitle("Hello World");
        Scene scene=new Scene(root, 1200, 700);
        primaryStage.setOnCloseRequest(event -> {
            try {
                dao.con.close();
                if (s!=null)
                s.serverSocket.close();
            } catch (Exception throwables) {
                System.out.println(throwables.getMessage());
            }

        });
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e->{
            System.out.println("ttttttttttttttt");
            for (int i = 0; i < MainServer.servers.size(); i++) {
                try {
                    JSONObject object  =new JSONObject();
                    object.put(types.type,types.teardown);
                    MainServer.servers.get(i).dataOutputStream.writeUTF(object.toString());
                    MainServer.servers.get(i).dataInputStream.close();
                    MainServer.servers.get(i).dataOutputStream.close();
                    MainServer.servers.get(i).socket.close();

                }catch (Exception ee){}
            }
            try {
                dao.con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                if (MainServer.serverSocket!=null)
                MainServer.serverSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }


}
