package com.tesseractmobile.sperobrain;

/**
* A brain receives input in the form of events
* Stores events in memory
* Outputs events that become actions
*/
abstract public interface Brain{

	/**
	* Send Input to the Brain
	* Brain receives input from the body
	*/
	abstract public void onState(final State state);
	
	/**
	* Get Output from the Brain
	* Brain tries to change state of the body
	*/
	abstract public void setState(final State state);
	
}
