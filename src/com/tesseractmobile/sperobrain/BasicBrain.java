package com.tesseractmobile.sperobrain;

abstract public class BasicBrain implements Brain{

    @Override
    public void onState(final State state) {
        setState(state);
    }

}
