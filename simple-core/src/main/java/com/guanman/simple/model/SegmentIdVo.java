package com.guanman.simple.model;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

@Data
@Builder
public class SegmentIdVo {

    private long minId;

    private long maxId;

    private long step;

    private AtomicLong currentId;
}
