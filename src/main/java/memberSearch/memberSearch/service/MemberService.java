package memberSearch.memberSearch.service;

import lombok.RequiredArgsConstructor;
import memberSearch.memberSearch.domain.Member;
import memberSearch.memberSearch.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member){
        memberRepository.save(member);
        return member;
    }

    public Member findMember(String memberId){
        Member findMember = memberRepository.findById(memberId);
        return findMember;
    }

    public List<Member> findAllMember(){
        List<Member> allMembers = memberRepository.findAll();
        return allMembers;
    }

    Member deleteMember(String memberId){
        Member deletedMember = memberRepository.delete(memberId);
        return deletedMember;
    }
}
