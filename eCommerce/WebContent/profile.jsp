<%@page import="entity.Customer"%>
<%@page import="entity.CustomerOrder"%>
<%@page import="java.util.List"%>
<%@page import="entity.AddressBook"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<head>

<link rel="icon" type="image/png" href="images/icons/favicon.ico" />
<link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
<link rel="stylesheet" type="text/css" media="screen" href="css/style.css">
<link rel="stylesheet" type="text/css" media="screen" href="menu/css/simple_menu.css">
<link rel="stylesheet" href="css/nivo-slider.css" type="text/css" media="screen">
<link rel="stylesheet" type="text/css" href="boxes/css/style6.css">

</head>

<%
	session.setAttribute("view", "/profile");
	Customer currUser = (Customer) session.getAttribute("user");
	List<AddressBook> addressBooks = currUser.getAddressBooks();
%>
<c:set var='view' value='/profile' scope='session'/>
<!-- JS Files -->
<script src="js/jquery.min.js"></script>
<script src="js/custom.js"></script>
<script src="js/slides/slides.min.jquery.js"></script>
<script src="js/cycle-slider/cycle.js"></script>
<script src="js/nivo-slider/jquery.nivo.slider.js"></script>
<script src="js/tabify/jquery.tabify.js"></script>
<script src="js/prettyPhoto/jquery.prettyPhoto.js"></script>
<script src="js/twitter/jquery.tweet.js"></script>
<script src="js/scrolltop/scrolltopcontrol.js"></script>
<script src="js/portfolio/filterable.js"></script>
<script src="js/modernizr/modernizr-2.0.3.js"></script>
<script src="js/easing/jquery.easing.1.3.js"></script>
<script src="js/kwicks/jquery.kwicks-1.5.1.pack.js"></script>
<script src="js/swfobject/swfobject.js"></script>
<script src="js/jquery.validate.js" type="text/javascript"></script>
<script src="js/tabify/jquery.tabify.js" type="text/javascript"></script>
<!-- FancyBox -->
<link rel="stylesheet" type="text/css" href="js/fancybox/jquery.fancybox.css" media="all">
<script src="js/fancybox/jquery.fancybox-1.2.1.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$("#profileForm").validate({
			rules : {
				name : {
					required : true
				},
				email : {
					required : true
				},
				phone : {
					required : true
				}
			}
		});
		
		$("#passForm").validate({
			rules : {
				curPass : {
					required : true
				},
				newPass : {
					required : true
				},
				checkPass : {
					required : true
				}
			}
		});
		
		$("#addrForm").validate({
			rules : {
				address : "required",
				city : "required",
				phone : "required"
			}
		});
	});
</script>
<title>Profile</title>
<div id="container">
	<div id="container-inner">
		<div class="heading_bg">
			<h2>
				<%=currUser.getName()%>'s wall
			</h2>
		</div>
<%--</div>
	<div class="two-third"> --%>
		<ul id="tabify_menu" class="menu_tab" style="margin: 0;">
			<li class="active"><a href="#fane1">Profile</a></li>
			<li><a href="#fane2">Password</a></li>
			<li><a href="#fane3">Address Book</a></li>
		</ul>
		<div id="fane1" class="tab_content" >
		 <form id="profileForm" action="<c:url value='editProfile'/>" method="post">
             <table>
             	<tr>
             		<td style="text-align:center"><i class="fa fa-user-circle-o"></i></td>
             		<td>User Name</td>
             		<td style="font-weight:bold"><%=currUser.getUsername()%></td>
             	</tr>
             	<tr>
             		<td style="text-align:center"><i class="fa fa-user"></i></td>
             		<td>Full Name <font color="red">*</font></td>
             		<td><input id="name" type="text" size = "80" name="name" 
             		value="<%=currUser.getName()%>"/></td>
             	</tr>
             	<tr>
             		<td style="text-align:center"><i class="fa fa-envelope"></i></td>
             		<td>Email <font color="red">*</font></td>
             		<td><input id="email" type="text" size = "80" name="email" 
             		value="<%=currUser.getEmail()%>"/></td>
             	</tr>
             	<tr>
             	<tr>
             		<td style="text-align:center"><i class="fa fa-phone-square"></i></td>
             		<td>Phone Number <font color="red">*</font></td>
             		<td><input id="phone" type="text" size = "80" name="phone" 
             		value="<%=currUser.getPhone()%>"/></td>
             	</tr>
             	<tr>
             		<td style="text-align:center"><i class="fa fa-address-card"></i></td>
             		<td>Address</td>
             		<td><input type="text" size = "80" name="address" 
             		value="<%=currUser.getAddress()%>"/></td>
             	</tr>
             	<tr>
             		<td style="text-align:center"><i class="fa fa-institution"></i></td>
             		<td>City</td>
             		<td><input type="text" size = "80" name="cityRegion" 
             		value="<%=currUser.getCityRegion()%>"/></td>
             	</tr>
             	<tr>
             		<td style="text-align:center"><i class="fa fa-cc-mastercard"></i></td>
             		<td>Credit Card</td>
             		<td><input type="text" size = "80" name="creditcard" 
             		value="<%=currUser.getCcNumber()%>"/></td>
             	</tr>
             	<tr>
             		<td style="text-align:center"><i class="fa fa-credit-card-alt"></i></td>
             		<td> Bank Card</td>
             		<td><input type="text" size = "80" name="atmcard" 
             		value="<%=currUser.getAcNumber()%>"/></td>
             	</tr>
             </table>
                 
                 <!-- Controls -->
                <fieldset>      
                        <input id="submit" class="button" name="submitSave" type="submit" style="float:right" value="Save" />
             </fieldset>
         </form>
		</div>

		<div id="fane2" class="tab_content">
		 <form id="passForm" action="<c:url value='changePass'/>" method="post">
             <table>
             	<tr>
             		<td>Current Password <span class="required">*</span></td>
             		<td><input type="password" size = "65" name="curPass"/></td>
             	</tr>
             	<tr>
             		<td>New Password <span class="required">*</span></td>
             		<td><input type="password" size = "65" name="newPass"/></td>
             	</tr>
             	<tr>
             		<td>Confirm Password <span class="required">*</span></td>
             		<td><input type="password" size = "65" name="checkPass"/></td>
             	</tr>
             </table>
                <% String errorPass = (String) request.getSession().getAttribute("errPass");
				if (errorPass == "curPass") { %>
					<div style="color: red">Wrong current password</div>
		 		<% } else if (errorPass == "newPass") {%>
		 		 	<div style="color: red">Wrong confirm password</div>
		 		<% } else if (errorPass == "success"){%>
		 			<div style="color: green">Success!</div>
		 		<% } %>
                 <!-- Controls -->
                <fieldset>      
                        <input id="submit" class="button" name="submitSave" type="submit" style="float:right" value="Save" />
             </fieldset>
         </form>
		</div>
		<div id="fane3" class="tab_content" >
             <table>
             	<th>Address</th>
				<th>City</th>
				<th>Phone</th>
				<th></th>
				<c:forEach var="adrBook" items="<%=addressBooks %>" varStatus="iter">
					<tr>
	             		<td>${adrBook.getAddress()}</td>
	             		<td>${adrBook.getCity()}</td>
	             		<td>${adrBook.getPhone()}</td>
	             		<td><a href="<c:url value='setDefault?${adrBook.getAddressId()}'/>" class="button">Set as default</a>
						<a href="<c:url value='delAddress?${adrBook.getAddressId()}'/>" class="button">Delete</a></td>
					</tr>
            	</c:forEach>
            		<tr>
					<form id="addrForm" action="<c:url value='addAddress'/>" method="post">
						<fieldset>
		             		<td><input id="address" type="text" name="address" /></td>
		             		<td><input id="city" type="text" name="city" /></td>
		             		<td><input id="phone" type="text" name="phone" /></td>
		             		<td><input id="submit" class="button" type="submit" value="Add new address" /></td>
						</fieldset>
					</form>
					</tr>
             </table>
         
		</div>
		<div style="clear:both"></div>
	</div>
	<div style="clear: both; height: 40px"></div>
</div>