<%@page import="entity.Customer"%>
<%@page import="entity.CustomerOrder"%>
<%@page import="java.util.List"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<title>Customer List</title>
<div id="container">
	<div class="container-inner">
		<div class="heading_bg">
			<h2>
				Customers List
			</h2>
		</div>
		<div class="tab_content">
	             <table>
	             	<th>Username</th>
	             	<th>Name</th>
					<th>Email</th>
	             	<c:forEach var="customer" items="${customerList}" varStatus="iter">
		             	<tr>
		             		<td><a href="<c:url value='viewPrf?${customer.getUsername()}'/>">
		             			${customer.getUsername()}</a></td>
		             		<td>${customer.getName()}</td>
		             		<td>${customer.getEmail()}</td>
		             	</tr>
	            	</c:forEach>
	             </table>
			</div>
			<div style="clear: both; height: 40px"></div>
	</div>
</div>