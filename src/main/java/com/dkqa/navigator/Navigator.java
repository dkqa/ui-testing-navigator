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

import com.dkqa.navigator.action.NavigatorAction;
import com.dkqa.navigator.action.PageAction;
import com.dkqa.navigator.param.PageParamExecutor;
import com.dkqa.navigator.param.PageParamDependent;

import java.util.*;

import static com.dkqa.navigator.PageNavigatorFactoryMethod.getPageName;

class Navigator {

    private final HashMap<String, Page> mapPages;
    private final HashMap<String, ArrayList<String>> foundedRoutes = new HashMap<>();
    private String UNKNOWN_PAGE = "unknown page";

    private NavigatorAction actionBeforePageDefinition;
    private NavigatorAction actionUnknownPage;
    private int iterationCount = 3;
    private double waitPage = 3;
    private int navigationHistorySize = 15;
    private NavigationHistory history = new NavigationHistory(navigationHistorySize);

    protected Navigator(HashMap<String, Page> mapPages) {
        this.mapPages = mapPages;
    }

    protected void setActionBeforePageDefinition(NavigatorAction action) {
        this.actionBeforePageDefinition = action;
    }

    protected void setActionUnknownPage(NavigatorAction action) {
        this.actionUnknownPage = action;
    }

    protected void setIterationCount(int count) {
        iterationCount = (count < 1) ? 3 : Math.min(count, 10);
    }

    protected void setWaitPageSeconds(double seconds) {
        waitPage = (seconds > 0) ? seconds : 3;
    }

    protected void setNavigationHistorySize(int maxSize) {
        navigationHistorySize = (maxSize < 5) ? 15 : Math.min(maxSize, 100);
        history = new NavigationHistory(navigationHistorySize);
    }

    protected void setUnknownPageName(String name) {
        if (name != null) {
            UNKNOWN_PAGE = name + " (unknown page)";
        }
    }

    protected void navigate(Page toPage, List<PageParamExecutor> params) {
        String toPageName = getPageName(toPage.getClass());
        String currentPage = definePageNotUnknown();

        cycleParam:
        for (PageParamExecutor param : params) {
            String pointPage = param.info().fromPageName();
            ArrayList<String> route = getRoute(currentPage, pointPage);
            route.add(0, currentPage);

            for (String page : route) {
                PageParamDependent dependentParam = mapPages(page).pageDependentParam(param.info().name());

                if (dependentParam != null) {
                    if (!currentPage.equals(page)) {
                        currentPage = navigate(currentPage, page, params);
                    }
                    if (param.equals(dependentParam)) {
                        continue cycleParam;
                    } else {
                        break;
                    }
                }
            }

            currentPage = navigate(currentPage, pointPage, params);
        }

        navigate(currentPage, toPageName, params);
    }

    private String navigate(String fromPage, String toPage, List<PageParamExecutor> params) {
        String currentPage = fromPage;

        iteration:
        for (int i = 0; i < iterationCount; i++) {
            List<String> route = getRoute(currentPage, toPage);
            if (route.size() > 0) {
                System.out.println("Route: " + currentPage + " -> " + String.join(" -> ", route));
            }

            for (String nextPage : route) {
                applyParam(currentPage, nextPage, params);
                goPage(currentPage, nextPage);
                currentPage = waitPage(nextPage, waitPage);

                if (currentPage.equals(UNKNOWN_PAGE)) {
                    currentPage = definePageNotUnknown();
                }

                if (!currentPage.equals(nextPage)) {
                    continue iteration;
                }
            }

            if (currentPage.equals(toPage)) return currentPage;
        }

        throw new NavigationException();
    }

    private String definePageNotUnknown(String... estimatedPages) {
        for (int i = 0; i < 3; i++) {
            String currentPage = definePage(estimatedPages);
            if (currentPage.equals(UNKNOWN_PAGE)) {
                if (actionUnknownPage != null) {
                    actionUnknownPage.apply();
                }
            } else {
                return currentPage;
            }
        }
        throw new NavigationException();
    }

    private String waitPage(String pageName, double seconds) {
        double endTimer = System.currentTimeMillis() + (seconds * 1000);
        String currentPage;
        do {
            currentPage = definePage(pageName);
            if (currentPage.equals(pageName)) {
                return currentPage;
            }
        } while (System.currentTimeMillis() <= endTimer);
        return currentPage;
    }

    private void goPage(String fromPage, String toPage) {
        mapPages(fromPage).pageWay(toPage).go();
    }

    private void applyParam(String fromPage, String toPage, List<PageParamExecutor> actualParams) {
        PageParamExecutor param = mapPages(fromPage).pageParam(toPage);
        if (param != null) {
            PageParamExecutor resultParam = actualParams.stream()
                    .filter(p -> p.equals(param))
                    .findFirst()
                    .orElse(param);
            resultParam.apply();
        }
    }

    protected String definePage(String... estimatedPages) {

        if (actionBeforePageDefinition != null) {
            actionBeforePageDefinition.apply();
        }

        for(String page : estimatedPages) {
            if (mapPages(page).pageDeterminant().determinate()) {
                history.add(page);
                return page;
            }
        }

        String page = mapPages.keySet().stream()
                .filter(k -> mapPages(k).pageDeterminant().determinate())
                .filter(k -> mapPages(k).pagePreviousPage() == null || (mapPages(k).pagePreviousPage().equals(history.lastPage()) | k.equals(history.lastPage())))
                .findFirst()
                .orElse(UNKNOWN_PAGE);

        applyPageAction(page);

        history.add(page);

        return page;
    }

    private void applyPageAction(String page) {
        if (!page.equals(UNKNOWN_PAGE)) {
            PageAction pageAction = mapPages(page).pageAction();
            if (pageAction != null) {
                pageAction.go();
            }
        }
    }

    private ArrayList<String> getRoute(String from, String to) {
        if (from.equals(UNKNOWN_PAGE) || from.equals(to)) return new ArrayList<>();
        ArrayList<String> route = foundedRoutes.get(from + " -> " + to);

        if (route == null) {
            route = getWay(Arrays.asList(new ArrayList<>(Arrays.asList(from))), to, 0);
            foundedRoutes.put(from + " -> " + to, new ArrayList<>(route));
        }

        if (route.size() != 0) {

            route.remove(0);
        } else {
            throw new RouteException(from, to);
        }

        return route;
    }

    private ArrayList<String> getWay(List<ArrayList<String>> ways, String to, double foundedWeight) {
        List<ArrayList<String>> newWays = new ArrayList<>();

        for (ArrayList<String> way : ways) {
            String lastPoint = way.get(way.size() - 1);

            if (lastPoint.equals(to) && getRouteWeight(way) <= foundedWeight) {
                newWays.add(way);
                continue;
            }

            for (String nextPoint : mapPages(lastPoint).pageWays().keySet()) {
                ArrayList<String> newWay = new ArrayList<>(way);

                if (!newWay.contains(nextPoint)) {
                    newWay.add(nextPoint);

                    if (foundedWeight != 0 && getRouteWeight(newWay) >= foundedWeight) {
                        continue;
                    }

                    if (nextPoint.equals(to)) {
                        foundedWeight = getRouteWeight(newWay);
                    }

                    newWays.add(newWay);
                }
            }
        }

        long correctWaySize = newWays.stream()
                .filter(way -> way.get(way.size() - 1).equals(to))
                .count();

        if (foundedWeight != 0 && newWays.size() == correctWaySize) {
            for (ArrayList<String> way : newWays) {
                if (getRouteWeight(way) == foundedWeight) {
                    return way;
                }
            }
        }

        if (newWays.size() == 0) {
            return new ArrayList<>();
        }

        return getWay(newWays, to, foundedWeight);
    }

    private double getRouteWeight(List<String> route) {
        double weight = 0;
        for (int i = 1; i < route.size(); i++) {
            weight += mapPages(route.get(i - 1)).pageWay(route.get(i)).getWeight();
        }
        return weight;
    }

    private Page mapPages(String page) {
        return mapPages.get(page);
    }

    class NavigationHistory extends ArrayList<String> {
        private int maxSize;

        NavigationHistory(int maxSize) {
            this.maxSize = maxSize;
        }

        @Override
        public boolean add(String element) {
            if (!lastPage().equals(element)) {
                if (size() > maxSize) {
                    remove(0);
                }
                return super.add(element);
            }
            return false;
        }

        public String lastPage() {
            if (size() > 0) {
                return get(size() - 1);
            } else {
                return UNKNOWN_PAGE;
            }

        }
    }

    class RouteException extends RuntimeException {
        RouteException(String from, String to) {
            super("It is impossible to build a route from the '" + from + "' page to the '" + to + "' page");
        }
    }

    class NavigationException extends RuntimeException {
        NavigationException() {
            super("Navigation failed\n Route history (list of last " + navigationHistorySize + " pages):\n-> ... ->\n-> " + String.join(" ->\n-> ", history));
        }
    }

}
