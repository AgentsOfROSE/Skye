# Skye
CSSE 375 Term Project For Trevor Burch &amp; Emily Richardson

Milestone 1:

Design: 
	Our design uses the Visitor pattern, as discussed in lecture. The UMLTextParser handles the control flow of the system. This class uses various classes who extend ClassVisitor in order to handle various tasks needed in order to generate the dot code for GraphViz to take in. The design also uses different "Info" objects which store information about different fields, methods, and classes. These classes are useful as we are able to store more information on a single run through of the classes than would be possible if we were printing the UML as we visited the different classes, methods, and fields. Our design also strives to follow the Command-Query design principle through the use of getters and setters and method that only return something or affect something but not both.
	
Who did what:
	For Milestone 1, Trevor and Emily designed and pair programmed all of the class blocks and debugged the code. We switched off periodically to make sure we both did about the same amount of work. Emily also handled submitting the GitHub link and Trevor handled generating and committing the UML pictures.


Instructions:
	Set up the Run Configurations to have the Arguments include the paths of the classes whose UML you want to generate GraphViz code for. Then run the UMLTestParrser class. Output will appear in the console. Copy and paste this code into a new GV sheet. Then run GraphViz by clicking Settings and then OK. A picture of the UML should then appear.
	
Milestone 2:

