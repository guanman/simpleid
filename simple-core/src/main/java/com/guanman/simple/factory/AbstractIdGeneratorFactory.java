package com.guanman.simple.factory;


import com.guanman.simple.service.IdGenerator;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ID生成器模板
 */
public abstract class AbstractIdGeneratorFactory implements IdGeneratorFactory {

    //缓存不同业务的ID生成器
    private static ConcurrentHashMap<String, IdGenerator> generators = new ConcurrentHashMap<>();

    @Override
    public IdGenerator getIdGenerator(String bizType) {
        //缓存中存在直接返回
        if (generators.containsKey(bizType)) {
            return generators.get(bizType);
        }
        //同步锁检查，存在直接返回，否则创建后返回
        synchronized (this) {
            if (generators.containsKey(bizType)) {
                return generators.get(bizType);
            }
            IdGenerator idGenerator = createIdGenerator(bizType);
            generators.put(bizType, idGenerator);
            return idGenerator;
        }
    }

    /**
     * 根据bizTag创建id生成器
     *
     * @param bizType
     * @return
     */
    protected abstract IdGenerator createIdGenerator(String bizType);
}
