<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>	
{title:"<s:message code="label.scheduler.id" text="Id"/>", name:"id",canFilter:false},
{title:"<s:message code="label.scheduler.name" text="Name"/>", name:"name",canFilter:false},
{title:"<s:message code="label.scheduler.startDate" text="Start Date"/>", name:"startDate",canFilter:true},
{title:"<s:message code="label.scheduler.merchant" text="Store"/>", name: "merchant", align: "center",canFilter:false},
{title:"<s:message code="label.entity.details" text="Details"/>", name: "buttonField", align: "center",canFilter:false,canSort:false, canReorder:false}

