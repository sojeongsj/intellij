package org.iclass.mvc.dao;

import org.iclass.mvc.dto.Community;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommunityMapperTest {

    @Autowired
    CommunityMapper dao;

    @Test
    @DisplayName("count 테스트")
    void count() {
        int count = dao.count();
        Assertions.assertNotEquals(0,count);
    }

    @Test
    @DisplayName("idx 테스트")
    void selectByIdx() {

        Community dto = dao.selectByIdx(22);
        Assertions.assertNotNull(dto);
    }
}