package com.mygdx.main.objects;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.main.Main;
import com.mygdx.main.actors.creation.CreateActor;
import com.mygdx.main.actors.creation.CreateTexture;
import com.mygdx.main.ui.Scaler;

/**
 * Created by FlapJack on 8/7/2017.
 */

public class Platform
{
    private CreateActor object;
    private String file;

    public Platform()
    {
        object = new CreateTexture(BodyDef.BodyType.StaticBody);
        file = null;
    }

    public Platform(String file_name, Main main_p)
    {
        object = new CreateTexture(file_name, main_p, BodyDef.BodyType.StaticBody);
    }

    public void setFilter(short category, short mask)
    {
        object.setFilter(category, mask);
    }

    public void create(World world, float xVal, float yVal, float w, float h)
    {
        object.create(world, xVal, yVal, w * Scaler.scaleX, h * Scaler.scaleY, false);
    }

    /**Only render if file isn't null, empty box2D bodies do not need to rendered*/
    public void display()
    {
        object.display();
    }

    public void movement()
    {

    }
}