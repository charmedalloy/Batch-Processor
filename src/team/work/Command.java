package team.work;

import java.util.Map;

import org.w3c.dom.Element;

public abstract class Command {
	String id;
	String path;

	public abstract String describe();

	public abstract void execute(String workingDir, Map<String, Command> cmdMap) throws ProcessException;

	public abstract void parseCmd(Element elem) throws ProcessException;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return this.path;
	}
}
