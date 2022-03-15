package com.guanman.simple.factory;


import com.guanman.simple.service.IdGenerator;

public interface IdGeneratorFactory {
    /**
     * 根据bizTag获取id生成器
     * @param bizTag
     * @return
     */
    IdGenerator getIdGenerator(String bizTag);
}
