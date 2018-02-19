package com.example.bouncingball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;



public class MyBox extends View  implements OnTouchListener{

    MyThread myThread;
    Handler myHandler = new Handler();

    private float width=100;    // width & height will be reset on first onDraw call
    private float height=100;   
    Paint background, redPen;
    float ball_x=50.0f, ball_y=50.0f;
    float ball_vx = 1.0f, ball_vy = 1.5f;
    int radius = 6;
    Rect rect= new Rect();

   
    public void startThread()
    {
        myThread= new MyThread(ball_x, ball_y, ball_vx, ball_vy,  myHandler, this);
        myThread.start();
    }
    public void stopThread()
    {
        myThread.interrupt();
    }



    private void init()
    {
        background = new Paint();
        //background.setStyle(Paint.Style.FILL_AND_STROKE);
        background.setColor(0xffcfffff);

        redPen = new Paint();
        redPen.setColor(0xffff0000);


    }
    public MyBox(Context context) {
        super(context);
        init();
    }
    public MyBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyBox(Context context, 
            AttributeSet ats, 
            int defaultStyle) {
        super(context, ats, defaultStyle);
        init();
    }


    private float draw_x(float x)
    {
        return x*(width/100);
    }
    private float draw_y(float y)
    {
        return y*(height/100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Draw the background...

        width = getWidth();
        height = getHeight();

        canvas.drawRect(0, 0, width, height, background);

        canvas.drawCircle(draw_x(ball_x), draw_y(ball_y), radius,  redPen);

    }

    public boolean onTouch(View v, MotionEvent event) {     
        // Handle touch events here...
        if ((event.getAction() & MotionEvent.ACTION_MASK) ==
            MotionEvent.ACTION_DOWN)
        {
            float x = event.getX();
            float y = event.getY();
            myThread.update(x*(100/width), y*(100/height));
        }
        return true; // indicate event was handled
    }

    public void progressUpdate(float x, float y, float vx, float vy) {

        ball_x = x;
        ball_y = y;
        ball_vx = vx;
        ball_vy = vy;
        invalidate();


        /*  More efficient approach 
        float ball_newx = x;
        float ball_newy = y;
        rect.set((int)draw_x(Math.min(ball_x, ball_newx))-radius-1, 
                (int)draw_y(Math.min(ball_y, ball_newy))-radius-1,
                (int)draw_x(Math.max(ball_x, ball_newx))+radius+1, 
                (int)draw_y(Math.max(ball_y, ball_newy))+radius+1);


        //Log.d("Mine", "rect="+rect);
        invalidate(rect);
        ball_x = ball_newx;
        ball_y = ball_newy;
         */

    }


}