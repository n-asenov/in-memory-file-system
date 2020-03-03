package parser;

import java.util.List;

public interface Parser {
    boolean hasNextLine();

    List<String> getCommandLine();

    void close();
}
