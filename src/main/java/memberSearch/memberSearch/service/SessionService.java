package memberSearch.memberSearch.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import memberSearch.memberSearch.domain.MySession;
import memberSearch.memberSearch.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionService {

    private final SessionRepository sessionRepository;

    public MySession save(MySession mySession){
        return sessionRepository.insert(mySession);
    }

    public String getId(String sessionId){
        MySession mySession = sessionRepository.select(sessionId);
        if(checkExpired(mySession)){
            return null;
        }
        return mySession.getId();
    }

    public boolean checkExpired(MySession mySession){
        LocalDateTime createdBy = mySession.getCreatedBy();
        LocalDateTime due = createdBy.plusMinutes(1);
        LocalDateTime now = LocalDateTime.now();
        log.info("due={}, now={}", due, now);
        return now.isAfter(due);
    }

    public void updateCreatedBy(MySession mySession){
        mySession.setCreatedBy(LocalDateTime.now());
        sessionRepository.update(mySession);
    }

    public void delete(String sessionId){
        sessionRepository.delete(sessionId);
    }
}
