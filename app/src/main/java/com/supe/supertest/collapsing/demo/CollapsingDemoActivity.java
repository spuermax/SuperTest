package com.supe.supertest.collapsing.demo;

import android.os.Bundle;
import android.widget.TextView;

import com.supe.supertest.R;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.mvp.QsActivity;

/**
 * @Author yinzh
 * @Date 2020/3/18 10:05
 * @Description
 */
public class CollapsingDemoActivity extends QsActivity {

    @Bind(R.id.tv_content)
    TextView tv_content;


    @Override
    public int layoutId() {
        return R.layout.activity_collapsing_demo;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
            tv_content.setText("我记得 16 、17年的时候这个效果在很多 APP 上都有出现 。之前写过demo ，然后一直也没机会在项目中使用 。\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "实现类似的效果 ，主要是使用三个控件相结合 CoordinatorLayout 、AppbarLayout 、NestedScrollView 。\n" +
                    "\n" +
                    "AppbarLayout 是一种支持响应滚动手势的 app bar 布局 , CollapsingToolbarLayout 则是专门用来实现子布局内不同元素响应滚动细节的布局 。与 AppbarLayout 组合的滚动布局 (RecyclerView, NestedScrollView等) , 需要设置 app:layout_behavior = \"@strng/appbar_scrolling_view_behavior\" 。 没有设置的话 ， AppbarLayout 将不会响应滚动布局的滚动事件 。我记得 16 、17年的时候这个效果在很多 APP 上都有出现 。之前写过demo ，然后一直也没机会在项目中使用 。\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "实现类似的效果 ，主要是使用三个控件相结合 CoordinatorLayout 、AppbarLayout 、NestedScrollView 。\n" +
                    "\n" +
                    "AppbarLayout 是一种支持响应滚动手势的 app bar 布局 , CollapsingToolbarLayout 则是专门用来实现子布局内不同元素响应滚动细节的布局 。与 AppbarLayout 组合的滚动布局 (RecyclerView, NestedScrollView等) , 需要设置 app:layout_behavior = \"@strng/appbar_scrolling_view_behavior\" 。 没有设置的话 ， AppbarLayout 将不会响应滚动布局的滚动事件 。我记得 16 、17年的时候这个效果在很多 APP 上都有出现 。之前写过demo ，然后一直也没机会在项目中使用 。\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "实现类似的效果 ，主要是使用三个控件相结合 CoordinatorLayout 、AppbarLayout 、NestedScrollView 。\n" +
                    "\n" +
                    "AppbarLayout 是一种支持响应滚动手势的 app bar 布局 , CollapsingToolbarLayout 则是专门用来实现子布局内不同元素响应滚动细节的布局 。与 AppbarLayout 组合的滚动布局 (RecyclerView, NestedScrollView等) , 需要设置 app:layout_behavior = \"@strng/appbar_scrolling_view_behavior\" 。 没有设置的话 ， AppbarLayout 将不会响应滚动布局的滚动事件 。我记得 16 、17年的时候这个效果在很多 APP 上都有出现 。之前写过demo ，然后一直也没机会在项目中使用 。\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "实现类似的效果 ，主要是使用三个控件相结合 CoordinatorLayout 、AppbarLayout 、NestedScrollView 。\n" +
                    "\n" +
                    "AppbarLayout 是一种支持响应滚动手势的 app bar 布局 , CollapsingToolbarLayout 则是专门用来实现子布局内不同元素响应滚动细节的布局 。与 AppbarLayout 组合的滚动布局 (RecyclerView, NestedScrollView等) , 需要设置 app:layout_behavior = \"@strng/appbar_scrolling_view_behavior\" 。 没有设置的话 ， AppbarLayout 将不会响应滚动布局的滚动事件 。我记得 16 、17年的时候这个效果在很多 APP 上都有出现 。之前写过demo ，然后一直也没机会在项目中使用 。\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "实现类似的效果 ，主要是使用三个控件相结合 CoordinatorLayout 、AppbarLayout 、NestedScrollView 。\n" +
                    "\n" +
                    "AppbarLayout 是一种支持响应滚动手势的 app bar 布局 , CollapsingToolbarLayout 则是专门用来实现子布局内不同元素响应滚动细节的布局 。与 AppbarLayout 组合的滚动布局 (RecyclerView, NestedScrollView等) , 需要设置 app:layout_behavior = \"@strng/appbar_scrolling_view_behavior\" 。 没有设置的话 ， AppbarLayout 将不会响应滚动布局的滚动事件 。");
    }
}
