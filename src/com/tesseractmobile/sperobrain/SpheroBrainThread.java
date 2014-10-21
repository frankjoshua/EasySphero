package com.tesseractmobile.sperobrain;

import orbotix.robot.base.CollisionDetectedAsyncData;
import orbotix.robot.sensor.DeviceSensorsData;
import orbotix.robot.sensor.LocatorData;
import orbotix.sphero.Sphero;

import com.tesseractmobile.easysphero.SpheroService.SpheroListener;

public class SpheroBrainThread extends BrainThread implements SpheroListener {

    /** Holds the last state received */
    private State currentState = new State();
    private Sphero mSphero;
    
    @Override
    public void onSensorUpdated(final DeviceSensorsData deviceSensorData) {
        //Just save the data
        currentState = SpheroBrainTranslater.sensorDataToState(deviceSensorData);
    }

    @Override
    public void onCollisionDetected(final CollisionDetectedAsyncData collisionDetectedAsyncData) {
        //Create Event and State then send to the brain
        final Event event = SpheroBrainTranslater.collisionToEvent(collisionDetectedAsyncData);
        onEvent(event);
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
        //Create Event
        final Event event = SpheroBrainTranslater.locationToEvent(location);
        //Add current state
        event.state = currentState;
        //Report Event
        onEvent(event);
    }


}
