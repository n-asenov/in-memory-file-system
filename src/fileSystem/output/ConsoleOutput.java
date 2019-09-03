package fileSystem.output;

public class ConsoleOutput implements Output {

	@Override
	public void print(String result) {
		if(!result.equals("")) {
			System.out.println(result);
		}
	}
}
