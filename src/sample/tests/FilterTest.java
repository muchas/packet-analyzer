package sample.tests;

import org.junit.Test;
import sample.entities.Filter;

import static org.junit.Assert.*;

public class FilterTest {

    @Test
    public void testGetCode() throws Exception {
        Filter filter = new Filter("name", "code");
        assertEquals("function name(packet) { \ncode\n}", filter.getCode());
    }

    @Test
    public void testGetName() throws Exception {
        Filter filter = new Filter("name", "code");
        assertEquals("name", filter.getName());
    }

    @Test
    public void testFilterCodeParsing() throws Exception {
        String code = String.join("\n", new String[] {
            "function exampleName(packet) {",
            "   return true;",
            "}"
        });
        Filter filter = new Filter(code);
        assertEquals("exampleName", filter.getName());
        assertEquals("   return true;", filter.getBody());
    }

    @Test
    public void testSetName() throws Exception {
        Filter filter = new Filter("name", "code");

        filter.setName("differentname");
        assertEquals("differentname", filter.getName());
    }


    @Test
    public void testToString() throws Exception {
        Filter filter = new Filter("name", "code");

        assertEquals("name", filter.getName());
    }
}