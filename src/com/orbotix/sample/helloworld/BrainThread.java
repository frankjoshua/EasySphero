package com.orbotix.sample.helloworld;

public class BrainThread extends Brain implements Runnable{
	
	//Tracking thread
    private Thread blinker;
	
	@Override
	public void run(){
		// TODO: Implement this method
	}

	@Override
	public void onEvent(final Event event, final State state){
		// TODO: Implement this method
	}

	@Override
	public Event getNextEvent(){
		// TODO: Implement this method
		return null;
	}
	
	public synchronized void startThread(){
        blinker = new Thread(this);
        blinker.start();
    }

    public synchronized void stopThread(){
        try{
            blinker.join();
        } catch (final Exception e){

        }
		blinker = null;
    }
	
	/**
	* Returns true if running
	*/
	public boolean isRunning(){
		return null != blinker;
	}
}
