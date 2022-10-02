package memberSearch.memberSearch.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import memberSearch.memberSearch.domain.MySession;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
@Slf4j
public class SessionRepository {

    private final JdbcTemplate template;

    public SessionRepository(DataSource dataSource){
        this.template = new JdbcTemplate(dataSource);
    }

    public MySession insert(MySession mySession){
        String sql = "insert into mysession(sessionid, id, createdby) values (?, ?, ?)";

        template.update(sql,
                mySession.getSessionId(),
                mySession.getId(),
                mySession.getCreatedBy());

        return mySession;
    }

    public MySession select(String sessionId){
        String sql = "select * from mysession where sessionid = ?";

        try {
            return template.queryForObject(sql, mySessionRowMapper(), sessionId);
        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    public void update(MySession mySession){
        String sql = "update mysession set sessionid = ?, id = ?, createdby = ? where sessionid = ?";
        template.update(sql,
                mySession.getSessionId(),
                mySession.getId(),
                mySession.getCreatedBy(),
                mySession.getSessionId()
                );
    }

    public RowMapper<MySession> mySessionRowMapper(){
        return ((rs, rowNum) -> {
            return new MySession(
                    rs.getString("sessionid"),
                    rs.getString("id"),
                    rs.getTimestamp("createdby").toLocalDateTime()
            );
        });
    }

    public void delete(String sessionId){
        String sql = "delete from mysession where sessionid = ?";
        template.update(
                sql,
                sessionId
        );
    }

    public void deleteAll(){
        String sql = "delete from mysession";
        template.update(sql);
    }
}
