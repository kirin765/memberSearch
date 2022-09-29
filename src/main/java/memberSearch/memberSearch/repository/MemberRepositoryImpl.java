package memberSearch.memberSearch.repository;

import lombok.extern.slf4j.Slf4j;
import memberSearch.memberSearch.Connection.DBConnectionUtil;
import memberSearch.memberSearch.domain.Member;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBConnectionUtil.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);

            rs = pstmt.executeQuery();
            if(rs.next()){
                String findMemberId = rs.getString("member_id");
                String findName = rs.getString("name");
                String findPassword = rs.getString("password");

                Member member = new Member(findMemberId, findName, findPassword);
                return member;
            }else{
                throw new NoSuchElementException("member not found memberId="+memberId);
            }

        }catch (SQLException e){
            log.error("db error", e);
            return null;
        }finally {
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    log.info("error", e);
                }
            }
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
    public List<Member> findAll() {
        String sql = "select * from member";

        List<Member> memberList = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBConnectionUtil.getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()){
                String memberId = rs.getString("member_id");
                String name = rs.getString("name");
                String password = rs.getString("password");

                Member findMember = new Member(memberId, name, password);

                memberList.add(findMember);
            }
            return memberList;
        }catch (SQLException e){
            log.error("db error",e);
            return null;
        }finally {
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    log.info("error", e);
                }
            }
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
    public void delete(String memberId) {
        String sql = "delete from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBConnectionUtil.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            pstmt.executeUpdate();
        }catch (SQLException e){
            log.error("db error", e);
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
}
