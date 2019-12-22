<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<title>Sign Up</title>

<head>

<link rel="icon" type="image/png" href="images/icons/favicon.ico" />
<link rel="stylesheet" type="text/css" href="vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
<link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">
<link rel="stylesheet" type="text/css" href="vendor/css-hamburgers/hamburgers.min.css">
<link rel="stylesheet" type="text/css" href="vendor/animsition/css/animsition.min.css">
<link rel="stylesheet" type="text/css" href="vendor/select2/select2.min.css">
<link rel="stylesheet" type="text/css" href="vendor/daterangepicker/daterangepicker.css">
<link rel="stylesheet" type="text/css" href="css/util.css">
<link rel="stylesheet" type="text/css" href="css/main.css">

</head>

<c:set var='view' value='/signUp' scope='session'/>
<script src="js/jquery.validate.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#signUpForm").validate({
			rules : {
				name : "required",
				username : "required",
				password : {
					required : true,
					password : true,
				},
				email : {
					required : true,
					email : true
				},
				phone : {
					required : true,
					number : true,
					minlength : 9
				},
				address : {
					required : true
				},
				cityRegion : {
					required : true
				},
				creditcard : {
					//required : true
					//creditcard : true
				}
			}
		});
	});
</script>

<body>
	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100">
				<div class="login100-form-title"
					style="background-image: url(img/login.jpg);">
					<span class="login100-form-title-1"> SIGN UP </span>
				</div>
				<form id="signUpForm" class="login100-form validate-form" action="<c:url value='signUp'/>" method="post">

					<div class="wrap-input100 validate-input m-b-26"
						data-validate="Username is required">
						<span class="label-input100">Username<span class="required">*</span></span> <input
							class="input100" type="text" name="username" id="username"
							placeholder="Enter username"> <span
							class="focus-input100"></span>
					</div>
					<% String userIsExist = (String) request.getSession().getAttribute("userExist");
					if (userIsExist == "exist") { %>
						<div style="color: red">User Name existed!</div>
			 		<% } %> 

			 		<div class="wrap-input100 validate-input m-b-18"
						data-validate="Password is required">
						<span class="label-input100">Password<span class="required">*</span></span> <input
							class="input100" type="password" name="password" id="password"
							placeholder="Enter password"> <span
							class="focus-input100"></span>
					</div>					
					<div class="wrap-input100 validate-input m-b-18"
						data-validate="Name is required">
						<span class="label-input100">Name<span class="required">*</span></span> <input
							class="input100" type="text" name="name" id="name"
							placeholder="Enter your name"> <span
							class="focus-input100"></span>
					</div>
					<div class="wrap-input100 validate-input m-b-18"
						data-validate="Email is required">
						<span class="label-input100">Email<span class="required">*</span></span> <input
							class="input100" type="text" name="email" id="email"
							placeholder="Enter email address"> <span
							class="focus-input100"></span>
					</div>
					<div class="wrap-input100 validate-input m-b-18"
						data-validate="Phone Number is required">
						<span class="label-input100">Phone Number<span class="required">*</span></span> <input
							class="input100" type="text" name="phone" id="phone"
							placeholder="Enter your phone number"> <span
							class="focus-input100"></span>
					</div>
					<div class="wrap-input100 validate-input m-b-18"
						data-validate="Address is required">
						<span class="label-input100">Address<span class="required">*</span></span> <input
							class="input100" type="text" name="address" id="address"
							placeholder="Enter your address"> <span
							class="focus-input100"></span>
					</div>
					<div class="wrap-input100 validate-input m-b-18"
						data-validate="City is required">
						<span class="label-input100">City<span class="required">*</span></span> <input
							class="input100" type="text" name="cityRegion" id="cityRegion"
							placeholder="Enter your city"> <span
							class="focus-input100"></span>
					</div>
					<div class="wrap-input100 validate-input m-b-18"
						data-validate="Credit Card is required">
						<span class="label-input100">Credit Card</span> <input
							class="input100" type="text" name="creditcard" id="creditcard"
							placeholder="Enter your credit card number"> <span
							class="focus-input100"></span>
					</div>
					<div class="wrap-input100 validate-input m-b-18"
						data-validate="Credit Card is required">
						<span class="label-input100">Bank Card</span> <input
							class="input100" type="text" name="atmcard" id="creditcard"
							placeholder="Enter your ATM card number"> <span
							class="focus-input100"></span>
					</div>
					<fieldset>
					<div class="container-login100-form-btn">
						<button class="login100-form-btn">Sign Up</button>
					</div>
					</fieldset>
					
				</form>				
			</div>
		</div>
	</div>