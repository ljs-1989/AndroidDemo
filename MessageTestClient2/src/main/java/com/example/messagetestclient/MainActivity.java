package com.example.messagetestclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Messenger messenger;
    public static final int FEED_BACK_TO_CLIENT = 2;
    private boolean mBound = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.findViewById(R.id.hello_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(messenger!=null&& mBound){
                    try {
                        //  Message msg =  Message.obtain(null,2,new Messenger(clientHandler));
                          Message msg =  Message.obtain(null,FEED_BACK_TO_CLIENT);
                        msg.replyTo = getReplyMessanger;
                        messenger.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
            mBound = true;
            Log.d("messenger","messenger has bind");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
            messenger = null;
            Log.d("messenger","messenger has unbind");
        }
    };
    private Messenger getReplyMessanger = new Messenger(clientHandler);

     public static Handler clientHandler = new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case 3:
               Log.e("reply",msg.getData().getString("reply"));
                break;
            case 4:
                break;

        }
    }
};
    @Override
    protected void onStop() {
        super.onStop();
       if(mBound){
            unbindService(mConnection);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent serviceIntent = new Intent("message.test.msgservice");
        serviceIntent.putExtra("client_messenger",new Messenger(clientHandler));
        serviceIntent.setPackage("com.example.messagetest");
        bindService(serviceIntent,mConnection, Context.BIND_AUTO_CREATE);
    }
}
