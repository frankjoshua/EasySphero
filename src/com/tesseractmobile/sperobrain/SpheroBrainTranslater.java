package com.tesseractmobile.sperobrain;
import orbotix.robot.base.CollisionDetectedAsyncData;
import orbotix.robot.sensor.DeviceSensorsData;
import orbotix.sphero.Sphero;

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
		throw new UnsupportedOperationException();
	}

	/**
	 * Takes Sensor data and returns a State
	 * @param deviceSensorData
	 * @return
	 */
    public static State sensorDataToState(final DeviceSensorsData deviceSensorData) {
        throw new UnsupportedOperationException();
    }
}
