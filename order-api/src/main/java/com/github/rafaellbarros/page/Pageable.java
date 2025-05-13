package com.github.rafaellbarros.page;

public class Pageable {

    private int page;
    private int size;
    private String ordination;
    private SortDirection direction;

    public Pageable(int page, int size) {
        this(page, size, null, SortDirection.ASC);
    }

    public Pageable(int page, int size, String ordination, SortDirection direction) {
        this.page = page;
        this.size = size;
        this.ordination = ordination;
        this.direction = direction;
    }

    // Getters
    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public String getOrdination() {
        return ordination;
    }

    public SortDirection getDirection() {
        return direction;
    }

    public int getFirstResult() {
        return (page - 1) * size;
    }
}