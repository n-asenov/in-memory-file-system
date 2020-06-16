package parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.junit.After;
import org.junit.Test;

public class StandardInputParserTest {
    private Parser parser;
    
    @After
    public void close() {
	parser.close();
    }
    
    @Test
    public void hasNextLine_StandardInputHasNextLine_ReturnTrue() {
	setUp("Next line");

	assertTrue(parser.hasNextLine());
    }
    
    private void setUp(String command) {
	byte[] commandLine = command.getBytes();
	ByteArrayInputStream input = new ByteArrayInputStream(commandLine);
	parser = new StandardInputParser(input);
    }

    @Test
    public void hasNextLine_StandardInputDoesNotHaveNextLine_ReturnFalse() {
	setUp("");

	assertFalse(parser.hasNextLine());
    }

    @Test
    public void getCommnadLine_CommandWithNoOptionsAndArguments_ReturnTheCommandName() {
	setUp("wc");

	List<String> result = parser.getCommandLine();

	assertEquals(1, result.size());
	assertEquals("wc", result.get(0));
    }

    @Test
    public void getCommnad_CommandWithArgumentsOnly_ReturnCommandAndArguemnts() {
	setUp("ls /home/dir1");

	List<String> result = parser.getCommandLine();

	assertEquals(2, result.size());
	assertEquals("ls", result.get(0));
	assertEquals("/home/dir1", result.get(1));
    }

    @Test
    public void getCommand_CommandWithOptionsAndArguments_ReturnCommandAndSetOptionsAndArguments() {
	setUp("ls -l /home");

	List<String> result = parser.getCommandLine();

	assertEquals(3, result.size());
	assertEquals("ls", result.get(0));
	assertEquals("-l", result.get(1));
	assertEquals("/home", result.get(2));
    }
}
