package com.mygdx.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.main.animator.Animator;

/**
 * Created by FlapJack on 8/13/2017.
 */

public class Message  implements Screen
{
    private float scale_x = (Gdx.graphics.getWidth() / 800);

    final private Stage stage;
    final private Main main;

    private Table scoretable;
    private Table retrytable;
    private Table menutable;
    private TextButton retry;
    private TextButton menu;
    private Label score;
    private OrthographicCamera camera;
    private ExtendViewport viewport;
    private Animator death;

    private int scoreval;
    private float x;
    private float y;

    public Message(final Main main_p, int scoreval, float x_p, float y_p)
    {
        this.main = main_p;

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new ExtendViewport(800, 680, camera);
        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);

        scoretable = new Table();
        scoretable.setY(120f);

        retrytable = new Table();
        retrytable.setY(-60f);
        retrytable.setX(-120f);

        menutable = new Table();
        menutable.setY(-60f);
        menutable.setX(120f);

        retry = new TextButton("Retry", main.skin);
        menu = new TextButton("Menu", main.skin);
        score = new Label("Distance: " + scoreval + " Km", main.skin);
        score.setFontScale(1.5f, 1.5f);

        death = new Animator("ghost.atlas", main.assetmanager.dataholder, 5.0f);

        x = x_p;
        y = y_p;
    }

    /** Called when this screen becomes the current screen for a {@link com.badlogic.gdx.Game}. */
    public void show ()
    {
        main.assetmanager.dataholder.update();

        scoretable.setFillParent(true);
        retrytable.setFillParent(true);
        menutable.setFillParent(true);
        stage.addActor(scoretable);
        stage.addActor(retrytable);
        stage.addActor(menutable);

        retrytable.add(retry).fillX().uniformX();
        menutable.add(menu).fillX().uniformX();
        scoretable.add(score).fillX().uniformX();

        //Button functionality
        menu.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                main.setScreen(new UIMenu(main));
                dispose();
            }
        });

        retry.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                main.setScreen(new FirstMap(main));
                dispose();
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
        main.batch.draw((Texture)main.assetmanager.dataholder.get("mapA.png"), 0, 0, stage.getWidth(), stage.getHeight());
        main.batch.end();

        //death.render(main.batch, 25 / (int) scale_x, y * 10f);

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

        System.out.println("Message is disposed");
    }
}
