/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import com.google.gson.Gson;
import exceptions.NotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Person;
import model.RoleSchool;

/**
 *
 * @author Valentina
 */
public class FacadeWithoutDB implements ISchoolFacade {
    Map<Long,Person> persons = new HashMap();
  private long nextId;
  private final Gson gson = new Gson();
  private static FacadeWithoutDB instance = new FacadeWithoutDB();

  public static FacadeWithoutDB getFacade(boolean reseet){
    if(true){
      instance = new FacadeWithoutDB();
    }
    return instance;
  }
  
    @Override
    public String getPersonsAsJSON() {
     if(persons.isEmpty()){
      return null;
    }
    return gson.toJson(persons.values());
    }

    @Override
    public String getPersonAsJson(long id) {
     Person p = persons.get(id);
    return gson.toJson(p);
    }

    @Override
    public Person addPersonFromGson(String json) {
     Person p = gson.fromJson(json, Person.class);
    p.setId(nextId);
    persons.put(nextId, p);
    nextId++;
    return p; }

    @Override
    public RoleSchool addRoleFromGson(String json, long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Person delete(long id) {
    Person p = persons.remove(id);
    return p;
    }
    
}
