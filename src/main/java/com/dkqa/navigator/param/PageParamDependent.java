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

import java.util.List;

public class PageParamDependent {

    private PageParam param;
    private List<PageParamValue> values;

    protected PageParamDependent(PageParam param, List<PageParamValue> values) {
        this.param = param;
        this.values = values;
    }

    public PageParam info() {
        String[] arrayValues = values.stream().map(PageParamValue::get).toArray(String[]::new);
        return this.param.setValues(arrayValues);
    }

    public boolean equals(PageParamExecutor param) {
        return this.info().equalsByName(param.info());
    }

}
