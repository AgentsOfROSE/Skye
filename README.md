# Skye
CSSE 375 Term Project For Trevor Burch &amp; Emily Richardson

Design: 
	Our design uses the Visitor pattern, as discussed in lecture. The Main class handles the control flow of the system. The Main class delegates to a class that implements Parser in order to do some sort of parsing activity. 
	
If the user desires a UML then the Main class uses the UMLTextParser class. This class uses various classes who extend ClassVisitor in order to handle various tasks needed in order to generate the dot code for GraphViz to take in. The design also uses different "Info" objects which store information about different fields, methods, and classes. These classes are useful as we are able to store more information on a single run through of the classes than would be possible if we were printing the UML as we visited the different classes, methods, and fields. 

If the user desires a Sequence Diagram then the Main class uses the SequenceDiagramTextParser class. This class uses the SequenceClassMethodVisitor which extends ClassVisitor. This class checks each method to see if they match the desired method and then uses a MethodSequenceVisitor in order to store relative information about the sequence of events in a SequenceDiagramInfo class. This data is then used by the SequenceDiagramTextParser class to construct the text for the creation of the sequence diagram.

Our design also strives to follow the Command-Query design principle through the use of getters and setters and method that only return something or affect something but not both. Furthermore, our Visitor classes only do one type of job in order to strive for high cohesion throughout the system.
	
Milestone 1:

Design Changes:

An initial design was created with the UMLTextParser handling all events. The parser goes through and as it is printing the information for the dot notation visits various Visitor classes in order to get the next pieces of data.

Who did what:
	For Milestone 1, Trevor and Emily designed and pair programmed all of the class blocks and debugged the code. We switched off periodically to make sure we both did about the same amount of work. Emily also handled submitting the GitHub link and Trevor handled generating and committing the UML pictures. We also worked together to write appropriate test cases.


Instructions:
	Set up the Run Configurations to have the Arguments include the paths of the classes whose UML you want to generate GraphViz code for. Then run the UMLTestParrser class. Output will appear in the console. Copy and paste this code into a new GV sheet. Then run GraphViz by clicking Settings and then OK. A picture of the UML should then appear.
	
Milestone 2:

Design Changes:
	For this Milestone, we greatly refactored our design as we saw many glaring holes in the design. We then used our refactored code from the first Milestone and expanded the functionality to support the addition of 'uses' and 'associates' arrows in our graphViz dot language. We still use the Visitor Pattern and follow the Command-Query design principle as did before. Due to the use of our "Info" classes we were able to make these additions with very little modification to the overall design of our system. 

Who did what:
	For Milestone 2, Trevor and Emily pair programmed, working together to refactor existing code to increase functionality, and also implementing the 'uses' and 'associates' requirements for this milestone. We took turns every once in a while to ensure that we submitted equal amounts of work. We also worked together to generate the manually drawn UML diagram and pair programmed the test cases. We also fully supported each other while staying up late to finish the milestone and going on a midnight Taco Bell run to destress and get some nom noms. (<-- Team matureness shown here ;) Hail Hydra!)
	
Instructions
	See above. We hope to streamline the process of adding classes to be handled in the future along with allowing the user to disable packages such as "java.lang" from being shown on the final UML.
	
Milestone 3:

Design:
	For this Milestone we added a Main class which handles the introduction of multiple parsing types. In order to handle these multiple parser types we constructed a Parser interface that all Parsers implement. The Main class has a HashMap of Parsers and keywords in order to handle which Parser to use based on a command line argument. We also added a SequenceDiagramTextParser that uses the information of a method in order to draw the sequence diagram that is spawned from that method. This Parser uses the Visitor pattern similarly to the UMLTextParser and also uses the SequenceDiagramInfo class in a manner similar to the uml info classes in order to store data needed for the creation of the sequence diagram syntax.

Who did what:
	For Milestone 3, Trevor and Emily once again pair programmed, working together to refactor existing code to increase functionality, and also implementing the sequence diagram requirements for this milestone. We took turns every once in a while to ensure that we submitted equal amounts of work. Emily wrote initial test cases, made the manual sequence diagrams and uml diagrams, and Trevor checked them and looked for any missing edges cases. Trevor generated the required sequence diagrams and updated all documentation needed for this milestone.
	
Instructions
	The user now uses the first command line argument to select a parser type, "uml" for a UML diagram and "sequence" for a sequence diagram. Then, for UML diagrams a list of classes, each with their package, is given as the remaining arguments. The system will now only show UML elements which are given as command line parameters. The Sequence Diagram parser first takes in the class name for the method, then takes in the method name, next it takes in the parameters for the method inside "<>" braces, space separated, and finally it optionally takes in the maximum depth for the sequence diagram to travers. If no maximum depth is given the system assumes a maximum depth of 5. 
	
Milestone 4:

Design:
	For this Milestone we added Singleton detection functionality. After refactoring our code to primarily use the Decorator patter when parsing for UMLs, we added a UMLSingletonParser that utilizes a SingletonVisitor that detects whether or not a class is a singleton, and then adds code to color the frame of the class and adds a tag beneath the name of the class, indicating that it is in fact a singleton.

Who did what:
	For Milestone 4, Trevor and Emily once again pair programmed, working together to refactor existing code, increase functionality, and finally implementing the Singleton detecting requirements set forth in the milestone. Trevor wrote the test cases and generated the UML diagrams. Emily made the manual UML diagram for the project in its current state, fixed the M3 Collections.shuffle manual sequence diagram, and updated the readme. We checked each other's work periodically as well.
	
Instructions
	Same as in the last milestone.
	
Milestone 5:

Design:
	For this Milestone we added Detector and Adapter detection functionality. After refactoring our code to more specifically use the Decorator patter when parsing for UMLs, we added two new parser classes and three visitors for each new type of detection. We also changed the design to now use the PatternDetection interface and three new detection classes which the parsers use in order to find different patterns. These detectors return strings of format <<PatternName>>-<<Role>>:<<Class>>~ with the role and class portions repeating as necessary.

Who did what:
	For Milestone 5, Trevor and Emily once again pair programmed, working together to refactor existing code, increase functionality, and finally implementing the new detecting requirements set forth in the milestone. Emily wrote the test cases Trevor generated the UML diagrams, made the manual UML diagram for the project in its current state, and updated the readme. We checked each other's work periodically as well.
	
Instructions
	Same as in the last milestone.

