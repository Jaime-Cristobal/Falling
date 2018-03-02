package com.mygdx.main.enemies;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.main.actors.AnimatedActor;
import com.mygdx.main.actors.GameActor;
import com.mygdx.main.Main;
import com.mygdx.main.animator.AnimatePOD;
import com.mygdx.main.collision.FilterID;

/**
 * Created by seacow on 9/17/2017.
 */

public class EnemyManager
{
    private GameActor star;

    public EnemyManager(Main main)
    {
        star = new AnimatedActor("pad.atlas", AnimatePOD.region, main, false);
    }

    public void create(World world)
    {
        star.setResolution(48, 32);
        star.setSpawn(0, 600, -30, -15);
        star.setFilter(FilterID.platform_category, FilterID.player_category);
        star.setData(0.4f, 0.2f);
        star.setBodyType(BodyDef.BodyType.DynamicBody);
        star.setLimit(49);
        star.setMoveVertical();
        star.create(world, 5, false);
    }

    public void display()
    {
        star.displayAll(1200);
    }

    public Object getPadData()
    {
        return star.getUserData();
    }
}
