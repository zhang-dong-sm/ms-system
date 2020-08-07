package com.shinemo.bi.ms.system.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: luchao
 * @email: luc@shinemo.com
 * @date: 2019/7/2
 */
public class SmStringUtils {

    private final static int LENGTH = 11;

    static Pattern numberP = Pattern.compile("^[0-9]*$");

    public static boolean isEffectiveMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return false;
        }
        Matcher matcher = numberP.matcher(mobile);
        if (matcher.find()) {
            if (mobile.startsWith("1") && mobile.length() == LENGTH) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(isEffectiveMobile("18368497687"));
    }
}
