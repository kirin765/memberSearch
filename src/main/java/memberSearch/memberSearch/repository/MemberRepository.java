package memberSearch.memberSearch.repository;

import memberSearch.memberSearch.domain.Member;

import java.util.List;

public interface MemberRepository {

    Member save(Member member);
    Member findById(String memberId);
    List<Member> findAll();
    void delete(String memberId);
}
