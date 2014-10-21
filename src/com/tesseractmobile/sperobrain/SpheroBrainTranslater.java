package com.tesseractmobile.sperobrain;
import orbotix.robot.base.CollisionDetectedAsyncData;
import orbotix.robot.sensor.DeviceSensorsData;
import orbotix.robot.sensor.LocatorData;
import orbotix.sphero.Sphero;

import com.tesseractmobile.sperobrain.Event.EventType;

public class SpheroBrainTranslater{
	
	/**
	* Translates an State and sends commands to the Sphero
	*/
	static public void stateToSphero(final Sphero sphero, final State state){
		throw new UnsupportedOperationException();
	}
	
	/**
	* Takes a Collision and Returns an Event
	*/
	static public Event collisionToEvent(final CollisionDetectedAsyncData collisionDetectedAsyncData){
		final Event event = new Event();
	    if(collisionDetectedAsyncData.hasImpactXAxis() || collisionDetectedAsyncData.hasImpactYAxis()){
		    event.eventType = EventType.COLLISION;
		} else {
		    event.eventType = EventType.NO_EVENT;
		}
	    return event;
	}

	/**
	 * Takes Sensor data and returns a State
	 * @param deviceSensorData
	 * @return
	 */
    public static State sensorDataToState(final DeviceSensorsData deviceSensorData) {
        final State state = new State();
        return state;
    }

    /**
     * Returns an Event from a location change
     * @param location
     * @return
     */
    public static Event locationToEvent(final LocatorData location) {
        throw new UnsupportedOperationException();
    }
}
