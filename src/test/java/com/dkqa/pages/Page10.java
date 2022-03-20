package com.dkqa.pages;

import com.dkqa.navigator.Page;
import com.dkqa.navigator.PageMarker;

@PageMarker(pageName = "10")
public class Page10 extends TestPage {

    @Override
    protected String determinantName() {
        return "same (8,10)";
    }

    @Override
    protected void pageNavigationInfo() {
        addPreviousPage(new Page9());
        addNavigation(new Page9(), () -> currentPage = "9");
    }

    @Override
    public Page registerPage() {
        return page();
    }
}
