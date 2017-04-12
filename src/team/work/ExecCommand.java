package team.work;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.w3c.dom.Element;

public class ExecCommand extends Command {
	List<String> cmdArgs;
	String inID;
	String outID;
	String path;

	@Override
	public String describe() {
		return "A CMD command {" + path + "," + cmdArgs.toString() + "} is executed.";
	}

	@Override
	public void execute(String workingDir, Map<String, Command> cmdMap) throws ProcessException {
		try {
			List<String> command = new ArrayList<String>();
			command.add(path);
			command.addAll(cmdArgs);

			ProcessBuilder builder = new ProcessBuilder();
			builder.command(command);

			if (inID != null && !inID.trim().isEmpty()) {
				Command cmd = cmdMap.get(inID);
				if (cmd != null) {
					builder.redirectInput(new File(cmd.getPath()));
				} else {
					throw new ProcessException("Unable to locate IN file command for id: " + inID);
				}
			}

			if (outID != null && !outID.trim().isEmpty()) {
				Command cmd = cmdMap.get(outID);
				if (cmd != null) {
					builder.redirectOutput(new File(cmd.getPath()));
				} else {
					throw new ProcessException("Unable to locate OUT file command: " + outID);
				}
			}

			Process process = builder.start();
			process.waitFor();

			System.out.println("Execution Completed.......... ");
		} catch (ProcessException error) {
			throw error;
		} catch (Exception e) {
			throw new ProcessException("Error executing exec " + id, e);
		}
	}

	@Override
	public void parseCmd(Element elem) throws ProcessException {

		id = elem.getAttribute("id");

		if (id == null || id.isEmpty()) {
			throw new ProcessException("Missing ID in Exec Command");

		}

		System.out.println("ID: " + id);

		path = elem.getAttribute("path");
		if (path == null || path.isEmpty()) {

			throw new ProcessException("Missing PATH in Exec Command");

		}
		System.out.println("Path: " + path);

		// Arguments must be passed to ProcessBuilder as a list of individual
		// strings.
		cmdArgs = new ArrayList<String>();
		String arg = elem.getAttribute("args");
		if (!(arg == null || arg.isEmpty())) {
			StringTokenizer st = new StringTokenizer(arg);
			while (st.hasMoreTokens()) {
				String tok = st.nextToken();
				cmdArgs.add(tok);
			}
		}

		inID = elem.getAttribute("in");

		outID = elem.getAttribute("out");

	}

}
