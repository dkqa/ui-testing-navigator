package com.dkqa.pages;

import com.dkqa.navigator.Page;
import com.dkqa.navigator.PageMarker;

@PageMarker(pageName = "15")
public class Page15 extends TestPage {

    @Override
    protected String determinantName() {
        return "15";
    }

    @Override
    protected void pageNavigationInfo() {
        addNavigation(new Page16(), () -> currentPage = "16");
    }

    @Override
    public Page registerPage() {
        return page();
    }
}
