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
    private String previousPage;
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
        System.out.println();
        System.out.println("Navigate");
        navigatorBuilder().buildInstance().navigate(this, params);
    }

    // Methods for pageNavigationInfo()
    protected void addNavigation(Page toPage, PageStep step, double stepWeigh) {
        String pageName = getPageName(toPage.getClass());
        stepWeigh = (stepWeigh > 0) ? stepWeigh : 1;
        if (ways.get(pageName) != null) {
            throw new RuntimeException("The page has duplicate navigation to " + pageName);
        }
        ways.put(pageName, new PageWay(step, stepWeigh));
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

    protected void addPreviousPage(Page page) {
        if (page != null) {
            previousPage = getPageName(page.getClass());
        }
    }

    protected void addDependentParam(PageParamDependent param) {
        dependentParams.put(param.info().name(), param);
    }

    // Methods for getting info
    protected PageAction pageAction() {
        return action;
    }

    protected String pagePreviousPage() {
        return previousPage;
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
