<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="true" %>				
<script type="text/javascript">
$(document).ready(function() {
	$("#scheduler-link").click(function() {
		window.location='/admin/scheduler/list.html';
	});
	$("#scheduler-list-link").click(function() {
			window.location='/admin/scheduler/list.html';
	});
	$("#scheduler-create-link").click(function() {
			window.location='/admin/scheduler/create.html';
	});
	
});
</script>				

<div class="tabbable">

					<jsp:include page="/common/adminTabs.jsp" />
  					
  					 <div class="tab-content">

    					<div class="tab-pane active" id="customer-section">



								<div class="sm-ui-component">	
								
								
				<h3>
						<s:message code="label.scheduler.list" text="List of Schedulers" />
				</h3>	
				<br/>

				 <!-- Listing grid include -->
				 <c:set value="/admin/scheduler/paging.html" var="pagingUrl" scope="request"/>
				 <c:set value="/admin/scheduler/remove.html" var="removeUrl" scope="request"/>
				 <c:set value="/admin/scheduler/edit.html" var="editUrl" scope="request"/>
				 <c:set value="/admin/scheduler/list.html" var="afterRemoveUrl" scope="request"/>
				 <c:set var="entityId" value="id" scope="request"/>
				 <c:set var="componentTitleKey" value="menu.scheduler-list" scope="request"/>
				 <c:set var="gridHeader" value="/pages/admin/scheduler/scheduler-gridHeader.jsp" scope="request"/>
				 <c:set var="canRemoveEntry" value="true" scope="request"/>

            	 <jsp:include page="/pages/admin/components/list.jsp"></jsp:include> 
				 <!-- End listing grid include -->

    					</div>  


 					</div>


					</div>

		</div>		      			     