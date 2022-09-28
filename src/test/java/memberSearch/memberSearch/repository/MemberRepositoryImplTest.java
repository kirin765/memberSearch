package memberSearch.memberSearch.repository;

import lombok.extern.slf4j.Slf4j;
import memberSearch.memberSearch.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryImplTest {

    MemberRepository memberRepository = new MemberRepositoryImpl();

    @Test
    void saveTest(){
        Member newMember = new Member("1", "member1", "1234");
        memberRepository.save(newMember);
    }
}