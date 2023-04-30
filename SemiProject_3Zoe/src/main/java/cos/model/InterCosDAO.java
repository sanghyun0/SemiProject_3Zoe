package cos.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import member.model.MemberVO;

public interface InterCosDAO {
	
	// 강의 리스트 불러오기 메소드 생성하기
	List<CosVO> selectBySpecName(Map<String, String> paraMap) throws SQLException;

	// 강의삭제하기에서 체크된 강의들 삭제하는 메소드 생성하기
	int delectCos(Map<String, String> paraMap) throws SQLException;

	// 찜한 강의 찜 테이블에 올려주는 메소드 생성하기
	int LikeTblAdd(String checkedHeart, String userid) throws SQLException;

	// 찜 풀은 강의 찜 테이블에서 삭제해주는 메소드 생성하기
	int dislike(String discheckedHeart, String userid) throws SQLException;
	
	// 제품상세목록 보여주는 메소드 생성하기
	CosVO selectOneProductByCourseCode(String courseCode)  throws SQLException;

	// 비슷한 강의 찾아주기 메소드 생성하기
	List<CosVO> CategoryListByCourseCode(Map<String, String> paraMap) throws SQLException;
	
	// 용훈님용 제품목록 불러오기 메소드 생성하기
	List<Map<String, String>> getCategoryList() throws SQLException;

}
