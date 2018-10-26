package com.example.usmanmalik.waypark_final;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.TextView;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    Socket myAppSocket = null;
    public static String wifiModuleIp= "192.168.43.176";
    public static int wifiModulePort= 12345;
    public static String CMD = "0";
    String TAG = "Log";
    Context view;
    //Button button;
    TextView s1,s2,s3,s4,s5,s6;
    android.support.v7.widget.CardView sa1,sa2,sa3,sa4,sa5,sa6;
    public void changestate(Integer... values) {
        Log.d(TAG, "changestate: C");
        if (values[0] == 'y') {
            this.s1.setText("Parked");
            sa1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
        if (values[0] == 'z') {
            this.s1.setText("Not Parked");
            sa1.setBackgroundColor(getResources().getColor(R.color.green));
        }
        if (values[0] == 'a') {
            this.s2.setText("parked");
            sa2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
        if (values[0] == 'b') {
            this.s2.setText("Not Parked");
            sa2.setBackgroundColor(getResources().getColor(R.color.green));
        }
        if (values[0] == 'c') {
            this.s3.setText("parked");
            sa3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
        if (values[0] == 'd') {
            s3.setText("Not Parked");
            sa3.setBackgroundColor(getResources().getColor(R.color.green));
        }
        if (values[0] == 'e') {
            this.s4.setText("parked");
            sa4.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
        if (values[0] == 'f') {
            this.s4.setText("Not Parked");
            sa4.setBackgroundColor(getResources().getColor(R.color.green));
        }
        if (values[0] == 'i') {
            this.s5.setText("Parked");
            sa5.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
        if (values[0] == 'j') {
            this.s5.setText("Not Parked");
            sa5.setBackgroundColor(getResources().getColor(R.color.green));
        }
        if (values[0] == 'k') {
            this.s6.setText("Parked");
            sa6.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
        if (values[0] == 'l') {
            this.s6.setText("Not Parked");
            sa6.setBackgroundColor(getResources().getColor(R.color.green));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //button= (Button) findViewById(R.id.button);
        view=getApplicationContext();
        s1=(TextView)findViewById(R.id.sensor1);
        s2=(TextView)findViewById(R.id.sensor2);
        s3=(TextView)findViewById(R.id.sensor3);
        s4=(TextView)findViewById(R.id.sensor4);
        s5=(TextView)findViewById(R.id.sensor5);
        s6=(TextView)findViewById(R.id.sensor6);
        sa1=(CardView) findViewById(R.id.s1);
        sa2=(CardView) findViewById(R.id.s2);
        sa3=(CardView) findViewById(R.id.s3);
        sa4=(CardView) findViewById(R.id.s4);
        sa5=(CardView) findViewById(R.id.s5);
        sa6=(CardView) findViewById(R.id.s6);
        Socket_AsyncTask so = new Socket_AsyncTask();
        so.execute();
    }
    public class Socket_AsyncTask extends AsyncTask<Void,Integer,Void>
    {
        Socket socket;
        @Override
        protected void onProgressUpdate(final Integer... values) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    changestate(values);
                }
            });
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Log.d(TAG, "doInBackground: ");
                InetAddress inetAddress = InetAddress.getByName(MainActivity.wifiModuleIp);
                socket = new Socket(inetAddress, MainActivity.wifiModulePort);
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                int i=0;
                while (i<1000) {
                    int dst=dataInputStream.read();

                    Log.d(TAG, "doInBackground: " + (char)dst);
                    onProgressUpdate(dst);
                    i++;
                }
                socket.close();
            } catch (UnknownHostException e) {
                Log.d(TAG, "doInBackground: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "doInBackground: " + e.getMessage());
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,Navigation_view.class));
    }
}
