package com.dkqa.navigator;

import com.dkqa.navigator.action.NavigatorAction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public class NavigatorBuilder {

    private static HashMap<String, Navigator> navigators = new HashMap<>();

    private String name;
    private String[] packagePaths;

    private Navigator navigator;
    private NavigatorAction actionBeforePageDefinition;
    private NavigatorAction actionUnknownPage;
    private int iterationCount;
    private double waitPageSeconds;
    private int navigationHistorySize;
    private String unknownPageName;

    public NavigatorBuilder(String navigatorName, String... packagePaths) {
        this.name = navigatorName;
        this.packagePaths = packagePaths;
    }

    public String getName() {
        return name;
    }

    public NavigatorBuilder setActionBeforePageDefinition(NavigatorAction action) {
        actionBeforePageDefinition = action;
        return this;
    }

    public NavigatorBuilder setActionUnknownPage(NavigatorAction action) {
        actionUnknownPage = action;
        return this;
    }

    public NavigatorBuilder setIterationCount(int count) {
        iterationCount = count;
        return this;
    }

    public NavigatorBuilder setWaitPage(double seconds) {
        waitPageSeconds = seconds;
        return this;
    }

    public NavigatorBuilder setNavigationHistorySize(int size) {
        navigationHistorySize = size;
        return this;
    }

    public NavigatorBuilder setUnknownPageName(String name) {
        unknownPageName = name;
        return this;
    }

    protected Navigator buildInstance() {
        if (navigators.get(name) == null) {
            System.out.println("Register navigation - " + name);
            HashMap<String, Page> graphPages = PageRegister.parsePages(name, packagePaths);
            if (graphPages.size() == 0) {
                throw new NotFoundPagesException(name, packagePaths);
            }
            navigator = new Navigator(graphPages);
            navigator.setActionBeforePageDefinition(actionBeforePageDefinition);
            navigator.setActionUnknownPage(actionUnknownPage);
            navigator.setIterationCount(iterationCount);
            navigator.setWaitPageSeconds(waitPageSeconds);
            navigator.setNavigationHistorySize(navigationHistorySize);
            navigator.setUnknownPageName(unknownPageName);
            navigators.put(name, navigator);
        } else {
            navigator = navigators.get(name);
        }
        return navigator;
    }

    static class NotFoundPagesException extends RuntimeException {
        NotFoundPagesException(String navigatorName, String... pagePaths) {
            super("Pages belonging to the navigation named '"
                    + navigatorName + "' were not found in the packages: "
                    + Arrays.stream(pagePaths).collect(Collectors.joining("', '", "'", "'"))
                    + " (this name is defined in the method - Page.navigatorName)"
            );
        }
    }
}
