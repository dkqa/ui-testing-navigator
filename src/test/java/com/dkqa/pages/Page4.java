package com.dkqa.pages;

import com.dkqa.navigator.Page;
import com.dkqa.navigator.PageMarker;

@PageMarker(pageName = "4")
public class Page4 extends TestPage {

    @Override
    protected String determinantName() {
        return "4";
    }

    @Override
    protected void pageNavigationInfo() {
        addDependentParam(Page2.dependentParam1());
        addDependentParam(Page3.dependentParam2());
        addNavigation(new Page3(), () -> currentPage = "3");
    }

    @Override
    public Page registerPage() {
        return page();
    }
}
