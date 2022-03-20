package com.dkqa.pages;

import com.dkqa.navigator.Page;
import com.dkqa.navigator.PageMarker;
import com.dkqa.navigator.param.PageParamBuilder;
import com.dkqa.navigator.param.PageParamDependent;

@PageMarker(pageName = "3")
public class Page3 extends TestPage {

    public static PageParamDependent dependentParam2() {
        return new PageParamBuilder(new Page2(), new Page3())
                .dependent()
                .value(() -> Page2.param2)
                .build();
    }

    @Override
    protected String determinantName() {
        return "3";
    }

    @Override
    protected void pageNavigationInfo() {
        addDependentParam(Page2.dependentParam1());
        addDependentParam(dependentParam2());
        addNavigation(new Page2(), () -> currentPage = "2");
        addNavigation(new Page4(), () -> currentPage = "4");
        addNavigation(new Page5(), () -> currentPage = "5");
    }

    @Override
    public Page registerPage() {
        return page();
    }
}
