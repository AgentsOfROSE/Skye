package umlParser;

public class RoseHulmanSGAPresident extends RoseHulmanStudent {

	private static final RoseHulmanSGAPresident president = new RoseHulmanSGAPresident();
	
	private RoseHulmanSGAPresident() {
	}
	
	public static RoseHulmanSGAPresident getPresident(){
		return president;
	}

}
