package com.mygdx.main.scoring;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.main.Main;
import com.mygdx.main.enemies.Trash;

/**
 * Created by seacow on 1/11/2018.
 */

public class Storage implements GestureListener
{
    private GestureDetector detector;

    private Array<Trash> trashes;
    private boolean isFull;

    private Vector2 playerPos;

    private int flingCount;

    public Storage()
    {
        detector = new GestureDetector(this);

        trashes = new Array<Trash>();
        isFull = false;

        //playerPos = new Vector2();

        flingCount = 0;
    }

    public void createTrash(Main main, World world)
    {
        for(int n = 0; n < 10; n++)
        {
            trashes.add(new Trash("rock.png", main));
            trashes.get(n).setListerner(world);
            trashes.get(n).setData(8, 32, 0.5f, 0.1f);
            trashes.get(n).setSpawn(1000, 1000, 1000, 1000);
            trashes.get(n).create(world, "" + n);
        }
    }

    public void display(Vector2 playerPos)
    {
        //for(Trash iter : trashes)
        //    iter.display(playerPos);

        for(int n = 0; n < flingCount; n++)
        {
            trashes.get(n).display(playerPos);

            if(!trashes.get(n).getRespawnVal())
            {
                trashes.get(n).resetRespawnVal();
                --flingCount;
            }
        }
    }

    public GestureDetector getGesture()
    {
        return detector;
    }

    /** @see InputProcessor#touchDown(int, int, int, int) */
    public boolean touchDown (float x, float y, int pointer, int button)
    {
        return false;
    }

    /** Called when a tap occured. A tap happens if a touch went down on the screen and was lifted again without moving outside
     * of the tap square. The tap square is a rectangular area around the initial touch position as specified on construction
     * time of the {@link GestureDetector}.
     * @param count the number of taps. */
    public boolean tap (float x, float y, int count, int button)
    {
        return false;
    }

    public boolean longPress (float x, float y)
    {
        return false;
    }

    /** Called when the user dragged a finger over the screen and lifted it. Reports the last known velocity of the finger in
     * pixels per second.
     * @param velocityX velocity on x in seconds
     * @param velocityY velocity on y in seconds */
    public boolean fling (float velocityX, float velocityY, int button)
    {
        flingCount++;

        return true;
    }

    /** Called when the user drags a finger over the screen.
     * @param deltaX the difference in pixels to the last drag event on x.
     * @param deltaY the difference in pixels to the last drag event on y. */
    public boolean pan (float x, float y, float deltaX, float deltaY)
    {
        return false;
    }

    /** Called when no longer panning. */
    public boolean panStop (float x, float y, int pointer, int button)
    {
        return false;
    }

    /** Called when the user performs a pinch zoom gesture. The original distance is the distance in pixels when the gesture
     * started.
     * @param initialDistance distance between fingers when the gesture started.
     * @param distance current distance between fingers. */
    public boolean zoom (float initialDistance, float distance)
    {
        return false;
    }

    /** Called when a user performs a pinch zoom gesture. Reports the initial positions of the two involved fingers and their
     * current positions.
     * @param initialPointer1
     * @param initialPointer2
     * @param pointer1
     * @param pointer2 */
    public boolean pinch (Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2)
    {
        return false;
    }

    /** Called when no longer pinching. */
    public void pinchStop ()
    {
        //put stuff here
    }
}
