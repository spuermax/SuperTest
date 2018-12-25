package com.supe.supertest.common.http;

import com.supe.supertest.common.model.BaseModelReq;
import com.supe.supertest.common.model.model.BookChaptersModel;
import com.supe.supertest.common.model.model.ChapterContentModel;
import com.supermax.base.common.aspect.Body;
import com.supermax.base.common.aspect.POST;

/**
 * @Author yinzh
 * @Date 2018/12/15 16:41
 * @Description
 */
public interface BookReadApi {
    /**
     * 请求电子书章节内容
     *
     * @param baseModel
     * @return
     */
    @POST("/ebook/paging")
    ChapterContentModel requestChapterContent(@Body BaseModelReq baseModel);

    /**
     * 请求电子书目录
     *
     * @param baseModelReq
     * @return
     */
    @POST("/ebook/ebookCatalogue")
    BookChaptersModel requestBookChapters(@Body BaseModelReq baseModelReq);

}
