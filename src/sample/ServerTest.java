package sample;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static sample.types.createRequestToPlay;
import static sample.types.createSingin;

class ServerTest {
    Socket clint;
    DataInputStream inputStream;
    DataOutputStream outputStream;
    Socket clint2;
    DataInputStream inputStream2;
    DataOutputStream outputStream2;
    Socket clint3;
    DataInputStream inputStream3;
    DataOutputStream outputStream3;
    JSONObject object;

    @org.junit.jupiter.api.BeforeEach
    void setUp() throws IOException {
        clint= new Socket("127.0.0.1",8000);
        inputStream=new DataInputStream(clint.getInputStream());
        outputStream=new DataOutputStream(clint.getOutputStream());
        clint2= new Socket("127.0.0.1",8000);
        inputStream2=new DataInputStream(clint2.getInputStream());
        outputStream2=new DataOutputStream(clint2.getOutputStream());
        clint3= new Socket("127.0.0.1",8000);
        inputStream3=new DataInputStream(clint3.getInputStream());
        outputStream3=new DataOutputStream(clint3.getOutputStream());
        object =new JSONObject();

    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() throws IOException {
        inputStream.close();
        outputStream.close();
        clint.close();
        inputStream2.close();
        outputStream2.close();
        clint2.close();
        inputStream3.close();
        outputStream3.close();
        clint3.close();
    }
    //test sign in
    @Test
    public void signin_test() throws Exception {
        object.put(sample.types.type, sample.types.SignIn);
        object.put(sample.types.Username,"mostafa");
        object.put(sample.types.Password,"0");

        outputStream.writeUTF(object.toString());
        System.out.println(inputStream.readUTF());

        outputStream2.writeUTF(object.toString());
        System.out.println(inputStream2.readUTF());
        Thread.sleep(5000);
    }
    @Test
    public void update_list() throws IOException, InterruptedException {
        object.put(sample.types.type, sample.types.SignIn);
        object.put(sample.types.Username,"mostafa");
        object.put(sample.types.Password,"0");
        outputStream.writeUTF(object.toString());
        System.out.println(inputStream.readUTF());
        object=new JSONObject();

        object.put(sample.types.type, sample.types.SignIn);
        object.put(sample.types.Username,"mostafaff");
        object.put(sample.types.Password,"0");
        outputStream3.writeUTF(object.toString());
        System.out.println(inputStream3.readUTF());
        object=new JSONObject();

        object.put(sample.types.type, sample.types.SignIn);
        object.put(sample.types.Username,"gamal");
        object.put(sample.types.Password,"0");
        outputStream2.writeUTF(object.toString());
        System.out.println(inputStream2.readUTF());

        JSONObject objec=createRequestToPlay("gamal");
        outputStream.writeUTF(objec.toString());

                System.out.println("mostafa -->  "+inputStream.readUTF() );


                System.out.println("gamal -->  "+inputStream2.readUTF() );

                System.out.println("mostafaff -->  "+inputStream3.readUTF() );

        System.out.println("mostafa -->  "+inputStream.readUTF() );


        System.out.println("gamal -->  "+inputStream2.readUTF() );

        System.out.println("mostafaff -->  "+inputStream3.readUTF() );
        System.out.println("mostafaff -->  "+inputStream3.readUTF() );

        Thread.sleep(5000);





//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true){
//
//                }
//            }
//        }).start();
//        String x=inputStream.readUTF();
//        System.out.println(x);
//         updateList(x);
//         x=inputStream.readUTF();
//        updateList(x);



//
//
//          System.out.println("mostafa"+inputStream.readUTF());
//          System.out.println("mostafa"+inputStream.readUTF());
//          clint2.close();
//
//          System.out.println("mostafa"+inputStream.readUTF());
//          System.out.println("mostafa"+inputStream.readUTF());




//         System.out.println("mostafa"+inputStream.readUTF());
//         System.out.println("mostafa"+inputStream.readUTF());
//         System.out.println("mostafa"+inputStream.readUTF());

    //     System.out.println("mostafa"+inputStream.readUTF());
      //   System.out.println("mostafafff"+inputStream3.readUTF());


//        System.out.println(inputStream.readUTF());
//        System.out.println(inputStream2.readUTF());
//        inputStream.close();
//        outputStream.close();
//        clint.close();
     //   System.out.println(inputStream2.readUTF());
     //   System.out.println(inputStream3.readUTF());



        //   String x=inputStream2.readUTF();
       //   System.out.println(x);




    }
    @Test
    public void signin_with_exists_sigin_test() throws Exception {
        object.put(sample.types.type, sample.types.SignIn);
        object.put(sample.types.Username,"mostafa");
        object.put(sample.types.Password,"0");
        outputStream.writeUTF(object.toString());
        System.out.println(inputStream.readUTF());
        outputStream2.writeUTF(object.toString());
        System.out.println(inputStream2.readUTF());
        Thread.sleep(7000 );
    }
    @Test
    public void signup_test() throws IOException, InterruptedException {
        object.put(sample.types.type, sample.types.SignUp);
        object.put(sample.types.Username,"modsffzzafaeffef");
        object.put(sample.types.Email,"eaeaasdff77e@xx.com");
        object.put(sample.types.Password,"0");
        outputStream.writeUTF(object.toString());
        System.out.println(inputStream.readUTF());
        Thread.sleep(3000);
    }

//        @Test
//    public void test1() throws IOException {
//        object.put(sample.types.type, sample.types.SignIn);
//        object.put(sample.types.Username,"dddd22");
//        object.put(sample.types.Password,"Dee33&&&");
//        outputStream2.writeUTF(object.toString());
//        System.out.println(inputStream2.readUTF());
//        object =new JSONObject();
//        object.put(sample.types.Username,"ddddddde");
//        object.put(sample.types.Password,"Add3344&&");
//        object.put(sample.types.type, sample.types.SignIn);
//        outputStream.writeUTF(object.toString());
//        System.out.println(inputStream.readUTF());
//
//        object =new JSONObject();
//        object.put(sample.types.type, sample.types.RequestToPlay);
//        object.put(sample.types.Opponent,"dddd22");
//        outputStream.writeUTF(object.toString());
//        System.out.println(inputStream2.readUTF());
//        inputStream.close();
//        outputStream.close();
//        clint.close();
//
//        object =new JSONObject();
//        object.put(sample.types.type, sample.types.RequestToPlayResponse);
//        object.put(sample.types.Opponent,"ddddddde");
//        object.put(sample.types.Message, sample.types.Refuse);
//        outputStream2.writeUTF(object.toString());
//        System.out.println(inputStream2.readUTF());
//
//    }
    @Test
    public  void test_two_requests() throws  Exception{
        object=new JSONObject();
        object.put(sample.types.type, sample.types.SignIn);
        object.put(sample.types.Username,"mostafa");
        object.put(sample.types.Password,"0");
        outputStream.writeUTF(object.toString());
        System.out.println(inputStream.readUTF());
        object=new JSONObject();

        object.put(sample.types.type, sample.types.SignIn);
        object.put(sample.types.Username,"mostafaff");
        object.put(sample.types.Password,"0");
        outputStream3.writeUTF(object.toString());
        System.out.println(inputStream3.readUTF());
        System.out.println(inputStream.readUTF());
        object=new JSONObject();
        object.put(sample.types.type, sample.types.SignIn);
        object.put(sample.types.Username,"gamal");
        object.put(sample.types.Password,"0");
        outputStream2.writeUTF(object.toString());

        object = new JSONObject();
        object.put(sample.types.type, sample.types.RequestToPlay);
        object.put(sample.types.Opponent,"gamal");
        outputStream.writeUTF(object.toString());


        //mostafaff

    }

    @Test
    public void test_accept_invention() throws  Exception{

        object=new JSONObject();
        object= sample.types.createSingin("mostafa","0");
        outputStream.writeUTF(object.toString());
        System.out.println(inputStream.readUTF());

        object=new JSONObject();
        object= sample.types.createSingin("mostafaff","0");
        outputStream3.writeUTF(object.toString());
        System.out.println(inputStream3.readUTF());
        object=new JSONObject();
        object= sample.types.createSingin("gamal","0");
        outputStream2.writeUTF(object.toString());
        System.out.println(inputStream2.readUTF());
        Thread.sleep(3000);
        object = new JSONObject();
        object.put(sample.types.type, sample.types.RequestToPlay);
        object.put(sample.types.Opponent,"gamal");
        outputStream.writeUTF(object.toString());
        System.out.println(inputStream2.readUTF());
        System.out.println(inputStream2.readUTF());
        Thread.sleep(3000);

        object = new JSONObject();
        object.put(sample.types.type, sample.types.RequestToPlayResponse);
        object.put(sample.types.Message, sample.types.Refuse);
        outputStream2.writeUTF(object.toString());
        System.out.println(inputStream.readUTF());
        Thread.sleep(3000);



        object = new JSONObject();
        object.put(sample.types.type, sample.types.RequestToPlay);
        object.put(sample.types.Opponent,"mostafa");
        outputStream2.writeUTF(object.toString());
        System.out.println(inputStream.readUTF());
      //  Thread.sleep(3000);

        object = new JSONObject();
        object.put(sample.types.type, sample.types.RequestToPlay);
        object.put(sample.types.Opponent,"mostafa");
        outputStream3.writeUTF(object.toString());
        Thread.sleep(3000);

        System.out.println("mostafaff-->"+inputStream3.readUTF());

        object = new JSONObject();
        object.put(sample.types.type, sample.types.RequestToPlay);
        object.put(sample.types.Opponent,"gamal");
        outputStream3.writeUTF(object.toString());

        System.out.println("mostafaff-->"+inputStream3.readUTF());
        System.out.println("mostafaff-->"+inputStream3.readUTF());

//        Thread.sleep(3000);

        object = new JSONObject();


        object.put(sample.types.type, sample.types.RequestToPlayResponse);
        object.put(sample.types.Message, sample.types.Accept);
        outputStream.writeUTF(object.toString());
        System.out.println(inputStream2.readUTF());

        object = new JSONObject();
        object.put(sample.types.type, sample.types.RequestToPlay);
        object.put(sample.types.Opponent,"gamal");
        outputStream3.writeUTF(object.toString());

        System.out.println(inputStream3.readUTF());

    }
    @Test
    void test_game() throws Exception {
       JSONObject object= createSingin("mostafa","0");
       outputStream.writeUTF(object.toString());
       object= createSingin("gamal","0");

       outputStream2.writeUTF(object.toString());
        object= createSingin("z","0");
        outputStream3.writeUTF(object.toString());
        System.out.println("mostafa  -->>  "+inputStream.readUTF());
        System.out.println("gamal  -->>  "+inputStream2.readUTF());
         object =creatRequest("mostafa");
        outputStream2.writeUTF(object.toString());
        object=createAccept();
        outputStream2.writeUTF(object.toString());
        System.out.println("mostafa  -->>  "+inputStream.readUTF());
        System.out.println("gamal  -->>  "+inputStream2.readUTF());
        System.out.println("gamal  -->>  "+inputStream2.readUTF());
        System.out.println("gamal  -->>  "+inputStream2.readUTF());
        System.out.println("gamal  -->>  "+inputStream2.readUTF());
        outputStream.writeUTF(createMove("00X").toString());
        System.out.println("gamal 1 -->>  "+inputStream2.readUTF());
     //   System.out.println("gamal 2-->>  "+inputStream2.readUTF());
        object=createMove("02O");
        outputStream2.writeUTF(object.toString());
        System.out.println("mostafa 1 -->>  "+inputStream.readUTF());
        System.out.println("mostafa  2-->>  "+inputStream.readUTF());
        System.out.println("mostafa  3-->>  "+inputStream.readUTF());
        System.out.println("mostafa  4-->>  "+inputStream.readUTF());
        Thread.sleep(3000);
        outputStream2.close();
        inputStream2.close();
        clint2.close();
        Thread.sleep(3000);

        // ----> Erorr
        // ----> Messges


        System.out.println("mostafa 1 -->>  "+inputStream.readUTF());
        System.out.println("mostafa 2 -->>  "+inputStream.readUTF());
        System.out.println("mostafa 3 -->>  "+inputStream.readUTF());
        System.out.println("mostafa 4 -->>  "+inputStream.readUTF());
        System.out.println("mostafa 5 -->>  "+inputStream.readUTF());

        Thread.sleep(3000);

        //System.out.println("mostafa  -->>  "+inputStream.readUTF());
//        System.out.println("mostafa  -->>  "+inputStream.readUTF());
//        System.out.println("mostafa  -->>  "+inputStream.readUTF());

       // Thread.sleep(6000);
    }
    public JSONObject creatRequest(String name){
        JSONObject object = new JSONObject();
        object.put(types.type,types.RequestToPlay);
        object.put(types.Opponent,name);
        return object;
    }
    public JSONObject createAccept(){
        JSONObject object = new JSONObject();
        object.put(types.type,types.RequestToPlayResponse);
        object.put(types.Message,types.Accept);
        return object;
    }
    public JSONObject createMove(String move){
        JSONObject object = new JSONObject();
        object.put(types.type,types.move);
        object.put(types.point,move);
        return object;
    }
}