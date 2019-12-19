<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- <!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirmation</title>
    </head>
    <body>--%>
		<div id="container">
		    <div class="one">
		        <div class="heading_bg">
		            <h2>Confirmation</h2>
		        </div>
		        <p id="confirmationText">
		            <strong>Success</strong>
					<br />
					<br />
					Confirmation Number: 
					<strong>${orderRecord.confirmationNumber}</strong>
					<br>
					Thanks for your purchase!
					<br>
		        </p>
		    </div>
		    <div class="two-third">
		        <div class="heading_bg">
		            <h3>Order Summary</h3>
		        </div>
		        <table>
		            <th>Product</th>
		            <th>Quantity</th>
		            <th>Price</th>
		            <c:forEach var="orderedProducts" items="${orderedProducts}"
		                           varStatus="iter">
		                <tr>
		                    <td>
		                    	${products[iter.index].name}
		                    </td>
		                    <td>
		                    	${orderedProducts.quantity}
		                    </td>
		                    <td>
		                    	<fmt:formatNumber type="currency"
									currencySymbol="&dollar; " value="${products[iter.index].price * orderedProducts.quantity}" /> 
		                    </td>
		                </tr>
		            </c:forEach>
		            <tr>
		                <td colspan="2"><strong>Surcharge:</strong></td>
		                <td>
		                	<fmt:formatNumber type="currency"
									currencySymbol="&dollar; " value="${initParam.deliveryFee}" />
		                </td>
		            </tr>
		            <tr>
		                <td colspan="2"><strong>Total :</strong></td>
		                <td> <fmt:formatNumber type="currency"
							currencySymbol="&dollar; " value="${orderRecord.amount}" />
						</td>
		            </tr>
		            <tr>
		                <td colspan="2"><strong>Date Process :</strong>
		                <td> ${orderRecord.dateCreated}	</td>        
		            </tr>
		        </table>
		    </div>
		    <div class="sidebar_right">
		        <div class="heading_bg">
		            <h3>Delivery Address </h3>
		        </div>
		        <table>
		            <tr>
		                <td colspan="3">
		                    ${customer.name}
		                    <br>
		                    ${customer.address}
		                    <br>
		                    City: ${customer.cityRegion}
		                    <br>
		                    <hr>
		                    <strong>Email :</strong>
		                    ${customer.email}
		                    <br>
		                    <strong>Phone :</strong>
		                    ${customer.phone}
		                </td>
		            </tr>
		        </table>
		    </div>
		    <div style="clear:both; height: 40px"></div>
		</div>
<%--    </body>
</html>--%> 
