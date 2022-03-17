package com.dkqa.navigator.param;

import com.dkqa.navigator.Page;

public class PageParamBuilder {

    private Page from;
    private Page to;

    public PageParamBuilder(Page from, Page to) {
        this.from = from;
        this.to = to;
    }

    public PageParamExecutorBuilder executor() {
        return new PageParamExecutorBuilder(from, to);
    }

    public PageParamDependentBuilder dependent() {
        return new PageParamDependentBuilder(from, to);
    }

}
