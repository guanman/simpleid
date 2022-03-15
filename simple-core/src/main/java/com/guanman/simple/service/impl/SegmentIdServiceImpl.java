package com.guanman.simple.service.impl;

import com.guanman.simple.dao.SegmentIdDao;
import com.guanman.simple.dao.impl.SegmentIdDaoImpl;
import com.guanman.simple.model.SegmentIdVo;
import com.guanman.simple.service.SegmentIdService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SegmentIdServiceImpl implements SegmentIdService {

    private SegmentIdDao segmentIdDao;

    public SegmentIdServiceImpl(String url, String username, String password) {
        segmentIdDao = new SegmentIdDaoImpl(url, username, password);
    }

    public SegmentIdVo getNextSegmentId(String bizTag) {
        for (int i = 0; i < 3; i++){
            return segmentIdDao.update(bizTag);
        }
        throw new RuntimeException("get next segmentId conflict");
    }
}
