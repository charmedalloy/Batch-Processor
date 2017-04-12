package team.work;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PipecmdExecCommand extends ExecCommand {

	private InputStream inputStream;
	private OutputStream outputStream;

	@Override
	public String describe() {

		return "PipeCmd Command: " + id + ", " + path;
	}

	public void execute(String workingDir, Map<String, Command> cmdMap) throws ProcessException {
		System.out.println("Executing: " + describe());

		try {
			List<String> command = new ArrayList<String>();
			command.add(path);
			command.addAll(cmdArgs);
			ProcessBuilder builder = new ProcessBuilder(command);
			//builder.directory(new File(workingDir));
			//File wd = builder.directory();

			if (inID != null && !inID.trim().isEmpty()) {
				Command cmd = cmdMap.get(inID);
				builder.redirectInput(new File(cmd.getPath()));
			}

			if (outID != null && !outID.trim().isEmpty()) {
				Command cmd = cmdMap.get(outID);
				builder.redirectOutput(new File(cmd.getPath()));
			}

			Process process = builder.start();
			if (outID == null || outID.trim().isEmpty()) {
				inputStream = process.getInputStream();
			}

			if (inID == null || inID.trim().isEmpty()) {
				outputStream = process.getOutputStream();
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new ProcessException("Error executing cmd " + id, e);
		}
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

}
