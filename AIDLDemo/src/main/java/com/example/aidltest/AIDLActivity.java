package com.example.aidltest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telecom.ConnectionService;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class AIDLActivity extends AppCompatActivity {
   private boolean mBound;
    private IRemoteService mIRemoteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(mIRemoteService!=null){
                        String pid =    mIRemoteService.getPid();
                        Toast.makeText(AIDLActivity.this,pid,Toast.LENGTH_LONG).show();
                      }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private ServiceConnection connectionService = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIRemoteService = IRemoteService.Stub.asInterface(service);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIRemoteService = null;
            mBound = false;
            Log.d("AIDLActivity","onServiceDisconnected");
            Toast.makeText(AIDLActivity.this,"onServiceDisconnected",Toast.LENGTH_LONG).show();
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_aidl, menu);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mBound){
            unbindService(connectionService);
            mBound = false;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent =  new Intent("com.example.aidlservice");
        intent.setPackage("com.example.aidltest");
       // Intent intent =  new Intent(this,RemoteService.class);
        bindService(intent, connectionService, Context.BIND_AUTO_CREATE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
