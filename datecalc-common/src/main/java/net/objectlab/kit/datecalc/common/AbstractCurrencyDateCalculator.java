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
 */package net.objectlab.kit.datecalc.common;

/**
  * Abstract Currency calculator implementation in order to encapsulate all the common functionality
  * between Jdk/Jdk8 and Joda implementations. It is parameterized on &lt;E&gt;
  * but basically <code>Date</code> and <code>LocalDate</code> are the only
  * viable values for it for now.
  *
  * This follows convention for currency, see http://www.londonfx.co.uk/valdates.html
  *
  * <h3>Currency Holiday</h3>
  * For most T+2 currency pairs (spotLag=2), if T+1 is a USD holiday, then this does not normally affect the spot date,
  * but if a non-USD currency in the currency pair has a holiday on T+1, then it will make the spot date
  * become T+3. If USD or either currency of a pair have a holiday on T+2, then the spot date
  * will be T+3. This means, for example, that crosses such as EUR/GBP can never have a spot date
  * on 4th July (although such a date could be quoted as an outright).
  *
  * <h3>Latin American currencies</h3>
  * USD holidays normally affect the spot date only if T+2 is a USD holiday.
  * If T+1 is a USD holiday, this does not normally prevent T+2 from being the spot date.
  * Certain Latin American currencies (ARS, CLP and MXN) are an exception to this.
  * If T+1 is a USD holiday, then the spot date for the affected currencies will be T+3.
  * For example, if the trade date is a Monday and a USD holiday falls on the Tuesday,
  * then the spot date for EUR/USD will be the Wednesday, but the spot date for USD/MXN will be the Thursday.
  *
  * @author Benoit Xhenseval
  *
  * @param <E>
  *            a representation of a date, typically JDK: Date, Calendar;
  *            Joda:LocalDate, YearMonthDay
  *
  */
 public abstract class AbstractCurrencyDateCalculator<E> {
     private final DateCalculator<E> calculator;
     private final CurrencyCalculatorConfig config;
     private HolidayCalendar<E> ccy1HolidayCalendar;
     private HolidayCalendar<E> ccy2HolidayCalendar;
     private HolidayCalendar<E> usdHolidayCalendar;
     private final String ccy1;
     private final String ccy2;

     public AbstractCurrencyDateCalculator(final String ccy1, final String ccy2, final DateCalculator<E> calculator,
             final CurrencyCalculatorConfig config) {
         super();
         this.calculator = calculator;
         this.ccy1 = ccy1;
         this.ccy2 = ccy2;
         this.config = config;
     }

     protected DateCalculator<E> getCalculator() {
         return calculator;
     }

     public void setHolidayCalendars(final HolidayCalendar<E> ccy1HolidayCalendar, final HolidayCalendar<E> ccy2HolidayCalendar,
             final HolidayCalendar<E> usdHolidayCalendar) {
         this.ccy1HolidayCalendar = ccy1HolidayCalendar != null ? ccy1HolidayCalendar : new DefaultHolidayCalendar();
         this.ccy2HolidayCalendar = ccy2HolidayCalendar != null ? ccy2HolidayCalendar : new DefaultHolidayCalendar();
         this.usdHolidayCalendar = usdHolidayCalendar != null ? usdHolidayCalendar : new DefaultHolidayCalendar();
     }

     private void applyNonUsdCalendars() {
         if (config.isSubjectToUSDForT1(ccy1) || config.isSubjectToUSDForT1(ccy2)) {
             applyCalendar(usdHolidayCalendar);
         }
         if (!"USD".equalsIgnoreCase(ccy1)) {
             applyCalendar(ccy1HolidayCalendar);
         }
         if (!"USD".equalsIgnoreCase(ccy2)) {
             applyCalendar(ccy2HolidayCalendar);
         }
     }

     public void applyCurrencyPairCalendars() {
         while (true) {
             final E currentBusinessDate = calculator.getCurrentBusinessDate();
             if (calculator.isWeekend(currentBusinessDate) //
                     || ccy1HolidayCalendar.isHoliday(currentBusinessDate) //
                     || ccy2HolidayCalendar.isHoliday(currentBusinessDate) //
                     ) {
                 moveToNextWeekday();
             } else {
                 break;
             }
         }
     }

     private void applyCalendar(final HolidayCalendar<E> holidayCalendar) {
         while (true) {
             if (calculator.isWeekend(calculator.getCurrentBusinessDate()) || holidayCalendar.isHoliday(calculator.getCurrentBusinessDate())) {
                 moveToNextWeekday();
             } else {
                 break;
             }
         }
     }

     protected abstract void moveToNextWeekday();

     public void applyAllCcyCalendars() {
         while (true) {
             final E currentBusinessDate = calculator.getCurrentBusinessDate();
             if (calculator.isWeekend(calculator.getCurrentBusinessDate())  //
                     || ccy1HolidayCalendar.isHoliday(currentBusinessDate) //
                     || ccy2HolidayCalendar.isHoliday(currentBusinessDate) //
                     || usdHolidayCalendar.isHoliday(currentBusinessDate)) {
                 moveToNextWeekday();
             } else {
                 break;
             }
         }
     }

     public void moveToSpotDate(final int spotLag) {
         if (spotLag == 0) {
             applyAllCcyCalendars();
         } else if (spotLag == 1) {
             moveToNextWeekday();
             applyAllCcyCalendars();
         } else {
             // Step 1) move date by 1, check date using calendar for non-USD ccy part
             // of the part AND USD calendar if one of the ccy is MXN/CLP/ARS. Move
             // fwd if required
             moveToNextWeekday();
             applyNonUsdCalendars();

             // Step 2) Move date by 1, check date using calendars for ccy pair AND
             // USD, move fwd if required
             moveToNextWeekday();
             applyAllCcyCalendars();
         }
     }
 }
