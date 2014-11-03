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
		if(state.color != null){
			sphero.setColor(state.color.red, state.color.green, state.color.blue);
		}
	}
	
	private static float stateToVelocity(final State state) {
        return state.velocity;
    }

    private static float stateToHeading(final State state) {
        return state.heading;
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
