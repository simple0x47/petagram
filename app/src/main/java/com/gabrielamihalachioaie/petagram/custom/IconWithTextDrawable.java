package com.gabrielamihalachioaie.petagram.custom;

import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class IconWithTextDrawable extends Drawable {
    private String text;
    private int alpha;
    private Bitmap icon;
    private Paint iconPaint;
    private TextPaint textPaint;
    private float textY;

    public IconWithTextDrawable(String text, Bitmap icon, Typeface textFont) {
        this.text = text;
        this.icon = icon;
        iconPaint = new Paint();
        iconPaint.setColor(0xFFFFFFFF);

        textPaint = new TextPaint();
        textPaint.linkColor = 0x000000FF;
        textPaint.setTextSize(48f);
        textPaint.setTextAlign(Paint.Align.CENTER);

        textY = textPaint.getTextSize() / 2f;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawBitmap(icon, new Rect(0, 0, icon.getWidth(), icon.getHeight()), canvas.getClipBounds(), iconPaint);
        canvas.drawText(text, 0f, textY, this.textPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        this.alpha = alpha;
        textPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return alpha;
    }
}
