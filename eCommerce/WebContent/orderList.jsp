<%@page import="entity.Customer"%>
<%@page import="entity.CustomerOrder"%>
<%@page import="java.util.List"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	List<CustomerOrder> customerOrders = (List<CustomerOrder>) session.getAttribute("ctmOrders");
%>
<title>Order List</title>
<div id="container">
	<div id="container-inner">
		<div class="heading_bg">
			<h2>
				Customers' Orders
			</h2>
		</div>
		<div class="tab_content">
	             <table>
	             	<th>Confirmation Number</th>
	             	<th>Customer</th>
					<th>Delivery Address</th>
					<th>Amount</th>
					<th>Payment Method</th>
					<th>Date Created</th>
					<th>Order Status</th>
	           
	             	<c:forEach var="ctmOrder" items="<%=customerOrders%>" varStatus="iter">
		             	<tr>
		             		<td><a href="<c:url value='purchaseHistory?${ctmOrder.getOrderId()}'/>">
		             			${ctmOrder.getConfirmationNumber()}</a></td>
		             		<td>${ctmOrder.getCustomer().getName()}</td>
		             		<td>${ctmOrder.getDeliveryAddress()}</td>
		             		<td><fmt:formatNumber type="currency" currencySymbol="&dollar; " 
		             				value="${ctmOrder.getAmount()}" /></td>
		             		<td>${ctmOrder.getPaymentMethod()}</td>
		             		<td>${ctmOrder.getDateCreated()}</td>
		             		<td><form action="<c:url value='updateOrder?${ctmOrder.getOrderId()}'/>" method="post">
			             			<select name="orderState" >
										<option value="Ordered">Ordered</option>
										<option value="Preparing">Preparing</option>
										<option value="Processing">Processing</option>
										<option value="Completed">Completed</option>
										<option value="${ctmOrder.getOrderState()}" selected="selected">${ctmOrder.getOrderState()}</option>			
									</select>
									<input type="submit" value="Save">
								</form>
		             		</td>
		             	</tr>
	            	</c:forEach>
	             </table>
			</div>
			<div style="clear: both; height: 40px"></div>
	</div>
</div>