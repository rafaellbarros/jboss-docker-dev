package com.github.rafaellbarros.dto;

import lombok.Getter;

import java.util.List;


@Getter
public class PageResposeDTO<T> {
    private List<T> content;
    private int currentPage;
    private int pageSize;
    private long totalElements;
    private int totalPages;

    public PageResposeDTO(List<T> content, int currentPage, int pageSize, long totalElements, int totalPages) {
        this.content = content;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}