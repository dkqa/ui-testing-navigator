package com.dkqa.navigator.param;

import com.dkqa.navigator.Page;

import java.util.ArrayList;
import java.util.List;

public class PageParamDependentBuilder {

    private PageParam param;
    private List<PageParamValue> values = new ArrayList<>();

    protected PageParamDependentBuilder(Page from, Page to) {
        param = new PageParam(from, to);
    }

    public PageParamDependentBuilder value(PageParamValue value) {
        values.add(value);
        return this;
    }

    public PageParamDependent build() {
        return new PageParamDependent(param, values);
    }


}
