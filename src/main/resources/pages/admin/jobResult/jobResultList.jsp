<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="true" %>				
<script type="text/javascript">
$(document).ready(function() {
	$("#jobResult-link").click(function() {
		window.location='/admin/jobResult/list.html';
	});
	$("#jobResult-list-link").click(function() {
			window.location='/admin/jobResult/list.html';
	});
	$("#jobResult-create-link").click(function() {
			window.location='/admin/jobResult/create.html';
	});
	
});
</script>				

<div class="tabbable">

					<jsp:include page="/common/adminTabs.jsp" />
  					
  					 <div class="tab-content">

    					<div class="tab-pane active" id="customer-section">



								<div class="sm-ui-component">	
								
								
				<h3>
						<s:message code="label.jobResult.list" text="List of Jobs" />
				</h3>	
				<br/>

				 <!-- Listing grid include -->
				 <c:set value="/admin/jobResult/paging.html" var="pagingUrl" scope="request"/>
				 <c:set value="/admin/jobResult/remove.html" var="removeUrl" scope="request"/>
				 <c:set value="/admin/jobResult/edit.html" var="editUrl" scope="request"/>
				 <c:set value="/admin/jobResult/list.html" var="afterRemoveUrl" scope="request"/>
				 <c:set var="entityId" value="id" scope="request"/>
				 <c:set var="componentTitleKey" value="menu.jobResult-list" scope="request"/>
				 <c:set var="gridHeader" value="/pages/admin/jobResult/jobResult-gridHeader.jsp" scope="request"/>
				 <c:set var="canRemoveEntry" value="true" scope="request"/>

            	 <jsp:include page="/pages/admin/components/list.jsp"></jsp:include> 
				 <!-- End listing grid include -->

    					</div>  


 					</div>


					</div>

		</div>		      			     