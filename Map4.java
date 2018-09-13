package com.mygdx.main.maps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.mygdx.main.Main;
import com.mygdx.main.actors.GameActor;
import com.mygdx.main.actors.TextureActor;
import com.mygdx.main.actors.creation.CreateTexture;
import com.mygdx.main.animator.Animator;
import com.mygdx.main.collision.FilterID;
import com.mygdx.main.objects.Background;
import com.mygdx.main.ui.Scaler;

/**
 * Created by seacow on 12/15/2017.
 */

public class Map4 implements MapManager
{
    final private Main main;
    final private World world;
    private ArrayMap<String, Float> backRegion;

    final private ArrayMap<String, Background> branches;
    final private ArrayMap<String, Background> cloudsBack;
    final private ArrayMap<String, Background> cloudsFront;
    final private ArrayMap<String, GameActor> enemies;

    final private Background smoke1, smoke2;

    public Map4(Main mainParam, World worldParam)
    {
        this.main = mainParam;
        backRegion = new ArrayMap<String, Float>();
        backRegion.put("Armature_move", 3.7f, 0);
        backRegion.put("Armature_splash", 3.7f, 0);

        branches = new ArrayMap<String, Background>();
        cloudsBack = new ArrayMap<String, Background>();
        cloudsFront = new ArrayMap<String, Background>();
        enemies = new ArrayMap<String, GameActor>();

        smoke1 = new Background("smoke_1.png", main, true);
        smoke2 = new Background("smoke_2.png", main, true);

        this.world = worldParam;
    }

    public void create()
    {
        //reateBranch();
        createClouds();
        createEnemy();

        smoke1.setSpawnArea(450, 700, 30, 30);
        smoke1.setResolution(403, 185);

        smoke2.setSpawnArea(450, 700, -35, -35);
        smoke2.setResolution(403, 204);
    }

    private void createBranch()
    {
        String name = " ";      //So the branches have a unique ID at the end of the name

        //branch1.png - 4 on the screen
        for(int n = 0; n < 4; n++)
        {
            name = "branch" + n;
            branches.put(name, new Background("branch1.png", main, false));
            branches.get(name).setSpawnArea(470, 490, -100, -1200);
            branches.get(name).setResolution(128, 99);
        }

        //branch2.png - 3 on the screen
        for(int n = 0; n < 3; n++)
        {
            name = "branch2" + n;
            branches.put(name, new Background("branch2.png", main, false));
            branches.get(name).setSpawnArea(100, 130, -100, -1200);
            branches.get(name).setResolution(-128, 99);
        }
    }

    private void createClouds()
    {
        String name = " ";

        //map4_cloud.png
        for(int n = 0; n < 5; n++)
        {
            name = "map4_cloud" + n;
            cloudsBack.put(name, new Background("map4_cloud.png", main, true));
            cloudsBack.get(name).setSpawnArea(620, 1500, 300, 460);
            cloudsBack.get(name).setResolution(64, 24);
        }

        for(int n = 0; n < 7; n++)
        {
            name = "map4_cloud_front" + n;
            cloudsFront.put(name, new Background("map4_cloud.png", main, true));
            cloudsFront.get(name).setSpawnArea(620, 2000, 0, 460);
            cloudsFront.get(name).setResolution(128, 42);
        }
    }

    private void createEnemy()
    {
        String name = " ";

        name = "ship1";
        enemies.put(name, new TextureActor("ship1.png", main));
        enemies.get(name).setSpawn(700, 2000, 0, 440);
        enemies.get(name).setLimit(-300, true);
        enemies.get(name).setFilter(FilterID.enemy_category, FilterID.player_category);
        enemies.get(name).setData(1f, 0, BodyDef.BodyType.DynamicBody);
        enemies.get(name).create(world, 450, 108, 2, false);
        enemies.get(name).setNoGravity();
    }

    public void createParachute()
    {

    }

    /**this should be placed between a batch.begin and batch.end
     * The map caller should have a stage, use the width and height there when
     * explicitly called there
     *
     * Note: The render order is that first top line is at the bottom of/behind the next line */
    public void display(float stageWidth, float stageHeight)
    {
        main.batch.draw((Texture) main.assetmanager.dataholder.get("map_back.png"), 0, 0,   //first line
                stageWidth, stageHeight);                                                     //rendered at the back
        main.batch.draw((Texture) main.assetmanager.dataholder.get("map_mid.png"), 0, 0,
                stageWidth, stageHeight);
        smoke1.display(-400, 40);
        main.batch.draw((Texture) main.assetmanager.dataholder.get("map_front.png"), 0, 0,
                stageWidth, stageHeight);
        //smoke2.display(-400, 40);

        //main.batch.draw((Texture) main.assetmanager.dataholder.get("map4_parts.png"), 0, 0, 600, 515);

        //for(ObjectMap.Entry<String, Background> iter : cloudsBack)
        //    iter.value.display(-50, 50);

        //for(ObjectMap.Entry<String, Background> iter : branches)
        //    iter.value.display(500, 350);

        //for(ObjectMap.Entry<String, Background> iter : cloudsFront)
        //    iter.value.display(-200, 100);

        //for(ObjectMap.Entry<String, GameActor> iter : enemies)
        //    iter.value.displayAll(-600);
    }

    public void displayLast(float stageWidth, float stageHeight)
    {
        smoke2.display(-400, 40);
    }
}
