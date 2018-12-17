package com.supe.supertest.common.model.model;

import com.supe.supertest.common.model.BaseModel;

import java.util.List;

/**
 * @Author yinzh
 * @Date 2018/12/15 17:02
 * @Description
 */
public class BookChaptersModel extends BaseModel{


    /**
     * bookCode : 1004912299104101110120105100111110103
     * name : 红楼梦之测试数据1
     * id : 10
     * catalogue : [{"chapterId":"1","chapterName":"第1章"},{"chapterId":"2","chapterName":"第2章"},{"chapterId":"3","chapterName":"第3章"}]
     */

    private String bookCode;
    private String name;
    private int id;
    private List<CatalogueBean> catalogue;

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CatalogueBean> getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(List<CatalogueBean> catalogue) {
        this.catalogue = catalogue;
    }

    public static class CatalogueBean {
        /**
         * chapterId : 1
         * chapterName : 第1章
         */

        private String chapterId;
        private String chapterName;

        public String getChapterId() {
            return chapterId;
        }

        public void setChapterId(String chapterId) {
            this.chapterId = chapterId;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }
    }
}
