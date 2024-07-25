package sample;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class testlogin {
    ServerSocket serverSocket;
    Socket socket;
    DataInputStream inputStream;
    DataOutputStream outputStream;
    @BeforeEach
    void start() throws IOException {
        serverSocket= new ServerSocket(8000);
    }
    @AfterEach
    void end() throws IOException {
        inputStream.close();
        outputStream.close();
        socket.close();
        serverSocket.close();
    }
    @Test
    void login() throws IOException {
        socket=serverSocket.accept();
        inputStream=new DataInputStream(socket.getInputStream());
        outputStream=new DataOutputStream(socket.getOutputStream());
        String message =inputStream.readUTF();
        JSONObject object= (JSONObject) JSONValue.parse(message);

        if ( object.get(sample.types.type).equals(sample.types.SignIn)){
         String name=(String)object.get(sample.types.Username);
         String pass=(String)object.get(sample.types.Password);
         if (name.equals("mostafa")&&pass.equals("0")){
             object=new JSONObject();
             object.put(sample.types.type, sample.types.Success);
             outputStream.writeUTF(object.toString());
         }else{
             object=new JSONObject();
             object.put(sample.types.type, sample.types.Error);
             object.put(sample.types.Message,"not found in database");
             outputStream.writeUTF(object.toString());

         }
        }
    }
}
