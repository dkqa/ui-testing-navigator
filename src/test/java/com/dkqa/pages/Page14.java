package com.dkqa.pages;

import com.dkqa.navigator.Page;
import com.dkqa.navigator.PageMarker;

@PageMarker(pageName = "14")
public class Page14 extends TestPage {

    @Override
    protected String determinantName() {
        return "14";
    }

    @Override
    protected void pageNavigationInfo() {
        addNavigation(new Page15(), () -> currentPage = "15");
    }

    @Override
    public Page registerPage() {
        return page();
    }
}
