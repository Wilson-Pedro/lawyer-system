package com.advocacia.estacio.domain.dto;

import java.util.List;

public class RequestIds {
    private List<Long> ids;

    public RequestIds(List<Long> ids) {
        this.ids = ids;
    }

    public List<Long> getIds() {
        return ids;
    }
}
