package com.mygdx.main.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.main.actors.creation.CreateAnimation;
import com.mygdx.main.collision.FilterDetector;
import com.mygdx.main.collision.FilterID;
import com.mygdx.main.Main;
import com.mygdx.main.ui.Scaler;


/**
 * Created by FlapJack on 7/22/2017.
 */

public class Player
{
    private final Main main;

    private CreateAnimation actor;
    private InputControl controls;

    private ArrayMap<String, Float> frames;

    private OrthographicCamera camera;

    private String file;
    private float originX;
    private float originY;

    public Player(Main main_p)
    {
        this.main = main_p;
        file = "player.atlas";

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        controls = new InputControl();

        originX = 0;
        originY = 0;

        frames = new ArrayMap<String, Float>();
        frames.put("Armature_down", 3.4f, 0);

        actor = new CreateAnimation(file, frames, main, BodyDef.BodyType.DynamicBody);
    }

    public void createBody(World world, float posX, float posY, float w, float h)
    {
        actor.setFilter(FilterID.player_category, (short) (FilterID.floor_category |
                FilterID.ceiling_category | FilterID.enemy_category | FilterID.platform_category
                | FilterID.coin_category));

        actor.setData(0.5f, 0, true);
        actor.create(world, posX, posY, w, h, false);
    }

    private void setControls(FilterDetector collision)
    {
        controls.running(actor);
    }

    public void display(FilterDetector collision)
    {
        setControls(collision);

        switch(controls.flightPlayback())
        {
            case 0:
                actor.setRegion("Armature_down");
                break;
            default:
                break;
        }

        actor.display();
    }

    public void applyJump()
    {
        actor.applyForce(0, 60);
    }

    public void setInactive()
    {
        actor.setActive(false);
    }

    /**If you need to reset to the original position like after a screen change*/
    public void reset()
    {
        actor.setPosition(originX, originY);
    }

    public GestureDetector getGesture()
    {
        return controls.getGesture();
    }

    public InputProcessor getInputProcessor()
    {
        return controls.getInputProcess();
    }

    public Vector2 getInputPosition()
    {
        return controls.getPosition();
    }

    public float getX()
    {
        return actor.getX() * Scaler.PIXELS_TO_METERS;
    }

    public Body getBody()
    {
        return actor.getBody();
    }

    public float getY()
    {
        return actor.getY() * Scaler.PIXELS_TO_METERS;
    }
}
