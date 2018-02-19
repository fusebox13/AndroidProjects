package com.example.mycompass;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View; 

public class CompassView extends View {

    private float bearing=30;

    private Paint markerPaint;
    private Paint textPaint;
    private Paint circlePaint;
    private int textHeight;

    public void increment()
    {
        bearing += 10;
        invalidate();
    }
    public void reset()
    {
        bearing = 0;
        invalidate();
    }

    public CompassView(Context context) {
        super(context);
        initCompassView();
    }

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCompassView();
    }

    public CompassView(Context context,
            AttributeSet ats,
            int defaultStyle) {
        super(context, ats, defaultStyle);
        initCompassView();
    }

    protected void initCompassView() {
        setFocusable(true);

        Resources r = this.getResources();

        circlePaint = new Paint();
        circlePaint.setColor(r.getColor(R.color.backgroundColor));

        textPaint = new Paint();
        textPaint.setColor(r.getColor(R.color.bluePen));
        textPaint.setTextSize(20);
        textPaint.setStyle(Paint.Style.STROKE);

        textHeight = (int)textPaint.measureText("yY");

        markerPaint = new Paint();
        markerPaint.setColor(r.getColor(R.color.redPen));
        markerPaint.setStrokeWidth(4);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int width = getWidth();
        int height = getHeight();

        int centerX = width / 2;
        int centerY = height / 2 ;

        int radius = Math.min(centerX, centerY);

        // Draw the background
        canvas.drawCircle(centerX, centerY, radius, circlePaint);
        canvas.drawCircle(centerX, centerY, radius, textPaint); // Border in blue

        // Rotate our perspective so that the 'top' is
        // facing the current bearing.
        canvas.save();
        canvas.rotate(-bearing, centerX, centerY);

        int textWidth = (int)textPaint.measureText("W"); // "W" is the widest character
        int cardinalX = centerX-textWidth/2;
        int cardinalY = centerY-radius+textHeight;

        // Draw the marker every 15 degrees and text every 45.
        for (int iRotateCount = 0; iRotateCount < 24; iRotateCount++) {
            // Draw a marker.
            canvas.drawLine(centerX, centerY-radius, centerX, centerY-radius+15, markerPaint);

            // Draw the cardinal points
            if (iRotateCount % 6 == 0) 
            {
                String dirString = "";
                switch (iRotateCount) 
                {
                case(0)  : dirString = "N"; break;
                case(6)  : dirString = "E"; break;
                case(12) : dirString = "S"; break;
                case(18) : dirString = "W"; break;
                }
                canvas.drawText(dirString, cardinalX, cardinalY+textHeight, textPaint);
            }

            else if (iRotateCount % 3 == 0) {
                // Draw the text every alternate 45deg
                String angle = String.valueOf(iRotateCount*15);
                float angleTextWidth = textPaint.measureText(angle);

                int angleTextX = (int)(centerX-angleTextWidth/2);
                int angleTextY = centerY-radius+textHeight;
                canvas.drawText(angle, angleTextX, angleTextY+textHeight, textPaint);
            }

            canvas.rotate(15, centerX, centerY);
        }
        canvas.restore();
    }


}