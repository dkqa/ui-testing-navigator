package com.dkqa.pages;

import com.dkqa.navigator.Page;
import com.dkqa.navigator.PageMarker;

@PageMarker(pageName = "17")
public class Page17 extends TestPage {

    @Override
    protected String determinantName() {
        return "17";
    }

    @Override
    protected void pageNavigationInfo() {
        addNavigation(new Page12(), () -> currentPage = "12");
    }

    @Override
    public Page registerPage() {
        return page();
    }
}
