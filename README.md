### 使用说明

具体使用可参考 `simple-example`

#### 引入依赖
```xml
<dependency>
    <groupId>com.guanman.simple.plugin</groupId>
    <artifactId>simpleid-spring-boot-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

#### 配置simple.properties到你的classpath下面
```properties
segment.url=
segment.username=
segment.password=
```

#### 配置介绍

| 配置项                    | 含义                          | 默认值 |
| ------------------------- | ----------------------------- | ------ |
| segment.url             | mysql 库地址                  |        |
| segment.username        | mysql 用户名                  |        |
| segment.password        | mysql 密码                    |        |

需要建立DB表，并配置segment.url, segment.username, segment.password

##### 创建数据表

```sql
CREATE TABLE `segment_id_t` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `biz_tag` varchar(32) DEFAULT NULL COMMENT '业务标识',
  `max_id` bigint(20) DEFAULT NULL COMMENT '最大ID',
  `step` bigint(20) DEFAULT NULL COMMENT '步长',
  `desc` varchar(64) DEFAULT NULL COMMENT '描述',
  `version` bigint(20) DEFAULT NULL COMMENT '版本号',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_biz_tag` (`biz_tag`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

INSERT INTO `segment_id_t`(`biz_tag`, `max_id`, `step`, `desc`, `version`) VALUES ('user_tag', 0, 100, '用户id', 1);
```
