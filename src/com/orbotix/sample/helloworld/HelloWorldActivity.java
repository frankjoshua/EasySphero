package com.orbotix.sample.helloworld;

import orbotix.sphero.Sphero;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/** Connects to an available Sphero robot, and then flashes its LED. */
public class HelloWorldActivity extends SpheroActivity implements Runnable{
    private static final String TAG = "OBX-HelloWorld";
    
    //Tracking thread
    private Thread blinker;
    //Current thread
    private Thread thread;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }


    @Override
    protected void onPause() {
        stopThread();
        super.onPause();
    }




    @Override
    public void onSpheroConnected(final Sphero sphero) {
        //Log and toast connection
        Log.d(TAG, "Connected: " + sphero);
        Toast.makeText(this, sphero.getName() + " Connected", Toast.LENGTH_LONG).show();
        startThread();
    }

    @Override
    public void onSpheroDisonnected() {
        Toast.makeText(this, "Disconnected", Toast.LENGTH_LONG).show();
        finish();
    }


    @Override
    public void run() {
        float heading = 0f;
        final float velocity = .25f;
        final Sphero sphero = getSphero();
        while(blinker == thread){
            
            heading += 10.0f;
            if(heading > 360){
                heading = 0;
            }
            sphero.drive(heading, velocity);
            try {
                Thread.sleep(50);
            } catch (final InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        sphero.stop();
    }
    
    private void startThread(){
        blinker = new Thread(this);
        thread = blinker;
        thread.start();
    }
    
    private void stopThread(){
        blinker = null;
        try{
            thread.join();
        } catch (final Exception e){
            
        }
    }
}
