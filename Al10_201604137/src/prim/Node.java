package prim;

public class Node {
	Node parent;
	int key;
	
	public Node(Node parent, int key) {
		super();
		this.parent = parent;
		this.key = key;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}
	
	
}
