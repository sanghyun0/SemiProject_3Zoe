<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
    String ctxPath = request.getContextPath();
    //    /MyMVC
%>   
 
<style type="text/css">

	input.chkboxCos{
		zoom: 1.5; 
		border-radius:10px;
		border:solid 1px lightgray;
		margin-right:5px;
	}
	
	div.item3{

		border-bottom:solid 1px gray;
		 height:70%; 
		 margin-top:10px; 
		 display: flex; 
		 justify-content: center; 
		 align-items: center;
	}
	

	
</style>
  
<jsp:include page="header.jsp"/>


<div class="main1">
  	
  	<div class="content4">
  		
  		<div>
  			<ul class="navbar-nav" style="width: 100%; display: flex; justify-content: space-between;">

		
			    <li class="nav-item " style="display: flex; align-items: center; ">
			      	<span><b>종료된 과정</b></span>
			      	
			 
			      	<input type="checkbox" id="chk1" class="chkboxCos"  style=" margin-left:30px; "></input> <label for="chk1" style="font-size:11pt; position:relative; top:3px;">정기구독 과정만 보기</label>
		

			      	<select style=" height:50px; color:gray; text-align:center; margin-left:1060px; width:150px; height: 45px; font-size:13pt;">
							<option>최근 신청순</option>
							<option>오래된 신청순</option>
							<option >과정명 오름차순</option>
							<option >과정명 내림차순</option>
							<option>시작일 오름차순</option>
							<option>시작일 내림차순</option>
							<option>종료일 오름차순</option>
							<option>종료일 내림차순</option>
		
					</select> 
  
			    </li>
			  </ul>
			<hr style="border:solid 0.5px black; margin-top:20px;">
	  
	  		<div class="item3">종료된 과정이 없습니다.</div>
	  
  		</div>

  	</div>
  
</div>
	
	
	
<script type="text/javascript">
/* Loop through all dropdown buttons to toggle between hiding and showing its dropdown content - This allows the user to have multiple dropdowns without any conflict */
var dropdown = document.getElementsByClassName("dropdown-btn");
var i;

for (i = 0; i < dropdown.length; i++) {
  dropdown[i].addEventListener("click", function() {
    this.classList.toggle("active");
    var dropdownContent = this.nextElementSibling;
    if (dropdownContent.style.display === "block") {
      dropdownContent.style.display = "none";
    } else {
      dropdownContent.style.display = "block";
    }
  });
}
</script>
	
</body>
</html>