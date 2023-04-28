package ssh.member.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import member.model.MemberVO;
import util.security.AES256;
import util.security.SecretMyKey;
import util.security.Sha256;

public class MemberDAO implements InterMemberDAO {

	
	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool) 이다.    
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private AES256 aes;
	
	
	// 사용한 자원을 반납하는 close() 메소드 생성하기
	private void close() {
	   try {
 		    if(rs != null)     {rs.close();    rs=null;}
 		    if(pstmt != null)  {pstmt.close(); pstmt=null;}
 		    if(conn != null)   {conn.close(); conn=null;}
	   } catch(SQLException e)  {
		   e.printStackTrace();
	   }
	}
	
	// 생성자
	public MemberDAO() {
		try {
		     Context initContext = new InitialContext();
	         Context envContext  = (Context)initContext.lookup("java:/comp/env");
	         ds = (DataSource)envContext.lookup("jdbc/semi_oracle");
	         
	         aes = new AES256(SecretMyKey.KEY);
	         // SecretMyKey.KEY 은 우리가 만든 암호화/복호화 키이다.
	         
		} catch(NamingException e) {
			e.printStackTrace();
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}	
	
	// 회원가입을 해주는 메소드(tbl_member 테이블에 insert)
	@Override
	public int registerMember(MemberVO member) throws SQLException {
		
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " insert into tbl_member(userid, pwd, name, email, mobile) " 
					   + " values(?, ?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(sql); 
			
			pstmt.setString(1, member.getUserid());
			pstmt.setString(2, Sha256.encrypt(member.getPwd()) ); // 암호를 SHA256 알고리즘으로 단방향 암호화 시킨다. 
			pstmt.setString(3, member.getName());
			pstmt.setString(4, aes.encrypt(member.getEmail()) );  // 이메일을 AES256 알고리즘으로 양방향 암호화 시킨다. 
			pstmt.setString(5, aes.encrypt(member.getMobile()) ); // 휴대폰번호를 AES256 알고리즘으로 양방향 암호화 시킨다. 

			
			result = pstmt.executeUpdate();
			
		} catch(GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return result;
	}
	
	
	// ID 중복검사(tbl_member 테이블에서 userid 가 존재하면 true를 리턴해주고, userid 가 존재하지 않으면 false를 리턴해준다.)
	@Override
	public boolean idDuplicateCheck(String userid) throws SQLException {
		
		boolean isExists = false;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select userid "
					   + " from tbl_member "
					   + " where userid = ? ";
			
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, userid);
			
			rs = pstmt.executeQuery();
			
			isExists = rs.next(); // 행이 있으면(중복된 userid) true,
			                      // 행이 없으면(사용가능한 userid) false
			
		} finally {
			close();
		}
		
		return isExists;
	}
	
	// 입력받은 paraMap 을 가지고 한명의 회원정보를 리턴시켜주는 메소드(로그인 처리)
	@Override
	public MemberVO selectOneMember(Map<String, String> paraMap) throws SQLException {
		
		MemberVO member = null;
		
		try {
			 conn = ds.getConnection();
			 
			 String sql = " SELECT  userid, name, email, mobile, postcode, address, detailaddress, extraaddress, gender, "
			 		    + " birthday, point, registerday, pwdchangegap, issue, checkEmail, checkMobile, "
			 		    + " NVL(lastlogingap, trunc( months_between(sysdate, registerday)) ) AS lastlogingap "
			 		    + " FROM "
			 		    + " ( "
			 		    + " select userid, name, email, mobile, postcode, address, detailaddress, extraaddress, gender , birthday "
			 		    + " , point, to_char(registerday, 'yyyy-mm-dd') AS registerday "
				 		+ " , trunc( months_between(sysdate, lastpwdchangedate) ) AS pwdchangegap "
			 		    + " , issue, checkEmail, checkMobile "
				 		+ " from tbl_member "
				 		+ " where status = 1 and userid = ? and pwd = ? "
				 		+ " ) M "
				 		+ " CROSS JOIN "
				 		+ " ( "
				 		+ " select trunc( months_between(sysdate, max(logdate)) ) AS lastlogingap "
				 		+ " from tbl_loginhistory "
				 		+ " where fk_userid = ? "
				 		+ " ) H "; 
			 
			 pstmt = conn.prepareStatement(sql); 
			 
			 pstmt.setString(1, paraMap.get("userid") );
			 pstmt.setString(2, paraMap.get("pwd") );
			 pstmt.setString(3, paraMap.get("userid") );
			 
			 rs = pstmt.executeQuery();
			 
			 if(rs.next()) {
				 member = new MemberVO();
				 
				 member.setUserid(rs.getString(1));
				 member.setName(rs.getString(2));
				 member.setEmail( rs.getString(3) );  // 복호화 
				 member.setMobile( rs.getString(4) ); // 복호화 
				 member.setPostcode(rs.getString(5));
				 member.setAddress(rs.getString(6));
				 member.setDetailaddress(rs.getString(7));
				 member.setExtraaddress(rs.getString(8));
				 member.setGender(rs.getString(9));
				 member.setBirthday(rs.getString(10));
				 member.setPoint(rs.getInt(11));
				 member.setRegisterday(rs.getString(12));
				 
				 if( rs.getInt(13) >= 3 ) { // 또는 rs.getInt("PWDCHANGEGAP")
					// 마지막으로 암호를 변경한 날짜가 현재시각으로 부터 3개월이 넘었으면 true
				    // 마지막으로 암호를 변경한 날짜가 현재시각으로 부터 3개월이 넘지 않았으면 false
					 
					member.setRequirePwdChange(true); // 로그인시 암호를 변경해라는 alert 를 띄우도록 할때 사용한다.  
				 }
				 
				 member.setIssue(rs.getString(14));
				 member.setCheckEmail(rs.getString(15));
				 member.setCheckMobile(rs.getString(16));
				 
				 if ( rs.getInt(17) >= 12 ) { // 또는 rs.getInt("LASTLOGINGAP")
					 // 마지막으로 로그인 한 날짜시간이 현재시각으로 부터 1년이 지났으면 휴면으로 지정  
					 
					 member.setIdle(1); 
					 
					 // === tbl_member 테이블의 idle 컬럼의 값을 1 로 변경하기 === //
					 sql = " update tbl_member set idle = 1 "
					 	 + " where userid = ? "; 
					 
					 pstmt = conn.prepareStatement(sql); 
					 pstmt.setString(1, paraMap.get("userid"));
					 
					 pstmt.executeUpdate();
				 }
				 
				 // === tbl_loginhistory(로그인기록) 테이블에 insert 하기 === // 
				 if(member.getIdle() != 1) {
				
					 sql = " insert into tbl_loginhistory(fk_userid, logip) "
					 	 + " values(?, ?) "; 
					 
					 pstmt = conn.prepareStatement(sql);
					 pstmt.setString(1, paraMap.get("userid"));
					 pstmt.setString(2, paraMap.get("logip"));
					 
					 pstmt.executeUpdate();
				 }
				 
			 }// end of if(rs.next())--------------------
			 
		} finally {
			close();
		}
		
		return member;
	}

	

	

}