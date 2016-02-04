package compositeSolution;

import java.awt.Dimension;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Iterator;

public class TowerSprite extends AbstractSprite {

	ArrayList<AbstractSprite> spriteComponents = new ArrayList<AbstractSprite>();
	
	public TowerSprite(double x, double y, double width, double height) {
		super(x, y, width, height);
		spriteComponents.add(new RectangleSprite(x+width/4, y, width/2, height/3));
		spriteComponents.add(new RectangleSprite(x+width/6, y + height /3, 2*width/3, height/3));
		spriteComponents.add(new RectangleSprite(x, y + 2* height /3, width, height/3));
	}
	
	@Override
	public Shape getShape(){
		return null;
	}

	@Override
	public void move(Dimension space) {
		for(ISprite sprite : spriteComponents){
			sprite.move(space);
		}
	}
	
	
	@Override
	public Iterator<AbstractSprite> createIterator(){
		return new CompositeIterator(spriteComponents.iterator());
	}


}
