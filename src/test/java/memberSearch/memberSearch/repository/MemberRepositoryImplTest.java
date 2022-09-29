package memberSearch.memberSearch.repository;

import lombok.extern.slf4j.Slf4j;
import memberSearch.memberSearch.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryImplTest {

    MemberRepository memberRepository = new MemberRepositoryImpl();

    @Test
    void saveTest(){
        Member newMember = new Member("1", "member1", "1234");
        memberRepository.save(newMember);
    }

    @Test
    void findByIdTest(){
        String memberId = "1";
        Member findMember = memberRepository.findById(memberId);
        log.info("findMember: memberId={}, name={}, password={}", findMember.getId(), findMember.getName(), findMember.getPassword());
    }

    @Test
    void findAllTest(){
        memberRepository.findAll().forEach(member -> log.info("member: {}", member));
    }

    @Test
    void deleteTest(){
        String memberId = "1";
        memberRepository.delete(memberId);
        Assertions.assertThatThrownBy(()->memberRepository.findById(memberId)).isInstanceOf(NoSuchElementException.class);
    }
}