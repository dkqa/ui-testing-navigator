package com.dkqa.pages;

import com.dkqa.navigator.Page;
import com.dkqa.navigator.PageMarker;

@PageMarker(pageName = "12")
public class Page12 extends TestPage {

    @Override
    protected String determinantName() {
        return "12";
    }

    @Override
    protected void pageNavigationInfo() {
        addNavigation(new Page2(), () -> currentPage = "2");
        addNavigation(new Page13(), () -> currentPage = "13");
    }

    @Override
    public Page registerPage() {
        return page();
    }
}
