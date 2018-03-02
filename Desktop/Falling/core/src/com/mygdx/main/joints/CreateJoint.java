package com.mygdx.main.joints;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.mygdx.main.ui.Scaler;

/**
 * Created by seacow on 12/22/2017.
 */

public class CreateJoint
{
    private RevoluteJointDef definition;
    private RevoluteJoint joint;

    public CreateJoint()
    {
        definition = new RevoluteJointDef();
    }

    /**
     * Sets the settings between two bodies.
     *
     * Connected as false means the joint between the two bodies
     * will not follow normal collision rules such as two dynamics bodies
     * with false sensors going through each other instead of sticking to
     * each other when colliding.*/
    public void setData(Body bodyA, Body bodyB, boolean connected)
    {
        definition.bodyA = bodyA;
        definition.bodyB = bodyB;
        definition.collideConnected = connected;

        /**
        definition.initialize(bodyA, bodyB, new Vector2(bodyB.getPosition().x / Scaler.PIXELS_TO_METERS,
                bodyB.getPosition().y / Scaler.PIXELS_TO_METERS));

        definition.lowerAngle = -0.5f * 3.14f;
        definition.upperAngle = 0.25f * 3.14f;

        definition.enableLimit = true;
        definition.enableMotor = true;
         */
    }

    /**Call this after calling setData as the JointDef will contain no
     * settings inside*/
    public void create(World world)
    {
        joint = (RevoluteJoint) world.createJoint(definition);
    }

    public void test(float x, float y)
    {
        joint.getBodyB().setTransform(x, y, joint.getBodyA().getAngle());
    }

    /**When destroying a body, destroy the joint first before destroying
     * the body.*/
    public void destroy(World world)
    {
        System.out.println("DESTROYYYY LERRROOOYY JEEEENNNNKKIIINSSS");
        world.destroyJoint(joint);
    }
}