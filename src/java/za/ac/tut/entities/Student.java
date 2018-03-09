/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author doctor
 */
@Entity
@Table(name="ClassList")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long studentNo;
    private String name;
    private String surname;

    public Student() {
    }

    public Student(Long studentNo, String name, String surname) {
        this.studentNo = studentNo;
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    
    public Long getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(Long studentNo) {
        this.studentNo = studentNo;
    }
}
