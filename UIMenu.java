package com.mygdx.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;


/**
 * Created by FlapJack on 8/2/2017.
 *
 * Notes:
 *  -Orthographic Camera is unnecessary due to ScreenViewport.
 *
 *  -Skin should hold all the textures and fonts for the GUI.
 *      -* [Try to see if assetmanager has Skin]
 *
 *  -Table holds the TextButton in a row like a spreadsheet.
 */

public class UIMenu implements Screen
{
    final private Main main;

    private Stage stage;
    private Table table;
    private TextButton button1;
    private TextButton button2;
    private TextButton button3;

    //For settings field
    private Dialog settings;
    private TextButton back;

    private BitmapFont title;
    private OrthographicCamera camera;
    private ExtendViewport viewport;

    public UIMenu(final Main batch_param)
    {
        this.main = batch_param;

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new ExtendViewport(600, 460, camera);
        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);

        //Main menu
        table = new Table();
        table.setPosition(0, -180);
        button1 = new TextButton("Play", main.skin);
        button2 = new TextButton("Settings", main.skin);
        button3 = new TextButton("Exit", main.skin);

        //Setting screen
        settings = new Dialog("Settings", main.skin);
        settings.setPosition(2000, 2000);
        back = new TextButton("Back", main.skin);
        back.setPosition(settings.getX() + 30, settings.getY() + 30);

        title = main.skin.getFont("title");
    }

    /** Called when this screen becomes the current screen for a {@link com.badlogic.gdx.Game}. */
    public void show ()
    {
        button1.setTransform(true);
        button2.setTransform(true);
        button3.setTransform(true);

        main.assetmanager.dataholder.update();

        //Creates the buttons
        table.setFillParent(true);
        //table.setDebug(true);      //FOR DEBUGGING PURPOSES - DO NOT REMOVE
        stage.addActor(table);

        //stage.addActor(settings);
        //stage.addActor(back);

        table.add(button1).fillX().uniformX().center().right().pad(0, 20, 0, 20);
        table.add(button2).fillX().uniformX().center().pad(0, 20, 0, 20);
        table.add(button3).fillX().uniformX().center().left().pad(0, 20, 0, 20);

        //Button functionality
        button1.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                main.setScreen(new FirstMap(main));
                dispose();
            }
        });

        /**
         button2.addListener(new ChangeListener()
         {
         @Override
         public void changed(ChangeEvent event, Actor actor)
         {
         stage.addActor(settings);
         stage.addActor(back);
         }
         });

         back.addListener(new ChangeListener()
         {
         @Override
         public void changed(ChangeEvent event, Actor actor)
         {
         //settings.setPosition(2000, 2000);
         //back.setPosition(settings.getX() + 30, settings.getY() + 30);
         }
         }); */

        button3.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                Gdx.app.exit();
            }
        });
    }

    /** Called when the screen should render itself.
     * @param delta The time in seconds since the last render. */
    public void render (float delta)
    {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        main.batch.setProjectionMatrix(stage.getCamera().combined);

        main.batch.begin();
        main.batch.draw((Texture) main.assetmanager.dataholder.get("water.png"), 0, 0, stage.getWidth(), stage.getHeight());
        main.batch.draw((Texture) main.assetmanager.dataholder.get("clouds.png"), 0, 0, stage.getWidth(), stage.getHeight());
        title.draw(main.batch, "title", 170, 230);
        main.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    /** @see ApplicationListener#resize(int, int) */
    public void resize (int width, int height)
    {
        stage.getViewport().update(width, height, true);
    }

    /** @see ApplicationListener#pause() */
    public void pause ()
    {

    }

    /** @see ApplicationListener#resume() */
    public void resume ()
    {

    }

    /** Called when this screen is no longer the current screen for a {@link com.badlogic.gdx.Game}. */
    public void hide ()
    {

    }

    /** Called when this screen should release all resources. */
    public void dispose ()
    {
        stage.dispose();

        System.out.println("UIMenu is diesposed");
    }
}
