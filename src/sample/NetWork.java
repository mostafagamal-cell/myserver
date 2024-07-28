package sample;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import static sample.View.*;
import org.json.simple.JSONValue;
import static sample.MainServer.*;

import java.io.IOException;
import java.util.ArrayList;

public class NetWork {
    DAO dao;
    Server server;
    public NetWork(DAO dao, Server server){
        this.server=server;
        this.dao=dao;
    }

    public void start() throws Exception {
            JSONObject object1;
            // type --> withdraw

            String message=server.dataInputStream.readUTF();
            System.out.println(message);
            object1=(JSONObject) JSONValue.parse(message);
        if (types.SignIn.equals(object1.get(types.type))) {
            RequestSignIng(object1);
        }else if(types.RequestToPlay.equals(object1.get(types.type))){
            RequestToPlay(object1); }
        else if(types.RequestToPlayResponse.equals(object1.get(types.type))){
            ResposeToPlay(object1);
        }
        else if(types.SignUp.equals(object1.get(types.type))){
            RequesSignUp(object1);
        }else if(object1.get(types.type).equals(types.move))RequestToMove(object1);
            else if (object1.get(types.type).equals(types.IWin)){
                win();
            } else if (object1.get(types.type).equals("List")){
                System.out.println(server.user.name+" ask to up date list");
                sendlistplayer();
            }
            else if (object1.get(types.type).equals(types.draw)){
                draw();
            }


    }
    public  void win() throws Exception {
        System.out.println(server.user.name+ " win "+server.user.Oppentment+" lose");
        dao.addscore(this.server.user.name,3);
        JSONObject object = new JSONObject();
        object.put(types.type,types.EndGame);
        object.put(types.Message,types.YouWin);
        this.server.dataOutputStream.writeUTF(object.toString());
        this.server.user.Status=0;
        update(this.server.user.name,0);
        for (int i = 0; i < servers.size(); i++) {
                if (this.server.user.Oppentment.equals(servers.get(i).user.name)){
                    object = new JSONObject();
                    object.put(types.type,types.EndGame);
                    object.put(types.Message,types.YouLose);
                    servers.get(i).dataOutputStream.writeUTF(object.toString());
                    servers.get(i).user.Status=0;
                    servers.get(i).user.Oppentment=null;

                    update(servers.get(i).user.name,0);
                    update(this.server.user.name,0);
                    // -1 offline
                    // 0  online
                    // 1 request
                    // 2 in game
                }
        }
        server.user.Oppentment=null;
        onlineCount+=2;
        inGameCount-=2;
        //  sendlistplayer();
    }
    public  void draw() throws Exception {
        dao.addscore(this.server.user.name,1);
        dao.addscore(this.server.user.Oppentment,1);
        JSONObject object = new JSONObject();
        object.put(types.type,types.EndGame);
        object.put(types.Message,types.draw);
        this.server.user.Status=0;
        update(this.server.user.Oppentment,0);

        update(this.server.user.name,0);
        this.server.dataOutputStream.writeUTF(object.toString());
        for (int i = 0; i < servers.size(); i++) {
            if (this.server.user.Oppentment.equals(servers.get(i).user.name)){
                object.put(types.type,types.EndGame);
                object.put(types.Message,types.draw);
                servers.get(i).dataOutputStream.writeUTF(object.toString());
                servers.get(i).user.Status=0;
                update(servers.get(i).user.name,0);
            }
        }
        server.user.Oppentment=null;
        onlineCount+=2;
        inGameCount-=2;
        //sendlistplayer();

    }
    public void RequesSignUp(JSONObject object) throws IOException {
        try {
            String name=  (String) object.get(types.Username);
            String email=  (String) object.get(types.Email);
            String pass=  (String) object.get(types.Password);
            dao.signup(name,pass,email);
            User u = new User();
            u.name=name;
            u.email=email;
            u.Score=0;
            allUsers.add(u);
            object=new JSONObject();
            object.put(types.type,types.Success);
            server.dataOutputStream.writeUTF(object.toString());
            offlineCount+=1;
            System.out.println(object.toString());
        }catch (Exception e){
            System.out.println("eeeeeeeeeee- >>>>"+e.getMessage());
            ErrorRespose(e);
        }
    }
    public void RequestSignIng(JSONObject object) throws Exception{
        try {
            String Name=(String) object.get(types.Username);
            String PassWord=(String) object.get(types.Password);
            dao.signin(Name,PassWord);
            for (int i = 0; i < servers.size(); i++) {
                if (servers.get(i).user.name.equals(Name)){
                    throw  new Exception("user already exists");
                }
            }
            this.server.user.name=Name;
//            for (int i = 0; i < allUsers.size(); i++) {
//                if (allUsers.get(i).name.equals(Name)){
//                    allUsers.get(i).Status=0;
//                    offlineCount-=1;
//
//                }
//            }
            server.user.Status=0;
            servers.add(server);
            System.out.println(" name login "+server.user.name);
            JSONObject object1= new JSONObject();
            object1.put(types.type,types.Success);
            server.dataOutputStream.writeUTF(object1.toString());
           // sendlistplayer();
            update(this.server.user.name,0);
            onlineCount++;
            offlineCount--;
        }catch (Exception e){
            ErrorRespose(e);
        }
    }
    public void update(String s,int x){
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).name.equals(s)){
                allUsers.get(i).Status=x;
            }
        }
    }
    public  void withdraw() throws Exception {
       String opp= this.server.user.Oppentment;
        for (int i = 0; i < servers.size(); i++) {
            if (servers.get(i).user.equals(opp)){
                JSONObject object = new JSONObject();
                object.put(types.type,types.EndGame);
                object.put(types.Message,types.YouWin);
                dao.addscore(opp,3);
                servers.get(i).dataOutputStream.writeUTF(object.toString());
                servers.get(i).user.Status=0;
                servers.get(i).user.Oppentment=null;
                this.server.user.Status=0;
                this.server.user.Oppentment=null;
                update(opp,0);
                update(servers.get(i).user.name,0);
            }
        }
    }

    public void ErrorRespose(Exception e) throws IOException {
        JSONObject object=new JSONObject();
        object.put(types.type,types.Error);
        object.put(types.Message,e.getMessage());
        server.dataOutputStream.writeUTF(object.toString());
    }
    public void RequestToPlay(JSONObject object) throws Exception {
        String Opponent=(String)  object.get(types.Opponent);
        server.user.Oppentment=(String) object.get(types.Opponent);
        server.user.Status=1;

        for (int i = 0; i < servers.size(); i++) {
            String ss=servers.get(i).user.name;
            if (servers.get(i).user.name.equals(Opponent)){
                System.out.println(servers.get(i).user);
                if (servers.get(i).user.Status==1){
                    System.out.println("----------sss"+servers.get(i).user);
                    object = responseBusy(types.Currentbusy);
                    server.user.Status=0;
                    server.user.Oppentment=null;
                    update(server.user.name,0);
                    server.dataOutputStream.writeUTF(object.toString());

                    return;
                }else if (servers.get(i).user.Status==2){
                     object = responseBusy(types.CurrentInGame);
                     server.user.Status=0;
                     server.user.Oppentment=null;
                     server.dataOutputStream.writeUTF(object.toString());
                    update(server.user.name,0);
                    return;
                }else if (servers.get(i).user.Status==0){
                    System.out.println("send request from "+ server.user.name+" to "+ servers.get(i).user.name );
                    JSONObject object1 = new JSONObject();
                    server.user.Oppentment=servers.get(i).user.name;
                    object1.put(types.type, types.RequestToPlay);
                    servers.get(i).user.Status = 1;
                    servers.get(i).user.Oppentment= server.user.name;
                    object1.put(types.Opponent, server.user.name);
                    servers.get(i).dataOutputStream.writeUTF(object1.toString());
                    update(server.user.name,1);

                    return;
                }
            }
        }
    }

    private JSONObject responseBusy(String currentbusy) {
        JSONObject object;
        object = new JSONObject();
        object.put(types.type, types.RequestToPlayResponse);
        object.put(types.Message, currentbusy);
        return object;
    }


    // type -->  Iwin



    // type -->  EndofGame
    // Message  -- > YouWin   , YouLOse

    public  synchronized   void  sendlistplayer()throws Exception{
        System.out.println("Sended");
            JSONArray array = new JSONArray();
            for (int j = 0; j < allUsers.size(); j++) {
                if (allUsers.get(j).name.equals(this.server.user.name))
                    continue;
                    JSONObject object = new JSONObject();
                    object.put(types.Username, allUsers.get(j).name);
                    object.put(types.pscore,allUsers.get(j).Score);
                    object.put(types.State, allUsers.get(j).Status);
                    array.add(object);
            }
            JSONObject object2 = new JSONObject();
                try {
                    object2.put(types.type, types.UpdateList);
                    object2.put(types.List, array);
                    System.out.println(" ----->"+array);
                    this.server.dataOutputStream.writeUTF(object2.toString());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }



    }

    public  void  ResposeToPlay(JSONObject object) throws Exception {
        System.out.println("-->>>>>>>>>>>>>>>"+object.toString());
        object.put(types.type,types.RequestToPlayResponse);
        for (int i = 0; i < servers.size(); i++) {
            if (servers.get(i).user.name.equals(server.user.Oppentment)){
                if (object.get(types.Message).equals(types.Accept)){
                    server.user.Status=2;
                    servers.get(i).user.Oppentment=server.user.name;
                    servers.get(i).user.name=server.user.Oppentment;
                    servers.get(i).user.Status=2;

                    // type --> Iwin
                    //typ
                    update(servers.get(i).user.name,2);
                    update(this.server.user.name,2);
                    servers.get(i).dataOutputStream.writeUTF(object.toString());
                    inGameCount+=2;
                    onlineCount-=2;
                }else {
                    System.out.println("jjjjjjjjjjjjjjjjjjjj");
                    server.user.Oppentment=null;
                    server.user.Status=0;
                    servers.get(i).user.Oppentment=null;
                    servers.get(i).user.Status=0;
                    update(server.user.name,0);
                    update(servers.get(i).user.name,0);
                    servers.get(i).dataOutputStream.writeUTF(object.toString());
                }
                //sendlistplayer();
                return;
            }
        }
    }
            public void  sendwinng() throws Exception {
                        JSONObject object = new JSONObject();
                        object.put(types.type,types.EndGame);
                        object.put(types.Message,types.YouWin);
                for (int s = 0; s < servers.size(); s++) {
                    if (servers.get(s).user.name.equals(server.user.Oppentment)){
                        servers.get(s).dataOutputStream.writeUTF(object.toString());
                        servers.get(s).user.Oppentment=null;
                        servers.get(s).user.Status=0;
                        dao.addscore(servers.get(s).user.name,3);
                        System.out.println("------------>>>   "+servers.get(s).user.name);
                    }
                }
                onlineCount+=2;
                inGameCount-=2;

            }

    private void createStartJson(int score1, int score2, JSONObject jsonObject, String opscore, String pscore) {
        jsonObject.put(types.type, types.startGame);
        jsonObject.put(opscore, score1);
        jsonObject.put(pscore, score2);
    }

    public void RequestToMove(JSONObject object) throws IOException {
        // type -> move
        // message -> 00X  , 01O
        for (int i = 0; i < servers.size(); i++) {
            if (server.user.Oppentment.equals(servers.get(i).user.name)) {
                System.out.println(server.user.name + " -->  " + servers.get(i).user.name + " move : " + (String) object.get(types.type)+"------------>"+object.toString());
                servers.get(i).dataOutputStream.writeUTF(object.toString());
            }
        }
    }
}

class types {

    public static String teardown="teardown";
    public  static  String draw="draw";
    public  static  String withdraw="withdraw";
    public static String opscore="oscore";
    public static String pscore="pscore";
    public static String data = "data";
    public static  String startGame="startGame";
    public static String Offline="Offline";
    public static String type = "type";
    public static String move = "move";
    public static String RequestToPlay = "RequestToPlay";
    public static String RequestToPlayResponse = "RequestToPlayResponse";
    public  static String UpdateList="UpdateList";
    public static String Error = "Error";
    public static String Message = "Message";
    public static String YouWin = "YouWin";
    public static String IWin = "IWin";
    public static String EndGame = "EndGame";
    public static String Currentbusy = "Currentbusy";
    public static String CurrentInGame = "CurrentInGame";
    public static String List = "List";
    public static String YouLose = "YouLose";
    public static String SignIn = "SignIn";
    public static String SignUp = "SignUp";
    public static String Email = "Email";
    public static String Password = "Password";
    public static String Username = "Username";
    public static String Opponent = "Opponent";
    public static String Success = "Success";
    public static String Accept = "Accept";
    public static String Refuse = "Refuse";
    public static String point = "point";
    public static String State = "State";

    public static JSONObject createSingin(String username, String password){
        JSONObject object = new JSONObject();
        object.put(types.type,types.SignIn);
        object.put(types.Username,username);
        object.put(types.Password,password);
        return object;
    }

    public static JSONObject createRequestToPlay(String opponent){
        JSONObject object = new JSONObject();
        object.put(types.type,types.RequestToPlay);
        object.put(types.Opponent,opponent);
        return object;
    }
    public static ArrayList<User> updateList(String message){
        JSONObject object = (JSONObject) JSONValue.parse(message);
        JSONArray jsonArray=(JSONArray) JSONValue.parse(object.get(types.List).toString());
        ArrayList<User> users= new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            User user = new User();
            user.name=(String) ((JSONObject)jsonArray.get(i)).get(types.Username);
            Long xe=(Long) ((JSONObject)jsonArray.get(i)).get(types.State);
            Long sc=(Long) ((JSONObject)jsonArray.get(i)).get(types.pscore);
            user.Status= Math.toIntExact(xe);
            user.Score= Math.toIntExact(sc);
            users.add(user);
        }
        System.out.println(users.size());
return users;
    }
    public static JSONObject createsignup(String name , String password , String email){
        JSONObject object = new JSONObject();

        object.put(types.type,types.SignUp);
        object.put(types.Password,password);
        object.put(types.Email,email);
        object.put(types.Username,name);

        return object;
    }
    public static JSONObject createRequest(String opponent){
        JSONObject object =new JSONObject();
        object.put(types.type,types.RequestToPlay);
        object.put(types.Opponent,opponent);
        return object;
    }

    public static JSONObject responseAccept(){
        JSONObject object =new JSONObject();
        object.put(types.type,types.RequestToPlayResponse);
        object.put(types.Message,types.Accept);
        return object;
    }
    public static JSONObject responseRefuse(){
        JSONObject object =new JSONObject();
        object.put(types.type,types.RequestToPlayResponse);
        object.put(types.Message,types.Refuse);
        return object;
    }
    public static JSONObject sendMove( String move){
        JSONObject object =new JSONObject();
        object.put(types.type,types.move);
        object.put(types.Message,move);
        return object;
    }
//    public static  void parseMessage(String message)
//    {
//        JSONObject object= (JSONObject) JSONValue.parse(message);
//        if (object.get(types.type).equals()){
//
//        }
//
//    }
}
