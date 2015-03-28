/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import exceptions.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Person;
import model.RoleSchool;
import model.Student;
import model.Teacher;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kaloyan
 */
public class TestDB {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("CA2PUJ");
        EntityManager em = emf.createEntityManager();
    public TestDB() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    @Test
    public void addPersonToDB() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CA2PUJ");
        EntityManager em = emf.createEntityManager();
        Person p1 = new Person("Tobias","Jensen","4532","tj@gmail.com");
        Person p2 = new Person("Henrik","Lars","232452","HL@gmail.com");
        em.getTransaction().begin();
        em.persist(p1);
        em.persist(p2);
        em.getTransaction().commit();
         Person a= em.find(Person.class, p2.getId());
         String FirstName = a.getFirstName();
         assertEquals(FirstName, p2.getFirstName());
        
    }
    
    @Test
    public void getPersonFromRole(){
        Person p1 = new Person("Tobias","Jensen","4532","tj@gmail.com");
        p1.addStringRole( "teacher", "m1", "jul 5, 2010 12:00 AM");
        em.getTransaction().begin();
        em.persist(p1);
        em.getTransaction().commit();
        RoleSchool r = em.find(RoleSchool.class, p1.getId());
        String roleT = r.getRoleName();
        assertEquals(roleT, "teacher");
        System.out.println(r); 
    }
    
   
    
}
