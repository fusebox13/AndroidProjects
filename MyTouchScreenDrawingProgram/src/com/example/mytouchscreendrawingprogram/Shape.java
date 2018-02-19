package com.example.mytouchscreendrawingprogram;
import java.io.Serializable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public abstract class Shape implements Serializable {
    float x, y;
    int color;
    public abstract void draw(Canvas canvas, Paint paint);
    public abstract void subsequentPoint(float x, float y);

    public void setColor(Paint paint)
    {       
        this.color = paint.getColor();
    }

    public Shape(float x, float y, Paint paint)
    {
        this.x = x;
        this.y =y;
        setColor(paint);
    }    

}

class Rectangle extends Shape
{
    private float second_x, second_y;

    public Rectangle(float x, float y, Paint paint)
    {
        super(x,y, paint);
        second_x = x;
        second_y =y;
    }
    public  void subsequentPoint(float x, float y)
    {
        second_x =x;
        second_y = y;
    }

    public void draw(Canvas canvas, Paint paint)
    {
        paint.setColor(color);
        float left = Math.min(x,  second_x);
        float top = Math.min(y,  second_y);
        float right = Math.max(x, second_x);
        float bottom = Math.max(y,  second_y);
        canvas.drawRect(left, top, right, bottom, paint);
    }
}

class Oval extends Shape
{
    private float second_x, second_y;

    public Oval(float x, float y, Paint paint)
    {
        super(x,y,paint);
        second_x = x;
        second_y =y;
    }

    public  void subsequentPoint(float x, float y)
    {
        second_x =x;
        second_y = y;
    }
    public void draw(Canvas canvas, Paint paint)
    {
        paint.setColor(color);
        float left = Math.min(x,  second_x);
        float top = Math.min(y,  second_y);
        float right = Math.max(x, second_x);
        float bottom = Math.max(y,  second_y);
        RectF rect= new RectF(left, top, right, bottom);
        canvas.drawOval(rect, paint);
    }
}

class Line extends Shape
{
    private float second_x, second_y;

    public Line(float x, float y, Paint paint)
    {
        super(x,y,paint);
        second_x = x;
        second_y =y;
    }

    public  void subsequentPoint(float x, float y)
    {
        second_x =x;
        second_y = y;
    }
    public void draw(Canvas canvas, Paint paint)
    {
        paint.setColor(color);
        canvas.drawLine(x,y, second_x, second_y, paint);
    }


}
