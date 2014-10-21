package com.tesseractmobile.sperobrain;

abstract public class BrainThread extends BasicBrain implements Runnable, Brain{
	
	//Tracking thread
    private Thread blinker;
	
	@Override
	public void run(){
		while(Thread.currentThread() == blinker){
		    //Do thinking here
		}
	}

    @Override
	public void onEvent(final Event event, final State state){
		// TODO: Implement this method
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
	public synchronized boolean isRunning(){
		return null != blinker;
	}
}
