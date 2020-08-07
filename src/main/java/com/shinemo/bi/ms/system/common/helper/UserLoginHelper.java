package com.shinemo.bi.ms.system.common.helper;

import com.shinemo.common.lang.SmRequestContext;

/**
 * User: huangyuting
 * Mail: huangyt@shinemo.com
 * Date: 16/8/5
 * Time: 下午4:01
 *
 * @author luchao
 */
public class UserLoginHelper {
    public static void set(String uid, String mobile, String name) {
        SmRequestContext.put("uid", uid);
        SmRequestContext.put("mobile", mobile);
        SmRequestContext.put("name", name);
    }

    public static String getUid() {
        return SmRequestContext.get("uid");
    }

    public static String getMobile() {
        return SmRequestContext.get("mobile");
    }

    public static String getName() {
        return SmRequestContext.get("name");
    }
}
