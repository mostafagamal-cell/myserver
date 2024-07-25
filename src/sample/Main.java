package sample;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
    }


    public static void main(String[] args) {
        launch(args);
    }


}
