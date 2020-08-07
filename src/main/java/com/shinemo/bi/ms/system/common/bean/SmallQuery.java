package com.shinemo.bi.ms.system.common.bean;

import com.shinemo.common.utils.SmDateUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Create Time: 2019/2/15
 * User: luchao
 * Email: luc@shinemo.com
 */
public class SmallQuery {
    @NotNull(message = "页码不能为空")
    private Integer pageIndex;
    private Integer pageSize = 20;
    private String beginTime;
    private String endTime;

    private Map<String, Object> queryMap = new HashMap<>(4);

    public SmallQuery() {
    }

    public SmallQuery(Integer pageIndex, Integer pageSize, String beginTime, String endTime) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public SmallQuery(Integer pageIndex, Integer pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) { // param pageIndex start with 0 from client
        if (pageIndex > 0) {
            this.pageIndex = pageIndex - 1;
        }
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        if (StringUtils.isNotBlank(beginTime)) {
            this.beginTime = beginTime;
        }
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        if (StringUtils.isNotBlank(endTime)) {
            this.endTime = endTime;
        }
    }

    public Map<String, Object> toQueryMap() {
        queryMap.put("start", pageIndex * pageSize);
        queryMap.put("limit", pageSize);
        Date beginDate = dateFormat(beginTime);
        Date endDate = dateFormat(endTime);
        if (beginDate != null) {
            queryMap.put("beginTime", beginDate);
        }
        if (endDate != null) {
            queryMap.put("endTime", endDate);
        }
        return queryMap;
    }

    private Date dateFormat(String time) {
        if (StringUtils.isBlank(time)) {
            return null;
        }
        if (time.length() == 10) {
            time = time + " 00:00:00";
        }
        if (time.length() == 19) {
            return SmDateUtil.toDate(time, "yyyy-MM-dd HH:mm:ss");
        }
        return null;
    }
}
