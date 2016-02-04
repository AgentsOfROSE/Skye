package compositeSolution;

import java.awt.Dimension;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

public class CircleSprite extends AbstractSprite {

	public CircleSprite(double x, double y, double width, double height) {
		super(x, y, width, height);
		shape = new Ellipse2D.Double(x, y, width, height);
	}
	
	@Override
	public Shape getShape(){
		return this.shape;
	}

	@Override
	public void move(Dimension space) {
		Rectangle2D bounds = this.computeNewBoundsAfterMoving(space);
		shape = new Ellipse2D.Double(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
	}
	
	@Override
	public Iterator<AbstractSprite> createIterator(){
		return new NullIterator();
	}
}
