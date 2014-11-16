package yiliao.demo.tool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class BorderView extends TextView {
	// no_high:Color.rgb(220, 230, 242)
	// high:Color.rgb(162, 205, 144)
	int colorBack;
	private Paint paint = new Paint();
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// Paint paint = new Paint();
		int radius = 30;
		int colorPaint = Color.rgb(66, 120, 185);

		paint.setColor(colorPaint);
		paint.setStrokeWidth(8);
		canvas.drawCircle(radius, radius, radius, paint);
		canvas.drawCircle(this.getWidth() - radius, radius, radius, paint);

		canvas.drawCircle(radius, this.getHeight() - radius, radius, paint);
		canvas.drawCircle(this.getWidth() - radius, this.getHeight() - radius,
				radius, paint);

		paint.setColor(colorBack);
		// left top
		canvas.drawCircle(radius, radius, radius - 4, paint);
		canvas.drawRect(radius, 0, radius * 2, radius * 2, paint);
		canvas.drawRect(0, radius, radius * 2, radius * 2, paint);
		// right top
		canvas.drawCircle(this.getWidth() - radius, radius, radius - 4, paint);
		canvas.drawRect(this.getWidth() - radius * 2, 0, this.getWidth()
				- radius, radius * 2, paint);
		canvas.drawRect(this.getWidth() - radius * 2, radius, this.getWidth(),
				radius * 2, paint);
		// left bottom
		canvas.drawCircle(radius, this.getHeight() - radius, radius - 4, paint);
		canvas.drawRect(0, this.getHeight() - radius * 2, radius * 2,
				this.getHeight() - radius, paint);
		canvas.drawRect(radius, this.getHeight() - radius, radius * 2,
				this.getHeight(), paint);
		// right bottom
		canvas.drawCircle(this.getWidth() - radius, this.getHeight() - radius,
				radius - 4, paint);
		canvas.drawRect(this.getWidth() - radius * 2, this.getHeight() - radius
				* 2, this.getWidth(), this.getHeight() - radius, paint);
		canvas.drawRect(this.getWidth() - radius * 2, this.getHeight() - radius
				* 2, this.getWidth() - radius, this.getHeight(), paint);
		// draw top left right line
		paint.setColor(colorPaint);
		canvas.drawLine(radius, 0, this.getWidth() - radius, 0, paint);
		canvas.drawLine(0, radius, 0, this.getHeight() - radius, paint);
		canvas.drawLine(this.getWidth(), radius, this.getWidth(),
				this.getHeight() - radius, paint);
		// draw down line
		canvas.drawLine(radius, this.getHeight(), this.getWidth() - radius,
				this.getHeight(), paint);
		// draw in Rect
		paint.setColor(colorBack);
		canvas.drawRect(radius * 2, 4, this.getWidth() - radius * 2,
				this.getHeight() - 4, paint);
		canvas.drawRect(4, radius * 2, this.getWidth() - 4, this.getHeight()
				- radius * 2, paint);
	}

	public BorderView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setColorHigh(int color) {
		colorBack = color;
	}
}
