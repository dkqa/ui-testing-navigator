package com.dkqa.navigator;

import com.dkqa.navigator.action.PageStep;

class PageWay {
    private PageStep step;
    private double weight;

    PageWay(PageStep step, double weight) {
        this.step = step;
        this.weight = weight;
    }

    public void go() {
        step.go();
    }

    public double getWeight() {
        return weight;
    }
}
