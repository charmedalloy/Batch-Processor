package team.work;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PipecmdCommand extends Command {

	PipecmdExecCommand pipe_cmd1;
	PipecmdExecCommand pipe_cmd2;

	public String describe() {
		return "Pipe Command";

	}

	public void parseCmd(Element element) throws ProcessException {

		System.out.println("Parsing pipe Command in input xml file");
		NodeList nodes = element.getChildNodes();
		for (int idx = 0; idx < nodes.getLength(); idx++) {
			Node node = nodes.item(idx);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element elem = (Element) node;
				//System.out.println("pipecmd element is " + elem);
				if (pipe_cmd1 == null) {
					pipe_cmd1 = new PipecmdExecCommand();
					pipe_cmd1.parseCmd(elem);

				} else if (pipe_cmd2 == null) {
					pipe_cmd2 = new PipecmdExecCommand();
					pipe_cmd2.parseCmd(elem);

				}
				parseCmd(elem);

			}
		}
	}

	@Override
	public void execute(String workingDir, Map<String, Command> cmdLookUp) throws ProcessException {

		try {
			pipe_cmd1.execute(workingDir, cmdLookUp);
			pipe_cmd2.execute(workingDir, cmdLookUp);

			InputStream inputStream = pipe_cmd1.getInputStream();
			OutputStream outputStream = pipe_cmd2.getOutputStream();

			int achar;
			while ((achar = inputStream.read()) != -1) {
				outputStream.write(achar);
			}
			outputStream.flush();
			outputStream.close();
			System.out.println("Completed: " + pipe_cmd1.describe());
			System.out.println("Completed: " + pipe_cmd2.describe());
			System.out.println("Completed: " + describe());

		}

		catch (Exception e) {
			throw new ProcessException("Error executing cmd " + id, e);

		}

	}

}
