package com.dkqa.pages;

import com.dkqa.navigator.Page;
import com.dkqa.navigator.PageMarker;

import static com.dkqa.pages.Page3.dependentParamPage1Page2;

@PageMarker(pageName = "Page10")
public class Page10 extends WebPage {

    @Override
    protected void pageNavigationInfo() {
        addDependentParam(dependentParamPage1Page2());
        addNavigation(new Page8(), () -> {
            System.out.println("[GO] go to Page 8");
            currentPage = "8";
        });
    }

    @Override
    public Page registerPage() {
        return page();
    }

    @Override
    protected String determinantName() {
        return "10";
    }
}
