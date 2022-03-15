package com.guanman.simple.service.impl;

import com.guanman.simple.model.SegmentIdVo;
import com.guanman.simple.service.IdGenerator;
import com.guanman.simple.service.SegmentIdService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedIdGenerator implements IdGenerator {

    //业务标签
    protected String bizTag;

    //当前分段ID缓存对象
    private volatile SegmentIdVo current;

    //下一个分段ID缓存对象
    private volatile SegmentIdVo next;

    //是否加载下一个分段ID缓存对象
    private volatile boolean isLoadingNext;

    //计算获取下一个段ID缓存对象的偏移量
    private static final float OFFSET = 0.7f;

    //步长
    private volatile Long segmentSize;

    //加锁对象
    private Object lock = new Object();

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private SegmentIdService segmentIdService;

    public CachedIdGenerator(String bizTag, SegmentIdService segmentIdService) {
        this.bizTag = bizTag;
        this.segmentIdService = segmentIdService;
        //初始化当前分段ID缓存对象
        loadCurrent();
    }

    /**
     * 加载当前分段ID缓存对象
     */
    public synchronized void loadCurrent() {
        if (current == null || !(current.getCurrentId().get() <= current.getMaxId())) {
            if (next == null) {
                this.current = querySegmentId();
            } else {
                current = next;
                next = null;
            }
        }
    }

    private SegmentIdVo querySegmentId() {
        SegmentIdVo segmentIdVo = segmentIdService.getNextSegmentId(bizTag);
        if (segmentIdVo != null) {
            segmentSize = segmentIdVo.getStep();
            return segmentIdVo;
        }
        throw new RuntimeException();
    }

    /**
     * 获取新生的id值
     * @return
     */
    public Long nextId() {
        while (true) {
            if (current == null) {
                loadCurrent();
                continue;
            }
            long id = current.getCurrentId().incrementAndGet();
            if (id > current.getMaxId()) {
                loadCurrent();
            } else {
                if (id == current.getMinId() + segmentSize * OFFSET) {
                    loadNext();
                }
                return id;
            }
        }
    }

    /**
     * 加载下一个分段ID缓存对象
     */
    public void loadNext() {
        if (next == null && !isLoadingNext) {
            synchronized (lock) {
                if (next == null && !isLoadingNext) {
                    isLoadingNext = true;
                    executorService.submit(() -> {
                        try {
                            // 无论获取下个segmentId成功与否，都要将isLoadingNext赋值为false
                            next = querySegmentId();
                        } finally {
                            isLoadingNext = false;
                        }
                    });
                }
            }
        }
    }
}
