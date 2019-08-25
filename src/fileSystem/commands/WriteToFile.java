package fileSystem.commands;

import java.util.List;

import fileSystem.fs.FileSystem;

public class WriteToFile implements Command {
	private FileSystem fs;

	public WriteToFile(FileSystem fs) {
		this.fs = fs;
	}

	@Override
	public void execute(List<String> options, List<String> arguments) {
		// TODO Auto-generated method stub
		
	}

}
