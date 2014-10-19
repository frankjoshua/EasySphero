package com.orbotix.sample.helloworld;

import java.util.ArrayList;
import java.util.List;

import orbotix.robot.base.CollisionDetectedAsyncData;
import orbotix.robot.base.Robot;
import orbotix.robot.base.RobotProvider;
import orbotix.robot.sensor.DeviceSensorsData;
import orbotix.sphero.CollisionListener;
import orbotix.sphero.ConnectionListener;
import orbotix.sphero.DiscoveryListener;
import orbotix.sphero.PersistentOptionFlags;
import orbotix.sphero.SensorControl;
import orbotix.sphero.SensorFlag;
import orbotix.sphero.SensorListener;
import orbotix.sphero.Sphero;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class SpheroService extends Service {

    public interface SpheroListener {
        public void onSpheroConnected(final Sphero sphero);
        public void onSpheroDisonnected();
    }

    public class LocalBinder extends Binder {
        public SpheroService getService(){
            return SpheroService.this;
        }
    }

    protected static final String TAG = "SpheroService";

    private final IBinder binder = new LocalBinder();

    /** The Sphero Robot */
    private Sphero mRobot;
    
    /** List of Sphero Listeners **/
    private final ArrayList<SpheroListener> mSperoListeners = new ArrayList<SpheroListener>();
    
    @Override
    public IBinder onBind(final Intent arg0) {
        return binder ;
    }

    /**
     * Register to listen for Sphero connection events
     * @param spheroListener
     * @return true if successful
     */
    public boolean registerSpheroListener(final SpheroListener spheroListener){
        //Add to list of listeners
        return mSperoListeners.add(spheroListener);
    }
    
    /**
     * Stop listening for Sphero connection events
     * @param spheroListener
     * @return true if successful
     */
    public boolean unregisterSpheroListener(final SpheroListener spheroListener){
        //Remove form list of listeners
        return mSperoListeners.remove(spheroListener);
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        RobotProvider.getDefaultProvider().addConnectionListener(new ConnectionListener() {
            @Override
            public void onConnected(final Robot robot) {
                //Register Sphero
                setSphero((Sphero) robot);
                //Listen for Sensors
                final SensorControl control = mRobot.getSensorControl();
                control.addSensorListener(new SensorListener() {
                    @Override
                    public void sensorUpdated(final DeviceSensorsData sensorDataArray) {
                        Log.d(TAG, sensorDataArray.toString());
                    }
                } ,SensorFlag.ACCELEROMETER_NORMALIZED, SensorFlag.GYRO_NORMALIZED);
                control.setRate(1);
                //Listen or Collisions
                mRobot.getCollisionControl().startDetection(255,255,255,255,255);
                mRobot.getCollisionControl().addCollisionListener(new CollisionListener() {
                    public void collisionDetected(final CollisionDetectedAsyncData collisionData) {
                        Log.d(TAG, collisionData.toString());
                    }
                });
                
                //Setup some stuff
                final boolean preventSleepInCharger = mRobot.getConfiguration().isPersistentFlagEnabled(PersistentOptionFlags.PreventSleepInCharger);
                Log.d(TAG, "Prevent Sleep in charger = " + preventSleepInCharger);
                Log.d(TAG, "VectorDrive = " + mRobot.getConfiguration().isPersistentFlagEnabled(PersistentOptionFlags.EnableVectorDrive));

                mRobot.getConfiguration().setPersistentFlag(PersistentOptionFlags.PreventSleepInCharger, false);
                mRobot.getConfiguration().setPersistentFlag(PersistentOptionFlags.EnableVectorDrive, true);

                Log.d(TAG, "VectorDrive = " + mRobot.getConfiguration().isPersistentFlagEnabled(PersistentOptionFlags.EnableVectorDrive));
                Log.v(TAG, mRobot.getConfiguration().toString());
                
                mRobot.enableStabilization(true);
                mRobot.drive(90, 0);
                mRobot.setBackLEDBrightness(.5f);
            }

            @Override
            public void onConnectionFailed(final Robot sphero) {
                Log.d(TAG, "Connection Failed: " + sphero);
                Toast.makeText(SpheroService.this, "Sphero Connection Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDisconnected(final Robot robot) {
                Log.d(TAG, "Disconnected: " + robot);
                Toast.makeText(SpheroService.this, "Sphero Disconnected", Toast.LENGTH_SHORT).show();
                setSphero(null);
            }
        });

        RobotProvider.getDefaultProvider().addDiscoveryListener(new DiscoveryListener() {
            @Override
            public void onBluetoothDisabled() {
                Log.d(TAG, "Bluetooth Disabled");
                Toast.makeText(SpheroService.this, "Bluetooth Disabled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void discoveryComplete(final List<Sphero> spheros) {
                Log.d(TAG, "Found " + spheros.size() + " robots");
            }

            @Override
            public void onFound(final List<Sphero> sphero) {
                Log.d(TAG, "Found: " + sphero);
                RobotProvider.getDefaultProvider().connect(sphero.iterator().next());
            }
        });

        final boolean success = RobotProvider.getDefaultProvider().startDiscovery(this);
        if(!success){
            Toast.makeText(SpheroService.this, "Unable To start Discovery!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Let the listeners know that the Sphero has connected
     * @param sphero
     */
    private void notifiySpheroConnected(final Sphero sphero) {
        for(final SpheroListener spheroListener : mSperoListeners){
            spheroListener.onSpheroConnected(sphero);
        }
    }

    /**
     * Let the listeners know that the Sphero has disconnected
     * @param sphero
     */
    private void notifiySpheroDisconnected() {
        for(final SpheroListener spheroListener : mSperoListeners){
            spheroListener.onSpheroDisonnected();
        }
    }
    
    /**
     * Returns a Sphero
     * @return
     */
    public Sphero getSphero() {
        return mRobot;
    }

    private void setSphero(final Sphero sphero){
        this.mRobot = sphero;
        if(sphero != null){
            notifiySpheroConnected(sphero);
        } else {
            notifiySpheroDisconnected();
        }
    }

    

}
