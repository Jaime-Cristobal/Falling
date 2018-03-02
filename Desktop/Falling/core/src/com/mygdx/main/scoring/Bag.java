package com.mygdx.main.scoring;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.main.Main;
import com.mygdx.main.actors.Movement;
import com.mygdx.main.actors.creation.CreateActor;
import com.mygdx.main.actors.creation.CreateTexture;

/**
 * Created by seacow on 1/9/2018.
 */

public class Bag
{
    private CreateActor actor;
    private Movement movement;

    private float xMin;
    private float xMax;
    private float yMin;
    private float yMax;
    private float speed;

    private float originX;
    private float originY;

    private boolean isContact;

    public Bag(String file, Main main)
    {
        actor = new CreateTexture(file, main, BodyDef.BodyType.DynamicBody);
        movement = new Movement();

        xMin = 0;
        xMax = 0;
        yMin = 0;
        yMax = 0;

        originX = 0;
        originY = 0;

        isContact = false;
    }

    public void setData(float density, float restitution, boolean rotationLock)
    {
        actor.setData(density, restitution, rotationLock);
    }

    public void setSpawn(float xmin, float xmax, float ymin, float ymax)
    {
        xMin = xmin;
        xMax = xmax;
        yMin = ymin;
        yMax = ymax;

        movement.setSpawn(xMin, xMax, yMin, yMax);
    }

    public void setSpeed(float speed)
    {
        this.speed = speed;
    }

    public void setFilter(short category, short mask)
    {
        actor.setFilter(category, mask);
    }

    public void create(World world, float width, float height)
    {
        actor.create(world, MathUtils.random(xMin, xMax), MathUtils.random(yMin, yMax),
                width, height, true);
    }

    public void display()
    {
        movement.move(actor, speed);

        actor.display();
    }

    public void setPosition(float x, float y)
    {
        actor.setPosition(x, y);
    }
}