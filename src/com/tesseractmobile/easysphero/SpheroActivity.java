package com.tesseractmobile.easysphero;

import orbotix.sphero.Sphero;
import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.tesseractmobile.easysphero.SpheroService.LocalBinder;
import com.tesseractmobile.easysphero.SpheroService.SpheroListener;

public abstract class SpheroActivity extends Activity implements ServiceConnection, SpheroListener {

    private SpheroService mSpheroService;
    
    @Override
    protected void onStart() {
        super.onStart();
        //Bind to SpheroService
        final Intent serviceIntent = new Intent(this, SpheroService.class);
        final boolean connected = bindService(serviceIntent, this, Service.BIND_AUTO_CREATE);
        if(connected == false){
            throw new UnsupportedOperationException("Could not connecct to service!");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Stop listening for events and unregister service if needed
        final SpheroService spheroService = mSpheroService;
        if(spheroService != null){
            spheroService.unregisterSpheroListener(this);
            unbindService(this);
        }
        
    }

    @Override
    public void onServiceConnected(final ComponentName name, final IBinder service) {
        mSpheroService = ((LocalBinder) service).getService();
        mSpheroService.registerSpheroListener(this);
    }

    @Override
    public void onServiceDisconnected(final ComponentName arg0) {
        mSpheroService = null;
    }

    protected Sphero getSphero(){
        final SpheroService spheroService = mSpheroService;
        if(spheroService != null){
            return spheroService.getSphero();
        }
        throw new UnsupportedOperationException("Not connected to SpheroService!");
    }
}
