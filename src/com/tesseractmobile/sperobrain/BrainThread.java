package com.tesseractmobile.sperobrain;

abstract public class BrainThread extends BasicBrain implements Runnable{
	
	//Tracking thread
    private Thread blinker;
	
	@Override
	public void run(){
		while(null != blinker){
		    //Do thinking here
		    onState(getCurrentState());
		    //Sleep
		    try {
                Thread.sleep(1000);
            } catch (final InterruptedException e) {
                //Do Nothing
            }
		}
	}
	
	protected abstract State getCurrentState();

    public synchronized void startThread(){
        blinker = new Thread(this);
        blinker.start();
    }

    public synchronized void stopThread(){
        try{
            final Thread t = blinker;
            blinker = null;
            t.join();
        } catch (final Exception e){

        }
    }
	
	/**
	* Returns true if running
	*/
	public synchronized boolean isRunning(){
		return null != blinker;
	}
}
