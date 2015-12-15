package problem;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class FileData implements Subject{

	private List<Observer<FileData>> observers;
	private List<Process> processes;
	private Path file;
	String eventName;
	
	public FileData(List<Process> processes, Path file, String eventName) {
		this.processes = processes;
		this.file = file;
		this.eventName = eventName;
		this.observers = new ArrayList<Observer<FileData>>();
	}

	public List<Process> getProcesses() {
		return processes;
	}

	public void setProcesses(List<Process> processes) {
		this.processes = processes;
		filesChanged();
	}

	public Path getFile() {
		return file;
	}

	public void setFile(Path file) {
		this.file = file;
		filesChanged();
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
		filesChanged();
	}

	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObserver() {
		for(Observer<FileData> o : observers){
			o.update(this);
		}
	}
	
	public void filesChanged(){
		notifyObserver();
	}
		
}
