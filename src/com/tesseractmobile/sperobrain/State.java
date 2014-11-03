package com.tesseractmobile.sperobrain;

public class State{
	public float velocity;
	public float heading;
	public Color color;
    public float x;
    public float y;
    public float vx;
    public float vy;
	
	static public class Color{
		public int red, green, blue;
		
		public Color(final int r, final int g, final int b){
			this.red = r;
			this.green = g;
			this.blue = b;
		}
	}
}
