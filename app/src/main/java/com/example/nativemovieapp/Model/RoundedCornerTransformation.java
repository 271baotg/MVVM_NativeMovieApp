package com.example.nativemovieapp.Model;

import android.graphics.*;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.squareup.picasso.Transformation;

@RequiresApi(api = Build.VERSION_CODES.O_MR1)
public class RoundedCornerTransformation implements Transformation {

    private int radius;
    private int margin;

    public RoundedCornerTransformation(int radius, int margin) {
        this.radius = radius;
        this.margin = margin;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        RectF rectF = new RectF(margin, margin, width - margin, height - margin);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        source.recycle();
        return output;
    }

    @Override
    public String key() {
        return "rounded";
    }
}
