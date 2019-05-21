package com.supe.supertest.book;

import android.os.Bundle;
import android.util.Log;

import com.supe.supertest.R;
import com.supe.supertest.book.presenter.ReadBookPresenter;
import com.supe.supertest.common.model.model.BookChaptersModel;
import com.supe.supertest.common.model.model.ChapterContentModel;
import com.supe.supertest.common.wdiget.bookpage.PageLoader;
import com.supe.supertest.common.wdiget.bookpage.PageView;
import com.supe.supertest.common.wdiget.bookpage.TxtChapter;
import com.supe.supertest.common.wdiget.bookpage.bean.BookChapterBean;
import com.supe.supertest.common.wdiget.bookpage.bean.CollBookBean;
import com.supe.supertest.common.wdiget.bookpage.manager.ReadSettingManager;
import com.supermax.base.common.aspect.ThreadPoint;
import com.supermax.base.common.aspect.ThreadType;
import com.supermax.base.common.log.L;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.common.widget.toast.QsToast;
import com.supermax.base.mvp.QsActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author yinzh
 * @Date 2018/11/27 16:02
 * @Description
 */
public class ReadBookActivity extends QsActivity<ReadBookPresenter> {

    private static final String TAG = "ReadBookActivity";
    public static final String EXTRA_COLL_BOOK = "extra_coll_book";
    public static final String EXTRA_IS_COLLECTED = "extra_is_collected";
    List<TxtChapter> mTxtChapters = new ArrayList<>();

    @Bind(R.id.pageView)
    PageView pageView;

    private String mBookid;
    private PageLoader mPageLoader;
    private CollBookBean mCollBook;
    private boolean isNightMode = false;
    private boolean isFullScreen = false;
    private String mBookId = null;
    private List<BookChapterBean> bookChapterList = new ArrayList<>();


    @Override
    public int layoutId() {
        return R.layout.activity_read_book;
    }

    @Override
    public void initData(Bundle bundle) {
        mCollBook = (CollBookBean) getIntent().getSerializableExtra(EXTRA_COLL_BOOK);
        isNightMode = ReadSettingManager.getInstance().isNightMode();
        isFullScreen = ReadSettingManager.getInstance().isFullScreen();
        if (mBookId == null) {
            mBookId = "/storage/emulated/0/Download/browser/元尊.txt";
        }

        //这里id表示本地文件的路径

        //判断是否文件存在
        if (mCollBook == null) {
            mCollBook = new CollBookBean();
        }


        mCollBook.setIsLocal(true);
        mCollBook.set_id(mBookId);

        //获取页面加载器
        //用IsLocal字段判断是不是本地
        mPageLoader = pageView.getPageLoader(mCollBook.getIsLocal());//=====update为数据库方法


        if (mCollBook.getIsLocal()) {
            mPageLoader.openBook(mCollBook);
        }


//        getPresenter().requestBookChapters("1004912299104101110120105100111110103");
//        getPresenter().requestChapterContent("1004912299104101110120105100111110103",1,2);


        mPageLoader.setOnPageChangeListener(new PageLoader.OnPageChangeListener() {
            @Override
            public void onChapterChange(int pos) {
                Log.i("FreeChapter", "" + pos);
                // 选中可选的 章节目录。
            }

            @Override
            public void onLoadChapter(List<TxtChapter> chapters, int pos) {
                //章节加载。
            }

            @Override
            public void onCategoryFinish(List<TxtChapter> chapters) {

                mTxtChapters.clear();
                mTxtChapters.addAll(chapters);

            }

            @Override
            public void onPageCountChange(int count) {

            }

            @Override
            public void onPageChange(int pos) {

            }
        });


        pageView.setTouchListener(new PageView.TouchListener() {
            @Override
            public void center() {
                QsToast.show("点击中间区域");
            }

            /**
             * 返回值  true 执行翻页的逻辑
             * false  中间区域显示和隐藏
             * @return
             */
            @Override
            public boolean onTouch() {
                Log.i("FBReader", "onTouch");
                return true;
            }

            /**
             * 上一页
             * @return
             */
            @Override
            public boolean prePage() {
                Log.i("FBReader", "prePage");
                return true;
            }

            /**
             * 下一页
             * @return
             */
            @Override
            public boolean nextPage() {
                Log.i("FBReader", "prePage");
                return true;
            }


            @Override
            public void cancel() {
            }

            @Override
            public void onLabel(int id) {
                QsToast.show("你已经点击了  标注");
            }

            @Override
            public void onWindow(PageView.Mode mode) {

            }
        });

    }


    @ThreadPoint(ThreadType.MAIN)
    public void requestBookChapters(BookChaptersModel model) {
        if (model != null) {
            bookChapterList.clear();
            L.i(initTag(), "-------" + model);
            List<BookChaptersModel.CatalogueBean> catalogueBeans = model.getCatalogue();

            for (BookChaptersModel.CatalogueBean catalogueBean : catalogueBeans) {
                BookChapterBean chapterBean = new BookChapterBean();
                chapterBean.setBookCode(model.getBookCode());
                chapterBean.setName(model.getName());
                chapterBean.setId(model.getId());
                chapterBean.setChapterId(catalogueBean.getChapterId());
                chapterBean.setChapterName(catalogueBean.getChapterName());
                bookChapterList.add(chapterBean);
            }


            mCollBook.setBookChapters(bookChapterList);
            mPageLoader.openBook(mCollBook);
        }
    }

    @ThreadPoint(ThreadType.MAIN)
    public void requestChapterContent(ChapterContentModel model) {
        if (model != null) {
            L.i(initTag(), "-----" + model);
        }
    }
}
