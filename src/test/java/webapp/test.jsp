<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %> 

<%@taglib uri=    "http://www.example.com/example/jsp" prefix="ex"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/foo" prefix="foo"		%>
<%@ 
taglib tagdir = "/WEB-INF/tags/test" prefix=
"test"		%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" 		prefix="fn"%>
<%@               taglib uri="http://jakarta.apache.org/taglibs/regexp-1.0" 
prefix="rx"%>
<%@taglib tagdir="/WEB-INF/tags/gsa" prefix="gsa-ext" %>

<c:forEach items="${current.box}" var="box" varStatus="status">

		<c:choose>
			<%-- Filter/Archive --%>
			<c:when test="${!empty var}">	
				<%-- <ex:getBar/>	--%>		 
				<ex:addBar bar="foo"/>
				<foo:getBar/>
<%-- 				<sc:displayTeaserIntranet/> --%>
				<test:testTag/>
				<test:testTag />
				<test:testTag2  a="b"  />
				<test:testTag3>dfsguidfhgi
				</test:testTag3>
				
			</c:when>
			 
			<%--
			<c:when test="${!empty var}">
				<c:set value="${teasers[0]}" var="teaser"/>
				<c:if test="${teaser.teaserImageType != 'none'}">
					<ex:getData var="data" />
				</c:if>
				
				<c:set var="displayOptions">${externalComponent.displayOptions}</c:set>
				<div class="module">
					<div class="teaser">
						<div class="boxhead">${externalComponent.title}</div>
						<ex:map 
							var="params"
						/>
						<ex:getInclude />
					</div>
				</div>
			</c:when>
			 --%>
		</c:choose>
</c:forEach> 