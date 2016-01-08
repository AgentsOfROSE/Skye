# Skye
CSSE 375 Term Project For Trevor Burch &amp; Emily Richardson

Milestone 1:

Design: 
	Our design uses the Visitor pattern, as discussed in lecture. The UMLTextParser handles the control flow of the system. This class uses various classes who extend ClassVisitor in order to handle various tasks needed in order to generate the dot code for GraphViz to take in. The design also uses different "Info" objects which store information about different fields, methods, and classes. These classes are useful as we are able to store more information on a single run through of the classes than would be possible if we were printing the UML as we visited the different classes, methods, and fields. Our design also strives to follow the Command-Query design principle through the use of getters and setters and method that only return something or affect something but not both.
	
Who did what:
	For Milestone 1, Trevor and Emily designed and pair programmed all of the class blocks and debugged the code. We switched off periodically to make sure we both did about the same amount of work. Emily also handled submitting the GitHub link and Trevor handled generating and committing the UML pictures. We also worked together to write appropriate test cases.


Instructions:
	Set up the Run Configurations to have the Arguments include the paths of the classes whose UML you want to generate GraphViz code for. Then run the UMLTestParrser class. Output will appear in the console. Copy and paste this code into a new GV sheet. Then run GraphViz by clicking Settings and then OK. A picture of the UML should then appear.
	
Milestone 2:

Design:
	For this Milestone, we used our refactored code from the first Milestone and expanded the functionality to support the addition of 'uses' and 'associates' arrows in our graphViz dot language. We still use the Visitor Pattern and follow the Command-Query design principle as did before. Due to the use of our "Info" classes we were able to make these additions with very little modification to the overall design of our system. 

Who did what:
	For Milestone 2, Trevor and Emily pair programmed, working together to refactor existing code to increase functionality, and also implementing the 'uses' and 'associates' requirements for this milestone. We took turns every once in a while to ensure that we submitted equal amounts of work. We also worked together to generate the manually drawn UML diagram and pair programmed the test cases. We also fully supported each other while staying up late to finish the milestone and going on a midnight Taco Bell run to destress and get some nom noms. (<-- Team matureness shown here ;) Hail Hydra!)
	
Instructions
	See above. We hope to streamline the process of adding classes to be handled in the future along with allowing the user to disable packages such as "java.lang" from being shown on the final UML.

