//    MIT License
//
//    Copyright (c) 2021 Danil Kopylov
//
//    Permission is hereby granted, free of charge, to any person obtaining a copy
//    of this software and associated documentation files (the "Software"), to deal
//    in the Software without restriction, including without limitation the rights
//    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//    copies of the Software, and to permit persons to whom the Software is
//    furnished to do so, subject to the following conditions:
//
//    The above copyright notice and this permission notice shall be included in all
//    copies or substantial portions of the Software.
//
//    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//    SOFTWARE.

package com.dkqa.navigator;

import com.dkqa.navigator.action.*;
import com.dkqa.navigator.param.PageParamExecutor;
import com.dkqa.navigator.param.PageParamDependent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.dkqa.navigator.PageNavigatorFactoryMethod.getPageName;

public abstract class Page {

    private PageAction action;
    private List<String> previousPages;
    private HashMap<String, PageWay> ways = new HashMap<>();
    private HashMap<String, PageParamExecutor> params = new HashMap<>();
    private HashMap<String, PageParamDependent> dependentParams = new HashMap<>();

    private List<PageParamExecutor> navigationParams = new ArrayList<>();

    protected abstract PageDeterminant pageDeterminant();
    protected abstract void pageNavigationInfo();
    protected abstract NavigatorBuilder navigatorBuilder();
    public abstract Page registerPage();

    public String defineCurrentPage() {
        return navigatorBuilder().buildInstance().definePage();
    }

    public void goTo(Page page) {
        String fromPageName = getPageName(this.getClass());
        String toPageName = getPageName(page.getClass());
        PageWay way = ways.get(toPageName);
        if (way == null) {
            throw new RuntimeException(fromPageName + " page has not way to " + toPageName + " page");
        }
        way.go();
    }

    public Page page() {
        pageNavigationInfo();
        return this;
    }

    // Methods for navigation
    public Page param(PageParamExecutor param) {
        navigationParams.add(param);
        return this;
    }

    public void navigate() {
        List<PageParamExecutor> params = new ArrayList<>(navigationParams);
        navigationParams = new ArrayList<>();

        navigatorBuilder().buildInstance().navigate(this, params);
    }

    // Methods for pageNavigationInfo()
    protected void addNavigation(Page toPage, PageStep step, double stepWeigh) {
        String pageName = getPageName(toPage.getClass());
        if (!pageName.equals(getPageName(this.getClass()))) {
            stepWeigh = (stepWeigh > 0) ? stepWeigh : 1;
            if (ways.get(pageName) != null) {
                throw new RuntimeException("The page has duplicate navigation to " + pageName);
            }
            ways.put(pageName, new PageWay(step, stepWeigh));
        }
    }

    protected void addNavigation(Page toPage, PageParamExecutor param, PageStep step, double stepWeigh) {
        String fromPageName = getPageName(this.getClass());
        String toPageName = getPageName(toPage.getClass());
        if (!fromPageName.equals(param.info().fromPageName()) || !toPageName.equals(param.info().toPageName())) {
            throw new RuntimeException("Incorrect parameter 'param' for the 'addNavigation(...)' method " +
                    "(param should has the fromPage is '" + fromPageName + "' and the to page is '" + toPageName + "')");
        }
        params.put(toPageName, param);
        addNavigation(toPage, step, stepWeigh);
    }

    protected void addNavigation(Page toPage, PageParamExecutor param, PageStep step) {
        addNavigation(toPage, param, step, 1);
    }

    protected void addNavigation(Page toPage, PageStep step) {
        addNavigation(toPage, step, 1);
    }

    protected void addAction(PageAction action) {
        this.action = action;
    }

    protected void addPreviousPage(Page... variants) {
        for (Page page : variants) {
            if (page != null) {
                if (previousPages == null) {
                    previousPages = new ArrayList<>();
                }
                previousPages.add(getPageName(page.getClass()));
            }
        }
    }

    protected void addDependentParam(PageParamDependent param) {
        dependentParams.put(param.info().getName(), param);
    }

    // Methods for getting info
    protected PageAction pageAction() {
        return action;
    }

    protected List<String> pagePreviousPages() {
        return previousPages;
    }

    protected HashMap<String, PageWay> pageWays() {
        return ways;
    }

    protected PageWay pageWay(String page) {
        return ways.get(page);
    }

    protected HashMap<String, PageParamExecutor> pageParams() {
        return params;
    }

    protected PageParamExecutor pageParam(String page) {
        return params.get(page);
    }

    protected HashMap<String, PageParamDependent> pageDependentParams() {
        return dependentParams;
    }

    protected PageParamDependent pageDependentParam(String paramName) {
        return dependentParams.get(paramName);
    }


}
