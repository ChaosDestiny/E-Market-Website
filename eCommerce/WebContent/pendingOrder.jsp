<%@page import="entity.Customer"%>
<%@page import="entity.CustomerOrder"%>
<%@page import="java.util.List"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.Date"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	Customer currUser = (Customer) session.getAttribute("user");
	List<CustomerOrder> customerOrders = currUser.getCustomerOrders();

%>
<title>Personal Orders</title>
<div id="container">
	<div class="container-inner">
		<div class="heading_bg">
			<h2>
				<%=currUser.getName()%>'s Orders
			</h2>
		</div>
<%--</div>
	<div class="two-third"> --%>
		<div class="tab_content">
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
			<div style="clear: both; height: 40px"></div>
	</div>
</div>