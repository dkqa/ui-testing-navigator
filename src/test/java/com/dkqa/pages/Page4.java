package com.dkqa.pages;

import com.dkqa.navigator.Page;
import com.dkqa.navigator.PageMarker;

import static com.dkqa.pages.Page3.dependentParamPage1Page2;

@PageMarker(pageName = "Page4")
public class Page4 extends WebPage {

    @Override
    protected void pageNavigationInfo() {
        addDependentParam(dependentParamPage1Page2());
        addNavigation(new Page1(), () -> {
            currentPage = "1";
        }, 2);
        addNavigation(new Page3(), () -> {
            currentPage = "3";
        });
        addNavigation(new Page5(), () -> {
            currentPage = "5";
        });
        addNavigation(new Page6(), () -> {
            currentPage = "6";
        });
    }

    @Override
    public Page registerPage() {
        return page();
    }

    @Override
    protected String determinantName() {
        return "4";
    }
}
