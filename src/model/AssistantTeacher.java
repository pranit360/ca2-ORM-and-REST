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
public class AssistantTeacher extends RoleSchool implements Serializable {
    private static final long serialVersionUID = 1L;

    public AssistantTeacher(String roleName) {
        super(roleName);
    }

    public AssistantTeacher() {
    }
    
    
}
