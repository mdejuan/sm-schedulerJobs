<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>	
{title:"<s:message code="label.jobResult.id" text="Id"/>", name:"id",canFilter:false},
{title:"<s:message code="label.jobResult.schedulerId" text="Scheduler Id"/>", name:"scheduler.id",canFilter:false},
{title:"<s:message code="label.jobResult.idJob" text="Id Job"/>", name:"scheduler.job",canFilter:false},
{title:"<s:message code="label.jobResult.result" text="Result"/>", name:"result",canFilter:true},
{title:"<s:message code="label.jobResult.date" text="Date"/>", name:"date",canFilter:true},
{title:"<s:message code="label.jobResult.merchant" text="Store"/>", name: "merchant", align: "center",canFilter:false},
{title:"<s:message code="label.entity.details" text="Details"/>", name: "buttonField", align: "center",canFilter:false,canSort:false, canReorder:false}

