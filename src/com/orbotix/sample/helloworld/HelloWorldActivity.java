package com.orbotix.sample.helloworld;

import orbotix.sphero.Sphero;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.widget.*;
import android.os.*;
import android.view.View.*;
import android.view.*;
import orbotix.robot.base.*;

/** Connects to an available Sphero robot, and then flashes its LED. */
public class HelloWorldActivity extends SpheroActivity implements Runnable, OnClickListener
{
	
    private static final String TAG = "OBX-HelloWorld";
    
    //Tracking thread
    private Thread blinker;
    //Current thread
    private Thread thread;

	/** Handler for updating Views **/
	private Handler handler = new Handler();
	
	/** button to toggle thread **/
	private Button mButtonToggle;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		mButtonToggle = (Button) findViewById(R.id.btnToggle);
		enableButton(false);
		mButtonToggle.setOnClickListener(this);
    }

	@Override
	public void onClick(View p1)
	{
		if(blinker == null){
			startThread();
			mButtonToggle.setText("Off");
		} else {
			stopThread();
			mButtonToggle.setText("On");
		}
	}


    @Override
    public void onSpheroConnected(final Sphero sphero) {
        //Log and toast connection
        Log.d(TAG, "Connected: " + sphero);
        Toast.makeText(this, sphero.getName() + " Connected", Toast.LENGTH_LONG).show();
        enableButton(true);
    }

	private void enableButton(final boolean enable){
		handler.post(new Runnable(){
				@Override
				public void run(){
					mButtonToggle.setEnabled(enable);
					if(enable){
						mButtonToggle.setText("On");
					} else {
						mButtonToggle.setText("Off");
					}
				}	
			});
	}

    @Override
    public void onSpheroDisonnected() {
        Toast.makeText(this, "Disconnected", Toast.LENGTH_LONG).show();
        finish();
    }

	private float heading = 0f;
    @Override
    public void run() {
        
        final float velocity = .5f;
        final Sphero sphero = getSphero();
		final TextView tvStatus = (TextView) findViewById(R.id.tvStatus);
        while(blinker == thread){
            
            //heading += 45.0f;
            //if(heading >= 360){
            //    heading = 0;
            //}
			final String strHeading = Float.toString(heading);
			handler.post(new Runnable(){
					@Override
					public void run()
					{
						tvStatus.setText(strHeading);
					}	
			});
			
            sphero.drive(heading, velocity);
            try {
                Thread.sleep(10);
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
	

	@Override
	public void onCollisionDeteced(final CollisionDetectedAsyncData collisionDetectedAsyncData){
		if(collisionDetectedAsyncData.hasImpactXAxis()){
			heading += 135;
			if(heading >= 360){
				heading -= 360;
			}
			handler.post(new Runnable(){
					
				@Override
				public void run(){
					mButtonToggle.setText("Hit");	
				}
			});
		}
	}
	
}
