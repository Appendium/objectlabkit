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
 * $Id: CalendarModifiedPreceedingHandler.java 203 2006-10-11 12:53:07Z benoitx $
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
 */package net.objectlab.kit.datecalc.jdk;

 import java.util.Calendar;

 import net.objectlab.kit.datecalc.common.DateCalculator;
 import net.objectlab.kit.datecalc.common.HolidayHandlerType;

 /**
  * A Jdk <code>Calendar</code> implementation of the
  * {@link net.objectlab.kit.datecalc.common.HolidayHandler}, for the
  * <strong>Modified Preceeding</strong> algorithm.
  *
  * @author Marcin Jekot
  * @author $LastChangedBy: benoitx $
  * @version $Revision: 203 $ $Date: 2006-10-11 13:53:07 +0100 (Wed, 11 Oct 2006) $
  *
  */
 public class CalendarModifiedPrecedingHandler extends CalendarModifiedFollowingHandler {

     /**
      * If the current date of the give calculator is a non-working day, it will
      * be moved according to the algorithm implemented.
      *
      * @param calculator
      *            the calculator
      * @return the date which may have moved.
      */
     @Override
     public Calendar moveCurrentDate(final DateCalculator<Calendar> calculator) {
         return move(calculator, -1);
     }

     /**
      * Give the type name for this algorithm.
      *
      * @return algorithm name.
      */
     @Override
     public String getType() {
         return HolidayHandlerType.MODIFIED_PRECEDING;
     }
 }

 /*
  * ObjectLab, http://www.objectlab.co.uk/open is sponsoring the ObjectLab Kit.
  *
  * Based in London, we are world leaders in the design and development
  * of bespoke applications for the securities financing markets.
  *
  * <a href="http://www.objectlab.co.uk/open">Click here to learn more about us</a>
  *           ___  _     _           _   _          _
  *          / _ \| |__ (_) ___  ___| |_| |    __ _| |__
  *         | | | | '_ \| |/ _ \/ __| __| |   / _` | '_ \
  *         | |_| | |_) | |  __/ (__| |_| |__| (_| | |_) |
  *          \___/|_.__// |\___|\___|\__|_____\__,_|_.__/
  *                   |__/
  *
  *                     www.ObjectLab.co.uk
  */
