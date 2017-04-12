package team.work;

import java.util.Map;

import org.w3c.dom.Element;

public class FilenameCommand extends Command {
	String path;

	@Override
	public String describe() {

		return "A File Command with path " + path + " is executed";
	}

	@Override
	public void parseCmd(Element elem) throws ProcessException {

		// getting id attribute value
		id = elem.getAttribute("id");
		if (id == null || id.isEmpty()) {

			throw new ProcessException("Missing ID in filename command");

		}

		// getting path attribute value
		path = elem.getAttribute("path");
		if (path == null || path.isEmpty()) {

			throw new ProcessException("Missing PATH in filename command");

		}

	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getPath() {
		return path;
	}

	@Override
	public void execute(String workingDir, Map<String, Command> cmdMap) throws ProcessException {
		// TODO Auto-generated method stub

	}

}