package com.mygdx.main.maps;

/**
 * Created by seacow on 12/17/2017.
 *
 * You can call this interface to select maps in the screen selection.
 */

public interface MapManager
{
    /**You can make multiple separate create functions for different objects
     * but make sure to call those functions inside here*/
    void create();

    /**stage width's and height's are parameters for the purpose that
     * it might be needed for the background image/texture that takes up
     * the whole screen*/
    void display(float stageWidth, float stageHeight);
}
