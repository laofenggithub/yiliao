package yiliao.demo.tool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.TextView;

public class QuestionTextView extends TextView
{
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        Paint paint = new Paint();

        paint.setColor(Color.rgb(66, 120, 185));
        paint.setStrokeWidth(8);
        canvas.drawCircle(40, 40, 40, paint);
        canvas.drawCircle(this.getWidth()-40, 40, 40, paint);

        canvas.drawCircle(40, this.getHeight()-70, 40, paint);
        canvas.drawCircle(this.getWidth()-40, this.getHeight()-70, 40, paint);

        paint.setColor(Color.rgb(220, 230, 242));
        // left top
        canvas.drawCircle(40, 40, 36, paint);
        canvas.drawRect(40, 0, 80, 80, paint);
        canvas.drawRect(0, 40, 80, 80, paint);
        // right top
        canvas.drawCircle(this.getWidth()-40, 40, 36, paint);
        canvas.drawRect(this.getWidth()-80, 0, this.getWidth()-40, 80, paint);
        canvas.drawRect( this.getWidth()-80, 40, this.getWidth(), 80, paint);
        // left bottom
        canvas.drawCircle(40, this.getHeight()-70, 36, paint);
        canvas.drawRect(0, this.getHeight()-110, 80, this.getHeight()-70, paint);
        canvas.drawRect(40, this.getHeight()-70, 80, this.getHeight()-30, paint);
        // right bottom
        canvas.drawCircle(this.getWidth()-40, this.getHeight()-70, 36, paint);
        canvas.drawRect(this.getWidth()-80, this.getHeight()-110, this.getWidth(), this.getHeight()-70, paint);
        canvas.drawRect(this.getWidth()-80, this.getHeight()-110, this.getWidth()-40, this.getHeight()-30, paint);
        // draw top left right line
        paint.setColor(Color.rgb(66, 120, 185));
        canvas.drawLine(40, 0, this.getWidth()-40, 0, paint);
        canvas.drawLine(0, 40, 0, this.getHeight()-70, paint);
        canvas.drawLine(this.getWidth(), 40, this.getWidth(), this.getHeight()-70, paint);
        // draw out triangle
        Path path = new Path();
        path.moveTo(92,this.getHeight()-33);
        path.lineTo(149,this.getHeight());
        path.lineTo(206,this.getHeight()-33);
        // draw down line
        paint.setStrokeWidth(4);
        canvas.drawLine(40, this.getHeight()-31, 100, this.getHeight()-31, paint);
        canvas.drawLine(200, this.getHeight()-31, this.getWidth()-40, this.getHeight()-31, paint);
        canvas.drawPath(path, paint);
        // draw in triangle
        paint.setColor(Color.rgb(220, 230, 242));
        Path path1 = new Path();
        path1.moveTo(100,this.getHeight()-33);
        path1.lineTo(149,this.getHeight()-5);
        path1.lineTo(198,this.getHeight()-33);
        canvas.drawPath(path1, paint);
        // draw in Rect
        canvas.drawRect(80, 4, this.getWidth()-80, this.getHeight()-33, paint);
        canvas.drawRect(4, 80, this.getWidth()-4, this.getHeight()-110, paint);

    }
    public QuestionTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
}