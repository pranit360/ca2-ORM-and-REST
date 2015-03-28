/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import model.Person;
import model.RoleSchool;


public interface ISchoolFacade {

    public String getPersonsAsJSON();

    public String getPersonAsJson(long id);

    public Person addPersonFromGson(String json);

    public RoleSchool addRoleFromGson(String json, long id);

    public Person delete(long id);

}
