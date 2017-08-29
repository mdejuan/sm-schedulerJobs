
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


<script type="text/javascript">

	$(document).ready(function() {
		
		$('#sortOrder').numeric();

		if ('${scheduler.id}' == '') {
			
			$('#merchant').find('option:first').attr('selected', 'selected');
			$('#schedulerForm :input').not(':button,:hidden').each(function() {
				$(this).val('');
			});
			$('#btnSubmit').attr('disabled', true);$('#btnSubmit').attr('disabled', true);
			
		} else {
			
					
    	}



		$("#schedulerForm :input").on("change keyup paste", function() {
			isValid();
		});
		

		function isValid() {
			if (isFValid()) {
				$('#btnSubmit').attr('disabled', false);
				return true;
			} else {
				$('#btnSubmit').attr('disabled', true);
				return false;
			}
		}
		
		$('#active').change(function() {
			if ($(this).is(":checked")) {
				$('#active').val('true');	
				
			} else {
				$('#active').val('false');	
			}

		});

		function isFValid() {
			var fieldValid;
			$("form#schedulerForm :input").each(function() {
				if ($(this).hasClass('highlight')) {

					console.log($(this).attr('id') + ' - ' + $(this).css('display'));

					fieldValid = isFieldValid($(this));
					if (fieldValid) {
						return true;
					} else {
						return false;
					}
				}
			});

			return fieldValid;
		}
		function isFieldValid(field) {
			if (field.is(":hidden")) {
				return true;
			}
			var value = field.val();
			if (!emptyString(value)) {
				field.css('background-color', '#FFF');
				return true;
			} else {
				field.css('background-color', '#FFC');
				return false;
			}
		}

		function emptyString($value) {
			return !$value || !/[^\s]+/.test($value);
		}




	});
</script>

<div id="tabbable" class="tabbable">

	<jsp:include page="/common/adminTabs.jsp" />

	<div class="tab-content">

		<div class="tab-pane active" id="customer-section">



			<div class="sm-ui-component">


				<h3>
					<s:message code="menu.scheduler" text="Scheduler management" />
				</h3>


				<h3>
					<c:choose>
						<c:when test="${scheduler.id!=null && scheduler.id>0}">
							<s:message code="label.scheduler.edit" text="Edit scheduler" />
						</c:when>
						<c:otherwise>
							<s:message code="label.scheduler.create" text="Create scheduler" />
						</c:otherwise>
					</c:choose>

				</h3>
				<br />

				<c:url var="schedulerSave" value="/admin/scheduler/save.html" />


				<form:form method="POST" commandName="scheduler"
					action="${schedulerSave}" id="schedulerForm">


					<form:errors path="*" cssClass="alert alert-error" element="div" />
					<div id="store.success" class="alert alert-success"
						style="<c:choose><c:when test="${success!=null}">display:block;</c:when><c:otherwise>display:none;</c:otherwise></c:choose>">
						<s:message code="message.success" text="Request successfull" />
					</div>


					<form:hidden path="id" id="idScheduler" />

					<div class="control-group">
						<label class="required"><s:message
								code="label.scheduler.name" text="Name" /></label>
						<div class="controls">
							<form:input cssClass="input-large highlight" path="name" />
							<span class="help-inline"><form:errors path="name"
									cssClass="error" /></span>

						</div>
					</div>
					<div class="control-group">
						<label class="required"><s:message
								code="label.scheduler.description" text="Description" /></label>
						<div class="controls">
							<form:input cssClass="input-large highlight" path="description" />
							<span class="help-inline"><form:errors path="description"
									cssClass="error" /></span>

						</div>
					</div>
					<div class="control-group">
						<label class="required"><s:message
								code="label.scheduler.type" text="Type" /></label>
						<div class="controls">
							<div class="controls">
								<form:select path="type" >
									<form:options items="${schedulerTypes}" />
								</form:select>
								<span class="help-inline"><form:errors
										path="type" cssClass="error" /></span>

							</div>

						</div>
					</div>
					<div class="control-group">
						<label class="required"><s:message
								code="label.scheduler.id" text="ID" /></label>
						<div class="controls">
							<form:input cssClass="input-large highlight" path="job" />
							<span class="help-inline"><form:errors path="job"
									cssClass="error" /></span>

						</div>
					</div>
					<div class="control-group">
						<label class="required"><s:message
								code="label.customer.optin.merchant" text="Store Name" /></label>
						<div class="controls">

							<form:select cssClass="highlight" path="merchant.id"
								id="merchant">
								<form:option value="" label="*** Select Option ***" />
								<form:options items="${merchants}" itemValue="id"
									itemLabel="storename" />
							</form:select>
							<span class="help-inline"><form:errors
									path="merchant.code" cssClass="error" /></span>


						</div>
					</div>


					<div class="control-group">
						<label class="required"><s:message
								code="label.scheduler.startDate" text="Start Date" /></label>
						<div class="controls">

							<form:input cssClass="input-large" readonly="true"
								path="startDate" class="small" type="date"
								data-date-format="<%=com.salesmanager.core.business.constants.Constants.DEFAULT_DATE_FORMAT%>" />
							<script type="text/javascript">
									$('#startDate').datepicker();
								</script>
							<span class="help-inline"><form:errors path="startDate"
									cssClass="error" /></span>

						</div>
					</div>

					<%-- <div class="control-group">
						<label class="required"><s:message
								code="label.scheduler.repeat" text="Every" /></label>
						<div class="controls">

							<form:select cssClass="highlight" path="repeat" id="repeat">
								<form:option value="" label="*** Select Option ***" />
								<form:options items="${frequency}" />
							</form:select>
							<span class="help-inline"><form:errors path="repeat"
									cssClass="error" /></span>


						</div>
					</div> --%>

					<div class="control-group">
						<label><s:message code="label.scheduler.active"
								text="Is active?" /></label>
						<div class="controls">
							<form:checkbox path="active" id="active" />
						</div>
					</div>



					<div class="form-actions">

						<div class="pull-right">

							<button type="submit" class="btn btn-success" id="btnSubmit">
								<s:message code="button.label.submit" text="Submit" />
							</button>


						</div>

					</div>

				</form:form>


				<br />


			</div>


		</div>


	</div>

</div>
