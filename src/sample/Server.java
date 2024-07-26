package sample;


import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static sample.Main.dao;
import static sample.MainServer.allUsers;
import static sample.View.inGameCount;
import static sample.View.offlineCount;
import static sample.View.onlineCount;


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
                MainServer.servers.remove(this);
                System.out.println(this.user.name+"  logout avialable "+ offlineCount+"  av"+onlineCount );
                onlineCount=MainServer.servers.size();
                if (user.name!=null) {
                       offlineCount += 1;
                    for (int i = 0; i < allUsers.size(); i++) {
                        if (allUsers.get(i).name.equals(user.name)){
                            allUsers.get(i).Status=-1;
                            break;
                        }
                    }
                }

                if (user.Status==2)
                {
                    try {
                        netWork.sendwinng();
                    } catch (Exception ioException) {
                        System.out.println("eeeeeeeeeeeee"+ioException.getMessage());
                    }
                        if (inGameCount>1)
                            inGameCount-=2;
                        else
                            inGameCount=0;
                }

                //     System.out.println("logout "+user.name);

                try {
                    NetWork.sendlistplayer();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                this.user.Oppentment=null;
                this.user.Status=0;
                closeConnections();
                interrupt();
                break; // Exit the loop if there's an error
            }
        }
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
