package com.mygdx.main.animator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.main.ui.Scaler;

/**
 * Created by FlapJack on 7/22/2017.
 */

public class Animator
{
    final private float scale_width = (Gdx.graphics.getWidth() / 800);
    final private float scale_height = (Gdx.graphics.getHeight() / 680);

    private Array<Animation> animation;
    private ArrayMap<String, Float> region;
    private float elapsed_time;
    private float endTime;
    private int playback;

    private float speed;
    private String file;

    private float width;
    private float height;
    private int box2dScale;

    private Animator(String file, float speed)
    {
        this.file = file;
        this.speed = speed;
        playback = 0;
        elapsed_time = 0f;

        animation = new Array<Animation>();
        region = new ArrayMap<String, Float>();

        box2dScale = 0;

        endTime = -1;   //has to be different from elapsed time
    }

    public Animator(String file, AssetManager dataholder, float speed)
    {
        this(file, speed);

        animation.add(new Animation(speed * Gdx.graphics.getDeltaTime(), dataholder.get(file, TextureAtlas.class).getRegions()));

        width = dataholder.get(this.file, TextureAtlas.class).getRegions().first().packedWidth * scale_width;
        height = dataholder.get(this.file, TextureAtlas.class).getRegions().first().packedHeight * scale_height;
    }

    public Animator(String file, ArrayMap<String, Float> region, AssetManager dataholder)
    {
        this(file, 0);

        this.region = region;

        for(int n = 0; n < this.region.size; n++)
        {
            animation.add(new Animation(region.getValueAt(n) * Gdx.graphics.getDeltaTime(),
                    dataholder.get(this.file, TextureAtlas.class).findRegions(this.region.getKeyAt(n))));
        }

        width = dataholder.get(file, TextureAtlas.class).getRegions().first().packedWidth * scale_width;
        height = dataholder.get(file, TextureAtlas.class).getRegions().first().packedHeight * scale_height;
    }

    public void setPlayback(String call)
    {
        int n = 0;

        for(ObjectMap.Entry<String, Float> iter : region)
        {
            if(iter.key == call)
            {
                playback = n;
                return;
            }

            n++;
        }
    }

    /**playback is the type or set of animations you want to render from the atlas.
     * The ordering is heavily dependant on the order you passed the regions*/
    public void render(Batch batch, float x, float y)
    {
        elapsed_time += Gdx.graphics.getDeltaTime();

        batch.draw((TextureRegion) animation.get(playback).getKeyFrame(elapsed_time, true),
                ((x * Scaler.PIXELS_TO_METERS) - width/2) + box2dScale,
                (y * Scaler.PIXELS_TO_METERS) - height/2,
                width, height);
    }

    public void recordEndTime()
    {
        if(animation.get(playback).isAnimationFinished(elapsed_time))
        {
            endTime = elapsed_time;
        }
    }

    public void resetTime()
    {
        endTime = -1;
        elapsed_time = 0;
    }

    public void outValues()
    {
        System.out.println("elapse: " + elapsed_time);
        System.out.println("end: " + endTime);
    }

    public boolean ifFrameEnd()
    {
        if(endTime == elapsed_time)
        {
            /**
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    //nothing
                }
            }, 30);
             */
            return true;
        }

        return false;
    }

    /**For manually upscaling and downscaling resolutions*/
    public void setScale(float width, float height)
    {
        this.width = width;
        this.height = height;
    }

    public void flip(int val)
    {
        width *= -1;

        box2dScale = val;
    }

    public void setPosScale(int val)
    {
        box2dScale = val;
    }

    public float getWidth()
    {
        return width;
    }

    public float getHeight()
    {
        return height;
    }
}
