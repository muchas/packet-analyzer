package main.java.pl.edu.agh.iisg.to.visualizer.test;

import com.athaydes.automaton.FXApp;
import com.athaydes.automaton.FXer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import main.java.pl.edu.agh.iisg.to.visualizer.src.App;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.athaydes.automaton.assertion.AutomatonMatcher.hasText;
import static com.athaydes.automaton.selector.StringSelectors.matchingAny;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Suota on 2017-01-03.
 */
public class BasicTest {

    @BeforeClass
    public static void launchApp() throws Exception {
        System.out.println( "Launching Java App" );

        FXApp.startApp( new App() );

        System.out.println( "App has been launched" );

        Thread.sleep( 2000 );
    }

    @Test
    public void automatonTest() {
        System.out.println( "Running test" );
        FXer user = FXer.getUserWith();

        assertThat( user.getAt(Label.class),
                hasText( "Welcome to Packet Analyzer Tool" ) );

    }
}

