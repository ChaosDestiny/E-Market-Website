<head>

<link rel="icon" type="image/png" href="images/icons/favicon.ico" />
<link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
<link rel="stylesheet" type="text/css" href="css/util.css">
<link rel="stylesheet" type="text/css" href="css/main.css">

</head>
<title>Login</title>
<div class="limiter">
	<div class="container-login100">
		<div class="wrap-login100">
			<div class="login100-form-title"
				style="background-image: url(img/login.jpg);">
				<span class="login100-form-title-1"> SIGN IN </span>
			</div>
			<form class="login100-form validate-form" action="<c:url value='login'/>" method="post">
				<div class="wrap-input100 validate-input m-b-26"
					data-validate="Username is required">
					<span class="label-input100">Username</span> <input
						class="input100" type="text" name="username"
						placeholder="Enter username"> <span
						class="focus-input100"></span>
				</div>
				<div class="wrap-input100 validate-input m-b-18"
					data-validate="Password is required">
					<span class="label-input100">Password</span> <input
						class="input100" type="password" name="password"
						placeholder="Enter password"> <span
						class="focus-input100"></span>
				</div>
				<%
					String admin = (String) request.getSession().getAttribute("admin");
					String customer = (String) request.getSession().getAttribute("customer");
					String errorA = (String) request.getSession().getAttribute("errorAccount");
					String errorP = (String) request.getSession().getAttribute("errorPass");
					if (admin == null && customer == null){ 
						if (errorA != null) {	%>
							<font color=red>User Name not exist !</font>
					<% 	} else if (errorP != null) {%>
							<font color=red>Wrong Password !</font>
					<%	}
					} %>
				<div class="flex-sb-m w-full p-b-30">
					<div class="contact100-form-checkbox">
						<input class="input-checkbox100" id="ckb1" type="checkbox"
							name="remember-me"> <label class="label-checkbox100"
							for="ckb1"> Remember me </label>
					</div>
					<div>
						<a href="#" class="txt1"> Forgot Password? </a>
					</div>
				</div>
				<div class="container-login100-form-btn">
					<button class="login100-form-btn">Login</button>
				</div>
			</form>
		</div>
	</div>
</div>