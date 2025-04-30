package com.dancesys.dancesys.infra;


import java.util.List;

public class PaginatedResponse<T> {
    private List<T> conteudo;
    private long total;

    public PaginatedResponse(List<T> conteudo, long total) {
        this.conteudo = conteudo;
        this.total = total;
    }

    public List<T> getConteudo() {
        return conteudo;
    }

    public long getTotal() {
        return total;
    }
}
