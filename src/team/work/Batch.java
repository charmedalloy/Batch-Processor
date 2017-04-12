package team.work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Batch {
	private String wd;
	private Map<String, Command> cmdLookup;
	private List<Command> commandlist;

	public Batch() {
		commandlist = new ArrayList<Command>();
		cmdLookup = new HashMap<String, Command>();
	}

	public void addCommand(Command command) {

		commandlist.add(command);
		cmdLookup.put(command.id, command);
		
	}

	public String getWorkingDir() {
		return wd;

	}

	public void setWorkingDir(String wd) {
		this.wd = wd;
	}

	public Map<String, Command> getCmds() {
		return cmdLookup;

	}

	public List<Command> getCmdList() {
		return commandlist;

	}
}
