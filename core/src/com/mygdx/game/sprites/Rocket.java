package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.JetRaket;

/**
 * Created by Thomas on 28/03/2018.
 */

public class Rocket {
    private Vector3 position;
    private Vector3 velocity;
    public Sprite sprite;
    private Sound shoot;
    public boolean colliding;

    public Rocket(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        Texture texture = new Texture("rocket.png");
        sprite = new Sprite(texture);
        sprite.setFlip(false,true);
        sprite.setPosition(x,y);
        sprite.setOrigin(0,0);
        sprite.setScale(0.1f);
        //birdAnimation = new Animation(new TextureRegion(texture), 1, 0.5f);
        shoot = Gdx.audio.newSound(Gdx.files.internal("rocket.wav"));
        colliding = false;
    }

    public void update(float dt){
        //birdAnimation.update(dt);
        //velocity.add(1, 0, 0);
        velocity.scl(dt);
        position.add(velocity.x, velocity.y, 0);

        // BOUNDARY CHECK
        if(position.x < 0) {
            velocity.x = -velocity.x * 0.5f;
            position.x = 0;
        }
        else if(position.x > JetRaket.WIDTH - sprite.getWidth()*sprite.getScaleX()) {
            velocity.x = -velocity.x * 0.5f;
            position.x = JetRaket.WIDTH - sprite.getWidth() * sprite.getScaleX();
        }
        if(position.y < 0){
            velocity.y = -velocity.y*0.5f;
            position.y = 0;}
        else if(position.y > JetRaket.HEIGHT - sprite.getHeight()*sprite.getScaleY()){
            velocity.y = -velocity.y*0.5f;
            position.y = JetRaket.HEIGHT - sprite.getHeight()*sprite.getScaleY();}
        velocity.scl(1/dt);
        sprite.setPosition(position.x, position.y);
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public void setPosition(float x, float y) {
        this.position.set(x,y,0);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return sprite.getTexture();
    }

    public void shoot(){
        shoot.play(0.5f);
    }

    public void move(float x, float y){
        int maxVel = 250;
        int step = 8;

        long distance = Math.round(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
        double angle = Math.atan2(x,y);
        double stepX = step * Math.sin(angle);
        double stepY = step * Math.cos(angle);
        long futVel = Math.round(Math.sqrt(Math.pow(velocity.x+stepX,2)+Math.pow(velocity.y+stepY,2)));

        if(distance > 50 && futVel < maxVel) {
            velocity.x += stepX;
            velocity.y += stepY;
        }

        if(distance < 50) {
            velocity.x /= 1.1;
            velocity.y /= 1.1;
        }
    }

    public Rectangle getBounds(){
        return sprite.getBoundingRectangle();
    }

    public void dispose(){
        sprite.getTexture().dispose();
        shoot.dispose();
    }
}
