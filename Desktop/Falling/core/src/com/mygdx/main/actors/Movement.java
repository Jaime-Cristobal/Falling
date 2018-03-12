package com.mygdx.main.actors;

import com.badlogic.gdx.math.MathUtils;
import com.mygdx.main.actors.creation.CreateActor;

/**
 * Created by seacow on 12/17/2017.
 */

public class Movement
{
    private int vertical;
    private int horizontal;

    private float xMin;
    private float xMax;
    private float yMin;
    private float yMax;
    private float limit;

    private boolean justRespawned;

    public Movement()
    {
        vertical = 0;
        horizontal = 1;

        xMin = 0;
        xMax = 0;
        yMin = 0;
        yMax = 0;
        limit = 0;

        justRespawned = false;
    }

    /**these values will be needed for respawning*/
    public void setSpawn(float xmin, float xmax, float ymin, float ymax)
    {
        xMin = xmin;
        xMax = xmax;
        yMin = ymin;
        yMax = ymax;
    }

    public void setLimit(float val)
    {
        limit = val;
    }

    /**int instead of boolean values because whatever direction has a value of 1
     * will move and 0 will result with 0 speed (no movement).*/
    public void setDirection(int vert, int horiz)
    {
        if(vert > 1 || vert < 0 || horiz > 1 || horiz < 1)
        {
            System.err.println("In class Movement, function setDirection(int vert, int horiz) must" +
                    "contain a value of either 0 or 1 in its parameters.");
            return;
        }

        vertical = vert;
        horizontal = horiz;
    }

    /**currentActor should be an iterator passed*/
    public void move(CreateActor currentActor, float speed)
    {
        //These 2 lines just moves the actor across the map
        currentActor.setActive(true);   //body might not be active for moving
        currentActor.setSpeed(speed * horizontal, speed * vertical);

        //If it reaches at the limit meter, it will respawn
        if(respawn(currentActor))
            currentActor.setPosition(MathUtils.random(xMin, xMax),
                    MathUtils.random(yMin, yMax));
    }

    private boolean respawn(CreateActor currentActor)
    {
        if(((int)currentActor.getX() == limit && vertical == 0) ||
                ((int)currentActor.getY() == limit && horizontal == 0))
        {
            justRespawned = true;
            return true;
        }

        return false;
    }

    public boolean getRespawnVal()
    {
        return justRespawned;
    }

    public void resetRespawnVal()
    {
        justRespawned = false;
    }

    public float getRandomX()
    {
        return MathUtils.random(xMin, xMax);
    }

    public float getRandomY()
    {
        return MathUtils.random(yMin, yMax);
    }
}
