package com.supe.supertest.common.wdiget.bookpage;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.supe.supertest.common.utils.ScreenUtils;
import com.supe.supertest.common.wdiget.bookpage.animation.CoverPageAnim;
import com.supe.supertest.common.wdiget.bookpage.animation.HorizonPageAnim;
import com.supe.supertest.common.wdiget.bookpage.animation.NonePageAnim;
import com.supe.supertest.common.wdiget.bookpage.animation.PageAnimation;
import com.supe.supertest.common.wdiget.bookpage.animation.ScrollPageAnim;
import com.supe.supertest.common.wdiget.bookpage.animation.SimulationPageAnim;
import com.supe.supertest.common.wdiget.bookpage.animation.SlidePageAnim;
import com.supe.supertest.common.wdiget.bookpage.show.ShowChar;
import com.supe.supertest.common.wdiget.bookpage.show.ShowLine;
import com.supermax.base.common.log.L;
import com.supermax.base.common.widget.toast.QsToast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.CLIPBOARD_SERVICE;


/**
 * @Author yinzh
 * @Date 2018/11/26 15:47
 * @Description
 */
public class PageView extends View implements GestureDetector.OnGestureListener{

    //滚动效果
    public final static int PAGE_MODE_SIMULATION = 0;
    public final static int PAGE_MODE_COVER = 1;
    public final static int PAGE_MODE_SLIDE = 2;
    public final static int PAGE_MODE_NONE = 3;
    public final static int PAGE_MODE_SCROLL = 4;

    //长按事件 时间
    protected final static int LONG_CLICK_DURATION = 500;

    private final static String TAG = "BookPageWidget";

    private int mViewWidth = 0; // 当前View的宽
    private int mViewHeight = 0; // 当前View的高

    private int mStartX = 0;
    private int mStartY = 0;
    private boolean isMove = false;
    //初始化参数
    private int mBgColor = 0xFFCEC29C;
    private int mPageMode = PAGE_MODE_COVER;

    //是否允许点击
    private boolean canTouch = true;
    //判断是否初始化完成
    private boolean isPrepare = false;
    //是否是长按事件
    private boolean isLongClick = false;
    //唤醒菜单的区域
    private RectF mCenterRect = null;

    private RectF mViewF = null;

    private Timer timer;

    /*************************************手势滑动********************************************/
    private Mode mCurrentMode = Mode.Normal;  // 模式
    private ShowChar FirstSelectShowChar = null;// 选中的做文字，
    private ShowChar LastSelectShowChar = null; //选中的的右文字

    //动画类
    private PageAnimation mPageAnim;
    //动画监听类
    private PageAnimation.OnPageChangeListener mPageAnimListener = new PageAnimation.OnPageChangeListener() {
        @Override
        public boolean hasPrev() {
            return PageView.this.hasPrev();
        }

        @Override
        public boolean hasNext() {
            return PageView.this.hasNext();
        }

        @Override
        public void pageCancel() {
            mTouchListener.cancel();
            mPageLoader.pageCancel();
        }
    };

    //点击监听
    private TouchListener mTouchListener;
    //内容加载器
    private PageLoader mPageLoader;

    public PageView(Context context) {
        this(context, null);
    }

    public PageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 注册长按事件
     */
    private void init() {
        setOnLongClickListener(longClickListener);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        //重置图片的大小,由于w,h可能比原始的Bitmap更大，所以如果使用Bitmap.setWidth/Height()是会报错的。
        //所以最终还是创建Bitmap的方式。这种方式比较消耗性能，暂时没有找到更好的方法。
        setPageMode(mPageMode);
        //重置页面加载器的页面
        mPageLoader.setDisplaySize(w, h);
        //初始化完成
        isPrepare = true;
    }

    //设置翻页的模式
    public void setPageMode(int pageMode) {
        mPageMode = pageMode;
        //视图未初始化的时候，禁止调用
        if (mViewWidth == 0 || mViewHeight == 0) return;

        switch (pageMode) {
            case PAGE_MODE_SIMULATION:
                mPageAnim = new SimulationPageAnim(mViewWidth, mViewHeight, this, mPageAnimListener);
                break;
            case PAGE_MODE_COVER:
                mPageAnim = new CoverPageAnim(mViewWidth, mViewHeight, this, mPageAnimListener);
                break;
            case PAGE_MODE_SLIDE:
                mPageAnim = new SlidePageAnim(mViewWidth, mViewHeight, this, mPageAnimListener);
                break;
            case PAGE_MODE_NONE:
                mPageAnim = new NonePageAnim(mViewWidth, mViewHeight, this, mPageAnimListener);
                break;
            case PAGE_MODE_SCROLL:
                mPageAnim = new ScrollPageAnim(mViewWidth, mViewHeight, 0,
                        ScreenUtils.dpToPx(PageLoader.DEFAULT_MARGIN_HEIGHT), this, mPageAnimListener);
                break;
            default:
                mPageAnim = new SimulationPageAnim(mViewWidth, mViewHeight, this, mPageAnimListener);
        }
    }

    public Bitmap getNextPage() {
        if (mPageAnim == null) return null;
        return mPageAnim.getNextBitmap();
    }

    public Bitmap getBgBitmap() {
        if (mPageAnim == null) return null;
        return mPageAnim.getBgBitmap();
    }


    public boolean autoPrevPage() {
        //滚动暂时不支持自动翻页
        if (mPageAnim instanceof ScrollPageAnim) {
            return false;
        } else {
            startPageAnim(PageAnimation.Direction.PRE);
            return true;
        }
    }

    public boolean autoNextPage() {
        if (mPageAnim instanceof ScrollPageAnim) {
            return false;
        } else {
            startPageAnim(PageAnimation.Direction.NEXT);
            return true;
        }
    }

    private void startPageAnim(PageAnimation.Direction direction) {
        if (mTouchListener == null) return;
        //是否正在执行动画
        abortAnimation();
        if (direction == PageAnimation.Direction.NEXT) {
            int x = mViewWidth;
            int y = mViewHeight;
            //设置点击点
            mPageAnim.setTouchPoint(x, y);
            //初始化动画
            mPageAnim.setStartPoint(x, y);
            //设置方向
            Boolean hasNext = hasNext();

            mPageAnim.setDirection(direction);
            if (!hasNext) {
                return;
            }
        } else {
            int x = 0;
            int y = mViewHeight;
            //初始化动画
            mPageAnim.setStartPoint(x, y);
            //设置点击点
            mPageAnim.setTouchPoint(x, y);
            mPageAnim.setDirection(direction);
            //设置方向方向
            Boolean hashPrev = hasPrev();
            if (!hashPrev) {
                return;
            }
        }
        mPageAnim.startAnim();
        this.postInvalidate();
    }

    public void setBgColor(int color) {
        mBgColor = color;
    }

    public void canTouchable(boolean touchable) {
        canTouch = touchable;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //绘制背景
        canvas.drawColor(mBgColor);

        //绘制动画
        mPageAnim.draw(canvas);
    }

    private float Down_X = -1, Down_Y = -1;

    private OnLongClickListener longClickListener = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
//            QsToast.show("我是长按事件");
            if (Down_X > 0 && Down_Y > 0) {// 说明还没释放，是长按事件
//                isLongClick = true;
//                postInvalidate();
            }
            return false;
        }
    };

    private boolean isLableClick;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        if (!canTouch && event.getAction() != MotionEvent.ACTION_DOWN) return true;

        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = x;
                mStartY = y;

                Down_X = x;
                Down_Y = y;
                isMove = false;
                isLableClick = false;

                //-------------------------------------模式选择   invalidate  是为了如果是选中状态，但是非点击Icon区域，就取消Select状态。
                if(mCurrentMode != Mode.Normal){//
                    boolean isSelectIcon = checkoutIfSelectMoveIcon(x, y);
                    if(!isSelectIcon){
                        mCurrentMode = Mode.Normal;
                        invalidate();
                    }
                }


                //--------------------------------做每个标注的
                List<Rect> listRect = mPageLoader.getListRect();
                for (int i = 0; i < listRect.size(); i ++){
                    if(listRect.get(i).contains(x, y)){
                        isLableClick = true;
                    }
                }

                cancelLongClickListen();
                canTouch = mTouchListener.onTouch();//  onTouch 事件。
                mPageAnim.onTouchEvent(event);


                // 长按事件  ---------------------
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ((Activity) getContext()).runOnUiThread(() ->
                                isLongClick = true
                        );
                    }
                }, LONG_CLICK_DURATION);

                //定位----行-------字----------------------------------------------------
                TxtPage curPage = mPageLoader.getCurPage(mPageLoader.getPagePos());
                List<ShowLine> showLines = curPage.showLines;
                for (int m = 0; m < showLines.size(); m++) {
                    if (y < showLines.get(m).lintHeight) {// 确定行
                        if (m == 0) {
                            char firstChar = showLines.get(m).CharsData.get(0).charData;
                            char lastChar = showLines.get(m).CharsData.get(showLines.get(m).CharsData.size() - 1).charData;
                        } else {
                            if (y > showLines.get(m - 1).lintHeight && y < showLines.get(m).lintHeight) {

                                //------------------遍历循环每个字的位置，得到具体位置的某个字
                                List<ShowChar> charList = showLines.get(m).CharsData;

                                for (int n = 0; n < charList.size(); n++) {

                                    if (n == charList.size() - 1) {
                                        Log.i("PageView Log", "踏破铁鞋无觅处  是你-  是你--  就是你---" + charList.get(n).charData);
                                        break;
                                    }
                                    if (x >= charList.get(n).x && x <= charList.get(n + 1).x) {
                                        Log.i("PageView Log", "踏破铁鞋无觅处  是你-  是你--  就是你---" + charList.get(n).charData);
                                        break;
                                    }
                                }
                                Log.i("PageView Log", showLines.get(m).getLineData());
                                //  复制到粘贴板。
                            }

                        }

                    }

                }
                break;
            case MotionEvent.ACTION_MOVE:

                //---------------------------------在此事件进行绘制-------------------------------------------




                //判断是否大于最小滑动值。
                int slop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
                if (!isMove) {
                    isMove = Math.abs(mStartX - event.getX()) > slop || Math.abs(mStartY - event.getY()) > slop;
                }

                //如果滑动了，则进行翻页。
                if (isMove) {
                    cancelLongClickListen();
                    isLableClick = false;
                    mPageAnim.onTouchEvent(event);
                }
                break;
            case MotionEvent.ACTION_UP:
                Release();
                if (!isMove && !isLongClick && !isLableClick) {// 事件优先级最低 的中间区域事件
                    cancelLongClickListen();
                    //设置中间区域范围
                    if (mCenterRect == null) {
                        mCenterRect = new RectF(mViewWidth / 5, mViewHeight / 3,
                                mViewWidth * 4 / 5, mViewHeight * 2 / 3);
                    }

                    //是否点击了中间  return true 会消费此事件  先注释，验证isLongClick。
                    if (mCenterRect.contains(x, y)) {
                        if (mTouchListener != null) {
                            mTouchListener.center();
                        }
                        return true;
                    }

                } else {

                    if(isLableClick){//-------如果点击是标注，需要进行查找。 定位
                        L.i("PageView Rect","我点击的是标注。。");
                        mTouchListener.onLabel(1);
                        isLableClick = false;
                        return true;

                    }

                    if(isLongClick){
                        cancelLongClickListen();
                        Log.i("PageView  Log","我是长按事件   isLongClick");
                        isLongClick = false;
                        return true;
                    }

                    cancelLongClickListen();

                }


                mPageAnim.onTouchEvent(event);
                break;
        }
        return true;
    }

    /**
     * Check whether the pressed area is the left and right Icon
     * @param x
     * @param y
     * @return
     */
    private boolean checkoutIfSelectMoveIcon(float x, float y){
        return false;
    }


    /**
     * Move Event do something...
     * @param event
     */
    private void moveAction(MotionEvent event){
        if(mCurrentMode == Mode.SelectMoveForward){// Mode is Forward.

            if(checkoutMoveForward(event.getX(), event.getY())){// Check if you're moving up

            }

        }

    }

    /**
     * Check if you're moving up
     * @param x
     * @param y
     * @return
     */
    private boolean checkoutMoveForward(float x, float y){
        return false;
    }

    private void cancelLongClickListen() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        isLongClick  =false;
    }

    private void Release() {
        Down_X = -1;// 释放
        Down_Y = -1;
    }

    //判断是否下一页存在
    private boolean hasNext() {
        Boolean hasNext = false;
        if (mTouchListener != null) {
            hasNext = mTouchListener.nextPage();
            //加载下一页
            if (hasNext) {
                hasNext = mPageLoader.next();
            }
        }
        return hasNext;
    }

    //判断是否存在上一页
    private boolean hasPrev() {
        Boolean hasPrev = false;
        if (mTouchListener != null) {
            hasPrev = mTouchListener.prePage();
            //加载下一页
            if (hasPrev) {
                hasPrev = mPageLoader.prev();
            }
        }
        return hasPrev;
    }

    @Override
    public void computeScroll() {
        //进行滑动
        mPageAnim.scrollAnim();
        super.computeScroll();
    }

    //如果滑动状态没有停止就取消状态，重新设置Anim的触碰点
    public void abortAnimation() {
        mPageAnim.abortAnim();
    }

    public boolean isPrepare() {
        return isPrepare;
    }

    public boolean isRunning() {
        return mPageAnim.isRunning();
    }

    public void setTouchListener(TouchListener mTouchListener) {
        this.mTouchListener = mTouchListener;
    }

    public void drawNextPage() {
        if (mPageAnim instanceof HorizonPageAnim) {
            ((HorizonPageAnim) mPageAnim).changePage();
        }
        mPageLoader.onDraw(getNextPage(), false);
    }

    /**
     * 刷新当前页(主要是为了ScrollAnimation)
     */
    public void refreshPage() {
        if (mPageAnim instanceof ScrollPageAnim) {
            ((ScrollPageAnim) mPageAnim).refreshBitmap();
        }
        drawCurPage(false);
    }

    //refreshPage和drawCurPage容易造成歧义,后面需要修改

    /**
     * 绘制当前页。
     *
     * @param isUpdate
     */
    public void drawCurPage(boolean isUpdate) {
        mPageLoader.onDraw(getNextPage(), isUpdate);
    }

    //获取PageLoader
    public PageLoader getPageLoader(boolean isLocal) {
        if (mPageLoader == null) {
            if (isLocal) {
                mPageLoader = new LocalPageLoader(this);
            } else {
                mPageLoader = new NetPageLoader(this);
            }
        }
        return mPageLoader;
    }






    /*******************************手势的监听************************************/

    /**
     * 用户按下屏幕的时候的回调
     * 在此时是做 点击区域的判断
     */
    @Override
    public boolean onDown(MotionEvent e) {

        return false;
    }

    /**
     * 用户按下按键后100ms（根据Android7.0源码）还没有松开或者移动就会回调
     * @param e
     */
    @Override
    public void onShowPress(MotionEvent e) {

    }


    /**
     * 用户手指松开（UP事件）的时候如果没有执行onScroll()和onLongPress()这两个回调的话，就会回调这个，
     * @param e
     * @return
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    /**
     * 手指滑动的时候执行的回调（接收到MOVE事件，且位移大于一定距离）
     * e1,e2分别是之前DOWN事件和当前的MOVE事件
     * distanceX和distanceY就是当前MOVE事件和上一个MOVE事件的位移量。
     * @param e1
     * @param e2
     * @param distanceX
     * @param distanceY
     * @return
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    /**
     * 用户长按后（好像不同手机的时间不同，源码里默认是100ms+500ms）触发，触发之后不会触发其他回调，直至松开（UP事件）。
     * @param e
     */
    @Override
    public void onLongPress(MotionEvent e) {

    }

    /**
     * 用户执行抛操作之后的回调，MOVE事件之后手松开（UP事件）那一瞬间的x或者y方向速度，如果达到一定数值（源码默认是每秒50px）
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }



    // ----------------------------------------------------------------------------------------

    public interface TouchListener {
        void center();

        boolean onTouch();

        boolean prePage();

        boolean nextPage();

        void cancel();

        void onLabel(int id);

        boolean onLongClickDown(int x, int y);

        void onLongClickMove(int x, int y);

        void onLongClickUp(int x, int y);

    }


    /**
     *
     */
    private enum Mode{
        Normal,PressSelectText, SelectMoveForward,SelectMoveBack
    }
}
