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

package com.dkqa.navigator.param;


import com.dkqa.navigator.Page;

import java.util.Arrays;
import java.util.List;

import static com.dkqa.navigator.PageNavigatorFactoryMethod.getPageName;

public class PageParam {

    private String name;
    private String fromPageName;
    private String toPageName;
    private List<String> values;

    protected PageParam(Page from, Page to) {
        this.fromPageName = getPageName(from.getClass());
        this.toPageName = getPageName(to.getClass());
        this.name = fromPageName + " -> " + toPageName;
    }

    protected PageParam values(String... values) {
        this.values = Arrays.asList(values);
        return this;
    }

    public String fromPageName() {
        return fromPageName;
    }

    public String toPageName() {
        return toPageName;
    }

    public String name() {
        return name;
    }

    public boolean equalsByName(PageParam param) {
        return this.name.equals(param.name);
    }

    public boolean equalsByValues(PageParam param) {
        return this.values.containsAll(param.values);
    }

}
