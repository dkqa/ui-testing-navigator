package com.dkqa.pages;

import com.dkqa.navigator.Page;
import com.dkqa.navigator.PageMarker;

import static com.dkqa.pages.Page3.dependentParamPage1Page2;

@PageMarker(pageName = "Page11")
public class Page11 extends WebPage {

    @Override
    protected void pageNavigationInfo() {
        addDependentParam(dependentParamPage1Page2());
        addNavigation(new Page5(), () -> {
            System.out.println("[GO] go to Page 5");
            currentPage = "5";
        });
        addNavigation(new Page12(), () -> {
            System.out.println("[GO] go to Page 12");
            currentPage = "12";
        });
    }

    @Override
    public Page registerPage() {
        return page();
    }

    @Override
    protected String determinantName() {
        return "11";
    }
}
