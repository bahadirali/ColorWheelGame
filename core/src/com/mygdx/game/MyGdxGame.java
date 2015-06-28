package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.IActionResolver.IActionResolver;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class MyGdxGame extends ApplicationAdapter implements ApplicationListener {

	public int count = 0;
	IActionResolver actionResolver;

	private SpriteBatch batch;
	private Texture wheelImage;
	private com.badlogic.gdx.math.Rectangle wheel;
	private TextureRegion region;

	private final int screenWidth = 800;
	private final int screenHeight = 480;
	private final int imageWidth = 366;
	private final int imageHeight = 366;

	private double centerOfWheelX;
	private double centerOfWheelY;
	private double radiusOfWheel;

	private long lastRotationTime;
	private float rotationAngle;


	public MyGdxGame(IActionResolver actionResolver){
		this.actionResolver = actionResolver;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		wheelImage = new Texture("colour-wheel(366x366).png");
		region = new TextureRegion(wheelImage);

		wheel = new com.badlogic.gdx.math.Rectangle();
		wheel.x = screenWidth/2 - imageWidth/2;
		wheel.y = screenHeight/2 - imageHeight/2;
		wheel.width = imageWidth;
		wheel.height = imageHeight;

		centerOfWheelX = wheel.x + imageWidth/2;
		centerOfWheelY = wheel.y + imageHeight/2;
		radiusOfWheel = imageHeight/2;

		lastRotationTime = TimeUtils.nanoTime();
		rotationAngle = 0;
	}



	@Override
	public void render () {
		//set background color
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		//rotation progress (by changing angle of image around its center <rotationAngle> )
		if(TimeUtils.nanoTime() - lastRotationTime > 100000000) {
			lastRotationTime = TimeUtils.nanoTime();
			rotationAngle = (rotationAngle + 10) % 360;
		}

		//code to draw without using region(it doesn't work properly)
		//batch.draw(wheelImage, wheel.x, wheel.y, imageWidth/2, imageHeight/2,
		//			imageWidth, imageHeight, 1.f, 1.f, rotationAngle, (int) wheel.x, (int) wheel.y, (int) imageWidth, (int) imageHeight, false, false);

		//draw wheel according to rotation angle
		batch.draw(region, wheel.x, wheel.y, imageWidth/2, imageHeight/2,
					imageWidth, imageHeight, 1.f, 1.f, -rotationAngle);

		// process user input
		//Used justTouched instead of isTouched since isTouched returns true
		//for a long time so program enters if condition several times
		if(Gdx.input.justTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			if(touchInsideWheel(touchPos) == true){
				printColorTouched(touchPos);
			}
			else{
				actionResolver.showShortToast("Disari");
			}
		}


		batch.end();

	}

	String angleToColor(int theta){

		String color = "";
		String[] colors = new String[]{"turuncu","sari","yesil","mavi","mor","kirmizi"};

		color = colors[(theta/60)%6];
		return color;
	}
	private void printColorTouched(Vector3 touchPos) {
		//some math stuff to find angle in degrees between touched place and center of wheel [0,360]
		double div = (double) (touchPos.y - centerOfWheelY)/(touchPos.x - centerOfWheelX);
		int theta = (int)Math.toDegrees(Math.atan(div));
		if(theta < 0) theta = -theta;
		else theta = 180 - theta;
		if(touchPos.y > centerOfWheelY) theta = 180 + theta;

		//to find angle for initial position of wheel
		//then we will just look for which angle corresponds which color initially
		theta = (theta + (int)rotationAngle) % 360;
		String color = angleToColor(theta);

		actionResolver.showShortToast(color);
	}

	private boolean touchInsideWheel(Vector3 touchPos) {
		double distanceX = abs(touchPos.x - centerOfWheelX);
		double distanceY = abs(touchPos.y - centerOfWheelY);
		double distance = sqrt(distanceX * distanceX + distanceY * distanceY);

		if(distance <= radiusOfWheel) return true;

		return false;
	}


	public void dispose(){
		wheelImage.dispose();
		batch.dispose();
	}
}
