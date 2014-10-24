package com.tesseractmobile.sperobrain;

import java.util.Random;

abstract public class BasicBrain implements Brain{
    
    private int heading;
    private final float velocity = .5f;
    
    @Override
    public void onState(final State state) {
        if(state.vx == 0 && state.vy == 0){
            final Random random = new Random(System.currentTimeMillis());
            heading = random.nextInt(360);
            //velocity = random.nextFloat();
        }
        state.velocity = velocity;
        state.heading = heading;
        setState(state);
    }

}
