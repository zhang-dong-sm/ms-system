package com.shinemo.bi.ms.system.common.config;

import com.shinemo.common.utils.SmNetUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Create Time:2019-10-08
 * User: luchao
 * Email: luc@shinemo.com
 */
public class OptionConfig {
    public final static String DEBUG = "debug";

    public static int SERVER_PORT = SmNetUtil.getRandomIdlePort();

    public final static String SEPARATOR_UNDERLINE = "_";

    public final static String SEPARATOR_COMMA = ",";

    public final static String SEPARATOR_SLASH = "/";

    public final static String SEPARATOR_POINT = ".";

    public final static List<String> IGNORE_URI = Arrays.asList("/actuator", "/backDoor");

    public final static String CHECK_STATUS = "checkstatus";

}
