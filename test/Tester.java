/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import model.Person;
import java.util.HashMap;
import java.util.Map;
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
public class Tester {

    public Tester() {
    }

    static Map<Long, Person> testMap;

    @BeforeClass
    public static void setUpClass() {
        testMap = new HashMap();
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
    public void addPerson() { // works 
        Person p1 = new Person("henrik", "lars", "42325", "HL@gmail.com");
        testMap.put(p1.getId(), p1);
        String fname = testMap.get(p1.getId()).getFirstName();
        assertEquals(fname, "henrik");
             
    }
   
   
}
