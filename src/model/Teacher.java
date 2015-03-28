/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Valentina
 */
@Entity
public class Teacher extends RoleSchool implements Serializable  {
    private static final long serialVersionUID = 1L;
    private String degree;
    private String employed;

    public Teacher(String degree, String timeI, String roleName) {
        super(roleName);
        this.degree = degree;
        this.employed = timeI;
    }

    public String getEmployed() {
        return employed;
    }

    public void setEmployed(String employed) {
        this.employed = employed;
    }

    

    public Teacher(String degree) {
        this.degree = degree;
    }

    public Teacher() {
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    
    

    
    
}
