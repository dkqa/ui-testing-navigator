package com.dkqa.pages;

import com.dkqa.navigator.Page;
import com.dkqa.navigator.PageMarker;

import static com.dkqa.pages.Page3.dependentParamPage1Page2;

@PageMarker(pageName = "Page6")
public class Page6 extends WebPage {

    @Override
    protected void pageNavigationInfo() {
        addDependentParam(dependentParamPage1Page2());
        addNavigation(new Page4(), () -> {
            currentPage = "4";
        });
    }

    @Override
    public Page registerPage() {
        return page();
    }

    @Override
    protected String determinantName() {
        return "6";
    }
}
