<%@page import="entity.Customer"%>
<%@page import="entity.CustomerOrder"%>
<%@page import="java.util.List"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.Date"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<head>

<link rel="icon" type="image/png" href="images/icons/favicon.ico" />
<link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="fonts/Linearicons-Free-v1.0.0/icon-font.min.css">

</head>

<%
	session.setAttribute("view", "/viewPrf");
	Customer viewUser = (Customer) session.getAttribute("viewUser");
	List<CustomerOrder> customerOrders = viewUser.getCustomerOrders();
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
			<li><a href="#fane1">Profile</a></li>
			<li><a href="#fane2">Orders</a></li>
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
	</div>
	<div style="clear: both; height: 40px"></div>
</div>