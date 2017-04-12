package team.work;

import java.io.File;
import java.util.List;
import java.util.Map;

public class BatchProcessor {

	public static void main(String[] args) {
		String filename = null;
		File file = null;

		try {
			if (args.length > 0) {
				filename = args[0];
				file = new File(filename);
			} else {
				filename = "batch5.xml";
			}
			file = new File(filename);
			System.out.println("Opening " + filename);
			// Parsing Batch xml file
			BatchParser bp = new BatchParser();
			Batch batch = bp.buildBatch(file);

			System.out.println("Completed Batch building>>>>>>>>>>>>>>>>>>>>>>");
			System.out.println("Batch built is executing.....");

			// calling for batch execution
			executeBatch(batch);

			System.out.println("..................Batch execution is completed!...........................");
		}

		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void executeBatch(Batch batch) throws ProcessException {
		List<Command> commandlist = batch.getCmdList();
		Map<String, Command> cmdmap = batch.getCmds();
		for (Command cmd : commandlist) {
			System.out.println("Executing: " + cmd.describe());
			batch.setWorkingDir("work");
			cmd.execute(batch.getWorkingDir(), cmdmap);
		}
	}
}
