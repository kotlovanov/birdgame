package com.mygdx.game.screens;



import static com.mygdx.game.MyGdxGame.SCR_HEIGHT;
import static com.mygdx.game.MyGdxGame.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.characters.Bird;
import com.mygdx.game.characters.Tube;
import com.mygdx.game.components.MovingBackground;
import com.mygdx.game.components.PointCounter;


public class ScreenGame implements Screen {

    final int pointCounterMarginTop = 60;
    final int pointCounterMarginRight = 400;

    MyGdxGame myGdxGame;

    Bird bird;
    PointCounter pointCounter;
    MovingBackground background;

    int tubeCount = 3;
    Tube[] tubes;

    int gamePoints;
    boolean isGameOver;

    public ScreenGame(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        initTubes();
        background = new MovingBackground("background/game_bg.png");
        bird = new Bird(20, SCR_HEIGHT / 2, 10, 250, 200);
        pointCounter = new PointCounter(SCR_WIDTH - pointCounterMarginRight, SCR_HEIGHT - pointCounterMarginTop);
    }


    @Override
    public void show() {
        gamePoints = 0;
        isGameOver = false;
        bird.setY(SCR_HEIGHT / 2);
        initTubes();
    }

    @Override
    public void render(float delta) {

        if (isGameOver) {
            myGdxGame.screenRestart.gamePoints = gamePoints;
            myGdxGame.setScreen(myGdxGame.screenRestart);
        }

        if (Gdx.input.justTouched()) {
            bird.onClick();
        }

        background.move();
        bird.fly();
        if (!bird.isInField()) {
            isGameOver = true;
        }
        for (Tube tube : tubes) {
            tube.move();
            if (tube.isHit(bird)) {
                isGameOver = true;
            } else if (tube.needAddPoint(bird)) {
                gamePoints += 1;
                tube.setPointReceived();
            }
        }

        ScreenUtils.clear(1, 0, 0, 1);
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        myGdxGame.batch.begin();

        background.draw(myGdxGame.batch);
        bird.draw(myGdxGame.batch);
        for (Tube tube : tubes) tube.draw(myGdxGame.batch);
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
        bird.dispose();
        background.dispose();
        pointCounter.dispose();
        for (int i = 0; i < tubeCount; i++) {
            tubes[i].dispose();
        }
    }

    void initTubes() {
        tubes = new Tube[tubeCount];
        for (int i = 0; i < tubeCount; i++) {
            tubes[i] = new Tube(tubeCount, i);
        }
    }

}
