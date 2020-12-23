package com.play.ekquiz.service;

import com.play.ekquiz.mapper.MainMapper;
import com.play.ekquiz.vo.MainVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MainService {
    @Autowired
    private MainMapper mainMapper;

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "dbTest")
    public MainVO getDbTest() throws Exception {
        return mainMapper.getDbTest();
    }

    @CacheEvict(cacheNames = "dbTest", allEntries = true)
    public void deleteCache() {
        System.out.println("delete cache all");
    }
}
