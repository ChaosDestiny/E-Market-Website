<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var='view' value='/register' scope='session'/>
<script src="js/jquery.validate.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#registrationForm").validate({
			rules : {
				name : "required",
				username : "required",
				password : {
					required : true,
					password : true
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
				creditcard : {
					required : true
					//creditcard : true
				}
			}
		});
	});
</script>

<div id="container">
		<div class="heading_bg">
			<h2>Register</h2>
		</div>
		<p>
			<strong>Fill in your registration form</strong>
		</p>
		<form id="registrationForm"  action="<c:url value='register'/>" method="post">
			<fieldset>
				<label>Name<span class="required">*</span></label> <input type="text" name="name" id="name" value="${param.name}" />
			</fieldset>
			<fieldset>
				<label>User name<span class="required">*</span></label> <input type="text" name="username" id="username" value="${param.username}" />
			</fieldset>
			<fieldset>
				<label>Password<span class="required">*</span></label> <input type="password" name="password" id="password" value="${param.password}" />
			</fieldset>
			<fieldset>
				<label>Email<span class="required">*</span></label> <input type="text" name="email" id="email" value="${param.email}" />
			</fieldset>
			<fieldset>
				<label>Phone <span class="required">*</span></label> <input type="text" name="phone" id="phone" value="${param.phone}" />
			</fieldset>
			<fieldset>
				<label>Address <span class="required">*</span></label> <input type="text" size="45" name="address" id="address" value="${param.address}" />
			</fieldset>
			<fieldset>
				<label>City <span class="required">*</span></label> <input type="text" size="45" name="cityRegion" id="cityRegion" value="${param.cityRegion}" />
			</fieldset>
			<fieldset>
				<label>Credit Card Number<span class="required">*</span></label> <input type="text" size="45" name="creditcard" id="creditcard" value="${param.creditcard}" />
			</fieldset>
			<fieldset>
				<input value="Submit register" class="button white" type="submit">
			</fieldset>
		</form>
			<% String userIsExist = (String) request.getSession().getAttribute("userExist");
			if (userIsExist == null) { %>
				<div style="color: red">User Name existed!</div>
	 		<% } %> 
	<div style="clear: both; height: 40px"></div>
</div>