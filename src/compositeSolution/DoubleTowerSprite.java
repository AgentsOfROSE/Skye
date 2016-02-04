package compositeSolution;

import java.awt.Dimension;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Iterator;

public class DoubleTowerSprite extends AbstractSprite {

	ArrayList<AbstractSprite> spriteComponents = new ArrayList<AbstractSprite>();
	
	public DoubleTowerSprite(double x, double y, double width, double height) {
		super(x, y, width, height);
		spriteComponents.add(new TowerSprite(x, y, width, height));
		spriteComponents.add(new TowerSprite(x, y + y, 2*width, 2*height));
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
