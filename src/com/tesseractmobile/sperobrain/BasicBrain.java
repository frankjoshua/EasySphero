package com.tesseractmobile.sperobrain;

import java.util.Random;

abstract public class BasicBrain implements Brain{
    
    private int heading;
    private final float velocity = .6f;
	private final State.Color color = new State.Color(0,0,0);
    
    @Override
    public void onState(final State state) {
        if(Math.abs(state.vx) < 10 && Math.abs(state.vy) < 10){
            final Random random = new Random(System.currentTimeMillis());
			//Find a heading more that 15 degrees different from current
			int newHeading = random.nextInt(360);
			while(Math.abs(newHeading - heading) < 90){
				newHeading = random.nextInt(360);
			}
            heading = newHeading;
            color.red = random.nextInt(255);
			color.blue = random.nextInt(255);
			color.green = random.nextInt(255);
        }
        state.velocity = velocity;
        state.heading = heading;
		state.color = color;
        setState(state);
    }

}
