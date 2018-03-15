package com.mygdx.main.scoring;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.main.Main;
import com.mygdx.main.actors.Movement;
import com.mygdx.main.actors.creation.CreateActor;
import com.mygdx.main.actors.creation.CreateAnimation;
import com.mygdx.main.actors.creation.CreateTexture;

/**
 * Created by seacow on 3/11/2018.
 */

public class Character
{
    final private Main main;
    private CreateActor actor;
    private Movement movement;                         //for positioning/moving sprite
    private float width, height;
    private float xMin, xMax, yMin, yMax;
    private String file;
    private Vector2 playerPos;
    private boolean followPlayer;

    public Character(String file, float width, float height, Main main)
    {
        this.main = main;
        movement = new Movement();
        movement.setLimit(65);
        movement.setDirection(1, 0);
        xMin = 0;
        xMax = 0;
        yMin = 0;
        yMax = 0;
        this.width = width;
        this.height = height;
        this.file = file;
        playerPos = null;
        followPlayer = false;
    }

    public void setSpawn(float xMin, float xMax, float yMin, float yMax)
    {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        movement.setSpawn(xMin, xMax, yMin, yMax);
    }

    /**For image files with no animations
     * Only either call createTexture or createAnimation -- DO NOT CALL BOTH AT THE SAME TIME
     * --------------------
     * xPos and yPos in create(...) are 0 because the Movement class will handle the spawning
     *      (look at movement.setSpawn(...) in the above function)
     * @param id -> So each coin is different from another coin. Typically used for differentiating collisions*/
    public void createTexture(World world, int id, float density, float restitution, short category, short mask)
    {
        actor = new CreateTexture(file, main, BodyDef.BodyType.DynamicBody, 0);
        actor.setUniqueID(file + id);
        actor.setData(density, restitution, true);
        actor.setFilter(category, mask);
        actor.create(world, MathUtils.random(xMin, xMax), MathUtils.random(yMin, yMax),
                width, height, false);
    }

    /**For animated sprites. Typically .atlas with animations packaged into a .png
     * Only either call createTexture or createAnimation -- DO NOT CALL BOTH AT THE SAME TIME*/
    public void createAnimation(World world, int id, float density, float restitution, short category, short mask, ArrayMap<String, Float> region)
    {
        actor = new CreateAnimation(file, region, main, BodyDef.BodyType.DynamicBody);
        actor.setUniqueID(file + id);
        actor.setData(density, restitution, true);
        actor.setFilter(category, mask);
        actor.create(world, MathUtils.random(xMin, xMax), MathUtils.random(yMin, yMax),
                width, height, false);
    }

    public void display(float speed)
    {
        if(followPlayer)
        {
            movement.setPosition(playerPos);
        }
        else
        {
            movement.move(actor, speed);
        }

        actor.display();
    }

    public void forceRespawn()
    {
        movement.resetRespawnVal();
    }

    public void setFollowPlayer(Vector2 playerPos)
    {
        followPlayer = true;
        this.playerPos = playerPos;
    }

    public boolean checkCollision(Event event, short category)
    {
        event.checkActor(actor.getBody().getUserData(), category);
        return event.isColliding();
    }

    public Object getUserData()
    {
        return actor.getBody().getUserData();
    }
}
