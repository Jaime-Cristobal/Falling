package com.mygdx.main.enemies;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.main.actors.AnimatedActor;
import com.mygdx.main.actors.GameActor;
import com.mygdx.main.Main;
import com.mygdx.main.actors.TextureActor;
import com.mygdx.main.animator.AnimatePOD;
import com.mygdx.main.collision.FilterID;
import com.mygdx.main.ui.Scaler;

/**
 * Created by seacow on 9/17/2017.
 */

public class EnemyManager
{
    private GameActor platform;
    private GameActor bandit;
    private GameActor destroyer;

    public EnemyManager(Main main)
    {
        platform = new AnimatedActor("pad.atlas", AnimatePOD.region, main, false);
        bandit = new TextureActor("rock.png", main);
        destroyer = new TextureActor("collector.png", main);
    }

    public void create(World world)
    {
        float height = Gdx.graphics.getHeight();

        platform.setSpawn(0, 400, -1000, -15);
        platform.setFilter(FilterID.platform_category, FilterID.player_category);
        platform.setData(0.4f, 0.2f, BodyDef.BodyType.KinematicBody);
        platform.setLimit(height + 50, true);
        platform.create(world, 48, 32, 5, false);

        bandit.setSpawn(0, 400, -1000, -15);
        bandit.setFilter(FilterID.bandit_category, (short)(FilterID.player_category | FilterID.coin_category
                            | FilterID.collector_category));
        bandit.setData(0, 0, BodyDef.BodyType.DynamicBody);
        bandit.setLimit(height + 20, true);
        bandit.create(world, 15, 36, 2, false);

        destroyer.setSpawn(0, 400, -1000, -15);
        destroyer.setFilter(FilterID.enemy_category, (short)(FilterID.player_category | FilterID.coin_category
                                | FilterID.collector_category));
        destroyer.setData(0, 0, BodyDef.BodyType.DynamicBody);
        destroyer.setLimit(height + 200, true);
        destroyer.create(world, 156, 112, 1, false);
    }

    public void display()
    {
        platform.displayAll(1200);
        bandit.displayAll(1100);
        destroyer.displayAll(1100);
    }

    public Object getPadData()
    {
        return platform.getUserData();
    }
}
