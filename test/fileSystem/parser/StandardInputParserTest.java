package fileSystem.parser;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

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
	public void getCommnad_CommandWithNoOptionsAndArguments_ReturnTheCommandName() {
		setUp("wc");
		
		StandardInputParser parser = new StandardInputParser();
		
		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		
		String result = parser.getCommand(options, arguments);
		String expectedResult = "wc";
		
		assertEquals(expectedResult, result);
		assertEquals(0, options.size());
		assertEquals(0, arguments.size());
	}
	
	@Test
	public void getCommnad_CommandWithArgumentsOnly_ReturnCommandAndArguemnts() {
		setUp("ls /home/dir1");
		
		StandardInputParser parser = new StandardInputParser();
		
		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		
		String command = parser.getCommand(options, arguments);
		String expectedCommnand = "ls";
		String expectedArgument = "/home/dir1";
		
		assertEquals(expectedCommnand, command);
		assertEquals(0, options.size());
		assertEquals(1, arguments.size());
		assertEquals(expectedArgument, arguments.get(0));
	}
	
	@Test
	public void getCommand_CommandWithOptionsAndArguments_ReturnCommandAndSetOptionsAndArguments() {
		setUp("ls -l /home /dir1");
		
		StandardInputParser parser = new StandardInputParser();
		
		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		
		assertEquals("ls", parser.getCommand(options, arguments));
		assertEquals(1, options.size());
		assertEquals(2, arguments.size());
		assertEquals("-l", options.get(0));
		assertEquals("/home", arguments.get(0));
		assertEquals("/dir1", arguments.get(1));
	}
	
	@Test
	public void getCommand_CommandWithArgumentsOnly_ReturnCommmandAndSetArguments() {
		setUp("ls - l /home");
		
		StandardInputParser parser = new StandardInputParser();
		
		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		
		assertEquals("ls", parser.getCommand(options, arguments));
		assertEquals(0, options.size());
		assertEquals(3, arguments.size());
	}
	
	@Test
	public void getCommand_CommandWithOptionsOnly_ReturnCommandAndSetOptions() {
		setUp("wc -l -5l -10l");
		
		StandardInputParser parser = new StandardInputParser();
		
		List<String> options = new ArrayList<String>();
		List<String> arguments = new ArrayList<String>();
		
		assertEquals("wc", parser.getCommand(options, arguments));
		assertEquals(3, options.size());
		assertEquals(0, arguments.size());
	}
	
	private void setUp(String command) {
		ByteArrayInputStream input = new ByteArrayInputStream(command.getBytes());
		
		System.setIn(input);
	}
}
