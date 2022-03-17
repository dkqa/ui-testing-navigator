package com.dkqa.navigator.param;

import java.util.List;

public class PageParamDependent {

    private PageParam param;
    private List<PageParamValue> values;

    protected PageParamDependent(PageParam param, List<PageParamValue> values) {
        this.param = param;
        this.values = values;
    }

    public PageParam info() {
        String[] arrayValues = values.stream().map(PageParamValue::get).toArray(String[]::new);
        return this.param.values(arrayValues);
    }

    public boolean equals(PageParamExecutor param) {
        return this.info().equalsByName(param.info());
    }

}
