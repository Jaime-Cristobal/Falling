package com.mygdx.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.main.actors.AnimatedActor;
import com.mygdx.main.actors.GameActor;
import com.mygdx.main.actors.TextureActor;
import com.mygdx.main.actors.creation.CreateActor;
import com.mygdx.main.actors.creation.CreateTexture;
import com.mygdx.main.collision.FilterDetector;
import com.mygdx.main.collision.FilterID;
import com.mygdx.main.collision.Splash;
import com.mygdx.main.enemies.EnemyManager;
import com.mygdx.main.enemies.Trash;
import com.mygdx.main.enemies.TrashManager;
import com.mygdx.main.maps.Map4;
import com.mygdx.main.objects.Platform;
import com.mygdx.main.player.Player;
import com.mygdx.main.scoring.Character;
import com.mygdx.main.scoring.Event;
import com.mygdx.main.ui.HUD;
import com.mygdx.main.ui.Scaler;

/**
 * Created by FlapJack on 7/22/2017.
 *
 * The game will be a platform base with a player, coin, traps, collector, bandit, and enemies
 *
 * coin -> various items on the screen to be collected
 *
 * collector -> bring coins to the collector to score
 *
 * bandit -> steals your score and destroy coins on the screen
 *
 * enemies -> kills the player; getting hit ends the game
 */

public class FirstMap implements Screen
{
    final private Main main;

    private World world;
    private OrthographicCamera camera;
    private ExtendViewport viewport;

    private String forest;
    private String map;
    private Stage stage;
    private float width;
    private float height;

    final private CreateActor parachute;

    private FilterDetector collision_filter;
    private Player player;
    private Platform ground;
    private Platform ceiling;
    private Platform leftwall;
    private Platform rightwall;
    private Map4 mapSelect;
    private EnemyManager enemy;
    private HUD hud;

    private float difficulty;
    private int goal;
    private int arrow_amount;
    private boolean night;
    private boolean contact;
    private boolean attack;
    private int stance;
    private float bullet_speed;

    private GameActor rock;
    private GameActor collector;
    private AnimatedActor coin;
    private ArrayMap<String, Float> region1;
    private Trash trash;

    private InputMultiplexer controller;

    private Box2DDebugRenderer debugRenderer;
    private Matrix4 debugMatrix;

    private TrashManager trashes;
    private GameActor bag;

    private Splash splash;

    //private Array<Character> coins;
    private Character coins;
    private Character collector1;
    private Event scoreSys;

    public FirstMap(final Main batch_param)
    {
        System.out.println("Birdmap is created.");

        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        this.main = batch_param;
        world = new World(new Vector2(0, -9.8f), true);
        collision_filter = new FilterDetector();

        camera = new OrthographicCamera(width, height);
        viewport = new ExtendViewport(width, height, camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        hud = new HUD(main, stage);

        player = new Player(main);
        player.createBody(world, 25, 330, 24, 34);

        ground = new Platform();
        ground.setFilter(FilterID.floor_category, FilterID.player_category);
        ground.create(world, 0, -50, 460, 10);

        ceiling = new Platform();
        ceiling.setFilter(FilterID.ceiling_category, FilterID.player_category);
        ceiling.create(world, 0, 600, 460, 5);

        leftwall = new Platform();
        leftwall.setFilter(FilterID.ceiling_category, FilterID.player_category);
        leftwall.create(world, -10, 0, 10, 600);

        rightwall = new Platform();
        rightwall.setFilter(FilterID.ceiling_category, FilterID.player_category);
        rightwall.create(world, 460, 0, 10, 600);

        enemy = new EnemyManager(main);
        enemy.create(world);

        controller = new InputMultiplexer();
        controller.addProcessor(stage);
        controller.addProcessor(player.getGesture());
        controller.addProcessor(player.getInputProcessor());
        Gdx.input.setInputProcessor(controller);

        difficulty = 1f;
        arrow_amount = 1;
        night = false;
        contact = false;
        attack = false;
        stance = 0;
        bullet_speed = 2000;

        mapSelect = new Map4(main, world);
        mapSelect.create();

        //Gdx.input.setInputProcessor(new GestureDetector(new Gesture()));

        parachute = new CreateTexture("parachute.png", main, BodyDef.BodyType.DynamicBody);
        parachute.setFilter(FilterID.enemy_category, FilterID.player_category);
        parachute.create(world, 1000, 1000, 128, 106, false);

        /**
        rock = new TextureActor("rock.png", main);
        rock.setData(0.5f, 0);
        rock.setNoGravity();
        rock.setFilter(FilterID.trash_category, FilterID.magnet_category);
        rock.setLimit(-200);
        rock.setResolution(12, 34);
        rock.setSpawn(600, 900, 0, 400);
        rock.create(world, 6);
        rock.setNoGravity();
         */

        /**
        collector = new TextureActor("collector.png", main);
        collector.setResolution(156, 112);
        collector.setSpawn(605, 700, 0, 120);
        collector.setLimit(-330);
        collector.setFilter(FilterID.collector_category, FilterID.trash_category);
        collector.create(world, 1, true);
        collector.setNoGravity();*/

        trash = new Trash("rock.png", main, 400);
        trash.setListerner(world);
        trash.setSpawn(600, 800, 200, 400);
        trash.setData(12, 34, 0.5f, 0);
        trash.setSpeed(-1000, 0);
        trash.create(world, null);

        /**
        trashes = new TrashManager();
        trashes.setSpawn(600, 800, 200, 400);
        trashes.setData(12, 34, 0.5f, 0);
        trashes.setSpeed(-1000, 0);
        trashes.setRotation(400);
        trashes.addTexture("rock.png", main, world, 5);
         */

        bag = new TextureActor("moon.png", main);
        bag.setFilter((short) 0, FilterID.player_category);
        bag.setSpawn(0,  600, -100, -300);
        bag.setMoveVertical();
        bag.setResolution(64, 53);
        bag.setLimit(600);
        bag.create(world, 4, false);    //true for playing purposes, false for testing

        region1 = new ArrayMap<String, Float>();
        region1.put("Armature_fly", 2.5f);

        /**
        coin = new AnimatedActor("watch.atlas", region1, main, false);
        coin.setSpawn(0, 600, -50, -300);
        coin.setResolution(50,50);
        coin.setData(0, 0);
        coin.setFilter(FilterID.coin_category, FilterID.player_category);
        coin.setLimit(65);
        coin.setMoveVertical();
        coin.create(world, 5, false);
         */

        coins = new Character("watch.atlas", 25, 25, main);
        coins.setSpawn(0, 460, -50, -300);
        coins.createAnimation(world, 1, 0, 0, FilterID.coin_category,
                (short)(FilterID.collector_category | FilterID.player_category), region1);

        /**
        coins = new Array<Character>();
        for(int n = 0; n < 5; n++)
        {
            coins.add(new Character("watch.atlas", 25, 25, main));
            coins.get(n).setSpawn(0, 460, -50, -300);
            coins.get(n).createAnimation(world, n, 0, 0, FilterID.coin_category,
                    (short)(FilterID.collector_category | FilterID.player_category), region1);
        }*/

        collector1 = new Character("ship1.png", 64, 22, main);
        collector1.setSpawn(0, 460, -50, -300);
        collector1.createTexture(world, 1, 0, 0, FilterID.collector_category,
                (short)(FilterID.coin_category | FilterID.player_category));

        scoreSys = new Event();

        splash = new Splash(main);
        splash.setDetector(collision_filter);
        splash.setData(player.getBody().getUserData(), enemy.getPadData());

        debugRenderer = new Box2DDebugRenderer();
    }

    /** Called when this screen becomes the current screen for a {@link com.badlogic.gdx.Game}. */
    @Override
    public void show ()
    {
        main.assetmanager.dataholder.update();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        forest = "bgmorning.png";
        map = "mlayout.png";

        goal = 500;

        hud.Create();
        world.setContactFilter(collision_filter);
        //world.setContactListener(splash.getListener());
        world.setContactListener(scoreSys.getListerner());

        splash.setDetector(collision_filter);

        //Button functionality for menu in HUD

        hud.menu.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                main.setScreen(new UIMenu(main));
                dispose();
            }
        });

        hud.shoot.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                //joint.destroy(world);
                //trash.makeFall(true);
                //trash.setFree();

                trashes.setFree();
            }
        });
    }

    /** Called when the screen should render itself.
     * @param delta The time in seconds since the last render. */
    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        main.batch.setProjectionMatrix(stage.getCamera().combined);

        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        debugMatrix = main.batch.getProjectionMatrix().cpy().scale(Scaler.PIXELS_TO_METERS, Scaler.PIXELS_TO_METERS, 0);

        //daytime();

        main.batch.begin();
        //main.batch.draw((Texture) main.assetmanager.dataholder.get(map), 0, 0, stage.getWidth(), stage.getHeight());

        /**
        if (night)
        {
            star.display(-100, 50);
            moon.display(700, 15);
        }
        else
        {
            sun.display(700, 15);
            bigcloud.display(-200, 60);
            smallcloud.display(-200, 40);
            jointcloud.display(-300, 35);
        }
         */

        mapSelect.display(stage.getWidth(), stage.getHeight());
        //main.batch.draw((Texture) main.assetmanager.dataholder.get("clouds.png"), 0, 0, stage.getWidth(), stage.getHeight());
        //main.batch.draw((Texture) main.assetmanager.dataholder.get("door1.png"), -270, 0, 300, 460);
        //main.batch.draw((Texture) main.assetmanager.dataholder.get("door2.png"), 570, 0, 300, 460);

        //trash.display(player.getBody().getPosition());

        //parachute.setPosition(player.getX(), player.getY() + 50);
        //parachute.display();

        //trashes.display(player.getBody().getPosition());
        //bag.displayAll(800);
        player.display(collision_filter);
        enemy.display();

        //collector.displayAll(-1000);

        /**
        for(Character iter : coins)
        {
            iter.display(1000);
            if(iter.checkCollision(scoreSys, FilterID.player_category))
                System.out.println("REKT");
            //scoreSys.checkActor(iter.getUserData(), FilterID.player_category);
            //if(scoreSys.isColliding())
            //{
            //    System.out.println("rekt");
            //    iter.forceRespawn();
            //}
        }*/

        //scoreSys.checkActor(coins.getUserData(), FilterID.player_category);
        //if(scoreSys.isColliding())
        scoreSys.checkActor(FilterID.coin_category, FilterID.player_category);
        if(scoreSys.checkCollision(coins.getUserData()) && scoreSys.isColliding())
        {
            System.out.println("rekt");
            //coins.forceRespawn();
            coins.setFollowPlayer(player.getInputPosition());
        }
        coins.display(1000);

        collector1.display(1400);

        //if(collision_filter.feedback(FilterID.player_category, FilterID.platform_category))
        //    mapSelect.splashEffect(player.getX(), player.getY());

        splash.render(player.getX(), player.getY());

        //coin.displayAll(1000);

        main.batch.end();

        if(collision_filter.feedback(FilterID.player_category, FilterID.coin_category))
            hud.addCoin(1);

        //if(collision_filter.feedback(FilterID.player_category, FilterID.platform_category))
        //    player.applyJump();

        debugRenderer.render(world, debugMatrix);

        hud.Render();
    }

    /** @see ApplicationListener#resize(int, int) */
    @Override
    public void resize (int width, int height)
    {
        stage.getViewport().update(width, height, true);
    }

    /** @see ApplicationListener#pause() */
    @Override
    public void pause ()
    {

    }

    /** @see ApplicationListener#resume() */
    @Override
    public void resume ()
    {

    }

    /** Called when this screen is no longer the current screen for a {@link com.badlogic.gdx.Game}. */
    @Override
    public void hide ()
    {

    }

    /** Called when this screen should release all resources. */
    @Override
    public void dispose ()
    {
        world.dispose();
        stage.dispose();

        System.out.println("Level is disposed");
    }

    private void daytime()
    {
        /**
        if(sun.getTimeChange(1) && !night) {
            map = "nlayout.png";       //night
            forest = "bgnight.png";
            night = true;
            sun.resetTime();
        }
        if(moon.getTimeChange(1) && night)
        {
            map = "mlayout.png";       //morning
            forest = "bgmorning.png";
            night = false;
            moon.resetTime();
        }
         */
    }
}
