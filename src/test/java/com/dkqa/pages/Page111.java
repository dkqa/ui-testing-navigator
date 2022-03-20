package com.dkqa.pages;

import com.dkqa.navigator.Page;
import com.dkqa.navigator.PageMarker;

@PageMarker(pageName = "111")
public class Page111 extends TestPage {

    @Override
    protected String determinantName() {
        return "111";
    }

    @Override
    protected void pageNavigationInfo() {

    }

    @Override
    public Page registerPage() {
        return page();
    }
}
