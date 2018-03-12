package com.mygdx.main.scoring;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.main.collision.FilterID;

/**
 * Created by seacow on 3/12/2018.
 */

public class Scoring implements ContactListener
{
    private Object actor;

    /**pass the box2D Body .getUserData here to check if the coin is colliding
     * with the player or collector*/
    public void checkActor(Object actor)
    {
        this.actor = actor;
    }

    /**Register this to box2D World*/
    public ContactListener getListerner()
    {
        return this;
    }

    /** Called when two fixtures begin to touch. */
    public void beginContact (Contact contact)
    {
        if((actor == contact.getFixtureA().getUserData()
                && FilterID.player_category == contact.getFixtureB().getFilterData().categoryBits)
                || (actor == contact.getFixtureB().getUserData()
                && FilterID.player_category == contact.getFixtureA().getFilterData().categoryBits))
        {
            System.out.println("Player has touched coin " + actor);
        }

        if((actor == contact.getFixtureA().getUserData()
                && FilterID.collector_category == contact.getFixtureB().getFilterData().categoryBits)
                || (actor == contact.getFixtureB().getUserData()
                && FilterID.collector_category == contact.getFixtureA().getFilterData().categoryBits))
        {
            System.out.println("Coin has touched collector " + actor);
        }
    }

    /** Called when two fixtures cease to touch. */
    public void endContact (Contact contact)
    {
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
