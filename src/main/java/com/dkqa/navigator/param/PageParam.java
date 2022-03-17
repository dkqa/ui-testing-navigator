package com.dkqa.navigator.param;


import com.dkqa.navigator.Page;

import java.util.Arrays;
import java.util.List;

import static com.dkqa.navigator.PageNavigatorFactoryMethod.getPageName;

public class PageParam {

    private String name;
    private String fromPageName;
    private String toPageName;
    private List<String> values;

    protected PageParam(Page from, Page to) {
        this.fromPageName = getPageName(from.getClass());
        this.toPageName = getPageName(to.getClass());
        this.name = fromPageName + " -> " + toPageName;
    }

    protected PageParam values(String... values) {
        this.values = Arrays.asList(values);
        return this;
    }

    public String fromPageName() {
        return fromPageName;
    }

    public String toPageName() {
        return toPageName;
    }

    public String name() {
        return name;
    }

    public boolean equalsByName(PageParam param) {
        return this.name.equals(param.name);
    }

    public boolean equalsByValues(PageParam param) {
        return this.values.containsAll(param.values);
    }

}
