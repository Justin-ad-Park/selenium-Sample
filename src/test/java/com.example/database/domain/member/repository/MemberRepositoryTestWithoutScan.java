package com.example.database.domain.member.repository;

import com.example.database.domain.member.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;


@SpringBootTest
//@EnableAutoConfiguration
//@SpringBootTest(classes = FastcampusMysqlApplicationTests.class)
//@ContextConfiguration(locations = "/test-member-context.xml")
class MemberRepositoryTestWithoutScan {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void findById() {

        var findMemberId = 4L;
        Optional<Member> member = memberRepository.findById(findMemberId);

        Assertions.assertEquals(findMemberId, member.get().getId());
    }

}