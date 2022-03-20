package com.dkqa.pages;

import com.dkqa.navigator.Page;
import com.dkqa.navigator.PageMarker;

@PageMarker(pageName = "5")
public class Page5 extends TestPage {

    @Override
    protected String determinantName() {
        return "5";
    }

    @Override
    protected void pageNavigationInfo() {
        addDependentParam(Page2.dependentParam1());
        addNavigation(new Page3(), () -> currentPage = "3");
    }

    @Override
    public Page registerPage() {
        return page();
    }
}
