package com.example.myfungame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {
    private Bitmap apple;
    private Bitmap ps;
    private boolean touchFlag = false;

    private int score;

    //canvas
    private  int canvasWidth;
    private  int canvasHeight;

    //apple
    private int appleX = 10;
    private int appleY;
    private int moveSpeed;
    //background:
    private Bitmap bgImage;
    private Bitmap resizeApple;
    private Bitmap resizePs;

    //score
    private Paint scorePaint = new Paint();

    //life
     private Bitmap life[] = new Bitmap[2];
    public int lifeCount = 3;


    // level
    private Paint levelPaint = new Paint();

    //PS
    private int psX;
    private int psY;
    private int psSpeed = 15;
    private Paint psPaint = new Paint();

    //Android
    Bitmap android;
    Bitmap resizeAndroid;
    private int androidX;
    private int androidY;
    private int androidSpeed = 16;





    public GameView(Context context) {
        super(context);

        //apple
        apple = BitmapFactory.decodeResource(getResources(),R.drawable.apple);
        resizeApple = Bitmap.createScaledBitmap(apple,100,100,true);


        //ps
        ps = BitmapFactory.decodeResource(getResources(),R.drawable.ps);
        resizePs = Bitmap.createScaledBitmap(ps,100,100,true);


        //android
        android = BitmapFactory.decodeResource(getResources(),R.drawable.android);
        resizeAndroid = Bitmap.createScaledBitmap(android,100,100,true);




        scorePaint.setColor(Color.BLACK );
        scorePaint.setTextSize(32);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        levelPaint.setColor(Color.BLACK);
        levelPaint.setTextSize(32);
        levelPaint.setTextAlign(Paint.Align.CENTER);
        levelPaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(),R.drawable.heart);
        life[1] = BitmapFactory.decodeResource(getResources(),R.drawable.heart_g);

        psPaint.setAntiAlias(false);

        //First position
        appleY = 500;






    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();


        //apple
        int minY =resizeApple .getHeight();
        int maxY = canvasHeight-resizeApple.getHeight()*3;

        appleY += moveSpeed;
        if(appleY<minY)appleY = minY;
        if(appleY>maxY)appleY = maxY;
        moveSpeed +=2;


        if(touchFlag){
            canvas.drawBitmap(resizeApple,appleX,appleY,null);
            touchFlag = false;
        }
        else{
            canvas.drawBitmap(resizeApple,appleX,appleY,null);
        }

        //ps
        psX -= psSpeed;
        if(psX < 0){
            psX = canvasWidth + 20;
            psY = (int)Math.floor((Math.random()*(maxY-minY)))+minY;
        }
        canvas.drawBitmap(resizePs,psX,psY,null);
        if(getHitCheck(psX,psY)){
            psX-=resizeApple.getWidth();
            lifeCount--;
        }


       // canvas.drawBitmap(resizeApple,0,0,null);
        androidX -=androidSpeed;
        if(androidX < 0){
            androidX = canvasWidth+20;
            androidY = (int)Math.floor((Math.random()*(maxY - minY)))+minY;
        }
        canvas.drawBitmap(resizeAndroid,androidX,androidY,null);

        if(getHitCheck(androidX,androidY)){
            System.out.println("Android x "+androidX+" Android Y： "+ androidY);
            score += 10;
            androidX-=100;
            System.out.println("point added");


        }


        //basic
        //Score
        canvas.drawText("Score: "+score,20,60,scorePaint);

        //Level
        canvas.drawText("LEVEL 1 ",canvas.getWidth()/2,60,levelPaint);

        //Life
        for(int i = 0; i<3;i++) {
            int x = (int) (860 + life[0].getWidth() * 1.5 * i);
            int y = 30;

            if (i<lifeCount) {
                canvas.drawBitmap(life[0],x,y,null);

            }
            else{
                canvas.drawBitmap(life[1],x,y,null);
            }
        }



    }
    public boolean getHitCheck(int x, int y){
        int appleCenterX = appleX + resizeApple.getWidth()/2;
        int appleCenterY = appleY + resizeApple.getHeight()/2;

        if(appleX < x && x < (appleX + resizeApple.getWidth()) && appleY < y && y < (appleY + resizeApple.getHeight())){
            System.out.println("Hit detected");
            return true;
        }
        return  false;
    }
    public void gameOver(){

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            touchFlag = true;
            moveSpeed= -20;
        }
        return true;
    }

}
