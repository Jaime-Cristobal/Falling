package com.mygdx.main.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.main.Main;
import com.mygdx.main.actors.Movement;
import com.mygdx.main.actors.creation.CreateActor;
import com.mygdx.main.actors.creation.CreateAnimation;
import com.mygdx.main.actors.creation.CreateTexture;
import com.mygdx.main.collision.FilterID;
import com.mygdx.main.ui.Scaler;

/**
 * Created by seacow on 12/25/2017.
 *
 * Trash items are a special kind of item that they use a singularity
 * contact listener instead of a global filter contact.
 */

public class Trash implements ContactListener
{
    private CreateActor actor;

    private float width;
    private float height;

    private float magnetVel;
    private Vector2 playerPos;

    private Movement movement;

    private boolean playerContact;    //contact with player
    private boolean fall;

    private float speedX;
    private float speedY;

    private Trash()
    {
        width = 0;
        height = 0;
        magnetVel = 0;

        movement = new Movement();

        playerContact = false;

        fall = false;
    }

    public Trash(String file, Main main)
    {
        this();

        actor = new CreateTexture(file, main, BodyDef.BodyType.DynamicBody);
    }

    /**If you want to rotate a texture body (NOTE: non-animated)*/
    public Trash(String file, Main main, float rotation)
    {
        this();

        actor = new CreateTexture(file, main, BodyDef.BodyType.DynamicBody, rotation);
    }

    public Trash(String file, Main main, ArrayMap<String, Float> region)
    {
        this();

        actor = new CreateAnimation(file, region, main, BodyDef.BodyType.DynamicBody);
    }

    public void setSpawn(float xMin, float xMax, float yMin, float yMax)
    {
        movement.setSpawn(xMin, xMax, yMin, yMax);
    }

    public void setData(float widthVal, float heightVal, float density, float restitution)
    {
        width = widthVal;
        height = heightVal;

        actor.setData(density, restitution, false);
    }

    /**initial speed. Call before changeSpeed() if you want a reference
     * to the initial speed before changing*/
    public void setSpeed(float xVel, float yVel)
    {
        speedX = xVel;
        speedY = yVel;
    }

    /**for changing the speed after create()*/
    public void changeSpeed(float xVel, float yVel)
    {
        actor.setSpeed(xVel, yVel);
    }

    public void create(World world, String ID)
    {
        actor.setFilter(FilterID.trash_category, (short)(FilterID.player_category | FilterID.collector_category));
        actor.setUniqueID(ID);
        actor.create(world, movement.getRandomX(), movement.getRandomY(), width, height, true);
        actor.setNoGravity();
    }

    /**Used to see if the player is nearby*/
    private void setPlayerPos(Vector2 position)
    {
        playerPos = position;
    }

    public void followPlayer()
    {
        actor.setPosition(playerPos.x * Scaler.PIXELS_TO_METERS, playerPos.y * Scaler.PIXELS_TO_METERS);
    }

    /**speed of positive value indicates going from left to right. negative is
     * right to left
     *
     * Vector2 position -> when grabbed by the player, trash will follow the player's position*/
    public void display(Vector2 position)
    {
        setPlayerPos(position);
        movement.move(actor, 0);

        actor.display();

        /**
        if(fall)
            changeSpeed(0, -2000);
        else if(playerContact)
            followPlayer();
        else
            changeSpeed(speedX, speedY);
         */
    }

    public void setListerner(World world)
    {
        world.setContactListener(this);
    }

    public Body getBody()
    {
        return actor.getBody();
    }

    public boolean getRespawnVal()
    {
        return movement.getRespawnVal();
    }

    public void resetRespawnVal()
    {
        movement.resetRespawnVal();
    }

    /**Trash is inside the magnets range*/
    public boolean getContact()
    {
        return playerContact;
    }

    public void setFree()
    {
        playerContact = false;
    }

    public void makeFall(boolean option)
    {
        fall = option;
    }

    public boolean getFall()
    {
        return fall;
    }

    /** Called when two fixtures begin to touch. */
    public void beginContact (Contact contact)
    {
        Short filterA = contact.getFixtureA().getFilterData().categoryBits;
        Short filterB = contact.getFixtureB().getFilterData().categoryBits;

        /**
        //if trash collides with the player
        if((filterA == FilterID.player_category && filterB == FilterID.trash_category) ||
                (filterA == FilterID.trash_category && filterB == FilterID.player_category))
        {
            System.out.println("PROBOBOBOB");
            playerContact = true;
        }
         */

        /**
        if((contact.getFixtureA().getBody().getUserData() == actor.getBody().getUserData()
                && filterB == FilterID.player_category) ||
                (contact.getFixtureB().getBody().getUserData() == actor.getBody().getUserData()
                && filterA == FilterID.player_category))
        {
            System.out.println("PROBOBOBOB");
            playerContact = true;
        }

        //if trash collides with the collector
        if((filterA == FilterID.collector_category && filterB == FilterID.trash_category) ||
                (filterA == FilterID.trash_category && filterB == FilterID.collector_category))
        {
            System.out.println("Caching");
        }
         */
    }

    /** Called when two fixtures cease to touch. */
    public void endContact (Contact contact)
    {
        playerContact = false;
    }

    /*
     * This is called after a contact is updated. This allows you to inspect a contact before it goes to the solver. If you are
     * careful, you can modify the contact manifold (e.g. disable contact). A copy of the old manifold is provided so that you can
     * detect changes. Note: this is called only for awake bodies. Note: this is called even when the number of contact points is
     * zero. Note: this is not called for sensors. Note: if you set the number of contact points to zero, you will not get an
     * EndContact callback. However, you may get a BeginContact callback the next step.
     */
    public void preSolve (Contact contact, Manifold oldManifold)
    {
        Short filterA = contact.getFixtureA().getFilterData().categoryBits;
        Short filterB = contact.getFixtureB().getFilterData().categoryBits;

        if(contact.getFixtureA().getFilterData().categoryBits == FilterID.enemy_category
                || contact.getFixtureB().getFilterData().categoryBits == FilterID.enemy_category)
            contact.setEnabled(false);
    }

    /*
     * This lets you inspect a contact after the solver is finished. This is useful for inspecting impulses. Note: the contact
     * manifold does not include time of impact impulses, which can be arbitrarily large if the sub-step is small. Hence the
     * impulse is provided explicitly in a separate data structure. Note: this is only called for contacts that are touching,
     * solid, and awake.
     */
    public void postSolve (Contact contact, ContactImpulse impulse)
    {

    }
}
