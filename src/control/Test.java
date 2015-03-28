/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import View.SchoolFacade;
import model.Person;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import model.RoleSchool;
import model.Student;
import model.Teacher;

/**
 *
 * @author Kaloyan
 */
public class Test {
     
     private static EntityManager createEntityManager() {
    EntityManagerFactory emf = 
        Persistence.createEntityManagerFactory("CA2PUJ");
    return emf.createEntityManager();
    }
  
  private static void testJpaTables() {
    
    Person person = new Person("Kurt","sgsdg", "3535764", "fsghdghd@gail.com");
    Person p1 = new Person("vala", "mata", "2353764", "sdfsg@gmail.com");
    Person p2 = new Person("mada", "dfghd", "2353764", "sdfsg@gmail.com");
//    p1.addRole("student");
//    person.addRole("teacher");
   p1.addStringRole("Teacher", "m1", "jul 5, 2010 12:00 AM");
    EntityManager em = createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();
    try {
    
      
      em.persist(p1);
      em.persist(person);
      em.persist(p2);
      
      
      //System.out.println("Cats in show "+);
      transaction.commit();
      
      }
    catch (Exception e) {
//      transaction.rollback();
        System.out.println("xxx"+e);
      }
    finally {
      em.close();
      }
    }
    public void deleteMe(){
              String s = "{\"employed\":\"Jul 5, 2010 12:00:00 AM\", \"degree\":\"m1\", \"roleName\":\"Teacher\"}";
        
        System.out.println(SchoolFacade.getFacade(true).addRoleFromGson(s, 1));
  
    }
    public static void main(String[] args) {
        new Test().testJpaTables();
        System.out.println(SchoolFacade.getFacade(true).getPersonsAsJSON());
        System.out.println(SchoolFacade.getFacade(true).getPersonAsJson(1));
       // new Test().deleteMe();
        // SchoolFacade.getFacade(true).delete(3);
    }
}
