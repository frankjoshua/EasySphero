package com.tesseractmobile.easysphero.sample;

import orbotix.robot.base.CollisionDetectedAsyncData;
import orbotix.robot.sensor.DeviceSensorsData;
import orbotix.robot.sensor.LocatorData;
import orbotix.sphero.Sphero;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.tesseractmobile.easysphero.SpheroActivity;
import com.tesseractmobile.sperobrain.R;

public class SampleActivity extends SpheroActivity implements OnClickListener {

    /** Used to update connection status */
    private View mViewStatus;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the XML layout
        setContentView(R.layout.main);

        // Get a reference to the status view
        mViewStatus = findViewById(R.id.viewStatus);
        mViewStatus.setBackgroundColor(Color.RED);

    }

    

    @Override
    public void onSpheroConnected(final Sphero sphero) {
        //Set Status color to green
        mViewStatus.setBackgroundColor(Color.GREEN);
        
        //Listen for button clicks
        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.btnForward).setOnClickListener(this);
        findViewById(R.id.btnLeft).setOnClickListener(this);
        findViewById(R.id.btnRight).setOnClickListener(this);
        findViewById(R.id.btnStop).setOnClickListener(this);
    }

    @Override
    public void onSpheroDisonnected() {
        mViewStatus.setBackgroundColor(Color.RED);
    }

    @Override
    public void onClick(final View v) {
        final int viewId = v.getId();
        //Change Sphero speed and direction depending on button pressed
        switch (viewId) {
        case R.id.btnForward:
            getSphero().drive(180f, 1f);
            break;
        case R.id.btnBack:
            getSphero().drive(0f, 1f);
            break;
        case R.id.btnRight:
            getSphero().drive(90f, 1f);
            break;
        case R.id.btnLeft:
            getSphero().drive(270f, 1f);
            break;
        case R.id.btnStop:
            getSphero().drive(0f, 0f);
            break;
        default:
            break;
        }
    }
    
    @Override
    public void onCollisionDetected(final CollisionDetectedAsyncData collisionDetectedAsyncData) {
        //Do Nothing
    }
    
    @Override
    public void onSensorUpdated(final DeviceSensorsData deviceSensorData) {
        //Do Nothing
    }

    @Override
    public void onLocationChanged(final LocatorData location) {
      //Do Nothing
    }
}
