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
package net.objectlab.kit.datecalc.common;

/**
 * Define a series of standard way to handle holidays.
 * 
 * @author Benoit Xhenseval
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 * 
 */
public final class HolidayHandlerType {

    /**
     * A Forward handler will move the date forward if it falls on a non working
     * day.
     */
    public static final String FORWARD = "forward";

    /**
     * A backward handler will move the date backward if it falls on a non
     * working day.
     */
    public static final String BACKWARD = "backward";

    /**
     * A modified following handler will move the date forward if it falls on a
     * non working day BUT, if the new date falls into another month, it will
     * revert to moving backward until it finds a working day.
     */
    public static final String MODIFIED_FOLLLOWING = "modifiedFollowing";

    /**
     * A modified preceeding handler will move the date backward if it falls on
     * a non working day BUT, if the new date falls into another month, it will
     * revert to moving forward until it finds a working day.
     */
    public static final String MODIFIED_PRECEEDING = "modifiedPreceeding";

    private HolidayHandlerType() {
    }
}
