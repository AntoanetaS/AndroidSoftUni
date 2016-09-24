package androidcourse.softuni.bg.spotifyrecycleview.ui.activities.decorations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import androidcourse.softuni.bg.spotifyrecycleview.R;

/**
 * Created by Sansarova on 10.9.2016 г..
 */
public class PlaylistCustomDecoration extends RecyclerView.ItemDecoration {

    Paint explicitPaint, textExplicitPaint;
    int offset;
    Bitmap bitmapDownload;
    int bitmap_w, bitmap_h;
    Rect rectSrc;

    public PlaylistCustomDecoration(Context context) {
        offset = 10;

        explicitPaint = implementPaint(context.getResources().getColor(R.color.colorExplicit));

        textExplicitPaint = implementPaint(context.getResources().getColor(R.color.colorRecycleView));

        bitmapDownload = BitmapFactory.decodeResource(
                context.getResources(),
                R.drawable.ic_action_name);
        bitmap_w = bitmapDownload.getWidth();
        bitmap_h = bitmapDownload.getHeight();
        rectSrc = new Rect(0, 0, bitmap_w, bitmap_h);
    }

    private Paint implementPaint( int Rcolor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Rcolor);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1f);

        return paint;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.set(offset, offset, offset, offset);
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);
        final RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        for (int i = 0; i < parent.getChildCount(); i++) {
            final View child = parent.getChildAt(i);
            if(i%2 != 0) {
                canvas.save();

                // We will now move our canvas to the top left corner of where we want our image to be drawn
                // The top left corner will be at y = 0 and x = recyclerview.width - our image.width
                canvas.translate(0, layoutManager.getLeftDecorationWidth(child));

                // We now draw our image on the canvas
                canvas.drawBitmap(bitmapDownload, layoutManager.getDecoratedLeft(child), layoutManager.getDecoratedTop(child), null);

                canvas.restore();
            }else{

                    canvas.drawRoundRect(new RectF(layoutManager.getDecoratedLeft(child), layoutManager.getDecoratedTop(child)+15,
                            layoutManager.getDecoratedRight(child)-400,
                            layoutManager.getDecoratedBottom(child)-65),6,6,explicitPaint);

                canvas.drawText("EXPLICIT",layoutManager.getDecoratedLeft(child)+10,layoutManager.getDecoratedTop(child)+35, textExplicitPaint );
            }
        }
    }

}
