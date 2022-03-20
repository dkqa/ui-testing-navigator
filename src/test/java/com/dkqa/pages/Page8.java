package com.dkqa.pages;

import com.dkqa.navigator.Page;
import com.dkqa.navigator.PageMarker;

@PageMarker(pageName = "8")
public class Page8 extends TestPage {

    @Override
    protected String determinantName() {
        return "same (8,10)";
    }

    @Override
    protected void pageNavigationInfo() {
        addPreviousPage(new Page7());
        addNavigation(new Page7(), () -> currentPage = "7");
    }

    @Override
    public Page registerPage() {
        return page();
    }
}
