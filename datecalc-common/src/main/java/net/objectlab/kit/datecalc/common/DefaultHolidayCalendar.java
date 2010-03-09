/*
 * ObjectLab, http://www.objectlab.co.uk/open is sponsoring the ObjectLab Kit.
 * 
 * Based in London, we are world leaders in the design and development 
 * of bespoke applications for the securities financing markets.
 * 
 * <a href="http://www.objectlab.co.uk/open">Click here to learn more</a>
 *           ___  _     _           _   _          _
 *          / _ \| |__ (_) ___  ___| |_| |    __ _| |__
 *         | | | | '_ \| |/ _ \/ __| __| |   / _` | '_ \
 *         | |_| | |_) | |  __/ (__| |_| |__| (_| | |_) |
 *          \___/|_.__// |\___|\___|\__|_____\__,_|_.__/
 *                   |__/
 *
 *                     www.ObjectLab.co.uk
 *
 * $Id: DateCalculator.java 200 2006-10-10 20:15:58Z benoitx $
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

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @TODO javadoc
 * 
 * @author xhensevb
 * @author $LastChangedBy: marchy $
 * @version $Revision: 138 $ $Date: 2006-09-10 13:29:15 +0100 (Sun, 10 Sep 2006) $
 * 
 */
public class DefaultHolidayCalendar<E> implements HolidayCalendar<E> {
    
    private Comparator<Calendar> calCmp = new Comparator<Calendar>() {
        public int compare(Calendar cal1, Calendar cal2) {
            return 
            (cal1.get(YEAR) - cal2.get(YEAR)) * 10000
            +
            (cal1.get(MONTH) - cal2.get(MONTH)) * 100
            +
            (cal1.get(DAY_OF_MONTH) - cal2.get(DAY_OF_MONTH));
        }
    };
    
    private Comparator<Date> dateCmp = new Comparator<Date>() {
        public int compare(Date date1, Date date2) {
            
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);
            
            return calCmp.compare(cal1, cal2);
        }
    };
    
    private static final long serialVersionUID = -8558686840806739645L;

    private Set<E> holidays;

    private E earlyBoundary = null;

    private E lateBoundary = null;

    public DefaultHolidayCalendar() {
        super();
        holidays = Collections.emptySet();
    }

    public DefaultHolidayCalendar(final Set<E> holidays, final E earlyBoundary, final E lateBoundary) {
        super();
        setHolidays(holidays);

        this.earlyBoundary = earlyBoundary;
        this.lateBoundary = lateBoundary;
    }

    public DefaultHolidayCalendar(final Set<E> holidays) {
        super();
        setHolidays(holidays);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.objectlab.kit.datecalc.common.HolidayCalendar#getEarlyBoundary()
     */
    public E getEarlyBoundary() {
        return earlyBoundary;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.objectlab.kit.datecalc.common.HolidayCalendar#getHolidays()
     */
    public Set<E> getHolidays() {
        return holidays;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.objectlab.kit.datecalc.common.HolidayCalendar#getLateBoundary()
     */
    public E getLateBoundary() {
        return lateBoundary;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.objectlab.kit.datecalc.common.HolidayCalendar#setEarlyBoundary(java.lang.Object)
     */
    public void setEarlyBoundary(final E earlyBoundary) {
        this.earlyBoundary = earlyBoundary;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.objectlab.kit.datecalc.common.HolidayCalendar#setHolidays(java.util.Set)
     */
    @SuppressWarnings("unchecked")
    public void setHolidays(final Set<E> holidays) {
        
        if (holidays == null) {
            this.holidays = Collections.emptySet();
            return;
        }
        
        Set<E> newSet = null;

        // this 'hack' is for Date/Calendar objects to be
        // 'equal' on the same day even if time fields differ
        Iterator<E> it = holidays.iterator();
        if (it.hasNext()) {
            E obj = it.next();
            
            if (obj instanceof Date) {
                newSet = new TreeSet(dateCmp);
            } else if (obj instanceof Calendar) {
                newSet = new TreeSet(calCmp);
            } 
        }
        
        if (newSet == null) {
            newSet = new HashSet<E>();
        }
        
        newSet.addAll(holidays);
        this.holidays = Collections.unmodifiableSet(newSet);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.objectlab.kit.datecalc.common.HolidayCalendar#setLateBoundary(java.lang.Object)
     */
    public void setLateBoundary(final E lateBoundary) {
        this.lateBoundary = lateBoundary;
    }

    public boolean isHoliday(final E date) {
        return holidays.contains(date);
    }
}
