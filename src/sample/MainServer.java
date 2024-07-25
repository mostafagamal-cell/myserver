package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Vector;

class MainServer extends  Thread{
    public    ServerSocket  serverSocket;
    public static Vector<Server>servers=new Vector<>();
    public  static IntegerProperty size = new SimpleIntegerProperty();

    public MainServer() throws IOException {
        serverSocket = new ServerSocket(8000);
        start();
    }
    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                if (!serverSocket.isClosed())
                  new Server(serverSocket);
            } catch (IOException e) {
                break;
            }
        }
    }
}
