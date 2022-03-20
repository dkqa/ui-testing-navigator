package com.dkqa.pages;

import com.dkqa.navigator.Page;
import com.dkqa.navigator.PageMarker;

@PageMarker(pageName = "16")
public class Page16 extends TestPage {

    @Override
    protected String determinantName() {
        return "16";
    }

    @Override
    protected void pageNavigationInfo() {
        addNavigation(new Page17(), () -> currentPage = "17");
    }

    @Override
    public Page registerPage() {
        return page();
    }
}
