/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.ejb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.ac.tut.entities.Student;

/**
 *
 * @author doctor
 */
public class AddStudentServlet extends HttpServlet {

    @EJB
    private StudentFacadeLocal studentFacade;
    String qryStr = "";
    ResultSet rs = null;
    ArrayList<Student> students = new ArrayList<>();

    // res.setContentType("text/html");
    Connection conn = null;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long studentNo = Long.parseLong(request.getParameter("studentNo"));
        String names = request.getParameter("names");
        String surname = request.getParameter("surname");

        Student student = createStudent(studentNo, names, surname);
        studentFacade.addStudent(student);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/student_register_outcome.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        Statement stmt = null;
        //HttpSession session = request.getSession();
        //using movie_resolved view
        try {
            // Get a Connection to the database

            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/StudentsDB", "APP", "123");
            // Create a Statement object            
            stmt = conn.createStatement();

            // Execute an SQL query, get a ResultSet
            qryStr = "SELECT * from CLASSLIST";

            rs = stmt.executeQuery(qryStr);

            //clearing a list to prevent duplicates of records
            students.clear();
            while (rs.next()) {

                Student student = new Student();
                student.setName(rs.getString("name"));
                student.setSurname(rs.getString("surname"));
                student.setStudentNo(rs.getLong("studentNo"));

                students.add(student);
            }

            request.setAttribute("students", students);
            RequestDispatcher view = request.getRequestDispatcher("students_outcome.jsp");
            view.forward(request, response);

        } catch (SQLException e) {
            out.println("SQLException caught: " + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddStudentServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Always close the database connection.
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ignored) {
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/StudentsDB", "APP", "123");

            qryStr = "UPDATE CLASSLIST SET STDENTNO=?, NAME=?, SURNAME=? WHERE STUDENTNO=?";

            PreparedStatement stmt = conn.prepareStatement(qryStr);

            stmt.setInt(1, 1231321);
            stmt.setString(2, "William Henry Bill Gates");
            stmt.setString(3, "bill.gates@microsoft.com");
            stmt.setString(4, "bill");
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing user was updated successfully!");
            }
        } catch (SQLException sQLException) {

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddStudentServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Always close the database connection.
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ignored) {
            }
        }

    }

    private Student createStudent(Long studentNo, String names, String surname) {
        Student student = new Student();

        student.setName(names);
        student.setStudentNo(studentNo);
        student.setSurname(surname);

        return student;
    }
}
