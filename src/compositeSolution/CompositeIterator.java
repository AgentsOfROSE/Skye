package compositeSolution;

import java.util.Iterator;
import java.util.Stack;

public class CompositeIterator implements Iterator<AbstractSprite>{

	Stack<Iterator<AbstractSprite>> stack = new Stack<Iterator<AbstractSprite>>();
	
	public CompositeIterator(Iterator<AbstractSprite> iterator) {
		stack.push(iterator);
	}
	
	public AbstractSprite next() {
		if (hasNext()) {
			Iterator<AbstractSprite> iterator = stack.peek();
			AbstractSprite sprite = iterator.next();
			
			stack.push(sprite.createIterator());
			
			return sprite;
		} else {
			return null;
		}
	}
	
	public boolean hasNext() {
		if (stack.empty()){
			return false;
		} else {
			Iterator<AbstractSprite> iterator = stack.peek();
			if (!iterator.hasNext()) {
				stack.pop();
				return hasNext();
			} else {
				return true;
			}
		}
	}

}
