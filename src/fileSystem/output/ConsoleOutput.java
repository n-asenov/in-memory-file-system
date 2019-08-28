package fileSystem.output;

public class ConsoleOutput implements Output {

	@Override
	public void print(String result) {
		System.out.println(result);
	}

}
