package sample;

import org.junit.Test;
import sample.entities.Filter;

import static org.junit.Assert.*;

public class FilterTest {

    @Test
    public void testGetCode() throws Exception {
        Filter filter = new Filter("name", "code");
        assertEquals("function name(packet) { \n" +
                "code\n" +
                "}", filter.getCode());
    }

    @Test
    public void testGetName() throws Exception {
        Filter filter = new Filter("name", "code");
        assertEquals("name", filter.getName());
    }

    @Test
    public void testSetName() throws Exception {
        Filter filter = new Filter("name", "code");

        filter.setName("differentname");
        assertEquals("differentname", filter.getName());
    }

    @Test
    public void testApply() throws Exception {

    }

    @Test
    public void testToString() throws Exception {

    }
}