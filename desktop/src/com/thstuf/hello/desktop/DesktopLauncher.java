package com.thstuf.hello.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.thstuf.hello.Constant;
import com.thstuf.hello.HelloGdxGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Bomb Eater";
        config.width = Constant.WIDTH;
        config.height = Constant.HEIGHT;
        new LwjglApplication(new HelloGdxGame(), config);
    }
}
