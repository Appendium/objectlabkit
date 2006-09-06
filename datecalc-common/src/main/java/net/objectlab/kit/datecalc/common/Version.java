/*
 * $Id: org.eclipse.jdt.ui.prefs 99 2006-09-04 20:30:25Z marchy $
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


import java.io.*;

/**
 * 
 * @author xhensevb
 * @author $LastChangedBy: marchy $
 * @version $Revision: 99 $ $Date: 2006-09-04 21:30:25 +0100 (Mon, 04 Sep 2006) $
 * 
 */
public class Version {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: java version <.class file>");
            System.exit(1);
        }

        if (!new File(args[0]).exists()) {
            System.err.println(args[0] + " does not exist!");
            System.exit(2);
        }

        DataInputStream dis = new DataInputStream(new FileInputStream(args[0]));
        int magic = dis.readInt();
        if (magic != 0xcafebabe) {
            System.err.println(args[0] + " is not a .class file");
            System.exit(3);
        }

        int minor = dis.readShort();
        int major = dis.readShort();
        System.out.println("class file version is " + major + "." + minor);

        String version = null;

        if (major < 48) {
            version = "1.3.1";
        } else if (major == 48) {
            version = "1.4.2";
        } else if (major == 49) {
            version = "1.5";
        } else if (major == 50) {
            version = "6";
        } else {
            version = "7";
        }
        System.out.println("You need to use JDK " + version + " or above");
    }
}