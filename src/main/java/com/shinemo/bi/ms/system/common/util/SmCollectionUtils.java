package com.shinemo.bi.ms.system.common.util;

import com.shinemo.bi.ms.system.common.config.OptionConfig;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: luchao
 * @email: luc@shinemo.com
 * @date: 2019/6/19
 */
public class SmCollectionUtils {

    public static Set<String> toStrSet(String str) {
        if (StringUtils.isBlank(str)) {
            return Collections.emptySet();
        }
        String[] list = str.split(OptionConfig.SEPARATOR_COMMA);
        return new HashSet<>(getList(list));
    }

    /**
     * 转数组 (去重)
     *
     * @param str
     * @return
     */
    public static List<String> toStrDisList(String str) {
        if (StringUtils.isBlank(str)) {
            return Collections.emptyList();
        }
        String[] list = str.split(OptionConfig.SEPARATOR_COMMA);
        return Stream.of(list).filter(StringUtils::isNotBlank).distinct().collect(Collectors.toList());
    }

    /**
     * 转数组
     *
     * @param str
     * @return
     */
    public static List<String> toStrList(String str) {
        if (StringUtils.isBlank(str)) {
            return Collections.emptyList();
        }
        String[] list = str.split(OptionConfig.SEPARATOR_COMMA);
        return Stream.of(list).filter(StringUtils::isNotBlank).collect(Collectors.toList());
    }

    private static List<String> getList(String[] list) {
        List<String> result = new ArrayList<>(list.length);
        Stream.of(list).forEach(s -> {
            if (StringUtils.isNotBlank(s)) {
                result.add(s);
            }
        });
        return result;
    }
}
