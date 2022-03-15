package com.guanman.simple.entity;

import lombok.Data;

import java.util.Date;

/**
 * 分布式ID对象
 */
@Data
public class SegmentId {

    private Long id;

    /**
     * 业务标识
     */
    private String bizTag;

    /**
     * 最大ID
     */
    private Long maxId;

    /**
     * 步长
     */
    private Long step;

    /**
     * 描述
     */
    private String desc;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 操作时间
     */
    private Date updateTime;
}
