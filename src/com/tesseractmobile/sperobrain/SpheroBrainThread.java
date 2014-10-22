package com.tesseractmobile.sperobrain;

import orbotix.robot.base.CollisionDetectedAsyncData;
import orbotix.robot.sensor.DeviceSensorsData;
import orbotix.robot.sensor.LocatorData;
import orbotix.sphero.Sphero;

import com.tesseractmobile.easysphero.SpheroService.SpheroListener;

public class SpheroBrainThread extends BrainThread implements SpheroListener {

    private Sphero mSphero;
    private LocatorData mLocation;
    private CollisionDetectedAsyncData mCollisionDetectedAsyncData;
    private DeviceSensorsData mDeviceSensorData;
    
    @Override
    public void onSensorUpdated(final DeviceSensorsData deviceSensorData) {
        //Just save the data
        this.mDeviceSensorData = deviceSensorData;
    }

    @Override
    public void onCollisionDetected(final CollisionDetectedAsyncData collisionDetectedAsyncData) {
        //Save the collision
        this.mCollisionDetectedAsyncData = collisionDetectedAsyncData;
    }

    @Override
    public void onSpheroConnected(final Sphero sphero) {
        this.mSphero = sphero;
    }

    @Override
    public void onSpheroDisonnected() {
        mSphero = null;
    }

    @Override
    public void setState(final State state) {
        //Send state to the Sphero
        final Sphero sphero = mSphero;
        if(sphero != null){
            SpheroBrainTranslater.stateToSphero(sphero, state);
        }
    }

    @Override
    public void onLocationChanged(final LocatorData location) {
        //Save location
        this.mLocation = location;
    }

    @Override
    protected State getCurrentState() {
        return SpheroBrainTranslater.createState(mDeviceSensorData, mCollisionDetectedAsyncData, mLocation);
    }


}
