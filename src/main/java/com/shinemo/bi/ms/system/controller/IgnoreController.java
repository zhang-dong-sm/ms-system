package com.shinemo.bi.ms.system.controller;

import com.shinemo.common.annotation.SmIgnore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create Time:2019-10-24
 * User: luchao
 * Email: luc@shinemo.com
 */
@RestController
public class IgnoreController {

    /**
     * 检测接口
     *
     * @return
     */
    @SmIgnore
    @GetMapping("checkstatus")
    public String checkstatus() {
        return "success";
    }

    /**
     * 后门程序
     *
     * @return
     */
    @SmIgnore
    @GetMapping("backDoor/test")
    public String test() {
        return "test";
    }
}
