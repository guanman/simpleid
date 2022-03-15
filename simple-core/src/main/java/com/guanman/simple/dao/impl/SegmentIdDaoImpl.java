package com.guanman.simple.dao.impl;

import com.guanman.simple.dao.SegmentIdDao;
import com.guanman.simple.entity.SegmentId;
import com.guanman.simple.mapper.SegmentIdMapper;
import com.guanman.simple.model.SegmentIdVo;
import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class SegmentIdDaoImpl implements SegmentIdDao {

    private SqlSessionFactory sqlSessionFactory;

    /**
     * 构造函数初始化dataSource,初始化sqlSessionFactory
     * @param url 连接地址
     * @param username 账号
     * @param password 密码
     */
    public SegmentIdDaoImpl(String url, String username, String password) {
        //Mysql 连接池数据源配置
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        //初始化mybatis配置
        TransactionFactory factory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", factory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(SegmentIdMapper.class);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    @Override
    public SegmentId get(String bizTag) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        try {
            return sqlSession.selectOne("com.guanman.simple.mapper.SegmentIdMapper.get", bizTag);
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public SegmentIdVo update(String bizTag) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            SegmentId key = sqlSession.selectOne("com.guanman.simple.mapper.SegmentIdMapper.get", bizTag);
            Map<String, Object> params = new HashMap<>();
            params.put("bizTag", bizTag);
            params.put("version", key.getVersion());
            int row = sqlSession.update("com.guanman.simple.mapper.SegmentIdMapper.update", params);
            sqlSession.commit();
            if (row == 1) {
                return SegmentIdVo.builder().currentId(new AtomicLong(key.getMaxId()))
                        .minId(key.getMaxId())
                        .maxId(key.getMaxId() + key.getStep())
                        .step(key.getStep())
                        .build();
            } else {
                log.info("getNextSegmentId conflict tinyIdInfo:{}", key);
            }
            return null;
        } catch (Exception e) {
            log.error("get next segmentId exception: ", e);
            throw new RuntimeException("get next segmentId conflict");
        } finally {
            sqlSession.close();
        }
    }
}
