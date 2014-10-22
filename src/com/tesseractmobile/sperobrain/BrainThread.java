package com.tesseractmobile.sperobrain;

abstract public class BrainThread extends BasicBrain implements Runnable{
	
	//Tracking thread
    private Thread blinker;
	
	@Override
	public void run(){
		while(Thread.currentThread() == blinker){
		    //Do thinking here
		    onState(getCurrentState());
		    //Sleep
		    try {
                Thread.sleep(10);
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
