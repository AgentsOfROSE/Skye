# Skye
CSSE 375 Term Project For Trevor Burch &amp; Emily Richardson

Design: 
	Our design uses the Visitor pattern, as discussed in lecture. The UMLTextParser handles the control flow of the system. This class uses various classes who extend ClassVisitor in order to handle various tasks needed in order to generate the dot code for GraphViz to take in.
	
Who did what:
	For Milestone 1, Trevor and Emily designed and pair programmed all of the class blocks and debugged the code. We switched off periodically to make sure we both did about the same amount of work. Emily also handled submitting the GitHub link and Trevor handled generating and committing the UML pictures.


Instructions:
	Set up the Run Configurations to have the Arguments include the paths of the classes whose UML you want to generate GraphViz code for. Then run the UMLTestParrser class. Output will appear in the console. Copy and paste this code into a new GV sheet. Then run GraphViz by clicking Settings and then OK. A picture of the UML should then appear.