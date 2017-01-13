package pl.agh.edu.iisg.to.visualizer;

import com.athaydes.automaton.FXApp;
import com.athaydes.automaton.FXer;
import javafx.scene.control.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.edu.agh.iisg.to.visualizer.App;

import static com.athaydes.automaton.assertion.AutomatonMatcher.hasText;
import static com.athaydes.automaton.selector.StringSelectors.matchingAny;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Suota on 2017-01-03.
 */
public class AppTest {

    @BeforeClass
    public static void setUp() throws Exception {
        System.out.println( "Launching Java App" );
        FXApp.startApp( new App() );

        System.out.println( "App has been launched" );
        // let the window open and show before running tests
        Thread.sleep( 2000 );
    }

    @AfterClass
    public static void cleanUp() {
        System.out.println( "Cleaning up" );
        FXApp.doInFXThreadBlocking( () -> FXApp.getStage().close() );
    }

    @Test
    public void shouldFindPacketAnalyzerLabelOnStartTest() {
        System.out.println( "Running test" );
        FXer user = FXer.getUserWith();

        assertThat( user.getAt(Label.class),hasText( "Welcome to Packet Analyzer Tool" ) );
    }

    @Test
    public void shouldDisplayTextOfUserGuideTest() {
        System.out.println( "Running test" );
        FXer user = FXer.getUserWith();

        user.clickOn(matchingAny("type:Tab", "text:Help") );
        assertThat( user.getAt( Button.class ), hasText( "User Guide" ) );

        user.clickOn(matchingAny("type:Button", "text:User Guide") );
        assertThat( user.getAt(TextArea.class),
                hasText( "This is Sample User Guide.\n\nGot few lines....\n\n... but nothing worth seeing yet.\n" ) );
    }

    @Test
    public void shouldFindChoiceBoxAfterClickOnStatisticsTest() {
        System.out.println(" Running test ");
        FXer user = FXer.getUserWith();

        user.clickOn(matchingAny("type:Tab", "text:Statistics"));
        assertNotNull(user.getAt(ChoiceBox.class));
    }
}

