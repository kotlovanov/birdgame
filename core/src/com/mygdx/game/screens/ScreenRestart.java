package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.MovingBackground;
import com.mygdx.game.components.PointCounter;
import com.mygdx.game.components.TextButton;


public class ScreenRestart implements Screen {

    MyGdxGame myGdxGame;

    MovingBackground background;
    TextButton buttonRestart;
    TextButton buttonExit;
    PointCounter pointCounter;

    int gamePoints;

    public ScreenRestart(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        pointCounter = new PointCounter(750, 530);
        buttonRestart = new TextButton(100, 400, "Restart");
        buttonExit = new TextButton(100, 100, "Exit");
        background = new MovingBackground("background/restart_bg.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if (Gdx.input.justTouched()) {

            Vector3 touch = myGdxGame.camera.unproject(
                    new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)
            );

            if (buttonRestart.isHit((int) touch.x, (int) touch.y)) {
                myGdxGame.setScreen(myGdxGame.screenGame);
            }
            if (buttonExit.isHit((int) touch.x, (int) touch.y)) {
                myGdxGame.setScreen(myGdxGame.screenMenu);
            }
        }

        ScreenUtils.clear(1, 0, 0, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();

        background.draw(myGdxGame.batch);
        buttonRestart.draw(myGdxGame.batch);
        buttonExit.draw(myGdxGame.batch);
        pointCounter.draw(myGdxGame.batch, gamePoints);

        myGdxGame.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
        buttonRestart.dispose();
        buttonExit.dispose();
    }
}
