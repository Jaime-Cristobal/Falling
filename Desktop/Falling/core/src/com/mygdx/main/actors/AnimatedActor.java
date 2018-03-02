package com.mygdx.main.actors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.main.Main;
import com.mygdx.main.actors.creation.CreateActor;
import com.mygdx.main.actors.creation.CreateAnimation;

/**
 * Created by seacow on 12/17/2017.
 */

public class AnimatedActor implements GameActor
{
    final private Main main;
    final private ArrayMap<String, Float> region;
    final private boolean flip;
    private Array<CreateAnimation> actors;
    private Movement movement;
    private BodyDef.BodyType bodyType;

    private String file;
    private float xMin;
    private float xMax;
    private float yMin;
    private float yMax;
    private float width;
    private float height;
    private short objectHex;
    private short playerHex;

    private float density;
    private float resitution;

    public AnimatedActor(String file, ArrayMap<String, Float> region, Main main, boolean flip)
    {
        this.main = main;
        this.region = region;
        this.flip = flip;
        this.file = file;
        actors = new Array<CreateAnimation>();

        objectHex = 0x0000;
        playerHex = 0x0000;

        movement = new Movement();
        movement.setDirection(0, 1);

        bodyType = BodyDef.BodyType.DynamicBody;

        density = 0;
        resitution = 0.1f;
    }

    /**Please use these to randomize by using the LibGdx MathUtensil.random*/
    public void setSpawn(float xmin, float xmax, float ymin, float ymax)
    {
        xMin = xmin;
        xMax = xmax;
        yMin = ymin;
        yMax = ymax;

        movement.setSpawn(xMin, xMax, yMin, yMax);
    }

    /**Because having it automatically take the images' original width and height
     * is too much hassle*/
    public void setResolution(float w, float h)
    {
        width = w;
        height = h;
    }

    /**bodyType is initialized as a default dynamic body*/
    public void setBodyType(BodyDef.BodyType type)
    {
        bodyType = type;
    }

    /**Manually set the body's density(heaviness) and restitution(bounciness)
     * values.
     * The default values are density = 0 and restitution = 0.1f*/
    public void setData(float dens, float resti)
    {
        density = dens;
        resitution = resti;
    }

    public void setFilter(short hex1, short hex2)
    {
        objectHex = hex1;
        playerHex = hex2;
    }

    /**Because we like controlling when our objects respawn*/
    public void setLimit(float value)
    {
        movement.setLimit(value);
    }

    /**
     * setDirection(vertical, horizontal) - which direction has the value
     * of 1 will have movement on that direction
     * */
    public void setMoveVertical()
    {
        movement.setDirection(1, 0);
    }
    /***
     * DO NOT CALL this without calling setSpawn, setFilter, AND setResolution
     * beforehand. If you do, you are a bad mannnnnnnnn.
     * */
    public void create(World world, int amount, boolean isSensor)
    {
        for(int n = 0; n < amount; n++)
        {
            actors.add(new CreateAnimation(file, region, main, bodyType));
            actors.get(n).setFilter(objectHex, playerHex);
            actors.get(n).setData(density, resitution, true);
            actors.get(n).create(world, MathUtils.random(xMin, xMax),
                    MathUtils.random(yMin, yMax), width, height, isSensor);

            if(flip)
                actors.get(n).flip();
        }
    }

    public void displayAll(float speed)
    {
        for(CreateActor iter : actors)
        {
            iter.display();
            movement.move(iter, speed);
        }
    }

    public void display(float speed, int amount)
    {
        int n = 0;

        for(CreateActor iter : actors)
        {
            if(n == amount)
                return;

            iter.display();
            movement.move(iter, speed);

            n++;
        }
    }

    public void setNoGravity()
    {
        for(CreateActor iter : actors)
        {
            iter.setNoGravity();
        }
    }

    public Object getUserData()
    {
        return actors.get(0).getBody().getUserData();
    }
}
