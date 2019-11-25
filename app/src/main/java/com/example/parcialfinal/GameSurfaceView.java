package com.example.parcialfinal;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.annotation.RequiresApi;
import android.graphics.Rect;

import java.util.concurrent.ThreadLocalRandom;

public class GameSurfaceView extends SurfaceView implements Runnable {

    private boolean isPlaying;
    private Nave nave;
    private Enemy enemigo;
    private Rock roca;
    private DisparoE disparoE;
    private DisparoNave disparoNave;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder holder;
    private Thread gameplayThread = null;
    private int Score=25;
    private int level=0;
    private int vida=100;
    private boolean tiempoC = true;

    /**
     * Contructor
     * @param context
     */
    public GameSurfaceView(Context context, float screenWith, float screenHeight) {
        super(context);
        nave = new Nave(context, screenWith, screenHeight);
        enemigo = new Enemy(context,screenWith,screenHeight);
        roca = new Rock(context,screenWith,screenHeight);
        disparoE = new DisparoE(context,screenWith,screenHeight);
        disparoNave = new DisparoNave(context,nave.getPositionX()+10,nave.getPositionY()+50,screenWith,screenHeight);

        paint = new Paint();
        holder = getHolder();
        isPlaying = true;
    }

    /**
     * Method implemented from runnable interface
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void run() {
        while (isPlaying) {
            paintFrame();
            updateInfo();
        }
        if(!isPlaying){
            gameOver();
        }
    }

    private void gameOver(){
        canvas = holder.lockCanvas();
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.gameover),0,0,null);
        holder.unlockCanvasAndPost(canvas);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void updateInfo() {
        if(!tiempoC) {
            nave.updateInfo();
            enemigo.updateInfo();
            roca.updateInfo();
            disparoE.updateInfo();
            disparoNave.updateInfo();

            Rect naveRec = new Rect((int)nave.getPositionX(),(int)nave.getPositionY(),
                    (int)nave.getPositionX()+nave.getSpriteSizeWidth(),(int)nave.getPositionY()+nave.getSpriteSizeHeigth());

            Rect enemigoRect = new Rect((int)enemigo.getPositionX(),(int)enemigo.getPositionY(),
                    (int)enemigo.getPositionX()+enemigo.getSpriteSizeWidth(),(int)enemigo.getPositionY()+enemigo.getSpriteSizeHeigth());

            Rect rocaRect = new Rect((int)roca.getPositionX(),(int)roca.getPositionY(),
                    (int)roca.getPositionX()+roca.getSpriteSizeWidth(),(int)roca.getPositionY()+roca.getSpriteSizeHeigth());

            Rect disparoERect = new Rect((int)disparoE.getPositionX(),(int)disparoE.getPositionY(),
                    (int)disparoE.getPositionX()+disparoE.getSpriteSizeWidth(),(int)disparoE.getPositionY()+disparoE.getSpriteSizeHeigth());

            Rect disparoNaveRect = new Rect((int)disparoNave.getPositionX(),(int)disparoNave.getPositionY(),
                    (int)disparoNave.getPositionX()+disparoNave.getSpriteSizeWidth(),(int)disparoNave.getPositionY()+disparoNave.getSpriteSizeHeigth());

            if(Rect.intersects(naveRec, rocaRect)){
                isPlaying = false;
            }
            if(Rect.intersects(naveRec, enemigoRect)){
                isPlaying = false;
            }
            if(Rect.intersects(disparoNaveRect,rocaRect)){
                roca.setPositionX(roca.getInitX());
                roca.setPositionY(ThreadLocalRandom.current().nextInt(20, 998 + 1));
            }
            if(Rect.intersects(naveRec,disparoERect)){
                vida -= 15;
                disparoE.setPositionX(enemigo.getPositionX());
            }
            if(Rect.intersects(enemigoRect,disparoNaveRect)){
                enemigo.setPositionX(enemigo.getInitX());
                enemigo.setPositionY((int) (Math.random() * 800) + 1);
                disparoE.setPositionX(enemigo.getPositionX() - 120);
                disparoE.setPositionY(enemigo.getPositionY() + 75);
                Score++;
            }

            if(disparoE.getPositionX()<=10){
                disparoE.setPositionX(enemigo.getPositionX()-10);
            }

            if (level > 20) {
                level = 0;
                vida = 100;
                Score = 0;
                enemigo.reset();
                roca.reset();
                disparoE.reset();
                disparoNave.reset();
            }

            if ((Score % 8) == 0) {
                level++;
                enemigo.velocityUpdate();
                roca.velocityUpdate();
                Score = 0;
            }
        }

    }


    private void paintFrame() {
        if (holder.getSurface().isValid()){
            canvas = holder.lockCanvas();
            Paint pincel1 = new Paint();
            pincel1.setTextSize(30);
            canvas.drawColor(Color.LTGRAY);
            canvas.drawBitmap(nave.getSpriteNave(), nave.getPositionX(), nave.getPositionY(),paint);
            canvas.drawBitmap(enemigo.getSpriteEnemy(),enemigo.getPositionX(),enemigo.getPositionY(),paint);
            canvas.drawBitmap(disparoE.getSpriteDisparo(), disparoE.getPositionX(), enemigo.getPositionY()+75,paint);
            canvas.drawBitmap(disparoNave.getSpriteDisparoNave(), disparoNave.getPositionX(), nave.getPositionY()+75,paint);
            canvas.drawBitmap(roca.getSpriteRock(), roca.getPositionX(), roca.getPositionY(),paint);

            canvas.drawText("VIDA: "+vida+"    PUNTAJE: " + Score + "    NIVEL: " + level, 1600, 100,pincel1);
            holder.unlockCanvasAndPost(canvas);

            if(tiempoC)
            {
                tiempoC = false;
                vida=100;
            }
        }

    }

    public void pause() {
        isPlaying = false;
        try {
            gameplayThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public void resume() {

        isPlaying = true;
        gameplayThread = new Thread(this);
        gameplayThread.start();
    }

    /**
     * Detect the action of the touch event
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                System.out.println("TOUCH UP - STOP JUMPING");
                nave.setJumping(false);
                break;
            case MotionEvent.ACTION_DOWN:
                System.out.println("TOUCH DOWN - JUMP");
                nave.setJumping(true);
                break;
        }
        return true;
    }

}
