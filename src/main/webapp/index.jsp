<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>XMLParser</title>
<style>
	table, th, td {
	border:1px solid black;
	}
</style>
</head>
<body>
	<h1>Parser for Candies from .xml</h1>
	<form action="Controller" method="post" enctype="multipart/form-data">
		Select file<br> <input type="file" name="file" />
		<input id="command" type="hidden" name="command" value="see-script" /> 
		<input	type="submit" name="validate" value="validate" onclick="javascript:setValidationCommand()"/>
		<c:out value="${message}"/><br>
		<input type="radio" name="parser" value="DOM" checked>DOM<br>
		<input	type="radio" name="parser" value="SAX">SAX<br> 
		<input	type="radio" name="parser" value="StAX">StAX<br> 
		<input	type="submit" name="parse" value="parse" onclick="javascript:setParseCommand()"/><br>
	</form>
	<script type="text/javascript">
		function setValidationCommand() {
			document.getElementById("command").value = "validation";
		}
		function setParseCommand() {
			document.getElementById("command").value = "parsing";
		}
	</script>
	<c:if test="${!empty candies}">
	<h1>Result of parsing</h1>
		<table style="width:100%">
			<tr>
				<th rowspan="2">ID</th>
				<th rowspan="2">Name</th>
				<th rowspan="2">Energy</th>
				<th rowspan="2">Producer</th>
				<th rowspan="2">ProductionDateTime</th>
				<th rowspan="2">Ingredients</th>
				<th colspan="3">Nutritional value</th>
				<th rowspan="2">Glaze</th>
				<th rowspan="2">Filling</th>
				<th rowspan="2">Pack</th>
			</tr>
			<tr>
				<th>Fats</th>
				<th>Protains</th>
				<th>Carbohydrates</th>
			</tr>
			<c:forEach var="candy" items="${candies }">
				<tr>
					<td><c:out value="${candy.getId()}"/></td>
					<td><c:out value="${candy.getName()}"/></td>
					<td><c:out value="${candy.getEnergy()}"/></td>
					<td><c:out value="${candy.getProducer()}"/></td>
					<td><c:out value="${candy.getProductionDateTime()}"/></td>
					<td><c:out value="${candy.getIngredients()}"/></td>
					<td><c:out value="${candy.getNutritionalValue().getFats()}"/></td>
					<td><c:out value="${candy.getNutritionalValue().getProtains()}"/></td>
					<td><c:out value="${candy.getNutritionalValue().getCarbohydrates()}"/></td>
					<td><c:out value="${candy.getGlaze().getType()}"/></td>
					<td><c:out value="${candy.getFilling()}"/></td>
					<td>
						<c:choose>
							<c:when test="${candy.getClass().getSimpleName() == 'PackedCandy'}">
								<c:out value="${candy.getPack()}"/>
							</c:when>	
							<c:otherwise>
							Unpacked
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>
