package com.mygdx.main.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.main.Main;

/**
 * Created by seacow on 12/26/2017.
 */

public class TrashManager
{
    private Array<Trash> trashes;
    private float rotation;

    private float xMin;
    private float xMax;
    private float yMin;
    private float yMax;
    private float x;
    private float y;

    private float width;
    private float height;
    private float density;
    private float restitution;

    private float speedX;
    private float speedY;

    public TrashManager()
    {
        trashes = new Array<Trash>();

        rotation = 0;
        xMin = 0;
        xMax = 0;
        yMin = 0;
        yMax = 0;

        width = 0;
        height = 0;
        density = 0;
        restitution = 0;

        speedX = 0;
        speedY = 0;
    }

    /**All the objects inside actors will spawn anywhere the range limits
     * given.*/
    public void setSpawn(float xmin, float xmax, float ymin, float ymax)
    {
        xMin = xmin;
        xMax = xmax;
        yMin = ymin;
        yMax = ymax;
    }

    public void invertSpawn()
    {
        xMin *= -1;
        xMax *= -1;
        yMin *= -1;
        yMax *= -1;
    }

    /**NOTE: Always call this before adding an item. These will
     * be the data settings for the objects.
     *
     * When adding a new object to the actors container, setData should be
     * called before adding the object. Calling it after the add functions
     * will results with data not being saved for that particular object.
     *
     * Data will also be saved into the next item called if it setData is
     * called after calling the add function*/
    public void setData(float widthVal, float heightVal, float densityVal, float restitutionVal)
    {
        width = widthVal;
        height = heightVal;
        density = densityVal;
        restitution = restitutionVal;
    }

    /**The same comment for setData(~~~) applies here*/
    public void setSpeed(float xVel, float yVel)
    {
        speedX = xVel;
        speedY = yVel;
    }

    public void setRotation(float value)
    {
        rotation = value;
    }

    public void addTexture(String file, Main main, World world, int amount)
    {
        for(int n = 0; n < amount; n++)
        {
            trashes.add(new Trash(file, main, rotation));
            trashes.get(n).setSpawn(xMin, xMax, yMin, yMax);
            trashes.get(n).setData(width, height, density, restitution);
            trashes.get(n).setListerner(world);
            trashes.get(n).create(world, "Trash" + n);
            trashes.get(n).setSpeed(speedX, speedY);
        }
    }

    public void addAnimation(String file, Main main, World world, ArrayMap<String, Float> region, int amount)
    {
        for(int n = 0; n < amount; n++)
        {
            trashes.add(new Trash(file, main, region));
            trashes.get(n).setSpawn(xMin, xMax, yMin, yMax);
            trashes.get(n).setData(width, height, density, restitution);
            trashes.get(n).setListerner(world);
            trashes.get(n).create(world, "trash" + n);
            trashes.get(n).setSpeed(speedX, speedY);
        }
    }

    public void display(Vector2 position)
    {
        for(Trash iter : trashes)
        {
            if(iter.getFall())
            {
                iter.display(position);
            }

            if(iter.getRespawnVal())
                iter.makeFall(false);

            if(iter.getFall())
                iter.changeSpeed(0, -2000);
            else if(iter.getContact())
                iter.followPlayer();
            else
                iter.changeSpeed(speedX, speedY);
        }
    }

    public void setFree()
    {
        for(int n = 0; n < 1; n++)
        {
            trashes.get(n).makeFall(true);      //rename makeFall; terrible func name
        }
    }
}