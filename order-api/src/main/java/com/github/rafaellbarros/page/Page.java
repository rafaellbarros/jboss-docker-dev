package com.github.rafaellbarros.page;

import java.util.List;

public class Page<T> {
    private List<T> content;
    private int currentPage;
    private int pageSize;
    private long totalElements;
    private int totalPages;

    public Page(List<T> content, int currentPage, int pageSize, long totalPages) {
        this.content = content;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / currentPage);
    }

    // Getters
    public List<T> getContent() {
        return content;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalElementos() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean next() {
        return currentPage < totalPages;
    }

    public boolean previous() {
        return currentPage > 1;
    }
}