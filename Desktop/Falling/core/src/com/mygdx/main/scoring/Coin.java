package com.mygdx.main.scoring;

import com.badlogic.gdx.math.MathUtils;
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

/**
 * Created by seacow on 3/11/2018.
 */

public class Coin implements ContactListener
{
    final private Main main;
    private CreateActor actor;
    private Movement movement;                         //for positioning/moving sprite
    private float xMin, xMax, yMin, yMax;              //for spawning
    private float width, height;
    private String file;
    private short category, mask;

    public Coin(String file, float width, float height, Main main)
    {
        this.main = main;
        movement = new Movement();
        movement.setDirection(1, 0);
        xMin = 0;
        xMax = 0;
        yMin = 0;
        yMax = 0;
        this.width = width;
        this.height = height;
        this.file = file;
    }

    public void setSpawn(float xMin, float xMax, float yMin, float yMax)
    {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }

    /**For image files with no animations
     * Only either call createTexture or createAnimation -- DO NOT CALL BOTH AT THE SAME TIME*/
    public void createTexture(World world, float density, float restitution)
    {
        actor = new CreateTexture(file, main, BodyDef.BodyType.DynamicBody, 0);
        actor.setData(density, restitution, true);
        actor.setFilter(FilterID.coin_category, FilterID.player_category);
        actor.create(world, MathUtils.random(), MathUtils.random(), width, height, false);
    }

    /**For animated sprites. Typically .atlas with animations packaged into a .png
     * Only either call createTexture or createAnimation -- DO NOT CALL BOTH AT THE SAME TIME*/
    public void createAnimation(World world, float density, float restitution, ArrayMap<String, Float> region)
    {
        actor = new CreateAnimation(file, region, main, BodyDef.BodyType.DynamicBody);
        actor.setData(density, restitution, true);
        actor.setFilter(FilterID.coin_category, FilterID.player_category);
        actor.create(world, MathUtils.random(), MathUtils.random(), width, height, false);
    }

    public void display()
    {
        movement.move(actor, 500);
        actor.display();
    }

    /** Called when two fixtures begin to touch. */
    public void beginContact (Contact contact)
    {
        
    }

    /** Called when two fixtures cease to touch. */
    public void endContact (Contact contact)
    {

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
