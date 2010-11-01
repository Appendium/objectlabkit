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
 * $Id: AbstractDateCalculator.java 309 2010-03-23 21:01:49Z marchy $
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
package net.objectlab.kit.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import net.objectlab.kit.util.StringUtil;

/**
 * Utility class for list generation and parsing.
 * 
 * @author Benoit Xhenseval
 * @version $Revision: 1.3 $
 */
public final class Util {
    /**
     * default constructor.
     */
    private Util() {
    }

    /**
     * helper method to convert a 'delimiter' separated string to a list.
     * 
     * @param str
     *            the 'delimiter' separated string
     * @param delimiter
     *            typically a ','
     * @return a list
     */
    public static List<String> listify(final String str, final String delimiter) {
        if (str == null) {
            return Collections.emptyList();
        }

        final StringTokenizer tok = new StringTokenizer(str, delimiter);
        final List<String> list = new ArrayList<String>();

        while (tok.hasMoreElements()) {
            list.add(StringUtil.trim(tok.nextToken()));
        }

        return list;
    }

    /**
     * convert a list to a comma separated string.
     * 
     * @param list
     *            list to "print"
     * @return a String comma separated.
     */
    public static String listToCSVString(final List list) {
        final StringBuffer buf = new StringBuffer();

        if (list != null) {
            boolean first = true;

            for (final Iterator it = list.iterator(); it.hasNext(); first = false) {
                if (!first) {
                    buf.append(",");
                }
                final Object obj = it.next();
                if (obj != null) {
                    buf.append(obj.toString());
                }
            }
        }

        return buf.toString();
    }

    /**
     * finds out the stack trace up to where the exception was thrown.
     * 
     * @return String that contains the stack trace
     */
    public static String buildStackTraceString(final Throwable ex) {
        final StringBuilder context = new StringBuilder(ex.toString());
        final StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw, true));
        context.append('\n');
        context.append(sw.toString());

        return context.toString();
    }

    /**
     * Finds information about the threads and dumps them into a String.
     * 
     * @return the String containing info about all threads
     */
    public static String dumpThreads() {
        final StringWriter sout = new StringWriter(); // Capture listing in a
        // string
        final PrintWriter out = new PrintWriter(sout);
        Util.listAllThreads(out);
        out.flush();
        return sout.toString();
    }

    /** Find the root thread group and list it recursively */
    private static void listAllThreads(final PrintWriter out) {
        final ThreadGroup currentThreadGroup = Thread.currentThread().getThreadGroup();
        ThreadGroup rootThreadGroup = currentThreadGroup;
        ThreadGroup parent = rootThreadGroup.getParent();

        while (parent != null) {
            rootThreadGroup = parent;
            parent = parent.getParent();
        }

        // And list it, recursively
        Util.printGroupInfo(out, rootThreadGroup, "");
    }

    /** Display info about a thread group and its threads and groups */
    private static void printGroupInfo(final PrintWriter out, final ThreadGroup group, final String indent) {
        if (group == null) {
            return;
        }

        final int numThreads = group.activeCount();
        final int numGroups = group.activeGroupCount();
        final Thread[] threads = new Thread[numThreads];
        final ThreadGroup[] groups = new ThreadGroup[numGroups];
        group.enumerate(threads, false);
        group.enumerate(groups, false);
        out.println(indent + "Thread Group: " + group.getName() + "  Max Priority: " + group.getMaxPriority() + (group.isDaemon() ? " Daemon" : ""));

        for (int i = 0; i < numThreads; i++) {
            Util.printThreadInfo(out, threads[i], indent + "    ");
        }

        for (int i = 0; i < numGroups; i++) {
            Util.printGroupInfo(out, groups[i], indent + "    ");
        }
    }

    private static void printThreadInfo(final PrintWriter out, final Thread thread, final String indent) {
        if (thread == null) {
            return;
        }

        out.println(indent + "Thread: " + thread.getName() + "  Priority: " + thread.getPriority() + (thread.isDaemon() ? " Daemon" : "")
                + (thread.isAlive() ? "" : " Not Alive") + (thread.isInterrupted() ? " Interrupted" : ""));
    }

}
