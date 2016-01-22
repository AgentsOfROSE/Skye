package umlParser;

public class RoseHulmanSABPresident extends RoseHulmanStudent {

	private static RoseHulmanSABPresident president;
	
	private RoseHulmanSABPresident() {
	}
	
	public RoseHulmanSABPresident getPresident(){
		if(president == null){
			president = new RoseHulmanSABPresident();
		}
		return president;
	}
}
