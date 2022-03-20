package com.dkqa.pages;

import com.dkqa.navigator.Page;
import com.dkqa.navigator.PageMarker;
import com.dkqa.navigator.param.PageParamBuilder;
import com.dkqa.navigator.param.PageParamDependent;
import com.dkqa.navigator.param.PageParamExecutor;

@PageMarker(pageName = "2")
public class Page2 extends TestPage {

    public static String param2;

    public static PageParamDependent dependentParam1() {
        return new PageParamBuilder(new Page1(), new Page2())
                .dependent()
                .value(() -> Page1.param1)
                .build();
    }

    public static PageParamExecutor param2(String value) {
        return new PageParamBuilder(new Page2(), new Page3())
                .executor()
                .values(value)
                .step(() -> param2 = value)
                .build();
    }

    @Override
    protected String determinantName() {
        return "2";
    }

    @Override
    protected void pageNavigationInfo() {
        addDependentParam(dependentParam1());
        addNavigation(new Page1(), () -> currentPage = "1");
        addNavigation(
                new Page3(),
                param2("default_p2_p3"),
                () -> currentPage = "3"
        );
        addNavigation(new Page6(), () -> currentPage = "6");
        addNavigation(new Page11(), () -> currentPage = "broken");
        addNavigation(new Page12(), () -> currentPage = "12");
    }

    @Override
    public Page registerPage() {
        return page();
    }
}
