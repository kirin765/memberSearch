package memberSearch.memberSearch.service;

import lombok.RequiredArgsConstructor;
import memberSearch.memberSearch.domain.Member;
import memberSearch.memberSearch.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String id, String password){
        Member loginMember = memberRepository.findById(id);
        if(loginMember == null){
            return null;
        }

        if(loginMember.getPassword().equals(password)){
            return loginMember;
        }
        return null;
    }
}
