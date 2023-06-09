package member.model;

public class MemberVO {
	
	private String userid;          	// 회원아이디   
	private String pwd;             	// 비밀번호   
	private String name;            	// 회원명
	private String email;           	// 이메일   
	private String mobile;  			// 연락처
	private String postcode;			// 우편번호 
	private String address;				// 주소
	private String detailaddress;		// 상세주소
	private int point; 				   	// 포인트 
	private String registerday;        	// 가입일자 
	private String lastpwdchangedate;  	// 마지막으로 암호를 변경한 날짜  
	private int status;                	// 회원탈퇴유무   1: 사용가능(가입중) / 0:사용불능(탈퇴) 
	private int idle;                  	// 휴면유무      0 : 활동중  /  1 : 휴면중   마지막으로 로그인 한 날짜 시간인 현재시간으로부터 1년이 지났으면 휴면 처리
	private String issue;
	private int checkEmail;
	private int checkMobile;
	private String lastLogdate;
	///////////////////////////////////////////////////////////////////////
	

	private boolean requirePwdChange = false;
	// 마지막으로 암호를 변경한 날짜가 현재시각으로부터 3개월이 지났으면 true
	// 마지막으로 암호를 변경한 날짜가 현재시각으로부터 3개월이 지나지 않았으면 false
										
	////////////////////////////////////////////////////////////////////////
	
	public MemberVO() {}
	
	
	public MemberVO(String name, String userid, String pwd, String email, String mobile, int checkEmail, int checkMobile) {
		this.userid = userid;
		this.pwd = pwd;
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.checkEmail = checkEmail;
		this.checkMobile = checkMobile;
	}
	
	public MemberVO(String userid, String pwd, String name, String email, String mobile, String postcode, String address,
				    String detailaddress, int point) {
		super();
		this.userid = userid;
		this.pwd = pwd;
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.postcode = postcode;
		this.address = address;
		this.detailaddress = detailaddress;
		this.point = point;
		
	}

	

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDetailaddress() {
		return detailaddress;
	}

	public void setDetailaddress(String detailaddress) {
		this.detailaddress = detailaddress;
	}


	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getRegisterday() {
		return registerday;
	}

	public void setRegisterday(String registerday) {
		this.registerday = registerday;
	}

	public String getLastpwdchangedate() {
		return lastpwdchangedate;
	}

	public void setLastpwdchangedate(String lastpwdchangedate) {
		this.lastpwdchangedate = lastpwdchangedate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIdle() {
		return idle;
	}

	public void setIdle(int idle) {
		this.idle = idle;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public int getCheckEmail() {
		return checkEmail;
	}

	public void setCheckEmail(int checkEmail) {
		this.checkEmail = checkEmail;
	}

	public int getCheckMobile() {
		return checkMobile;
	}

	public void setCheckMobile(int checkMobile) {
		this.checkMobile = checkMobile;
	}

	public boolean isRequirePwdChange() {
		return requirePwdChange;
	}

	public void setRequirePwdChange(boolean requirePwdChange) {
		this.requirePwdChange = requirePwdChange;
	}
	
	public String getLastLogdate() {
		return lastLogdate;
	}


	public void setLastLogdate(String lastLogdate) {
		this.lastLogdate = lastLogdate;
	}
	
	
	
	
}
