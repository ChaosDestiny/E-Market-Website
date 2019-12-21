<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var='view' value='/checkout' scope='session' />
<script src="js/jquery.validate.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#checkoutForm").validate({
			rules : {
				deliAdd : {
					required : true
				},
				pmMethod : {
					required : true
				},
				creditcard : {
					required : true
					//creditcard : true
				},
				atmcard : {
					
				}
			}
		});
	});
</script>
<title>Checkout</title>
<div id="container">
	<div class="one-half">
		<div class="heading_bg">
			<h2>Checkout</h2>
		</div>
		<p>
			<strong>In order to purchase the items in your shopping cart, please provide us with the following information:</strong>
		</p>
		<c:if test="${!empty orderFailureFlag}">
			<p style="color: #c00; font-style: italic">We were unable to process your order. Please try again!</p>
		</c:if>
		<form id="checkoutForm" action="<c:url value='purchase' />" method="post">
			<fieldset>
				<label>Delivery Address <span class="required">*</span></label> <input type="text" size="45" name="deliveryAddress" id="deliAdd" value="${param.address}" />
			</fieldset>
			<fieldset>
				<label>Credit Card Number <span class="required">*</span></label> <input type="text" size="45" name="creditcard" id="creditcard" value="${param.creditcard}" />
			</fieldset>
			<fieldset>
				<label>ATM Card Number <span class="required">*</span></label> <input type="text" size="45" name="atmcard" id="atmcard" value="${param.atmcard}" />
			</fieldset>
			<fieldset>
				<label>Payment Method <span class="required">*</span></label> 
				<select name="paymentMethod" id="pmMethod">
							<option value="Credit Cards">Credit Cards</option>
							<option value="Mobile Payments">Mobile Payments</option>
							<option value="Bank Transfers">Bank Transfers</option>
							<option value="Cash">Cash</option>			
				</select>
			</fieldset>
			<fieldset>
				<input value="Purchase" class="button white" type="submit">
			</fieldset>
		</form>
	</div>
	<div class="one-half last">
		<div class="heading_bg">
			<h2>Order Information</h2>
		</div>
		<p>
			<strong>Next-working day delivery is guaranteed</strong>
		</p>
		<p>
			<strong> A <fmt:formatNumber type="currency" currencySymbol="&dollar; " value="${initParam.deliveryFee}" /> delivery surcharge is applied to all purchase orders
			</strong>
		</p>
		<table>
			<th>Total</th>
			<th>Delivery Surcharge</th>
			<th>Credit Total</th>
			<tr>
				<td><fmt:formatNumber type="currency" currencySymbol="&dollar; " value="${cart.subtotal}" /></td>
				<td><fmt:formatNumber type="currency" currencySymbol="&dollar; " value="${initParam.deliveryFee}" /></td>
				<td><fmt:formatNumber type="currency" currencySymbol="&dollar; " value="${cart.total}" /></td>
			</tr>
		</table>
	</div>
	<div style="clear: both; height: 40px"></div>
</div>