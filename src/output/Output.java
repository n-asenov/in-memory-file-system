package output;

import java.io.Closeable;
import java.io.OutputStream;
import java.io.PrintStream;

public class Output implements AutoCloseable, Closeable {
    private PrintStream output;

    public Output(OutputStream output) {
	this.output = new PrintStream(output);
    }

    public void println(String result) {
	if (!result.equals("")) {
	    output.println(result);
	}
    }

    @Override
    public void close() {
	output.close();
    }	
}
