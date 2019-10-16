import java.util.Stack;

public class Undostack {

	Stack<Action> actions = new Stack<Action>();

	public void push(Action action) {
		actions.push(action);
	}

	public Action pop() {
		return actions.pop();
	}
}
