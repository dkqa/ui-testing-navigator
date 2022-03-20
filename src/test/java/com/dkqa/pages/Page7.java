package com.dkqa.pages;

import com.dkqa.navigator.Page;
import com.dkqa.navigator.PageMarker;

@PageMarker(pageName = "7")
public class Page7 extends TestPage {

    @Override
    protected String determinantName() {
        return "7";
    }

    @Override
    protected void pageNavigationInfo() {
        addNavigation(new Page6(), () -> currentPage = "6");
        addNavigation(new Page8(), () -> currentPage = "same (8,10)");
    }

    @Override
    public Page registerPage() {
        return page();
    }
}
