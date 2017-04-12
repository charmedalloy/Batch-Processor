package team.work;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;

public class BatchParser {

	public Batch buildBatch(File batchFile) {
		Batch batch = new Batch();
		try {
			FileInputStream fis = new FileInputStream(batchFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fis);

			Element pnode = doc.getDocumentElement();
			NodeList nodes = pnode.getChildNodes();
			for (int idx = 0; idx < nodes.getLength(); idx++) {
				Node node = nodes.item(idx);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element) node;
					Command cmd = buildCommand(elem);
					batch.addCommand(cmd);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return batch;

	}

	public Command buildCommand(Element elem) throws DOMException, ProcessException {
		String commandName = elem.getNodeName();
		Command cmd = null;

		if (commandName == null) {

			throw new ProcessException("unable to parse command from " + elem.getTextContent());

		} else if ("filename".equalsIgnoreCase(commandName)) {
			System.out.println("Parsing filename");
			cmd = new FilenameCommand();
			cmd.parseCmd(elem);

		} else if ("exec".equalsIgnoreCase(commandName)) {
			System.out.println("Parsing exec");
			cmd = new ExecCommand();
			cmd.parseCmd(elem);

		} else if ("pipecmd".equalsIgnoreCase(commandName)) {
			System.out.println("Parsing pipecmd");
			cmd = new PipecmdCommand();
			cmd.parseCmd(elem);

		} else {
			throw new ProcessException("Unknown command " + commandName + " from: " + elem.getBaseURI());
		}
		return cmd;

	}
}