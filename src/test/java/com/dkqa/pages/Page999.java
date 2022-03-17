package com.dkqa.pages;

import com.dkqa.navigator.Page;
import com.dkqa.navigator.PageMarker;

import static com.dkqa.pages.Page3.dependentParamPage1Page2;

@PageMarker(pageName = "Page999")
public class Page999 extends WebPage {

    @Override
    protected void pageNavigationInfo() {

    }

    @Override
    public Page registerPage() {
        return page();
    }

    @Override
    protected String determinantName() {
        return "999";
    }
}
