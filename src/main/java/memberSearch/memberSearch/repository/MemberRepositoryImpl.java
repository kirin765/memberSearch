package memberSearch.memberSearch.repository;

import lombok.extern.slf4j.Slf4j;
import memberSearch.memberSearch.Connection.DBConnectionUtil;
import memberSearch.memberSearch.domain.Member;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@Slf4j
public class MemberRepositoryImpl implements MemberRepository{
    @Override
    public Member save(Member member) {
        String sql = "insert into member(member_id, name, password) values (?, ?, ?)";
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBConnectionUtil.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getId());
            pstmt.setString(2, member.getName());
            pstmt.setString(3, member.getPassword());
            pstmt.executeUpdate();
            return member;
        }catch (SQLException e){
            log.error("db error", e);
            return null;
        }finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                log.info("error", e);
            }

            try {
                con.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
    }

    @Override
    public Member findById(String memberId) {
        return new Member("member1", "1", "");
    }

    @Override
    public List<Member> findAll() {
        return null;
    }

    @Override
    public Member delete(String memberId) {
        return null;
    }
}
