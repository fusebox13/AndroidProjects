package com.example.videodraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class MyDrawView1 extends View  {

    Context context;
    int drawingCount=0;
    
    // Keeping instance variables can cut down on the number of calls to "new" which
    // can ease the burden on the Garbage collector.  onDraw routines can be called a lot
    
    Paint translucentRedPen, bluePen;
    RectF rectf= new RectF();
    int backgroundColor;
    
    // initialize all of our instance variables one time.
    private void init()
    {
        Log.d("Mine","init called in MyDrawHangmanView");
        backgroundColor = getResources().getColor(R.color.backgroundColor);

        translucentRedPen = new Paint();
        //translucentRedPen.setStyle(Paint.Style.FILL_AND_STROKE);  // not needed because this is the default
        translucentRedPen.setColor(getResources().getColor(R.color.translucentRedPen));

        bluePen = new Paint();
        bluePen.setColor(getResources().getColor(R.color.bluePen));
        bluePen.setStyle(Paint.Style.STROKE);
        bluePen.setStrokeWidth(2);
    }


    public MyDrawView1(Context context) {
        super(context);
        init();
        this.context = context;
    }
    public MyDrawView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

        this.context = context;
    }

    public MyDrawView1(Context context, 
            AttributeSet ats, 
            int defaultStyle) {
        super(context, ats, defaultStyle);
        init();

        this.context = context;
    }
    
    
    public void increment()
    {
        drawingCount+= 1;
        invalidate();
    }
    public void reset()
    {
        drawingCount = 0;
        invalidate();
    }
    

    @Override
    protected void onDraw(Canvas canvas) {

        float width = getWidth();
        float  height = getHeight();
        // Draw the background...
        Log.d("Mine", "start onDraw width="+width + " height="+height);
        canvas.save();
        canvas.scale(width/600, height/650);

        canvas.drawColor(backgroundColor); 
        
        for (int i=1; i <= drawingCount; i++)
        {    
            switch(i)
            {
            case 1:
                canvas.drawLine(50, 200, 350, 200, bluePen); // top horizontal
                break;
            case 2:
                canvas.drawLine(50, 500, 350, 500, bluePen); // bottom horizontal
                break;
            case 3:
                canvas.drawLine(350, 200, 350, 500, bluePen); // right vertical
                break;
            case 4:
                canvas.drawLine(50, 200, 50, 500, bluePen);  // left vertical
                break;
            case 5:
                canvas.drawLine(30, 210, 200, 125, bluePen);  // left roof half
                break;
            case 6:
                canvas.drawLine(370, 210, 200, 125, bluePen); // right roof half
                break;
            
            case 7: 
                canvas.drawLine(270, 400, 320, 400, bluePen); // door: top horizontal
                break;
            case 8:
                canvas.drawLine(270, 500, 320, 500, bluePen); // door: bottom horizontal
                break;
            case 9:
                canvas.drawLine(320, 400, 320, 500, bluePen); // door: right vertical
                break;
            case 10:
                canvas.drawLine(270, 400, 270, 500, bluePen); // door: left vertical
                break;
            case 11:    
                rectf.set(0,100, 400,550);
                canvas.drawRoundRect(rectf, 100, 100, translucentRedPen); // Translucent Rounded Rectangle 
                break;
            } // end of switch
        } // end of for loop
        
        canvas.restore();        
    }
}