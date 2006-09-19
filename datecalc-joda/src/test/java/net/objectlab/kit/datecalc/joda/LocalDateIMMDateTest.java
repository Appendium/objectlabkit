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

import net.objectlab.kit.datecalc.common.AbstractIMMDateTest;
import net.objectlab.kit.datecalc.common.IMMDateCalculator;

import org.joda.time.LocalDate;

public class LocalDateIMMDateTest extends AbstractIMMDateTest<LocalDate> {

    @Override
    protected IMMDateCalculator<LocalDate> getDateCalculator(final String name) {
        return LocalDateKitCalculatorsFactory.getDefaultInstance().getIMMDateCalculator();
    }

    @Override
    protected LocalDate parseDate(final String string) {
        return new LocalDate(string);
    }
}