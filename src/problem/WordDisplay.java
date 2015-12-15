package problem;

import java.io.IOException;

public class WordDisplay implements Observer<FileData>{


	@Override
	public void update(FileData data) {
		
		if(!data.getEventName().equals("ENTRY_CREATE"))
			return;

		ProcessBuilder processBuilder = null;
		String command = null;
		String arg = null;
		
		String fileName = data.getFile().toString();
		System.out.println("Processing " + fileName + "...");
	
		if((fileName.endsWith(".docx") || fileName.endsWith(".doc")) && !fileName.contains("~$")){
			command = "C:\\Program Files (x86)\\Microsoft Office\\Office15\\winword";
			arg = fileName;
		}
		else {
			command = null;
			System.err.format("No support available for: %s...%n", data.getFile());
			return;
		}

		// Run the application if support is available
		try {
			System.out.format("Launching %s ...%n", command);
			processBuilder = new ProcessBuilder(command, arg);
			
			// Start and add the process to the processes list
			Process process = processBuilder.start();
			//this.processes.add(process);
			data.getProcesses().add(process);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
