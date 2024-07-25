package sample;


import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static sample.Main.dao;
import static sample.View.inGameCount;
import static sample.View.offlineCount;
import static sample.View.onlineCount;
import static sample.View.userList;

class Server extends Thread {
    NetWork netWork=new NetWork(dao,this);
    private Socket socket;
    public DataInputStream dataInputStream;
    public DataOutputStream dataOutputStream;
    private ServerSocket serverSocket;
    User user=new User();
    public Server(ServerSocket serverSocket) throws IOException {
        this.serverSocket = serverSocket;
        if (!serverSocket.isClosed()) {
            socket = serverSocket.accept();
            System.out.println("created new socket");
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            start();
        }
    }

    @Override
    public void run() {
        super.run();
        while (socket.isConnected()) {
            try {
                user.online=true;
                netWork.start();
            } catch (Exception e) {
                Platform.runLater(() -> updateUIForUser(user.name, 0));
                MainServer.servers.remove(this);
               if (user.name!=null) {
                   offlineCount += 1;
                   onlineCount -= 1;
               }
                if (user.Status==2)
                {
                    try {
                        netWork.sendwinng(user.name,user.Oppentment);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    inGameCount-=2;
                }
                System.out.println("logout "+user.name);
                try {
                    NetWork.sendlistplayer();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                closeConnections();
                interrupt();
                break; // Exit the loop if there's an error
            }
        }
    }



    private void updateUIForUser(String username, int status) {
        Platform.runLater(() -> {
            for (User user : userList) {
                if (user.name.equals(username)) {
                    user.Status = status;
                    userList.set(userList.indexOf(user), user); // Trigger ListView update
                    break;
                }
            }
        });
    }

    private void closeConnections() {
        try {
            if (dataInputStream != null) {
                dataInputStream.close();
            }
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
