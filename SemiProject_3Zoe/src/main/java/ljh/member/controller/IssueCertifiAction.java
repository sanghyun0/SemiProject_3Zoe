package ljh.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import cos.model.CosVO;
import ljh.member.model.InterMemberDAO;
import ljh.member.model.MemberDAO;
import member.model.MemberVO;

public class IssueCertifiAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		InterMemberDAO mdao = new MemberDAO();
		
		HttpSession session = request.getSession();
		//세션에서 로그인된 아이디 가져오기
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		// 로그인한 사람이 듣고있는 강의 가져오기
		List<CosVO> cosList= mdao.getIngCourse(loginuser.getUserid());
		

		// jsp로 넘긴다.
		request.setAttribute("cosList", cosList);
		
		
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/ljh.myPage/issueCertifi.jsp");
		
	}

}
