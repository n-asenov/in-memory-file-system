package parser;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import parser.StandardInputParser;

public class StandardInputParserTest {
    @After
    public void restore() {
	System.setIn(System.in);
    }

    @Test
    public void hasNextLine_StandardInputHasNextLine_ReturnTrue() {
	setUp("Next line");

	StandardInputParser parser = new StandardInputParser();

	assertTrue(parser.hasNextLine());
    }

    @Test
    public void hasNextLine_StandardInputDoesNotHaveNextLine_ReturnFalse() {
	setUp("");

	StandardInputParser parser = new StandardInputParser();

	assertFalse(parser.hasNextLine());
    }

    @Test
    public void getCommnadLine_CommandWithNoOptionsAndArguments_ReturnTheCommandName() {
	setUp("wc");

	StandardInputParser parser = new StandardInputParser();
	List<String> result = parser.getCommandLine();

	assertEquals(1, result.size());
	assertEquals("wc", result.get(0));
    }

    @Test
    public void getCommnad_CommandWithArgumentsOnly_ReturnCommandAndArguemnts() {
	setUp("ls /home/dir1");

	StandardInputParser parser = new StandardInputParser();
	List<String> result = parser.getCommandLine();

	assertEquals(2, result.size());
	assertEquals("ls", result.get(0));
	assertEquals("/home/dir1", result.get(1));
    }

    @Test
    public void getCommand_CommandWithOptionsAndArguments_ReturnCommandAndSetOptionsAndArguments() {
	setUp("ls -l /home /dir1");

	StandardInputParser parser = new StandardInputParser();
	List<String> result = parser.getCommandLine();

	assertEquals(4, result.size());
	assertEquals("ls", result.get(0));
	assertEquals("-l", result.get(1));
	assertEquals("/home", result.get(2));
	assertEquals("/dir1", result.get(3));
    }

    private void setUp(String command) {
	ByteArrayInputStream input = new ByteArrayInputStream(command.getBytes());

	System.setIn(input);
    }
}
