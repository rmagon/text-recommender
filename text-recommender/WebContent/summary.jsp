<%@ page import= "com.utilities.*,java.util.*" language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Summaries</title>
</head>
<body>

<% Hotel hotel = new Hotel();
//creating arraylist object of type category class
ArrayList<Hotel> list = (ArrayList<Hotel>) request.getAttribute("hotels");
//storing passed value from jsp

for(int i = 0; i < list.size(); i++) {

hotel = list.get(i);

%>
<% out.print("<a href='Recommender?hotel="+hotel.getName()+"'>"+hotel.getName()+"</a>");%>
<br/><br/><br/>
<%
out.println("City:"+hotel.getCity());
%><br/><br/><br/>
<%
out.println("Summary: "+hotel.getRawSummary());
%>
<br/><br/><br/>
<%
}
%>
</body>
</html>