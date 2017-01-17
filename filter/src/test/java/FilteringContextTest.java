import org.junit.Test;
import pl.edu.agh.iisg.to.filter.FilteringContext;
import pl.edu.agh.iisg.to.filter.Packet;
import pl.edu.agh.iisg.to.filter.entities.Filter;

import static org.junit.Assert.*;

public class FilteringContextTest {

    @Test
    public void testAddingFunction() throws Exception {
        FilteringContext context = new FilteringContext();
        Filter filter = new Filter("func1", "return true;");
        Packet packet = new Packet();

        context.add(filter);

        assertTrue(context.hasFilter(filter));
        assertTrue(context.apply(filter, packet));
    }

    @Test(expected = Exception.class)
    public void testApplyForFilterNotInContext() throws Exception {
        FilteringContext context = new FilteringContext();
        Filter filter = new Filter("func1", "return true;");
        Packet packet = new Packet();

        assertFalse(context.hasFilter(filter));
        assertFalse(context.apply(filter, packet));
    }

    @Test
    public void testApply() throws Exception {
        FilteringContext context = new FilteringContext();
        Filter filter = new Filter("func1", " if(packet.getProtocol() == 'ICMP') { return true; } return false;");
        context.add(filter);

        Packet packet = new Packet("ICMP");
        Packet packet2 = new Packet("TCP");

        assertTrue(context.apply(filter, packet));
        assertFalse(context.apply(filter, packet2));
    }
}