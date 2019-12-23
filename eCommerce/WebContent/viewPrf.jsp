<%@page import="entity.Customer"%>
<%@page import="entity.CustomerOrder"%>
<%@page import="java.util.List"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.Date"%>
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

<%
	session.setAttribute("view", "/viewPrf");
	Customer viewUser = (Customer) session.getAttribute("viewUser");
	List<CustomerOrder> customerOrders = viewUser.getCustomerOrders();
	List<AddressBook> addressBooks = viewUser.getAddressBooks();
%>
<c:set var='view' value='/profile' scope='session'/>

<title>Profile</title>
<div id="container">
	<div id="container-inner">
		<div class="heading_bg">
			<h2>
				<%=viewUser.getName()%>'s wall
			</h2>
		</div>
<%--</div>
	<div class="two-third"> --%>
		<ul id="tabify_menu" class="menu_tab" style="margin: 0;">
			<li class="active"><a href="#fane1">Profile</a></li>
			<li><a href="#fane2">Orders</a></li>
			<li><a href="#fane3">Address Book</a></li>
		</ul>
		<div id="fane1" class="tab_content">
             <table>
             	<tr>
             		<td style="text-align:center"><i class="fa fa-user-circle-o"></i></td>
             		<td>User Name</td>
             		<td style="font-weight:bold"><%=viewUser.getUsername()%></td>
             	</tr>
             	<tr>
             		<td style="text-align:center"><i class="fa fa-user"></i></td>
             		<td>Full Name <font color="red">*</font></td>
             		<td><%=viewUser.getName()%></td>
             	</tr>
             	<tr>
             		<td style="text-align:center"><i class="fa fa-envelope"></i></td>
             		<td>Email <font color="red">*</font></td>
             		<td><%=viewUser.getEmail()%></td>
             	</tr>
             	<tr>
             	<tr>
             		<td style="text-align:center"><i class="fa fa-phone-square"></i></td>
             		<td>Phone Number <font color="red">*</font></td>
             		<td><%=viewUser.getPhone()%></td>
             	</tr>
             	<tr>
             		<td style="text-align:center"><i class="fa fa-address-card"></i></td>
             		<td>Address</td>
             		<td><%=viewUser.getAddress()%></td>
             	</tr>
             	<tr>
             		<td style="text-align:center"><i class="fa fa-institution"></i></td>
             		<td>City</td>
             		<td><%=viewUser.getCityRegion()%></td>
             	</tr>
             	<tr>
             		<td style="text-align:center"><i class="fa fa-cc-mastercard"></i></td>
             		<td>Credit Card</td>
             		<td><%=viewUser.getCcNumber()%></td>
             	</tr>
             	<tr>
             		<td style="text-align:center"><i class="fa fa-credit-card-alt"></i></td>
             		<td> Bank Card</td>
             		<td><%=viewUser.getAcNumber()%></td>
             	</tr>
             </table>
		</div>
		<div id="fane2" class="tab_content">
			 <table>
             	<th>Confirmation Number</th>
				<th>Delivery Address</th>
				<th>Amount</th>
				<th>Payment Method</th>
				<th>Date Created</th>
				<th>Order Status</th>
				<c:forEach var="ctmOrder" items="<%=customerOrders %>" varStatus="iter">
	             	<tr>
	             		<td><a href="<c:url value='purchaseHistory?${ctmOrder.getOrderId()}'/>">
	             			${ctmOrder.getConfirmationNumber()}</a></td>
	             		<td>${ctmOrder.getDeliveryAddress()}</td>
	             		<td><fmt:formatNumber type="currency" currencySymbol="&dollar; " 
	             			value="${ctmOrder.getAmount()}" /></td>
	             		<td>${ctmOrder.getPaymentMethod()}</td>
	             		<td>${ctmOrder.getDateCreated()}</td>
	             		<td>${ctmOrder.getOrderState()}</td>
	             	</tr>
            	</c:forEach>
             </table>
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
	             		<td></td>
					</tr>
            	</c:forEach>
             </table>
		</div>
	</div>
	<div style="clear: both; height: 40px"></div>
</div>