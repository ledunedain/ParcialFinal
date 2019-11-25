package com.example.parcialfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import androidx.annotation.RequiresApi;
import java.util.concurrent.ThreadLocalRandom;

public class DisparoNave {
    public static final float INIT_X =300;
    public static final float INIT_Y =850;
    public static final int SPRITE_SIZE_WIDTH =150;
    public static final int SPRITE_SIZE_HEIGTH=100;
    public static final float GRAVITY_FORCE=10;
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    private float maxY;
    private float maxX;

    private float speed = 0;
    private float positionX;
    private float positionY;
    private Bitmap spriteDisparoNave;
    private boolean isJumping;
    private float velocity=10;


    public DisparoNave(Context context, float screenWidth, float screenHeigth){

        speed = 1;
        positionX = this.INIT_X;
        positionY = this.INIT_Y;
        isJumping = false;
        //Getting bitmap from resource
        Bitmap originalBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.disparo);
        spriteDisparoNave = Bitmap.createScaledBitmap(originalBitmap, SPRITE_SIZE_WIDTH, SPRITE_SIZE_HEIGTH, false);

        this.maxX = screenWidth - (spriteDisparoNave.getWidth()/2);
        this.maxY = screenHeigth - spriteDisparoNave.getHeight();
    }

    public DisparoNave(Context context, float initialX, float initialY, float screenWidth, float screenHeigth){

        speed = 1;
        positionX = initialX;
        positionY = initialY;

        Bitmap originalBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.disparo);
        spriteDisparoNave = Bitmap.createScaledBitmap(originalBitmap, SPRITE_SIZE_WIDTH, SPRITE_SIZE_HEIGTH, false);

        this.maxX = screenWidth - (spriteDisparoNave.getWidth()/2);
        this.maxY = screenHeigth - spriteDisparoNave.getHeight();

    }

    public static int getSpriteSizeWidth() {
        return SPRITE_SIZE_WIDTH;
    }

    public static int getSpriteSizeHeigth() {
        return SPRITE_SIZE_HEIGTH;
    }

    public static float getInitX() {
        return INIT_X;
    }

    public static float getInitY() {
        return INIT_Y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public Bitmap getSpriteDisparoNave() {
        return spriteDisparoNave;
    }

    public void setSpriteDisparoNave(Bitmap spriteDisparoNave) {
        this.spriteDisparoNave = spriteDisparoNave;
    }


    /**
     * Control the position and behaviour of the icecream car
     */
    public void updateInfo () {

        positionX += velocity;

        if(positionX>=1800){
            positionX=INIT_X;
        }

    }
    public void velocityUpdate(){
        velocity+=1;
    }
    public void reset() {
        velocity=10;
    }
}
