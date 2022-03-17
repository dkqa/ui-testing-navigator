package com.dkqa.navigator.param;

public class PageParamExecutor {

    private PageParam param;
    private PageParamStep step;

    protected PageParamExecutor(PageParam param, PageParamStep step) {
        this.param = param;
        this.step = step;
    }

    public PageParam info() {
        return param;
    }

    public void apply() {
        step.apply();
    }

    public boolean equals(PageParamExecutor param) {
        return this.param.equalsByName(param.info());
    }

    public boolean equals(PageParamDependent param) {
        return this.param.equalsByValues(param.info());
    }

}
