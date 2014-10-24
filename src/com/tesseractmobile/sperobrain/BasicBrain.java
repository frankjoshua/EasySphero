package com.tesseractmobile.sperobrain;

import java.util.Random;

abstract public class BasicBrain implements Brain{
    
    private int heading;
    private final float velocity = .6f;
    
    @Override
    public void onState(final State state) {
        if(Math.abs(state.vx) < 1 && Math.abs(state.vy) < 1){
            final Random random = new Random(System.currentTimeMillis());
			//Find a heading more that 15 degrees different from current
			int newHeading = random.nextInt(360);
			while(Math.abs(newHeading - heading) < 90){
				newHeading = random.nextInt(360);
			}
            heading = newHeading;
            //velocity = random.nextFloat();
        }
        state.velocity = velocity;
        state.heading = heading;
        setState(state);
    }

}
