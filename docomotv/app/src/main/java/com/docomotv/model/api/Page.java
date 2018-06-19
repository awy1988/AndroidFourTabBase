package com.docomotv.model.api;

/**
 *
 * 分页对象
 */
public class Page extends HttpQueryParamBaseModel {

    private Integer no;
    private Integer size;

    public Page(int no, int size) {
        this.no = no;
        this.size = size;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
