package com.guanman.simple.mapper;

import com.guanman.simple.entity.SegmentId;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

public interface SegmentIdMapper {

    @Select("/* to master */ " +
            "/*FORCE_MASTER*/ select biz_tag bizTag, max_id maxId, step, version from segment_id_t where biz_tag = #{bizTag}")
    SegmentId get(String bizTag);

    @Update("update segment_id_t set max_id = max_id + step, version = version + 1 where biz_tag = #{bizTag} and version = #{version}")
    int update(Map<String, Object> params);
}
