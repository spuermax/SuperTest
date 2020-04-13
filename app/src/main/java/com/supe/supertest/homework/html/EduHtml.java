package com.supe.supertest.homework.html;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.CharacterStyle;
import android.text.style.ImageSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author yinzh
 * @Date 2020/3/23 16:44
 * @Description
 */
public class EduHtml {

    private static Pattern IMAGE_FILTER = Pattern.compile("<[^>]+/?>", Pattern.DOTALL);
    public static Pattern IMAGE_URL_FILTER = Pattern.compile("<img src=['\"]([^>'\"]+)['\"][^>]+>", Pattern.DOTALL);
    private static boolean mIsClickable = true;
    private static boolean mIsMove = false;
    private static float mShiftDownY;
    private static View.OnTouchListener tvTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    if (mIsMove) {
                        mIsClickable = false;
                        mIsMove = false;
                    } else {
                        mIsClickable = true;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mShiftDownY != event.getY()) {
                        mIsMove = true;
                    }
                    break;
                case MotionEvent.ACTION_DOWN:
                    mShiftDownY = event.getY();
                    if (mIsMove) {
                        mIsClickable = false;
                        mIsMove = false;
                    } else {
                        mIsClickable = true;
                    }
                    break;
            }
            return false;
        }
    };
    private ArrayList<String> imageArray;
    private Context mContext;

    private EduHtml(Context context) {
        this.mContext = context;
    }

    public static SpannableStringBuilder addImageClickListener(
            SpannableStringBuilder spaned, TextView textView, Context context) {
        EduHtml instance = new EduHtml(context);
        instance.imageArray = new ArrayList<String>();
        textView.setClickable(true);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setOnTouchListener(tvTouchListener);
        spaned = instance.addImageClick(spaned, null);
        return spaned;
    }

    private String getImageUrl(String img) {
        Matcher matcher = IMAGE_URL_FILTER.matcher(img);
        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }

    private String getSourceImages(String source) {
        int imgCount = 0;
        StringBuilder builder = new StringBuilder();
        StringBuffer stringBuffer = new StringBuffer();

        Matcher matcher = IMAGE_FILTER.matcher(source);
        while (matcher.find()) {
            String tag = matcher.group();
            if (tag.startsWith("<img")) {
                String imgUrl = getImageUrl(tag);
                if (imgUrl != null) {
                    imageArray.add(imgUrl);
                }
                if (imgCount < 3) {
                    builder.append(tag).append("&nbsp;");
                }
                imgCount++;
            }
            matcher.appendReplacement(stringBuffer, "");
        }
        matcher.appendTail(stringBuffer);

        int length = stringBuffer.length();
        stringBuffer.delete(length > 20 ? 20 : length, length);
        if (imgCount > 0) {
            stringBuffer.append("<p></p>").append(builder);
        }
        return stringBuffer.toString();
    }

    private SpannableStringBuilder addImageClick(
            SpannableStringBuilder spanned, final ArrayList<String> array) {
        CharacterStyle[] characterStyles = spanned.getSpans(0, spanned.length(), CharacterStyle.class);
        int index = 0;
        for (CharacterStyle characterStyle : characterStyles) {
            if (characterStyle instanceof ImageSpan) {
                ImageSpan imageSpan = (ImageSpan) characterStyle;
                String src = imageSpan.getSource();
//                ImageClickSpan clickSpan = new ImageClickSpan(mContext, src, index); //  稍等 ，此处有漏斗
                int start = spanned.getSpanStart(characterStyle);
                int end = spanned.getSpanEnd(characterStyle);
                if (array == null) {
                    imageArray.add(index, src);
                }
                index++;
//                spanned.setSpan(clickSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);稍等 ，此处有漏斗
            }
        }

        return spanned;
    }

    @SuppressLint("AppCompatCustomView")
    private class MyTextView extends TextView {
        public MyTextView(Context context) {
            super(context);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            return super.onTouchEvent(event);
        }
    }
}

