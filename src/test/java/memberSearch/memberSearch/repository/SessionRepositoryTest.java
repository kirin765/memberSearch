package memberSearch.memberSearch.repository;

import lombok.extern.slf4j.Slf4j;
import memberSearch.memberSearch.domain.MySession;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class SessionRepositoryTest {

    @Autowired
    SessionRepository sessionRepository;

    @AfterEach
    void afterEach(){
        sessionRepository.deleteAll();
    }

    @Test
    void insertTest(){
        MySession mySession = new MySession("1", "2", LocalDateTime.now());
        sessionRepository.insert(mySession);

        MySession findSession = sessionRepository.select("1");

        Assertions.assertThat(mySession).isEqualTo(findSession);
    }

    @Test
    void afterBeforeTest(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime min1 = now.plusMinutes(1);

        log.info("now={}, min1={}", now, min1);

        log.info("now.isAfter(min1)={}, now.isBefore(min1)={}", now.isAfter(min1), now.isBefore(min1));
        log.info("min1.isAfter(now)={}, min1.isBefore(now)={}", min1.isAfter(now), min1.isBefore(now));
    }

    @Test
    void updateTest() throws InterruptedException {
        MySession mySession = new MySession("1", "2", LocalDateTime.now());
        sessionRepository.insert(mySession);

        log.info("before update={}", mySession);

        Thread.sleep(1000);

        LocalDateTime now = LocalDateTime.now();

        mySession.setCreatedBy(now);

        sessionRepository.update(mySession);

        MySession findSession = sessionRepository.select("1");

        log.info("after update={}", mySession);

        Assertions.assertThat(mySession).isEqualTo(findSession);
    }

    @Test
    void deleteTest(){
        MySession mySession = new MySession("1", "2", LocalDateTime.now());
        sessionRepository.insert(mySession);

        sessionRepository.delete("1");

        MySession findSession = sessionRepository.select("1");

        Assertions.assertThat(findSession).isNull();
    }
}