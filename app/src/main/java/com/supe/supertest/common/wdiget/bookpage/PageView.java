package com.supe.supertest.common.wdiget.bookpage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.supe.supertest.book.module.ShowChar;
import com.supe.supertest.common.utils.ScreenUtils;
import com.supe.supertest.common.wdiget.bookpage.animation.CoverPageAnim;
import com.supe.supertest.common.wdiget.bookpage.animation.HorizonPageAnim;
import com.supe.supertest.common.wdiget.bookpage.animation.NonePageAnim;
import com.supe.supertest.common.wdiget.bookpage.animation.PageAnimation;
import com.supe.supertest.common.wdiget.bookpage.animation.ScrollPageAnim;
import com.supe.supertest.common.wdiget.bookpage.animation.SimulationPageAnim;
import com.supe.supertest.common.wdiget.bookpage.animation.SlidePageAnim;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.RequiresApi;

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
    //取消长按事件的选中状态
    private boolean isCancelSelected;
    private boolean isLastRefresh = false;

    private RectF mViewF = null;


    //做长按事件
    private Timer timer;

    //Mode
    private Mode currentMode = Mode.Normal;
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

        if (isCancelSelected) {
            isCancelSelected = false;
            mPageLoader.onDraw(getNextPage());
        }

        //绘制高亮
        mPageLoader.onDraw();
        //绘制文本
        /**
         * 此此绘制Content，是为了刷掉高亮显示，但不是每次onDraw都需要，是在高亮显示的需要。
         * 在调用invalidate的时候，高亮显示并没有被刷新。 两种可能，一个是绘制的不是一个Canvas，另一个是没有走这写代码。
         *
         */
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

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
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
                isMove = false;
                isCancelSelected = false;

                cancelLongClickListen();
                canTouch = mTouchListener.onTouch();
                mPageAnim.onTouchEvent(event);

                /**
                 * 当长按高亮显示之后，然后进行点击区域模式划分
                 */
                if (currentMode != Mode.Normal) {
                    if (!mPageLoader.checkIfSelectRegionMove(x, y)) {
                        currentMode = Mode.Normal;
                        isCancelSelected = true;
                        mPageLoader.setMode(Mode.Normal);
                        Log.d("PageView", "这是取消长按的postInvalidate");
                        Log.i("postInvalidate", "---------这是取消长按----------我是MOVE事件的刷新页面");
                        postInvalidate();
                    } else {
                        currentMode = mPageLoader.getMode();
                        Log.d("PageView", "我是点击的滑动模式");
                    }

                } else {
                    Log.d("PageView", " mPageAnim.onTouchEvent");
                }

                /**
                 * 长按事件
                 * 此出会需要过滤在滑动时候的长按
                 */
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        ((Activity) getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (currentMode == Mode.Normal) {
                                    isLongClick = true;
                                    currentMode = Mode.PressSelectText;
                                    mPageLoader.setMode(Mode.PressSelectText);// 设置Mode
                                    mPageLoader.setDown_x(x);
                                    mPageLoader.setDown_y(y);
                                    Log.d("PageView", "这是长按的postInvalidate");
                                    Log.i("postInvalidate", "---------这是长按----------我是MOVE事件的刷新页面");
                                    postInvalidate();
                                }
                            }
                        });
                    }
                }, LONG_CLICK_DURATION);
                break;
            case MotionEvent.ACTION_MOVE:

                mTouchListener.onWindow(Mode.Normal);

                /**
                 * 滑动绘制高亮显示
                 * 与滑动翻页事件冲突。
                 */
                if (currentMode == Mode.SelectMoveForward) {//向前滑动的模式
                    if (mPageLoader.isCanMoveForward(event.getX(), event.getY())) {
                        Log.i("PageView", "CanMoveForward  I Can Move Forward");
                        isCancelSelected = true;
                        mPageLoader.checkSelectForwardText(event.getX(), event.getY());// 选择选中的字体。
                        postInvalidate();
                        Log.i("postInvalidate", "-------------SelectMoveForward------我是MOVE事件的刷新页面");
                    }
                } else if (currentMode == Mode.SelectMoveBack) {
                    if (mPageLoader.isCanMoveBack(event.getX(), event.getY())) {
                        Log.i("PageView", "CanMoveBack  I Can Move Back");
                        isCancelSelected = true;
                        mPageLoader.checkSelectBackText(event.getX(), event.getY());// 选择选中的字体。
                        Log.i("postInvalidate", "---------SelectMoveBack----------我是MOVE事件的刷新页面");
                        postInvalidate();
                    }
                } else {
                    //判断是否大于最小滑动值。
                    int slop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
                    if (!isMove) {
                        isMove = Math.abs(mStartX - event.getX()) > slop || Math.abs(mStartY - event.getY()) > slop;
                    }

                    //如果滑动了，则进行翻页。
                    if (isMove) {
                        isLableClick = false;
                        cancelLongClickListen();// 取消长按事件
                        mPageAnim.onTouchEvent(event);//
                    }
                }


                break;
            case MotionEvent.ACTION_UP:
                isLastRefresh = true;
                mTouchListener.onWindow(currentMode);
                if (!isMove && !isLongClick && !isLableClick) {// 事件优先级最低 的中间区域事件
                    cancelLongClickListen();
                    //设置中间区域范围
                    if (mCenterRect == null) {
                        mCenterRect = new RectF(mViewWidth / 5, mViewHeight / 3,
                                mViewWidth * 4 / 5, mViewHeight * 2 / 3);
                    }

                    //是否点击了中间
                    if (mCenterRect.contains(x, y)) {
                        if (mTouchListener != null && !isLongClick) {
                            mTouchListener.center();
                        }
                        return true;
                    }
                } else {
                    if (isLongClick) {
                        cancelLongClickListen();
                        Log.i("PageView  Log", "我是长按事件   isLongClick");
                        return true;
                    }
                }

                if (currentMode == Mode.Normal) {
                    mPageAnim.onTouchEvent(event);
                }
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

        void onWindow(Mode mode);
    }


    /**
     * Long press operation corresponding to the four modes
     */
    public enum Mode {
        Normal,
        PressSelectText,//按下滑动模式
        SelectMoveForward,//向前滑动模式
        SelectMoveBack//向后滑动模式
    }




}
