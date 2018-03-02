package com.mygdx.main.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.main.Main;
import com.mygdx.main.animator.Animator;

/**
 * Created by FlapJack on 7/22/2017.
 *
 * The terms dynamic and static here have nothing to do with how Box2D
 * defines them. They just mean they either moving or not.
 *
 * The default spawn areas are just outside the right edge on the
 * mid-bottom.
 *
 * Might be a good idea to add animated backgrounds
 */

public class Background
{
    private Main main;
    private Animator animate;

    private String file;
    private boolean timechange;
    private float speed;
    private float width;
    private float height;

    private float x;
    private float y;
    private float x_min;
    private float y_min;
    private float x_max;
    private float y_max;

    private boolean horizontal;


    public Background(String file_name, Main main_p, boolean horiz)
    {
        this.main = main_p;
        file = file_name;

        speed = 0;
        width = 0;
        height = 0;

        x_min = Gdx.graphics.getWidth();
        x_max = 1100;
        y_min = 0;
        y_max = Gdx.graphics.getHeight();

        timechange = false;
        horizontal = horiz;
    }

    /**Call this before generating the sprites or nothing will take effect*/
    public void setSpawnArea(float x_min_, float x_max_, float y_min_, float y_max_)
    {
        x_min = x_min_;
        x_max = x_max_;
        y_min = y_min_;
        y_max = y_max_;

        x = MathUtils.random(x_min, x_max);
        y = MathUtils.random(y_min, y_max);
    }

    public void setResolution(float w, float h)
    {
        width = w;
        height = h;
    }

    public void setAnimation(ArrayMap<String, Float> region)
    {
        animate = new Animator(file, region, main.assetmanager.dataholder);
    }

    /**display() must be called between the begin() and end() in the class where
     * your current screen is on*/

    /**Call this for non-moving/static sprites*/
    public void display()
    {
        main.batch.draw((Texture)main.assetmanager.dataholder.get(file), x, y, width, height);
    }

    public void displayAnimation(int limit, float s)
    {
        speed = s;

        animate.render(main.batch, x, y);

        if(x <= limit && horizontal)
        {
            x = MathUtils.random(x_min, x_max);
            y = MathUtils.random(y_min, y_max);

            timechange = true;
        }
        if(y >= limit && !horizontal)
        {
            x = MathUtils.random(x_min, x_max);
            y = MathUtils.random(y_min, y_max);

            timechange = true;

            System.out.println(" " + limit);
        }

        if(horizontal)
            x -= Gdx.graphics.getDeltaTime() * speed;   //subtraction dictates its moving to the left
        else
            y += Gdx.graphics.getDeltaTime() * speed;   //addition dictates its moving to the up
    }

    /**Call this for moving/dynamic sprites
     * horizontal just means you want it to move across.
     * set false if you it to move vertically*/
    public void display(int limit, float s)
    {
        speed = s;

        if(x <= limit && horizontal)
        {
            x = MathUtils.random(x_min, x_max);
            y = MathUtils.random(y_min, y_max);

            timechange = true;
        }
        if(y >= limit && !horizontal)
        {
            x = MathUtils.random(x_min, x_max);
            y = MathUtils.random(y_min, y_max);

            timechange = true;

            System.out.println(" " + limit);
        }

        //System.out.println(x);

        main.batch.draw((Texture)main.assetmanager.dataholder.get(file), x, y, width, height);

        if(horizontal)
            x -= Gdx.graphics.getDeltaTime() * speed;   //subtraction dictates its moving to the left
        else
            y += Gdx.graphics.getDeltaTime() * speed;   //subtraction dictates its moving to the down
    }

    /**These functions are for day time changing. Can also be possible used for
     * weather.*/
    public boolean getTimeChange()
    {
        return timechange;
    }

    public void resetTime()
    {
        timechange = false;
    }
}
