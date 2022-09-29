package memberSearch.memberSearch.repository;

import lombok.extern.slf4j.Slf4j;
import memberSearch.memberSearch.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryImplTest {

    MemberRepository memberRepository = new MemberRepositoryImpl();

    @AfterEach
    void afterEach(){
        List<String> ids = memberRepository.findAll(new MemberSearchCondition("", "", ""))
                .stream().map(member -> member.getId()).collect(Collectors.toList());
        ids.stream().forEach(id->memberRepository.delete(id));
    }

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
        Member member1 = memberRepository.save(new Member("1", "member1", "1234"));

        List<Member> members = memberRepository.findAll(new MemberSearchCondition("1", "", ""));

        Assertions.assertThat(members.size()).isEqualTo(1);
        Assertions.assertThat(members.get(0)).isEqualTo(member1);

        Member member2 = memberRepository.save(new Member("2", "member2", "4321"));

        members = memberRepository.findAll(new MemberSearchCondition("", "member2", ""));

        Assertions.assertThat(members.size()).isEqualTo(1);
        Assertions.assertThat(members.get(0)).isEqualTo(member2);

        members = memberRepository.findAll(new MemberSearchCondition("", "", "1234"));

        Assertions.assertThat(members.size()).isEqualTo(1);
        Assertions.assertThat(members.get(0)).isEqualTo(member1);

        members = memberRepository.findAll(new MemberSearchCondition("", "", ""));

        Assertions.assertThat(members.size()).isEqualTo(2);
        Assertions.assertThat(members).contains(member1, member2);

        members = memberRepository.findAll(new MemberSearchCondition("", "member", ""));

        Assertions.assertThat(members.size()).isEqualTo(2);
        Assertions.assertThat(members).contains(member1, member2);

        members = memberRepository.findAll(new MemberSearchCondition("", "member", "2"));

        Assertions.assertThat(members.size()).isEqualTo(2);
        Assertions.assertThat(members).contains(member1, member2);

        members = memberRepository.findAll(new MemberSearchCondition("", "", "23"));

        Assertions.assertThat(members.size()).isEqualTo(1);
        Assertions.assertThat(members.get(0)).isEqualTo(member1);
    }

    @Test
    void deleteTest(){
        String memberId = "1";
        memberRepository.delete(memberId);
        Assertions.assertThatThrownBy(()->memberRepository.findById(memberId)).isInstanceOf(NoSuchElementException.class);
    }
}