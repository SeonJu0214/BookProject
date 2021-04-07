<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LOGIN MODAL</title>
	<style type="text/css">
		#loginMenu a {
			color: #4B4B4B;
			text-decoration: none;
		}
	</style>
</head>
<body>
	<!-- 로그인 모달 START -->
	<div class="modal fade" id="loginModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
          			<button type="button" class="close" data-dismiss="modal" id="loginModalClose">&times;</button>
        		</div>

				<!-- Modal body -->
				<div class="modal-body">
					<div class="text-center">
						<br>
						<h4 id="title">LOGIN</h4>
						<br>
					<hr>
					
					<!-- 로그인 입력 창 START -->
					<div class="form-group">
						<!-- ID -->
						<input type="text" class="form-control" id="mem_id"
							placeholder="ID" name="mem_id" autocomplete="off">
						<div id="idCheckMsg" class="msg"></div>
					</div>
					<div class="form-group">
						<!-- 비밀번호 -->
						<input type="password" class="form-control" id="mem_pw"
							placeholder="PASSWORD" name="mem_pw" onkeyup="enterkey();"
							autocomplete="off">
						<div id="pwdCheckMsg" class="msg"></div>
					</div>
					<!-- 로그인 입력 창 END -->
					
					<!-- 로그인 버튼 -->
					<button type="submit" class="btn btn-secondary btn-lg"
						id="loginBtn">LOGIN</button>
					<div id="loginCheckMsg" class="msg"></div>
					<br>
						
					<!-- 로그인 모달 창 메뉴 START --> 
					<span class="text_bottom" id="loginMenu">
						<a href="${root }join/agreement" target="_self">회원가입</a>
						<a>&nbsp;│&nbsp;</a>
						<a href="${root }login/findIDPwd">ID · 비밀번호 찾기</a>
					</span>
					<!-- 로그인 모달 창 메뉴 END -->
					</div>
					<hr>
				</div>

			</div>
		</div>
	</div>
	<!-- 로그인 모달 END -->
</body>
</html>