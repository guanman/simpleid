package com.guanman.simple.service;

import com.guanman.simple.model.SegmentIdVo;

public interface SegmentIdService {

    /**
     * 根据业务标识获取下一个id分段
     * @param bizTag
     * @return
     */
    SegmentIdVo getNextSegmentId(String bizTag);
}
