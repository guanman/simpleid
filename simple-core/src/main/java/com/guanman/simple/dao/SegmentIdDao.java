package com.guanman.simple.dao;


import com.guanman.simple.entity.SegmentId;
import com.guanman.simple.model.SegmentIdVo;

public interface SegmentIdDao {

    /**
     * 根据业务标签获取表数据
     * @param bizTag
     * @return
     */
    SegmentId get(String bizTag);

    /**
     * 根据业务标签更新表数据
     * @param bizTag
     * @return
     */
    SegmentIdVo update(String bizTag);
}
