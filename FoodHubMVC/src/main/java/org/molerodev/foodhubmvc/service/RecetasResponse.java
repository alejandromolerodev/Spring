package org.molerodev.foodhubmvc.service;

import java.util.List;

public class RecetasResponse {
    private List<Hit> hits;

    public List<Hit> getHits() {
        return hits;
    }

    public void setHits(List<Hit> hits) {
        this.hits = hits;
    }
}
