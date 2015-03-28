/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import View.FacadeWithoutDB;
import com.google.gson.Gson;
import exceptions.NotFoundException;
import java.util.HashMap;
import java.util.Map;
import model.Person;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Valentina
 */
public class TestPart3NoDB {
     FacadeWithoutDB facade;
     Gson gson = new Gson();
    public TestPart3NoDB() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        facade=FacadeWithoutDB.getFacade(true);
    }
    
    @After
    public void tearDown() {
    }

    @Test
  public void testAddPerson() throws NotFoundException {
    Person person = facade.addPersonFromGson(gson.toJson(new Person("bbb", "bbb", "bbb","mail.gmail.com")));
    String expectedJsonString = gson.toJson(person);
    String actual = facade.getPersonAsJson(person.getId());
    assertEquals(expectedJsonString, actual);
  }
  
   @Test
  public void testGetPerson() throws Exception {
    testAddPerson();
  }
  
   @Test
  public void testGetPersons() {
    Person p = new Person("aaa", "aaa", "aaa","mail@gmail.com");
    Person person1 = facade.addPersonFromGson(gson.toJson(p));
    Person p2 = new Person("bbb", "bbb", "bbb","erwet@gmail.com");
    Person person2 = facade.addPersonFromGson(gson.toJson(p2));
    
    //Make the Expected String
    Map<Long,Person> test = new HashMap();
    test.put(person1.getId(),person1);
    test.put(person2.getId(),person2);
    String expected = gson.toJson(test.values());
    String result = facade.getPersonsAsJSON();
    assertEquals(expected,result);
  }
  
   @Test
  public void testDeletePerson()  {
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
