package sample;

import javafx.concurrent.Task;

import static sample.View.*;

class MyTask extends Task<MainServer> {
    @Override
    protected MainServer call() throws Exception {
        return new MainServer();
    }
}

class MyTask2 extends Task<String> {
    @Override
    protected String call()  {
        while (true){
         //   System.out.println(offlineCount+" "+onlineCount+" "+inGameCount);
            updateMessage(offlineCount+" "+onlineCount+" "+inGameCount);
    }}
}



