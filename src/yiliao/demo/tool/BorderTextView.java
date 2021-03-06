package yiliao.demo.tool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import yiliao.demo.R;

public class BorderTextView extends TextView
{
	private Paint paint = new Paint();
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        
        paint.setColor(getResources().getColor(R.color.kuangcolor));
        paint.setStrokeWidth(8);
        
        canvas.drawLine(0, 0, this.getWidth(), 0, paint);
        canvas.drawLine(0, 4, 0, this.getHeight(), paint);
        canvas.drawLine(this.getWidth(), 4, this.getWidth(), this.getHeight(), paint);
        canvas.drawLine(4, this.getHeight(), this.getWidth()-4, this.getHeight(), paint);
    }
    public BorderTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
}