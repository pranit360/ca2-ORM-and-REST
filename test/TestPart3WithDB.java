/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import View.SchoolFacade;
import com.google.gson.Gson;
import exceptions.NotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Person;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pranit Anand
 */
public class TestPart3WithDB {

    static Map<Integer, Person> persons = new HashMap();
    SchoolFacade facade = SchoolFacade.getFacade(true);
    Gson gson = new Gson();
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("CA2PUJ");
    static EntityManager em = emf.createEntityManager();
    List<Person> list;

    public TestPart3WithDB() {
        list = new ArrayList();
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
    public void testGetPersonAsJson(){
        Person p1 = facade.addPersonFromGson(gson.toJson(new Person("bbb", "bbb", "bbb","mail.gmail.com")));
         Person p2 = facade.addPersonFromGson(gson.toJson(new Person("VALA", "Mata", "23563","mail.gmail.com")));
         long nr =p1.getId();
         long nr2 =p2.getId();
         list.add(new Person(nr, "bbb", "bbb", "bbb","mail.gmail.com"));
         list.add(new Person(nr2,"VALA", "Mata", "23563","mail.gmail.com"));
String result = facade.getPersonsAsJSON();
String execute=gson.toJson(list);
        facade.delete(p1.getId());
        facade.delete(p2.getId());
assertEquals(execute,result);
    }
    
    @Test
    public void testGetPersonWithId()throws Exception{
     addPersonTest();
    }
    
    
    
    @Test
    public void addPersonTest(){
    Person person = facade.addPersonFromGson(gson.toJson(new Person("bbb", "bbb", "bbb","mail.gmail.com")));
    String expectedJsonString = gson.toJson(person);
    String actual = facade.getPersonAsJson(person.getId());
    facade.delete(person.getId());
    assertEquals(expectedJsonString, actual);
    }
    
    @Test
    public void deletePersonTest()
    {
        Person person = facade.addPersonFromGson(gson.toJson(new Person("bbb", "bbb", "bbb", "sdfsdg")));
    facade.delete(person.getId());
    String p =facade.getPersonAsJson(person.getId());
    boolean  cute = true;
    if(p.equals(null)){
        cute = false;
    }
       assertTrue(cute);
  
    }
}
