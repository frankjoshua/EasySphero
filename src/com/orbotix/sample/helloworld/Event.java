package com.orbotix.sample.helloworld;

/**
* Events are created from input and stored in memory
*/
public class Event{
	public enum EventType {
		COLLISION, ACCELERATION, NO_EVENT
	}
	
	public EventType eventType;
	public float eventValue;
}
