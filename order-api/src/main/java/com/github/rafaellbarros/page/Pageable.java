package com.github.rafaellbarros.page;

import java.util.Objects;

public class Pageable {
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;
    private static final SortDirection DEFAULT_DIRECTION = SortDirection.ASC;

    private int page;
    private int size;
    private String ordination;
    private SortDirection direction;

    // Construtor padrão com valores default
    public Pageable() {
        this(DEFAULT_PAGE, DEFAULT_SIZE, null, DEFAULT_DIRECTION);
    }

    // Construtor completo
    public Pageable(int page, int size, String ordination, SortDirection direction) {
        this.page = Math.max(page, DEFAULT_PAGE); // Garante que não seja negativo
        this.size = size <= 0 ? DEFAULT_SIZE : size; // Garante tamanho mínimo
        this.ordination = ordination;
        this.direction = Objects.requireNonNullElse(direction, DEFAULT_DIRECTION);
    }

    // Método factory para criação mais fluente
    public static Pageable of(int page, int size) {
        return new Pageable(page, size, null, DEFAULT_DIRECTION);
    }

    // Método factory com ordenação
    public static Pageable of(int page, int size, String ordination, SortDirection direction) {
        return new Pageable(page, size, ordination, direction);
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

    // Calcula o offset para a consulta SQL/JQL
    public int getFirstResult() {
        return page * size;
    }

    // Valida se tem ordenação definida
    public boolean hasOrdination() {
        return ordination != null && !ordination.trim().isEmpty();
    }

    // Builder pattern opcional (se quiser usar)
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int page = DEFAULT_PAGE;
        private int size = DEFAULT_SIZE;
        private String ordination;
        private SortDirection direction = DEFAULT_DIRECTION;

        public Builder page(int page) {
            this.page = Math.max(page, DEFAULT_PAGE);
            return this;
        }

        public Builder size(int size) {
            this.size = size <= 0 ? DEFAULT_SIZE : size;
            return this;
        }

        public Builder ordination(String ordination) {
            this.ordination = ordination;
            return this;
        }

        public Builder direction(SortDirection direction) {
            this.direction = direction != null ? direction : DEFAULT_DIRECTION;
            return this;
        }

        public Pageable build() {
            return new Pageable(page, size, ordination, direction);
        }
    }

    @Override
    public String toString() {
        return "Pageable{" +
                "page=" + page +
                ", size=" + size +
                ", ordination='" + ordination + '\'' +
                ", direction=" + direction +
                '}';
    }
}