package com.mygdx.main.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.main.Main;
import com.mygdx.main.animator.Animator;
import com.mygdx.main.ui.Scaler;

/**
 * Created by seacow on 2/17/2018.
 */

public class Splash implements ContactListener
{
    final private Main main;
    final private Animator splash;
    final private ArrayMap<String, Float> region;

    private Object playerData;
    private Object platformData;

    private boolean animate;
    private boolean stopAnimate;
    private FilterDetector detector;

    public Splash(Main mainParam)
    {
        this.main = mainParam;

        region = new ArrayMap<String, Float>();
        region.put("Armature_splash", 3.7f);

        splash = new Animator("splash.atlas", region, main.assetmanager.dataholder);
        splash.setScale(44, 21);
        splash.setPlayback("Armature_splash");

        animate = false;
        stopAnimate = true;
    }

    public void setDetector(FilterDetector detector)
    {
        this.detector = detector;
    }

    public void render(float playerPosX, float playerPosY)
    {
        splash.recordEndTime();
        collision();
        //splash.outValues();


        /**
        if(splash.ifFrameEnd() && animate)
        {
            System.out.println("GARARAR");
            animate = false;
            splash.resetTime();
            //stopAnimate = true;
        }*/
        if(animate && !splash.ifFrameEnd() && !stopAnimate)
        {
            //System.out.println("It's happening");
            splash.render(main.batch, playerPosX / Scaler.PIXELS_TO_METERS, playerPosY / Scaler.PIXELS_TO_METERS - 1.5f);
            //canAnimate = false;
        }

        /**
        if(animate && !splash.ifFrameEnd() && !stopAnimate)
        {
            System.out.println("It's happening");
            splash.render(main.batch, playerPosX / Scaler.PIXELS_TO_METERS, playerPosY / Scaler.PIXELS_TO_METERS - 1.2f);
            //canAnimate = false;
        }*/

        //System.out.println(stopAnimate);
        //System.out.println(animate);
    }

    public void setData(Object player, Object platform)
    {
        playerData = player;
        platformData = platform;
    }

    public void collision()
    {
        if(detector.feedback(FilterID.player_category, FilterID.platform_category) && stopAnimate)
        {
            System.out.println("Contact initiated");
            if(stopAnimate)
            {
                animate = true;
                stopAnimate = false;
            }
        }
        else
        {
            if(splash.ifFrameEnd())
            {
                splash.resetTime();
                stopAnimate = true;
                animate = false;
            }
        }
    }

    /**
     * Set this to setContactListener by calling from World*/
    public ContactListener getListener()
    {
        return this;
    }

    /** Called when two fixtures begin to touch. */
    public void beginContact (Contact contact)
    {
        //if((contact.getFixtureA().getUserData() == playerData && contact.getFixtureB().getUserData() == platformData)
        //        || (contact.getFixtureA().getUserData() == platformData && contact.getFixtureB().getUserData() == playerData))

        if((contact.getFixtureA().getFilterData().categoryBits == FilterID.player_category
                && contact.getFixtureB().getFilterData().categoryBits == FilterID.platform_category)
                || (contact.getFixtureA().getFilterData().categoryBits == FilterID.platform_category
                && contact.getFixtureB().getFilterData().categoryBits == FilterID.player_category)
                && stopAnimate)
        {
            System.out.println("Contact initiated");
            if(stopAnimate)
            {
                animate = true;
                stopAnimate = false;
            }
        }
    }

    /** Called when two fixtures cease to touch. */
    public void endContact (Contact contact)
    {
        System.out.println("No contact");

        if(splash.ifFrameEnd())
        {
            splash.resetTime();
            stopAnimate = true;
            animate = false;
        }
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
        //splash.resetTime();
    }
}
