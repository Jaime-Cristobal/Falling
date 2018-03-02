package com.mygdx.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.main.animator.AnimatePOD;

public class Main extends Game
{
	public SpriteBatch batch;
	public Asset assetmanager;

	public Skin skin;

	public void create()

	{
		System.out.println("Bird is created.");

		batch = new SpriteBatch();

		assetmanager = new Asset();
		assetmanager.Loadfiles();

		skin = assetmanager.dataholder.get("skin/flat-earth-ui.json");

		AnimatePOD.create();

		this.setScreen(new UIMenu(this));
	}

	public void render()
	{
		super.render();
	}

	public void dispose()
	{
		batch.dispose();
		assetmanager.Dispose();
		skin.dispose();

		if(this.getScreen() != null)
			this.getScreen().dispose();

		System.out.println("Bird is disposed");
	}
}
