package ljh.member.controller;


import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import ljh.member.model.InterMemberDAO;
import ljh.member.model.MemberDAO;
import member.model.MemberVO;


public class PasswdInputAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		HttpSession session = request.getSession();
		//세션에서 로그인된 아이디 가져오기
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		if(loginuser.getUserid() == "") {
			String message = "로그인을 하세요";
			String loc =request.getContextPath()+"/index.go";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		}
		
		
		else {
			String method = request.getMethod();
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/ljh.memberEdit/passwdInput.jsp");
			
			
			
			
			
			/*
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/ljh.memberEdit/infoChange.jsp");
			
			super.setRedirect(true);
			super.setViewPage(request.getContextPath()+"/index.up");
			*/
			
			
		
			if(!"post".equalsIgnoreCase(method)) {	

				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/ljh.memberEdit/passwdInput.jsp");
			}
		
			
			//post방식으로 들어왔을 땐 비밀번호를 입력하고 확인을 눌렀음
			else {
				String passwd = request.getParameter("passwd");
				
			
				
				String userid = loginuser.getUserid();
				
				
				InterMemberDAO dao = new MemberDAO();
				
				Map<String,String> paraMap = new HashMap<>();
				paraMap.put("userid", userid);
				paraMap.put("passwd", passwd);
				
				//입력받은 비밀번호와 로그인된 유저의 아이디와 같은지 체크
				boolean isSame = dao.checkPasswd(paraMap);
				
				if(!isSame) {	//입력받은 비밀번호가 다르면
					
					//다시 원래로 돌아가고 비밀번호가 틀렸다고 나오게 하게
			
					
					String message = "비밀번호를 확인하세요.";
					String loc ="javascript:history.back()";
					
					request.setAttribute("message", message);
					request.setAttribute("loc", loc);
					
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/msg.jsp");
				}
				else {	//입력받은 비밀번호가 같으면
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/ljh.memberEdit/infoChange.jsp");
				}
			}
			
			
			}
	}

}
