package com.supe.supertest.homework.html;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.supe.supertest.R;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;

/**
 * @Author yinzh
 * @Date 2020/3/23 16:04
 * @Description
 */
public class EduImageGetterHandler implements Html.ImageGetter {

    private Context mContext;
    private TextView mContainer;

    public EduImageGetterHandler(Context context, TextView view) {
        this.mContainer = view;
        this.mContext = context;
    }

    @Override
    public Drawable getDrawable(String s) {
        if (!s.startsWith("http")) {
            s = mContext.getString(R.string.app_base_url) + s;
        }
        final CacheDrawable drawable = new CacheDrawable();
        try {
            Glide.with(mContext).asBitmap().load(s).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
                    try {
                        float showMaxWidth, showMaxHeight;
                        DisplayMetrics dm2 = mContext.getResources().getDisplayMetrics();
                        showMaxWidth = dm2.widthPixels * 0.9f;
                        showMaxHeight = dm2.heightPixels * 0.3f;

                        int w = resource.getWidth();
                        int h = resource.getHeight();
                        if (w > h) {
                            resource = scaleImage(resource, w > showMaxWidth ? showMaxWidth : w, 0);
                        } else {
                            resource = scaleImage(resource, h > showMaxHeight ? showMaxHeight : h, 0);
                        }

                        drawable.bitmap = resource;
                        drawable.setBounds(0, 0, resource.getWidth(), resource.getHeight());
                        mContainer.setText(mContainer.getText());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    Bitmap failBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.html_image_fail);
                    drawable.bitmap = failBitmap;
                    drawable.setBounds(0, 0, failBitmap.getWidth(), failBitmap.getHeight());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drawable;
    }

    private class CacheDrawable extends BitmapDrawable {
        public Bitmap bitmap;
        private Bitmap loadBitmap;

        public CacheDrawable() {
            loadBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.html_image_loading);
            setBounds(0, 0, loadBitmap.getWidth(), loadBitmap.getHeight());
        }

        @Override
        public void draw(Canvas canvas) {
            super.draw(canvas);
            if (bitmap == null) {
                canvas.drawBitmap(loadBitmap, 0, 0, new Paint());
                return;
            }

            canvas.drawBitmap(bitmap, 0, 0, new Paint());
        }
    }

    /**
     * 图片旋转
     *
     * @param bitmap    图片
     * @param imageSize 图片大小
     * @param degree    图片旋转的角度，如果没有旋转，则为0
     * @return
     */
    public static Bitmap scaleImage(Bitmap bitmap, float imageSize, int degree) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        //float density = context.getResources().getDisplayMetrics().density;
        int bounding = Math.round(imageSize);

        float xScale = ((float) bounding) / width;
        float yScale = ((float) bounding) / height;
        float scale = (xScale <= yScale) ? xScale : yScale;

        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        matrix.postRotate((float) degree);

        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }
}

