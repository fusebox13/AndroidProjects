package com.example.enhancedtextview;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;


public class MyDrawTextView extends TextView {

  private Paint marginPaint;
  private Paint linePaint;
  private int paperColor;
  private float margin;
    
  public MyDrawTextView (Context context, AttributeSet ats, int ds) {
    super(context, ats, ds);
    init();
  }

  public MyDrawTextView (Context context) {
    super(context);
    init();
  }

  public MyDrawTextView (Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  private void init() {
    // Get a reference to our resource table.
    Resources myResources = getResources();

    // Create the paint brushes we will use in the onDraw method.
    marginPaint = new Paint();
    marginPaint.setColor(myResources.getColor(R.color.notepad_margin));

    linePaint = new Paint();
    linePaint.setColor(myResources.getColor(R.color.notepad_lines));
    linePaint.setStrokeWidth(20f);

    // Get the paper background color and the margin width.
    paperColor = myResources.getColor(R.color.notepad_paper);
    margin = myResources.getDimension(R.dimen.notepad_margin);
  }

  @Override
  public void onDraw(Canvas canvas) {
    // Color as paper
    canvas.drawColor(paperColor);
    float width = getWidth();
    float height = getHeight();
    
// Top and Bottom Thick Lines drawn
    canvas.drawLine(0, 0, width, 0, linePaint);
    canvas.drawLine(0, height, width, height, linePaint);

    // Draw margin (vertical skinny line)
    canvas.drawLine(margin, 0, margin, getHeight(), marginPaint);

    // Move the text across from the margin
   canvas.save();
   canvas.translate(margin, 0);

    // Use the TextView to render the text.
    super.onDraw(canvas);
    canvas.restore();
  }
}