package com.guanman.simple.example.controller;

import com.guanman.simple.factory.impl.IdGeneratorFactoryServer;
import com.guanman.simple.service.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("id")
public class IdGenerateController {

    @Autowired
    private IdGeneratorFactoryServer idGeneratorFactoryServer;

    @GetMapping(value = "next")
    public Long nextId() {
        IdGenerator idGenerator = idGeneratorFactoryServer.getIdGenerator("user_tag");
        Long id = idGenerator.nextId();
        log.info("获取分部式id : {}", id);
        return id;
    }
}
