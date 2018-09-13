package com.mygdx.main.scoring;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.main.actors.creation.CreateActor;
import com.mygdx.main.collision.FilterID;
import com.mygdx.main.player.Player;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * Created by seacow on 3/12/2018.
 */

public class Event implements ContactListener
{
    private Object actor;
    private boolean addScore, destroyCoin, subScore, isSplash;
    private boolean playerCol, banditCol;

    private Player player;
    private Character coin, collector;

    public Event()
    {
        addScore = false;
        subScore = false;
        isSplash = false;
        playerCol = false;
        banditCol = false;
        destroyCoin = false;

        player = null;
        coin = null;
        collector = null;
    }

    public void setCoin(Character coin)
    {
        this.coin = coin;
    }

    public void setCollector(Character collector)
    {
        this.collector = collector;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public boolean isAddScore()
    {
        return addScore;
    }

    public boolean isDestroyCoin()
    {
        return destroyCoin;
    }

    public boolean isBanditCollide()
    {
        return banditCol;
    }

    public void resetPlayerCollision()
    {
        playerCol = false;
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
        Object aActor = contact.getFixtureA().getBody().getUserData();
        Object bActor = contact.getFixtureB().getBody().getUserData();
        short filterA = contact.getFixtureA().getFilterData().categoryBits;
        short filterB = contact.getFixtureB().getFilterData().categoryBits;

        //if((coin.getUserData() == aActor && player.getBody().getUserData() == bActor)
        //        || (coin.getUserData() == bActor && player.getBody().getUserData() == aActor))
        if((FilterID.coin_category == filterA && FilterID.player_category == filterB)
                || (FilterID.coin_category == filterB && FilterID.player_category == filterA))
        {
            System.out.println("rekt");
            playerCol = true;
            coin.setEvent(player.getX() * 1000 * Gdx.graphics.getDeltaTime(),
                    player.getY() * 1000 * Gdx.graphics.getDeltaTime());
        }

        if((FilterID.collector_category == filterA && FilterID.player_category == filterB)
                || (FilterID.collector_category == filterB && FilterID.player_category == filterA))
        {
            System.out.println("HIT");
            addScore = true;
        }

        else if((FilterID.collector_category == filterA && FilterID.coin_category == filterB)
                || (FilterID.collector_category == filterB && FilterID.coin_category == filterA)
                && !playerCol)
        {
            System.out.println("DELETE BOI");
            destroyCoin = true;
        }

        if((FilterID.bandit_category == filterA && FilterID.coin_category == filterB)
                || (FilterID.bandit_category == filterB && FilterID.coin_category == filterA)
                && !playerCol)
        {
            System.out.println("DELETE BOI");
            destroyCoin = true;
        }
        else if((FilterID.bandit_category == filterA && FilterID.player_category == filterB)
                || (FilterID.bandit_category == filterB && FilterID.player_category == filterA))
        {
            System.out.println("BANDIT HIT");
            banditCol = true;
        }

        if((FilterID.enemy_category == filterA && FilterID.coin_category == filterB)
                || (FilterID.enemy_category == filterB && FilterID.coin_category == filterA)
                && !playerCol)
        {
            System.out.println("DELETE BOI");
            destroyCoin = true;
        }
    }

    /** Called when two fixtures cease to touch. */
    public void endContact (Contact contact)
    {
        addScore = false;
        destroyCoin = false;
        banditCol = false;
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
        Object aActor = contact.getFixtureA().getBody().getUserData();
        Object bActor = contact.getFixtureB().getBody().getUserData();
        short filterA = contact.getFixtureA().getFilterData().categoryBits;
        short filterB = contact.getFixtureB().getFilterData().categoryBits;

        if((FilterID.coin_category == filterA && FilterID.player_category == filterB)
                || (FilterID.coin_category == filterB && FilterID.player_category == filterA))
        {
            contact.setEnabled(false);
        }
        if((FilterID.collector_category == filterA && FilterID.player_category == filterB)
                || (FilterID.collector_category == filterB && FilterID.player_category == filterA))
        {
            contact.setEnabled(false);
        }
        if((FilterID.bandit_category == filterA && FilterID.player_category == filterB)
                || (FilterID.bandit_category == filterB && FilterID.player_category == filterA))
        {
            contact.setEnabled(false);
        }
        if((FilterID.bandit_category == filterA && FilterID.coin_category == filterB)
                || (FilterID.bandit_category == filterB && FilterID.coin_category == filterA))
        {
            contact.setEnabled(false);
        }
        if((FilterID.enemy_category == filterA && FilterID.coin_category == filterB)
                || (FilterID.enemy_category == filterB && FilterID.coin_category == filterA))
        {
            contact.setEnabled(false);
        }
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
        contact.setEnabled(true);
    }
}
