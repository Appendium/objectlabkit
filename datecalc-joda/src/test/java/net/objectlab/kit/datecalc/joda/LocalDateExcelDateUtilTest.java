/*
 * $Id$
 * 
 * Copyright 2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.AbstractExcelDateUtilTest;

import org.joda.time.LocalDate;

public class LocalDateExcelDateUtilTest extends AbstractExcelDateUtilTest<LocalDate> {

    @Override
    protected LocalDate createDate(final String str) {
        return new LocalDate(str);
    }

    @Override
    protected LocalDate createDateFromExcel(final double excelDate, final boolean use1904Windowing) {
        return JodaExcelDateUtil.getLocalDate(excelDate, use1904Windowing);
    }
}
