package com.example.bouncingballasync;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;



public class MyBox extends View  implements OnTouchListener{

    MyAsync myAsync;
    private float width=100;    // width & height will be reset on first onDraw call
    private float height=100;   
    Paint background, redPen;
    float ball_x=50.0f, ball_y=50.0f;   
    float ball_vx = 1.0f, ball_vy = 1.5f;

    int radius = 6;
    Rect rect= new Rect();
    
   
    public void startThread()
    {
        myAsync = new MyAsync();
        myAsync.execute(ball_x, ball_y, ball_vx, ball_vy);
    }
    public void stopThread()
    {
        myAsync.cancel(true);
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
            myAsync.update(x*(100/width), y*(100/height));
        }
        return true; // indicate event was handled
    }

  // ******** INNER CLASS  
    
class MyAsync extends AsyncTask<Float, Float, Boolean>{
        float x_update=-1.0f, y_update=-1.0f;
        float x, y, vx, vy;
       
        protected void onCancelled() {            
            
        }
        // update called from Gui side
        public synchronized void update(float x, float y)
        {
            x_update =x;
            y_update=y;
            Log.d("Mine","update "+x + " "+y);
        }
        // checkForUpdate called from the Thread side
        public synchronized void checkForUpdate()
        {
             if (x_update >= 0.0 && y_update >= 0.0)
             {
                 x=x_update;
                 y=y_update;
                 x_update=y_update =-1.0f;
                 Log.d("Mine","Update x,y");
             }  
        }

        // Excution in the Thread
        protected Boolean doInBackground(Float... parm) {
             x=parm[0];
             y=parm[1];
             vx=parm[2];
             vy=parm[3];
             
             while(true)
             {
                 if (isCancelled())
                 {
                     Log.d("Mine","isCancelled=true");
                     break;
                 }
                                  
                 checkForUpdate();
                 
                 x += vx;
                 y += vy;
                 if (x > 100) // Check for hitting right boundary
                 {
                    x = 100;
                    vx = - vx;
                 }
                 if (x < 0)  // Check for hitting left boundary
                 {
                    x = 0;
                    vx = -vx;
                 }
                 if (y > 100) // Check for hitting bottom boundary
                 {
                     y=100;
                     vy=-vy;
                 }
                 if (y < 0)  // Check for hitting top boundary
                 {
                     y=0;
                     vy=-vy;
                 }
                 publishProgress(x,y,vx,vy); // ---> onProgressUpdate
                 try
                 {                 
                     Thread.sleep(50);
                 }catch (InterruptedException e){}
             }
             Log.d("Mine"," Done");
             return true;
         }

         // Executed on the Gui side
         protected void onProgressUpdate(Float... progress) {
            
            ball_x = progress[0];
            ball_y = progress[1];
            ball_vx = progress[2];
            ball_vy = progress[3];
            invalidate();
            
             
             /*  More efficient approach 
            float ball_newx = progress[0];
            float ball_newy = progress[1];
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
         // Excecuted on the GUI side
         protected void onPostExecute(Boolean result) {
          
         }
     }// end of MyAsync


}