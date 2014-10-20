package com.orbotix.sample.helloworld;

/**
* A brain receives input in the form of events
* Stores events in memory
* Outputs events that become actions
*/
abstract public class Brain{

	/**
	* Send Input to the Brain
	*/
	abstract public void onEvent(final Event event, final State state);
	
	/**
	* Get Output from the Brain
	*/
	abstract public Event getNextEvent();
	
}
