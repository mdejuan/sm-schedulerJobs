
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>


<script
	src="<c:url value="/resources/js/jquery.alphanumeric.pack.js" />"></script>

<link
	href="<c:url value="/resources/css/bootstrap/css/datepicker.css" />"
	rel="stylesheet"></link>
<script
	src="<c:url value="/resources/js/bootstrap/bootstrap-datepicker.js" />"></script>


		
<div id="tabbable" class="tabbable">

	<jsp:include page="/common/adminTabs.jsp" />

	<div class="tab-content">

		<div class="tab-pane active" id="customer-section">



			<div class="sm-ui-component">


				<h3>
					<s:message code="menu.jobResult" text="Job Result" />
				</h3>


				<br />


				<form:form method="POST" commandName="jobResult">

					
				
					<div class="control-group">
						<label class="required"><s:message
								code="label.jobResult.schedulerId" text="Scheduler Id" /></label>
						<div class="controls">
							<form:input readonly="true" cssClass="input" path="scheduler.id" />
						</div>
					</div>
					
					<div class="control-group">
						<label class="required"><s:message
								code="label.jobResult.idJob" text="Job Id" /></label>
						<div class="controls">
							<form:input readonly="true" cssClass="input" path="scheduler.job" />
						</div>
					</div>
					
					<div class="control-group">
						<label class="required"><s:message
								code="label.jobResult.result" text="Result" /></label>
						<div class="controls">
							<form:input readonly="true" cssClass="input" path="result" />
						</div>
					</div>
					
					
					<div class="control-group">
						<label class="required"><s:message
								code="label.jobResult.merchant" text="Merchant" /></label>
						<div class="controls">
							<form:input readonly="true" cssClass="input" path="merchant.storename" />
						</div>
					</div>
					<div class="control-group">
						<label class="required"><s:message
								code="label.jobResult.description" text="Description Error" /></label>
						<div class="controls">
							<form:textarea class="span6" rows="20" path="description" />
						</div>
					</div>
					
				</form:form>


				<br />


			</div>


		</div>


	</div>

</div>
