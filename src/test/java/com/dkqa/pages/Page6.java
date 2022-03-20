package com.dkqa.pages;

import com.dkqa.navigator.Page;
import com.dkqa.navigator.PageMarker;

@PageMarker(pageName = "6")
public class Page6 extends TestPage {

    @Override
    protected String determinantName() {
        return "6";
    }

    @Override
    protected void pageNavigationInfo() {
        addNavigation(new Page2(), () -> currentPage = "2");
        addNavigation(new Page7(), () -> currentPage = "7");
        addNavigation(new Page9(), () -> currentPage = "9");
    }

    @Override
    public Page registerPage() {
        return page();
    }
}
