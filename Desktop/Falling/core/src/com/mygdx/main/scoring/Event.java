package com.mygdx.main.scoring;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.main.collision.FilterID;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * Created by seacow on 3/12/2018.
 */

public class Event implements ContactListener
{
    private Object actor;
    private Object aActor, bActor;
    private boolean addScore, subScore, isSplash;
    private boolean collision;
    private short catID;

    private short cat1, cat2;

    public Event()
    {
        addScore = false;
        subScore = false;
        isSplash = false;
        catID = 0;

        collision = false;
        cat1 = 0;
        cat2 = 0;
    }

    /**pass the box2D Body .getUserData here to check if the coin is colliding
     * with the player or collector*/
    public void checkActor(Object actor, short catID)
    {
        this.actor = actor;
        this.catID = catID;
    }

    public void checkActor(short cat1, short cat2)
    {
        this.cat1 = cat1;
        this.cat2 = cat2;
    }

    public boolean checkCollision(Object actorData)
    {
        if(actorData == aActor || actorData == bActor)
            return true;

        return false;
    }

    public boolean getAddScore()
    {
        return addScore;
    }

    public boolean getSubScore()
    {
        return subScore;
    }

    public boolean getSpash()
    {
        return isSplash;
    }

    public boolean isColliding()
    {
        return collision;
    }

    /**Register this to box2D World*/
    public ContactListener getListerner()
    {
        return this;
    }

    /** Called when two fixtures begin to touch. */
    /**
     * What is being checked depends on the checkActor(...) function
     * checkActor(...) --> is it colliding with ---> FilterID*/
    public void beginContact (Contact contact)
    {
        /**
        if((actor == contact.getFixtureA().getUserData()
                && catID == contact.getFixtureB().getFilterData().categoryBits)
                || (actor == contact.getFixtureB().getUserData()
                && catID == contact.getFixtureA().getFilterData().categoryBits))
        {
            System.out.println("Colliding");
            collision = true;
        }*/

        aActor = contact.getFixtureA().getBody().getUserData();
        bActor = contact.getFixtureB().getBody().getUserData();

        if((cat1 == contact.getFixtureA().getFilterData().categoryBits
                && cat2 == contact.getFixtureB().getFilterData().categoryBits)
                || (cat1 == contact.getFixtureB().getFilterData().categoryBits
                && cat2 == contact.getFixtureA().getFilterData().categoryBits))
        {
            collision = true;
        }
    }

    /** Called when two fixtures cease to touch. */
    public void endContact (Contact contact)
    {
        collision = false;
        //leave blank
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
        //leave blank
    }

    /*
     * This lets you inspect a contact after the solver is finished. This is useful for inspecting impulses. Note: the contact
     * manifold does not include time of impact impulses, which can be arbitrarily large if the sub-step is small. Hence the
     * impulse is provided explicitly in a separate data structure. Note: this is only called for contacts that are touching,
     * solid, and awake.
     */
    public void postSolve (Contact contact, ContactImpulse impulse)
    {
        //leave blank
    }
}
