package com.thstuf.hello;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MainMenuScreen extends ScreenAdapter {
    private final HelloGdxGame game;
    private OrthographicCamera camera;

    MainMenuScreen(final HelloGdxGame game) {
        super();
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constant.WIDTH, Constant.HEIGHT);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Bomb Eater!!!", Constant.WIDTH / 2 - 100, Constant.HEIGHT / 2 + 10);
        game.font.draw(game.batch, "Press space bar to begin!", Constant.WIDTH / 2 - 100, Constant.HEIGHT / 2 - 10);
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }
}
