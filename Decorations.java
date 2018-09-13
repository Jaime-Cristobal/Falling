package com.mygdx.main;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.main.objects.Background;

/**
 * Created by FlapJack on 8/29/2017.
 *
 * More comments in BGCreator
 */

public class Decorations
{
    private Array<Background> backgrounds;

    private String file;
    private ArrayMap<String, Float> region;

    /**--------------------------------------------------------
     * These should all be called before calling create()*/

    public Decorations()
    {
        backgrounds = new Array<Background>();
        region = null;
    }

    public void setSpawnArea(float x_min, float x_max, float y_min, float y_max)
    {

    }

    public void setResolution(float width, float height)
    {
        for(int n = 0; n < backgrounds.size; n++)
            backgrounds.get(n).setResolution(width, height);
    }

    /**Only call this if you are loading an animated sprite. Otherwise, you will run into
     * divided by zero errors inside the Animator class as the region inside the atlas cannot
     * be found with a null string value
     */
    public void setAnimation(ArrayMap<String, Float> regionParam)
    {
        this.region = regionParam;
    }

    /** These should all be called before calling create()
     * --------------------------------------------------------*/

    public void create(String fileName, Main main, int amount, boolean horizontal)
    {
        for(int n = 0; n < amount; n++)
        {
            /**
            backgrounds.add(new Background(file, main, horizontal));
            backgrounds.get(n).setResolution(width, height);
            backgrounds.get(n).setSpawnArea(x_min, x_max, y_min, y_max);

            if(region != null)
                backgrounds.get(n).setAnimation(region);
             */
        }
    }

    public void display()
    {
        for(int n = 0; n < backgrounds.size; n++)
            backgrounds.get(n).display();
    }

    public void display(int limit, float speed)
    {
        for(int n = 0; n < backgrounds.size; n++)
            backgrounds.get(n).display(limit, speed);
    }

    public void displayAnimtation(int limit, float speed)
    {
        for(int n = 0; n < backgrounds.size; n++)
            backgrounds.get(n).displayAnimation(limit, speed);
    }

    public int size()
    {
        return backgrounds.size;
    }

    /**You can just enter normal order of numbers for the amount inputed. Don't have to
     * start from 0 and just start at 1.*/
    public boolean getTimeChange(int index)
    {
        return backgrounds.get(index - 1).getTimeChange();
    }

    public void resetTime()
    {
        for(int n = 0; n < backgrounds.size; n++)
            backgrounds.get(n).resetTime();
    }
}
