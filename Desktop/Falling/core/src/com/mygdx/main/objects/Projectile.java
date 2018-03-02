package com.mygdx.main.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.main.Main;
import com.mygdx.main.actors.creation.CreateActor;
import com.mygdx.main.actors.creation.CreateAnimation;
import com.mygdx.main.actors.creation.CreateTexture;
import com.mygdx.main.ui.Scaler;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by seacow on 12/18/2017.
 * On the map functions, two containers should be created with one
 * containing the projectiles while the other will act as the projectile
 * pool.
 *
 * The pool will store all the deactivated and unused projectiles after
 * either hitting a target or getting out of range outside the viewing
 * screen.
 */

public class Projectile implements Poolable
{
    private CreateActor projectile;
    private boolean onScreen;
    private float screenWidth;
    private float screenHeight;

    public Projectile()
    {
        onScreen = true;
        screenWidth = Gdx.graphics.getWidth() / Scaler.PIXELS_TO_METERS;
        screenHeight = Gdx.graphics.getHeight() / Scaler.PIXELS_TO_METERS;
    }

    public void initTexture(String file, Main main)
    {
        projectile = new CreateTexture(file, main, BodyDef.BodyType.DynamicBody);
    }

    public void initAnimated(String file, ArrayMap<String, Float> region, Main main)
    {
        projectile = new CreateAnimation(file, region, main, BodyDef.BodyType.DynamicBody);
    }

    /**initial x and y position will be set outside of the screen with the projectile
     * deactivated*/
    public void create(World world, float width, float height)
    {
        projectile.create(world, 1000, 1000, width, height, false);
        projectile.setActive(false);
    }

    /**x and y should be the position of the object the projectile is being fired from*/
    public void display(float x, float y)
    {
        //Spawning happens only when the projectile is not used
        if(!projectile.isActive())
        {
            projectile.setPosition(x, y);
            projectile.setActive(true);
            onScreen = true;
        }

        projectile.display();
        outOfRange();
    }

    /**Checks to see if the projectile is still seen on the viewing screen*/
    private void outOfRange()
    {
        if(projectile.getX() > screenWidth || projectile.getX() < 0
                || projectile.getY() > screenHeight || projectile.getY() < 0)
            onScreen = false;
    }

    public boolean isOnScreen()
    {
        return onScreen;
    }

    /**projectile is deactivated outside of the screen
     * Note: automatically called by pool, do not explicitly call*/
    public void reset ()
    {
        projectile.setPosition(1000, 1000);
        projectile.setActive(false);
        onScreen = false;
    }
}
