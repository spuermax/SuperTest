package com.supe.supertest.book.presenter;

import com.supe.supertest.book.ReadBookActivity;
import com.supe.supertest.common.SuperPresenter;
import com.supe.supertest.common.http.BookReadApi;
import com.supe.supertest.common.model.model.BookChaptersModel;
import com.supe.supertest.common.model.model.ChapterContentModel;
import com.supe.supertest.common.model.req.ModelBookChapterReq;
import com.supe.supertest.common.model.req.ModelChapterContentReq;
import com.supermax.base.common.aspect.ThreadPoint;
import com.supermax.base.common.aspect.ThreadType;

/**
 * @Author yinzh
 * @Date 2018/12/15 16:40
 * @Description
 */
public class ReadBookPresenter extends SuperPresenter<ReadBookActivity> {

    private final String BOOK_CHAPTER_TAG = "requestBookChapters";
    private final String CHAPTER_CONTENT_TAG = "requestChapterContent";

    @ThreadPoint(ThreadType.HTTP)
    public void requestBookChapters(String bookCode) {
        BookReadApi request = createHttpRequest(BookReadApi.class, BOOK_CHAPTER_TAG);
        ModelBookChapterReq req = new ModelBookChapterReq();
        req.bookCode = bookCode;
        BookChaptersModel bookChaptersModel = request.requestBookChapters(req);
        getView().requestBookChapters(bookChaptersModel);

    }


    @ThreadPoint(ThreadType.HTTP)
    public void requestChapterContent(String bookCode, int pageIndex, int chapterId) {
        BookReadApi bookReadApi = createHttpRequest(BookReadApi.class, CHAPTER_CONTENT_TAG);
        ModelChapterContentReq req = new ModelChapterContentReq();
        req.bookCode = bookCode;
        req.pageIndex = pageIndex;
        req.chapterId = chapterId;
        ChapterContentModel contentModel = bookReadApi.requestChapterContent(req);
        getView().requestChapterContent(contentModel);
    }

}
