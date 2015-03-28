/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Person;
import model.RoleSchool;

/**
 *
 * @author Pranit Anand
 */
public class SchoolFacade implements ISchoolFacade {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("CA2PUJ");
    EntityManager em = emf.createEntityManager();
    //Map<Integer, Person> persons = new HashMap();
    private final Gson gson = new Gson();
    private static SchoolFacade instance = new SchoolFacade();

    private SchoolFacade() {
    }

    public static SchoolFacade getFacade(boolean reseet) {
        if (true) {
            instance = new SchoolFacade();
        }
        return instance;
    }

    public void createTestData() {
        addPersonFromGson(gson.toJson(new Person("Lars", "Mortensen", "1234", "lala@yahoo.com")));
        addPersonFromGson(gson.toJson(new Person("Lulu", "Mortensen", "1234", "lala@yahoo.com")));
    }

    @Override
    public String getPersonsAsJSON() {

        List<Person> P = em.createQuery("select P from Person p").getResultList();
        return gson.toJson(P);
    }

    @Override
    public String getPersonAsJson(long id) {
        Person p = em.find(Person.class, id);
        return gson.toJson(p);

    }

    @Override
    public Person addPersonFromGson(String json) {
        Person p = gson.fromJson(json, Person.class);
        em.getTransaction().begin();

        em.persist(p);

        em.getTransaction().commit();

        return p;
    }

    @Override
    public RoleSchool addRoleFromGson(String json, long id) {
        Person p = em.find(Person.class, id);
        System.out.println(p);
        RoleSchool rs = null;
        JsonElement jelement = new JsonParser().parse(json);
        JsonObject jobject = jelement.getAsJsonObject();
        String rolename = jobject.get("roleName").getAsString();

        System.out.println(rolename);
        if (rolename.equals("Teacher")) {
            String degree = jobject.get("degree").getAsString();
            String timeE = jobject.get("employed").getAsString();
            p.addStringRole(rolename, degree, timeE);
        } else if (rolename.equals("Student")) {
            String semester = jobject.get("semester").getAsString();
            p.addStringRole(rolename, semester, null);
        } else {
            p.addStringRole(rolename, null, null);
        }
        rs = em.find(RoleSchool.class, p.getId());
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        return rs;

    }

    @Override
    public Person delete(long id) {
        Person p1 = em.find(Person.class, id);
        em.getTransaction().begin();
        em.remove(p1);
        em.getTransaction().commit();
        return p1;
    }

}
