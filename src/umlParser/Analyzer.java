package umlParser;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class Analyzer implements IAnalyzer {

	HashMap<String, Class<? extends UMLParsable>> phases = new HashMap<>();
	HashMap<Class<? extends UMLParsable>, Class<? extends PatternDetector>> detectors = new HashMap<>();
	ArrayList<UMLParsable> outputGenerator = new ArrayList<>();
	ArrayList<String> classes;

	public Analyzer(ArrayList<String> classes) {
		this.classes = classes;
		// phases.put("Loader", UMLParser.class);
		phases.put("Decorator-Detector", UMLDecoratorParser.class);
		detectors.put(UMLDecoratorParser.class, DecoratorDetector.class);
		phases.put("Singleton-Detector", UMLSingletonParser.class);
		detectors.put(UMLSingletonParser.class, SingletonDetector.class);
	}

	@Override
	public boolean execute(String phase) {
		try {
			if(phase.equals("Loader")){
				//outputGenerator.put(UMLEndParser.class.newInstance(), "");
			} else if(phase.equals("Output")){
				((AbstractUMLParser) outputGenerator.get(outputGenerator.size()-1)).setParser(new UMLUsesParser(new UMLAssociationParser(new UMLImplementsParser(new UMLExtendsParser(new UMLParser())))));
				for(int i = outputGenerator.size()-2; i >= 0; i--){
					((AbstractUMLParser) outputGenerator.get(i)).setParser(outputGenerator.get(i+1));
				}
				AbstractUMLParser parser = new UMLEndParser(outputGenerator.get(0));
				parser.parse(classes.toArray(new String[classes.size()]));
			} else {
				ArrayList<String> classesToDetect = new ArrayList<String>();
				for(String className : this.classes){
					if(exceptions.get(phase) == null || !exceptions.get(phase).contains(className)){
						classesToDetect.add(className);
					}
				}
				String detected = detectors.get(phases.get(phase)).newInstance().detect(classesToDetect.toArray(new String[classesToDetect.size()]));
				outputGenerator.add(phases.get(phase).getConstructor(new Class[]{String.class}).newInstance(detected.split("~")[0]));
			}
		} catch (InstantiationException | IllegalAccessException | IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean executeAll(ArrayList<String> phases) {
		// TODO Auto-generated method stub
		return false;
	}

}
