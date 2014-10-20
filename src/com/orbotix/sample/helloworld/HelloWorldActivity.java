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
import orbotix.robot.sensor.*;

public class HelloWorldActivity extends SpheroActivity implements OnClickListener{

    private static final String TAG = "OBX-HelloWorld";

	/** Handler for updating Views **/
	private Handler handler = new Handler();
	
	/** button to toggle thread **/
	private Button mButtonToggle;
	
	private BrainThread brain = new BrainThread();
	private DeviceSensorsData deviceSensorData;
	
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
	public void onClick(View p1){
		if(brain.isRunning()){
			brain.startThread();
			mButtonToggle.setText("Off");
		} else {
			brain.stopThread();
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


	@Override
	public void onCollisionDetected(final CollisionDetectedAsyncData collisionDetectedAsyncData){
		//Create Event and State then send to the brain
		final Event event = new Event();
		final State state = new State();
		brain.onEvent(event, state);
	}
	
	@Override
	public void onSensorUpdated(final DeviceSensorsData deviceSensorData) {
		//Just save the data
		this.deviceSensorData = deviceSensorData;
		final Event event = brain.getNextEvent();
		
	}
	
}
