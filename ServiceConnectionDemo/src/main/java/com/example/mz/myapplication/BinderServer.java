package com.example.mz.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

/**
 * Created by MZ on 2016/7/14.
 */
public class BinderServer extends Service{

    private Binder mBinder = new LocalBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Service life","onCreate///////");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.d("Service life","onStart///////");
        super.onStart(intent, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("Service life","onUnbind///////");
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Service life","onStartCommand///////");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("Service life","onDestroy///////");
        super.onDestroy();
    }

    public int getRandomNumber(){
  return new Random().nextInt(100);
};
    public class LocalBinder extends Binder {
        BinderServer getService(){
            return  BinderServer.this;
        }
    }
}
