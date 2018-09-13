package com.mygdx.main.player;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.main.actors.creation.CreateAnimation;
import com.mygdx.main.collision.FilterDetector;
import com.mygdx.main.collision.FilterID;
import com.mygdx.main.ui.Scaler;


/**
 * Created by seacow on 9/8/2017.
 *
 * Movement through the x and y planes are done through box2D's linear velocity methods
 */

public class InputControl implements GestureListener, InputProcessor
{
    private float lastPosX;
    private float lastPosY;
    private boolean flip;
    private boolean flight;

    private Vector3 touchPos;
    private Vector2 currentPos;
    private Vector2 placement;

    private float xPos;
    private float yPos;

    private boolean run;
    private boolean hop;
    private boolean fly;
    private boolean contact;

    private OrthographicCamera camera;

    final private GestureDetector detector;

    public InputControl()
    {
        touchPos = new Vector3();
        currentPos = new Vector2();
        placement = new Vector2();

        run = false;
        hop = false;
        contact = false;

        detector = new GestureDetector(this);

        xPos = 0;
        yPos = 0;
        lastPosX = 0;
        lastPosY = 0;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 680);
    }

    public void setLastPosX(int val)
    {
        lastPosX = val;
    }

    public void floorContact(FilterDetector collision)
    {
        if(collision.FloorFeedback(FilterID.player_category, FilterID.floor_category))
        {
            contact = true;
            fly = false;
            hop = false;
        }
        else
        {
            contact = false;
            fly = true;
        }
    }

    /**contact has to be true for this to work*/
    public void detectIdle(float xVelocity)
    {
        if(xVelocity > -1 && xVelocity < 1)
        {
            run = false;
        }
    }

    /**xPos is set from setX below. The value is taken from the input methods below*/
    public void running(CreateAnimation actor)
    {
        //actor.setSpeed(placement.x * 2000, actor.getVelY());  //test this with parachute

        actor.setSpeed(placement.x * 2000, -500);

        currentPos.set(actor.getX(), actor.getY());

        placement.set((xPos / 9f) / Scaler.scaleX, 0);  //normalizing vector
        placement.sub(currentPos);
        placement.nor();

        if(xPos < lastPosX  && actor.getWidth() > 0)
        {
            actor.flip();
        }
        else if(xPos > lastPosX && actor.getWidth() < 0)
        {
            actor.flip();
        }

        //actor.getBody().getPosition().y -= 0.5f;
    }

    public Vector2 getPosition()
    {
        return placement;
    }

    public int flightPlayback()
    {
        if(flight)
            return  0;
        if(yPos < lastPosY && !flight)
            return 1;
        if(yPos > lastPosY && !flight)
        return 2;

        return -1;
    }

    /**Used to trigger animations. ex: 1 = RUN_ANIMATION...etc.*/
    public int actions()
    {
        if(run)
            return 1;
        else if(hop && !contact)
            return 2;

        return 0;
    }

    private void setPos(float x, float y)
    {
        lastPosX = xPos;
        lastPosY = yPos;

        touchPos.set(x, y, 0);
        camera.unproject(touchPos);
        xPos = touchPos.x - 32f;
        yPos = touchPos.y - 32f;
    }

    /**Send this to the inputProcessor so the touch control methods below work*/
    public GestureDetector getGesture()
    {
        return detector;
    }

    public InputProcessor getInputProcess()
    {
        return this;    //returns the inputprocessor inherited
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button)
    {
        /**
        if(contact)
        {
            fly = true;
            run = false;
        }
        else
        {
            fly = true;
            flight = false;
            run = false;
        }

        setPos(x, 0);
         */

        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button)
    {
        return false;
    }

    @Override
    public boolean longPress(float x, float y)
    {
        /**
        if(contact)
        {
            fly = true;
            run = false;
        }
        else
        {
            fly = true;
            flight = false;
            run = false;
        }

        setPos(x, 0);
         */

        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {

        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {

        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean zoom (float originalDistance, float currentDistance){

        return false;
    }

    @Override
    public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer)
    {
        return false;
    }
    @Override
    public void pinchStop () {
    }

    /** Called when a key was pressed
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed */
    public boolean keyDown (int keycode)
    {
        return false;
    }

    /** Called when a key was released
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed */
    public boolean keyUp (int keycode)
    {
        run = false;

        return false;
    }

    /** Called when a key was typed
     *
     * @param character The character
     * @return whether the input was processed */
    public boolean keyTyped (char character)
    {
        return false;
    }

    /** Called when the screen was touched or a mouse button was pressed. The button parameter will be {@link Input.Buttons#LEFT} on iOS.
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @param pointer the pointer for the event.
     * @param button the button
     * @return whether the input was processed */
    public boolean touchDown (int screenX, int screenY, int pointer, int button)
    {
        if(contact)
        {
            run = true;
            setPos(screenX, 0);
        }
        else
        {
            fly = true;
            setPos(screenX, screenY);
        }

        return false;
    }

    /** Called when a finger was lifted or a mouse button was released. The button parameter will be {@link Input.Buttons#LEFT} on iOS.
     * @param pointer the pointer for the event.
     * @param button the button
     * @return whether the input was processed */
    public boolean touchUp (int screenX, int screenY, int pointer, int button)
    {
        run = false;
        hop = false;

        return false;
    }

    /** Called when a finger or the mouse was dragged.
     * @param pointer the pointer for the event.
     * @return whether the input was processed */
    public boolean touchDragged (int screenX, int screenY, int pointer)
    {
        setPos(screenX, 0);

        return true;
    }

    /** Called when the mouse was moved without any buttons being pressed. Will not be called on iOS.
     * @return whether the input was processed */
    public boolean mouseMoved (int screenX, int screenY){
        return false;
    }

    /** Called when the mouse wheel was scrolled. Will not be called on iOS.
     * @param amount the scroll amount, -1 or 1 depending on the direction the wheel was scrolled.
     * @return whether the input was processed. */
    public boolean scrolled (int amount)
    {
        return false;
    }
}
