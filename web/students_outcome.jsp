<%-- 
    Document   : students_outcome
    Created on : 08 Mar 2018, 3:33:44 AM
    Author     : doctor
--%>

<%@page import="javax.script.ScriptEngineManager"%>
<%@page import="javax.script.ScriptEngine"%>
<%@page import="java.util.ArrayList"%>
<%@page import="za.ac.tut.entities.Student"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Students</title>
    </head>
    <body>
        <h1>Students display page</h1>
        <%
            request.getAttribute("students");
            int counter = 0;
        %>

        <%-- for (Student student : students) {
            System.out.println("Name: " + student.getName() + ", Surname: " + student.getSurname()
                    + ", Student number: " + String.valueOf(student.getStudentNo()));
        }--%>

        <table Border="1">
            <tr>
                <th><c:out value="Names" /></th>
                <th><c:out value="Surname" /></th>
                <th><c:out value="Student number" /></th>
            </tr>

            <c:forEach items="${students}" var="student">
                <%counter++;%>
                <tr>
                    <td><input type = "radio" name="radio${counter}"/><c:out value="${student.getName()}"/></td>
                    <td><c:out value="${student.getSurname()}" /></td>
                    <td><c:out value="${String.valueOf(student.getStudentNo())}" /></td>
                </tr>
            </c:forEach>
            <form action="AddStudentServlet.do" method="PUT">
                <table>
                    <%
                        ArrayList<Student> stds = (ArrayList<Student>) request.getAttribute("students");
                        counter++;
                        //request.setAttribute("student",);
                        //int index = giveMeSomething();
                        ScriptEngine runtime = runtime = new ScriptEngineManager().getEngineByName("javascript");

                    %>
                    <script>
                        function giveMeSomething() {
                            var radios = document.getElementsByTagName('input');
                            //var value;
                            for (var i = 0; i < radios.length; i++) {
                                if (radios[i].type === 'radio' && radios[i].checked) {
                                    // get value, set checked flag or do whatever you need to
                                    //value = radios[i].value;
                                    return i;
                                }
                            }
                            return;
                        }
                    </script>
                    <td><input type="submit" value="Update record"</td>
                </table>
            </form>
        </table>
    </body>
</html>
