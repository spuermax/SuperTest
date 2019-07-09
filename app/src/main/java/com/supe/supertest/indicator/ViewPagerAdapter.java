package com.supe.supertest.indicator;

import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @Author yinzh
 * @Date 2018/11/15 10:43
 * @Description
 */
public class ViewPagerAdapter extends PagerAdapter {
    private String[] arrStr;

    public ViewPagerAdapter(String [] arrStr) {
        super();
        this.arrStr = arrStr;
    }

    @Override
    public int getCount() {
        return arrStr.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TextView textView = new TextView(container.getContext());
        textView.setText(arrStr[position]);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(24);
        container.addView(textView);
        return textView;
    }



    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return arrStr[position];
    }
}
