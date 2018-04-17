package com.thstuf.hello;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

class GameScreen extends ScreenAdapter {
    private final HelloGdxGame game;
    private int bombEaten;
    private OrthographicCamera camera;
    private Sound dropSound;
    private Music rainMusic;
    private Texture bombEaterImg;
    private Texture bombImg;
    private Rectangle bombEater;
    private Array<Rectangle> bombs;
    private long lastDropTime;

    GameScreen(final HelloGdxGame game) {
        super();
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constant.WIDTH, Constant.HEIGHT);

        dropSound = Gdx.audio.newSound(Gdx.files.internal("30341__junggle__waterdrop24.wav"));

        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("28283__acclivity__undertreeinrain.mp3"));
        rainMusic.setLooping(true);
        rainMusic.play();

        bombEaterImg = new Texture("char.png");
        bombEater = new Rectangle();
        bombEater.x = Constant.WIDTH / 2 - 16 / 2;
        bombEater.y = 20;
        bombEater.width = 16;
        bombEater.height = 16;

        bombImg = new Texture("bomb.png");
        bombs = new Array<Rectangle>();
        spawnBombs();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor(1, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Bomb Eaten: " + bombEaten, 10, Constant.HEIGHT - 10);
        game.batch.draw(bombEaterImg, bombEater.x, bombEater.y);
        for (Rectangle bomb : bombs) {
            game.batch.draw(bombImg, bomb.x, bomb.y);
        }
        game.batch.end();

        int speed = 200; // 200 px/s
        if (Gdx.input.isKeyPressed(Input.Keys.A)) bombEater.x -= speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) bombEater.x += speed * delta;

        if (bombEater.x < 0) bombEater.x = 0;
        if (bombEater.x > Constant.WIDTH - 16) bombEater.x = Constant.WIDTH - 16;

        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnBombs();

        Iterator<Rectangle> iterator = bombs.iterator();
        while (iterator.hasNext()) {
            Rectangle bomb = iterator.next();
            bomb.y -= 200 * delta;
            if (bomb.y + 16 < 0) iterator.remove();
            if (bomb.overlaps(bombEater)) {
                bombEaten++;
                dropSound.play();
                iterator.remove();
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();

        dropSound.dispose();
        rainMusic.dispose();
        bombEaterImg.dispose();
        bombImg.dispose();
    }

    private void spawnBombs() {
        Rectangle bomb = new Rectangle();
        bomb.x = MathUtils.random(0, Constant.WIDTH - 16);
        bomb.y = Constant.HEIGHT;
        bomb.width = 16;
        bomb.height = 16;
        bombs.add(bomb);
        lastDropTime = TimeUtils.nanoTime();
    }
}
