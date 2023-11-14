/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lowcodesystem.util;

import java.util.Hashtable;
import java.util.List;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 *
 * @author Maykon
 */
public class Val {

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNullOrEmpy(String obj) {
        return obj == null || obj.trim().isEmpty();
    }

    public static boolean isNullOrEmpy(List list) {
        return list == null || list.isEmpty();
    }

    public static boolean validDomainEmail(String hostName) {
        if (!hostName.contains(".")) {
            return false;
        }
        try {
            Hashtable env = new Hashtable();
            env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            DirContext ictx = new InitialDirContext(env);
            Attributes attrs = ictx.getAttributes(hostName, new String[]{"MX"});
            Attribute attr = attrs.get("MX");
            if (attr == null) {
                return false;
            }
            return (attr.size()) > 0;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public static boolean isEquals(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        } else if (str1 == null || str2 == null) {
            return false;
        }
        return str1.equals(str2);
    }

    public static boolean isEqualsTrim(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        } else if (str1 == null || str2 == null) {
            return false;
        }
        return str1.trim().equals(str2.trim());
    }

    public static boolean isNullOrZero(Long value) {
        if (value == null) {
            return true;
        } else {
            return value == 0;
        }
    }

    public static boolean isNullOrZero(Integer value) {
        if (value == null) {
            return true;
        } else {
            return value == 0;
        }
    }
}
