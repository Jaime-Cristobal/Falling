package com.mygdx.main.actors.creation;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.main.ui.Scaler;

/**
 * Created by seacow on 10/27/2017.
 */

public class CreateBody
{
    private short category;
    private short mask;

    public Body body;
    private BodyDef.BodyType type;
    private BodyDef bodydef;
    private PolygonShape shape;
    private FixtureDef fixDef;

    private float density;
    private float restitution;

    private String ID;

    public CreateBody(BodyDef.BodyType type)
    {
        this.type = type;
        bodydef = new BodyDef();
        fixDef = new FixtureDef();

        category = 0;
        mask = 0;

        density = 0;
        restitution = 0.1f;

        ID = null;
    }

    /**Set filters for collision*/
    public void setFilter(short category, short mask)
    {
        this.category = category;
        this.mask = mask;
    }

    /**Manually set the body's data such as density(weight) and restitution(bounciness)
     * Call before calling create() for it to take effect*/
    public void setData(float density, float restitution, boolean lock)
    {
        this.density = density;
        this.restitution = restitution;

        bodydef.fixedRotation = lock;
    }

    /**This is for cases where multiple objects will have the same name file and want
     * to distinguish the UserData inside the fixtures with a unique set of ID*/
    public void setUniqueID(String ID)
    {
        this.ID = ID;
    }

    /**Bodydef.BodyType sets whether the body tpye is static, kinematic, or dynamic
     *
     * You still have to fix the resolution scaling for data*/
    public void create(String file, World world, float x, float y, float w, float h, boolean isSensor)
    {
        bodydef.type = type;
        bodydef.position.set(x / Scaler.PIXELS_TO_METERS,
                y / Scaler.PIXELS_TO_METERS);                  //box collision at the same dimension as the sprite

        body = world.createBody(bodydef);

        shape = new PolygonShape();
        shape.setAsBox(w / 2 / Scaler.PIXELS_TO_METERS * Scaler.scaleX,
                h / 2 / Scaler.PIXELS_TO_METERS * Scaler.scaleY);      //box collision at the same dimension as the sprite

        fixDef.shape = shape;
        fixDef.restitution = restitution;
        fixDef.density = density;
        fixDef.isSensor = isSensor;
        fixDef.filter.categoryBits = category;       //short something = CATEGORY
        fixDef.filter.maskBits = mask;

        if(ID != null)
            body.createFixture(fixDef).setUserData(ID);
        else if(file != null)
            body.createFixture(fixDef).setUserData(file);
        else    //if its a null file name
            body.createFixture(fixDef);

        body.setActive(false);      //will not move if the body is not active.

        shape.dispose();
    }

    public Filter getFilter() {
        return fixDef.filter;
    }
}
