package com.example.mydraw;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class MyDrawView1 extends View  {

    Context context;


    public MyDrawView1(Context context) {
        super(context);

        this.context = context;
    }
    public MyDrawView1(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
    }

    public MyDrawView1(Context context, 
            AttributeSet ats, 
            int defaultStyle) {
        super(context, ats, defaultStyle);

        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {

		float width = getWidth();
		float  height = getHeight();
		// Draw the background...
		Log.d("Mine", "start onDraw width="+width + " height="+height);

		Paint paint = new Paint();

		int color = context.getResources().getColor(R.color.blue);
		canvas.drawColor(color);

		paint.setColor(context.getResources().getColor(R.color.red));
		//paint.setStyle(Paint.Style.STROKE);
		//paint.setStrokeWidth(10);
		
		canvas.drawText("abcdefghijklmnopqrstuvwxyz", 300, 100, paint); // Left aligned by default
		canvas.drawLine(0, 100, 600, 100, paint);
		
		paint.setTextAlign(Paint.Align.CENTER);
		canvas.drawText("abcdefghijklmnopqrstuvwxyz", 300, 200, paint);
		canvas.drawLine(0, 200, 600, 200, paint);

		paint.setTextAlign(Paint.Align.RIGHT);  // Default is LEFT
		canvas.drawText("abcdefghijklmnopqrstuvwxyz", 300, 300, paint);
		canvas.drawLine(0, 300, 600, 300, paint);
		
		paint.setTextAlign(Paint.Align.LEFT); 
		paint.setTextSize(100);
		canvas.drawText("abcdefghijklmnopqrstuvwxyz", 0, 500, paint);
		canvas.drawLine(0, 400, 600, 400, paint);
		canvas.drawLine(0, 500, 600, 500, paint);
		
		Log.d("Mine", "End of onDraw");
	}
    
    /*
     * protected void onDraw(Canvas canvas) {

        float width = getWidth();
        float  height = getHeight();
        // Draw the background...
        Log.d("Mine", "start onDraw width="+width + " height="+height);

        Paint paint = new Paint();

        paint.setColor(context.getResources().getColor(R.color.blue));
        canvas.drawRect(0, 0, width, height, paint);

        paint.setColor(context.getResources().getColor(R.color.red));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        canvas.drawRect(200, 50, 300, 100, paint);

        canvas.drawLine(200, 150, 350, 170,  paint);
        
        RectF rect = new RectF(200, 200, 400, 270);
        canvas.drawOval(rect, paint);
        
        rect.set(200,300, 400, 370);
        canvas.drawRoundRect(rect, 20, 10, paint);

        Log.d("Mine", "End of onDraw");
    }
    
    
    @Override
    protected void onDraw(Canvas canvas) {

        float width = getWidth();
        float  height = getHeight();
        // Draw the background...
        Log.d("Mine", "start onDraw width="+width + " height="+height);

        Paint paint = new Paint();
        Resources res = context.getResources();
        int color = res.getColor(R.color.blue);

        paint.setColor(color);
        canvas.drawRect(0, 0, width, height, paint);

        paint.setColor(context.getResources().getColor(R.color.red));
        canvas.drawCircle(200, 50, 40, paint);

        paint.setColor(context.getResources().getColor(R.color.redOpaque));
        canvas.drawCircle(200, 150, 40, paint);

        paint.setColor(context.getResources().getColor(R.color.redTranslucent));
        canvas.drawCircle(200, 250, 40, paint);

        paint.setColor(context.getResources().getColor(R.color.green));
        canvas.drawCircle(200, 350, 40, paint);

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(200, 450, 40, paint);

        paint.setStrokeWidth(10);
        canvas.drawCircle(200, 550, 40, paint);

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(200, 650, 40, paint); 

        Log.d("Mine", "End of onDraw");
    }
     */
    
    

}
