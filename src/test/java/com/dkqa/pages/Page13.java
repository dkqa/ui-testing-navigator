package com.dkqa.pages;

import com.dkqa.navigator.Page;
import com.dkqa.navigator.PageMarker;

@PageMarker(pageName = "13")
public class Page13 extends TestPage {

    @Override
    protected String determinantName() {
        return "13";
    }

    @Override
    protected void pageNavigationInfo() {
        addNavigation(new Page12(), () -> currentPage = "12", 6);
        addNavigation(new Page14(), () -> currentPage = "14");
    }

    @Override
    public Page registerPage() {
        return page();
    }
}
