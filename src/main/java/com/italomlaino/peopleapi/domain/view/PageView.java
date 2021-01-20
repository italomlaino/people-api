package com.italomlaino.peopleapi.domain.view;

import org.springframework.data.domain.Page;

import java.util.List;

public class PageView<T> {

    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private List<T> content;

    public static <T> PageView<T> of(Page<T> page) {
        var pageView = new PageView<T>();
        pageView.setContent(page.getContent());
        pageView.setPageNumber(page.getNumber());
        pageView.setPageSize(page.getSize());
        pageView.setTotalElements(page.getTotalElements());

        return pageView;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
