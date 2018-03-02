package com.mygdx.main;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by FlapJack on 7/29/2017.
 */

public class Asset
{
    public AssetManager dataholder;

    public Asset()
    {
        dataholder = new AssetManager();
    }

    public void Loadfiles()
    {
        dataholder.load("water.png", Texture.class);
        dataholder.load("clouds.png", Texture.class);
        dataholder.load("sun.png", Texture.class);
        dataholder.load("cloud1.png", Texture.class);
        dataholder.load("cloud2.png", Texture.class);
        dataholder.load("cloud3.png", Texture.class);
        dataholder.load("moon.png", Texture.class);
        dataholder.load("star.png", Texture.class);
        dataholder.load("rock.png", Texture.class);
        dataholder.load("branch1.png", Texture.class);
        dataholder.load("branch2.png", Texture.class);
        dataholder.load("map4.png", Texture.class);
        dataholder.load("map4_parts.png", Texture.class);
        dataholder.load("door1.png", Texture.class);
        dataholder.load("door2.png", Texture.class);
        dataholder.load("map4_cloud.png", Texture.class);
        dataholder.load("map4_cloud2.png", Texture.class);
        dataholder.load("ship1.png", Texture.class);
        dataholder.load("parachute.png", Texture.class);
        dataholder.load("collector.png", Texture.class);

        dataholder.load("player.atlas", TextureAtlas.class);
        dataholder.load("branch.atlas", TextureAtlas.class);
        dataholder.load("pad.atlas", TextureAtlas.class);
        dataholder.load("splash.atlas", TextureAtlas.class);

        dataholder.load("skin/flat-earth-ui.json", Skin.class);

        dataholder.finishLoading();
    }

    public void Dispose()
    {
        dataholder.dispose();
    }
}