package com.dkqa.pages;

import com.dkqa.navigator.Page;
import com.dkqa.navigator.PageMarker;

@PageMarker(pageName = "11")
public class Page11 extends TestPage {

    @Override
    protected String determinantName() {
        return "11";
    }

    @Override
    protected void pageNavigationInfo() {
        addNavigation(new Page2(), () -> currentPage = "2");
    }

    @Override
    public Page registerPage() {
        return page();
    }
}
