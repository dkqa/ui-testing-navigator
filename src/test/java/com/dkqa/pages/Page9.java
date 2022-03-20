package com.dkqa.pages;

import com.dkqa.navigator.Page;
import com.dkqa.navigator.PageMarker;

@PageMarker(pageName = "9")
public class Page9 extends TestPage {

    @Override
    protected String determinantName() {
        return "9";
    }

    @Override
    protected void pageNavigationInfo() {
        addNavigation(new Page6(), () -> currentPage = "6");
        addNavigation(new Page10(), () -> currentPage = "same (8,10)");
    }

    @Override
    public Page registerPage() {
        return page();
    }
}
