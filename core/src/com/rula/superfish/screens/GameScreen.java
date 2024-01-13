package com.rula.superfish.screens;

import static java.lang.Math.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen{
    Vector3 touchpos;
    OrthographicCamera camera;
    SpriteBatch batch;
    Sprite fishsprite;
    Texture fishtexture;
    Texture musor;
    Rectangle musorect;
    Rectangle sprect;
    boolean pause;

    @Override
    public void show() {
        pause=false;
        sprect = new Rectangle();
        musorect = new Rectangle();
        musor = new Texture(Gdx.files.internal("musor.png"));
        fishtexture = new Texture(Gdx.files.internal("img.png"));
        fishsprite = new Sprite(fishtexture);
        fishsprite.setScale(0.3F);
        fishsprite.setPosition(20, 480/2-32);
        musorect.setY((float) (MathUtils.random(0,480)));
        musorect.setX(fishsprite.getX() + 800);
        musorect.setHeight(50);
        musorect.setWidth(50);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        batch = new SpriteBatch();

    }
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(musor, musorect.x, musorect.y, musorect.width, musorect.height);
        fishsprite.draw(batch);
        batch.end();
        if (!pause) {
            sprect.setX(fishsprite.getX());
            sprect.setY(fishsprite.getY());
            sprect.setWidth(fishsprite.getWidth());
            sprect.setHeight(fishsprite.getHeight());
            if (sprect.overlaps(musorect)) {
                pause = true;
            }
            if (Gdx.input.isTouched()) {
                touchpos = new Vector3();
                touchpos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touchpos);
                fishsprite.setY((int) (touchpos.y - 64 / 2));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                fishsprite.setY(fishsprite.getY() + (200 * delta));
                fishsprite.setRotation(45);
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                fishsprite.setY(fishsprite.getY() - (200 * delta));
                fishsprite.setRotation(-45);
            } else {
                fishsprite.setRotation(0);
            }

            if (fishsprite.getY() > 480 - 64 * 2)
                fishsprite.setY(480 - 64 * 2);
            else if (fishsprite.getY() < -64)
                fishsprite.setY(-64);

            camera.translate((float) 8, 0);
            fishsprite.setX((float) (fishsprite.getX() + 8));
            if (musorect.getX() < fishsprite.getX()) {
                musorect.setY((float) (random() * 480 / 2));
                musorect.setX(fishsprite.getX() + 800);
            }

            camera.update();

        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isTouched(1)) {
                fishsprite.setPosition(20, 480 / 2 - 32);
                musorect.setY((float) (random() * 480 / 2));
                musorect.setX(fishsprite.getX() + 800);
                musorect.setHeight(50);
                musorect.setWidth(50);

                camera.setToOrtho(false, 800, 480);
                pause=false;
                camera.update();
            }
        }
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
        fishtexture.dispose();
        batch.dispose();
    }
}
