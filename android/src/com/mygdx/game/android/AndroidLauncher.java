package com.mygdx.game.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class AndroidLauncher extends AndroidApplication {
	ActionResolverAndroid actionResolver;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		actionResolver = new ActionResolverAndroid(this);

		config.useAccelerometer = false;
		config.useCompass = false;
		initialize(new MyGdxGame(actionResolver), config);
	}
}
