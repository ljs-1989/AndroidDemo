package com.example.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by MZ on 2016/7/15.
 */
public class RemoteService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

   private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {
       @Override
       public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

       }

       @Override
       public String getPid() throws RemoteException {

           return "进程PID："+Process.myPid();
       }
   };
}
