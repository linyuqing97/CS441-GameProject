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
    private boolean touchFlag = false;

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

    //score
    private Paint scorePaint = new Paint();

    //life
    private Bitmap life[] = new Bitmap[2];

    // level
    private Paint levelPaint = new Paint();

    public GameView(Context context) {
        super(context);
        apple = BitmapFactory.decodeResource(getResources(),R.drawable.apple);
        resizeApple = Bitmap.createScaledBitmap(apple,100,100,true);
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

       // canvas.drawBitmap(resizeApple,0,0,null);
        canvas.drawText("Score:0",20,60,scorePaint);
        canvas.drawText("LEVEL 1 ",canvas.getWidth()/2,60,levelPaint);

        canvas.drawBitmap(life[0],560,30,null);
        canvas.drawBitmap(life[0],620,30,null);
        canvas.drawBitmap(life[0],680,30,null);


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
