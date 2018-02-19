package customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class HangmanView extends View {
	
	Context context;
	int backgroundColor;
	Paint blackPen;
	Paint thinBlackPen;
	int strokeWidth = 30;
	
	int guesses = 0;

	public HangmanView(Context context) 
	{
		super(context);
		this.context = context;	
		init();
	}
	
	public HangmanView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.context = context;
		init();
	}
	
	public HangmanView(Context context, AttributeSet attrs, int defaultStyle)
	{
		super(context, attrs, defaultStyle);
		this.context = context;
		init();
	}
	
	private void init()
	{
		backgroundColor = Color.WHITE;
		
		blackPen = new Paint();
		blackPen.setColor(Color.BLACK);
		blackPen.setStrokeWidth(strokeWidth);
		
		thinBlackPen = new Paint();
		thinBlackPen.setColor(Color.BLACK);
		thinBlackPen.setStrokeWidth(strokeWidth/2);
		thinBlackPen.setStyle(Paint.Style.STROKE);
	}
	
	public void themeBlackOnWhite()
	{
		backgroundColor = Color.WHITE;
		blackPen.setColor(Color.BLACK);
		thinBlackPen.setColor(Color.BLACK);
		invalidate();
	}
	
	public void themeWhiteOnBlack()
	{
		backgroundColor = Color.BLACK;
		blackPen.setColor(Color.WHITE);
		thinBlackPen.setColor(Color.WHITE);
		invalidate();
	}
	
	public void incrementDrawing()
	{
		guesses++;
		invalidate();
	}
	
	public void resetDrawing()
	{
		guesses = 0;
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		float width = this.getWidth();
		float height = this.getHeight();
		
		canvas.save();
		canvas.scale(width/1080, height/779);
		
		canvas.drawColor(backgroundColor);
		
		canvas.drawLine(width/2 - 100, 779-strokeWidth, width/2 + 400, 779-strokeWidth, blackPen);
		canvas.drawLine(width/2 + 300- (strokeWidth/2), 779-strokeWidth, width/2+300 - (strokeWidth/2), 779-600, blackPen);
		canvas.drawLine(width/2, 779-600, width/2 + 300, 779-600, blackPen);
		canvas.drawLine(width/2 + (strokeWidth/2), 779-600, width/2 + + (strokeWidth/2), 779-550, blackPen);
		
		int radius = 50;
		
		
		for (int i = 1; i <= guesses; i++)
		{
			switch(i)
			{
			case 1:
				//head
				canvas.drawCircle(width/2 + (strokeWidth/2), 779-550+radius, radius, thinBlackPen);
				break;
			case 2:
				//body
				canvas.drawLine(width/2 + (strokeWidth/2), 779-550+(radius*2), width/2+(strokeWidth/2), 779-200, thinBlackPen);
				break;
			case 3:
				//arm one
				canvas.drawLine(width/2 + (strokeWidth/2), 779-550+(radius*2)+75, width/2+(strokeWidth/2)-100, 779-550+(radius*2)+25, thinBlackPen);
				break;
			case 4:
				//arm two
				canvas.drawLine(width/2 + (strokeWidth/2), 779-550+(radius*2)+75, width/2+(strokeWidth/2)+100, 779-550+(radius*2)+25, thinBlackPen);
				break;
			case 5:
				//leg one
				canvas.drawLine(width/2 + (strokeWidth/2), 779-200-(strokeWidth/2)+10, width/2+(strokeWidth/2)-100, 779-200 +100, thinBlackPen);
				break;
			case 6:
				//leg two (GAMEOVER)
				canvas.drawLine(width/2 + (strokeWidth/2), 779-200-(strokeWidth/2)+10, width/2+(strokeWidth/2)+100, 779-200 +100, thinBlackPen);
				break;
			default:
				break;	
			}
		}
		canvas.restore();
		
	}
	

}
