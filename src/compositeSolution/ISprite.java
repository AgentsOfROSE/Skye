package compositeSolution;

import java.awt.Dimension;
import java.awt.Shape;
import java.util.Iterator;

public interface ISprite {
	public void move(Dimension space);
	public Shape getShape();
	public Iterator<AbstractSprite> createIterator();
}
