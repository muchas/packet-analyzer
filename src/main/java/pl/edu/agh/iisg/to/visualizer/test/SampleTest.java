package main.java.pl.edu.agh.iisg.to.visualizer.test;

import com.athaydes.automaton.FXApp;
import com.athaydes.automaton.FXer;
import javafx.scene.control.Button;
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
public class SampleTest {

    @BeforeClass
    public static void launchApp() throws Exception {
        System.out.println( "Launching Java App" );

        FXApp.startApp( new App() );


        System.out.println( "App has been launched" );

        // let the window open and show before running tests
        Thread.sleep( 2000 );
    }

    @AfterClass
    public static void cleanup() {
        System.out.println( "Cleaning up" );
        FXApp.doInFXThreadBlocking( () -> FXApp.getStage().close() );
    }

    @Test
    public void automatonTest() {
        System.out.println( "Running test" );
        FXer user = FXer.getUserWith();

        user.clickOn(matchingAny("type:Tab", "text:Help") );
        assertThat( user.getAt( Button.class ), hasText( "User Guide" ) );

        user.clickOn(matchingAny("type:Button", "text:User Guide") );
        assertThat( user.getAt(TextArea.class),
                hasText( "This is Sample User Guide.\n\nGot few lines....\n\n... but nothing worth seeing yet.\n" ) );

    }
}

