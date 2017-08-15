package diary.cloud.com.socketdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by liujs on 2017/7/19.
 * 邮箱：725459481@qq.com
 */

public class TCPServerService extends Service {
    private boolean mIsServiceDestoryed = false;
    private String[] mDefinedMessages = new String[]{
      "你好啊，哈哈！","请问你叫什么名字？","今天北京天气不错啊，shy",
            "你知道吗？可是可以跟多人同时聊天的哦","讲个笑话吧：据说爱笑的人运气都不差，不知道真假!"
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsServiceDestoryed = true;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class TcpServer implements Runnable{
        private int port = 8888;
        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(port);

                while (!mIsServiceDestoryed){
                    Socket client = serverSocket.accept();
                   System.out.print("accept");
                    new Thread(){
                        public void run(){

                        }
                    }.start();
                }
            } catch (IOException e) {
             System.err.print("establish tcp server failed, port:8888");
                e.printStackTrace();
            }

        }
    }

    private void responseClient(Socket client) throws  IOException{
        //用于接受客户端消息
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //用于向客户端发送消息
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),true);
        out.println("欢迎来到聊天室！");
        while (mIsServiceDestoryed){

            
        }
    }
}
