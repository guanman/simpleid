package com.guanman.simple.factory.impl;

import com.guanman.simple.factory.AbstractIdGeneratorFactory;
import com.guanman.simple.service.IdGenerator;
import com.guanman.simple.service.SegmentIdService;
import com.guanman.simple.service.impl.CachedIdGenerator;
import com.guanman.simple.service.impl.SegmentIdServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * @author du_imba
 */
@Slf4j
public class IdGeneratorFactoryServer extends AbstractIdGeneratorFactory {

    private SegmentIdService segmentIdService;

    public IdGeneratorFactoryServer(String url, String username, String password) {
        segmentIdService = new SegmentIdServiceImpl(url, username, password);
    }

    @Override
    public IdGenerator createIdGenerator(String bizTag) {
        log.info("createIdGenerator :{}", bizTag);
        return new CachedIdGenerator(bizTag, segmentIdService);
    }
}
