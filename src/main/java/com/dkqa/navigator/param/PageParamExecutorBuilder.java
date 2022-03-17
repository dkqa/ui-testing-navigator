package com.dkqa.navigator.param;

import com.dkqa.navigator.Page;

public class PageParamExecutorBuilder {

    private PageParam param;
    private PageParamStep step;

    protected PageParamExecutorBuilder(Page from, Page to) {
        this.param = new PageParam(from, to);
    }

    public PageParamExecutorBuilder values(String... values) {
        this.param.values(values);
        return this;
    }

    public PageParamExecutorBuilder step(PageParamStep step) {
        this.step = step;
        return this;
    }

    public PageParamExecutor build() {
        return new PageParamExecutor(param, step);
    }

}
