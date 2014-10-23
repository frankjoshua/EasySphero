package com.tesseractmobile.sperobrain;
import orbotix.robot.base.CollisionDetectedAsyncData;
import orbotix.robot.sensor.DeviceSensorsData;
import orbotix.robot.sensor.LocatorData;
import orbotix.sphero.Sphero;

public class SpheroBrainTranslater{
	
	/**
	* Translates an State and sends commands to the Sphero
	*/
	static public void stateToSphero(final Sphero sphero, final State state){
		sphero.drive(stateToHeading(state), stateToVelocity(state));
	}
	
	private static float stateToVelocity(final State state) {
        return 1f;
    }

    private static float stateToHeading(final State state) {
        return 0f;
    }

    /**
	 * Takes Sphero data and creates an event
	 * @param mDeviceSensorData
	 * @param mCollisionDetectedAsyncData
	 * @param mLocation
	 * @return
	 */
    public static State createState(final DeviceSensorsData mDeviceSensorData, final CollisionDetectedAsyncData mCollisionDetectedAsyncData, final LocatorData mLocation) {
        //Create state
        final State state = new State();
        
        //Set Values
        state.x = mLocation.getPositionX();
        state.y = mLocation.getPositionY();
        state.vx = mLocation.getVelocityX();
        state.vy = mLocation.getVelocityY();
        
        //Return state
        return state;
    }
}
