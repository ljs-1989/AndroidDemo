package com.example.messagetest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.LogPrinter;
import android.widget.Toast;

/**
 * Created by MZ on 2016/7/14.
 */
public class MessagerService extends Service {
    public static final int SAY_HELLO = 0;
    public static final int SAY_HELLO_FROM_SOFTWARE = 1;
    public static final int FEED_BACK_TO_CLIENT = 2;
    public static final int CALL_OTHER_CLIENT = 3;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        //向客户端提供binder
        return mMessager.getBinder();
    }
;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
          switch (msg.what){
              case SAY_HELLO:
                  Toast.makeText(getApplicationContext(), "hello!", Toast.LENGTH_SHORT).show();

                  break;
              case SAY_HELLO_FROM_SOFTWARE:
                  Toast.makeText(getApplicationContext(), "hello world! success", Toast.LENGTH_SHORT).show();
                  Log.e("messenger","hello world! success");
                  break;
              case FEED_BACK_TO_CLIENT:
                  try {
                      Toast.makeText(getApplicationContext(), "call service success", Toast.LENGTH_SHORT).show();
                      Messenger clientMessanger = msg.replyTo;
                      Message relpyMes = Message.obtain(null,3);
                      Bundle bundle = new Bundle();
                      bundle.putString("reply","嗯，你的消息我已收到！");
                      relpyMes.setData(bundle);
                      clientMessanger.send(relpyMes);
                  } catch (RemoteException e) {
                      e.printStackTrace();
                  }
                  break;
              case CALL_OTHER_CLIENT:
                  Toast.makeText(getApplicationContext(), "call other client success!", Toast.LENGTH_SHORT).show();
                  break;
              default:
                  Toast.makeText(getApplicationContext(), "no no no!", Toast.LENGTH_SHORT).show();

                  break;
          }
        }
    };


    /**
     * 向客户端公布的用于向IncomingHandler发送信息的Messager
     */

    final  Messenger mMessager = new Messenger(mHandler);
}
