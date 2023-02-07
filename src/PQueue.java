public class PQueue {
	class Node{
		Object elem;
		int key;
		public Node(Object elem, int key) {
			this.elem = elem;
			this.key = key;
		}
	}
	
	public final int maxSize = 256;
	public Node[] queArray = new Node[maxSize];
	public static int nItems = 0;
	
	public void insert(Object elem, int key) {
		Node myNode = new Node(elem, key);
		if (nItems == 0) {
			queArray[nItems++] = myNode;
		}
		else {
			int j;
			for (j = nItems - 1; j >= 0; j--) {
				if (myNode.key > queArray[j].key)
					queArray[j + 1] = queArray[j];
				else
					break;
			}
			queArray[j + 1] = myNode;
			nItems++;
		}
	}
	
	public Object removeMin() {
		nItems--;
		return queArray[nItems].elem;
	}
	
	public Object min() {
		return queArray[nItems-1];
	}
	
	public boolean isEmpty() {
		return (nItems == 0);
	}
	
	public int size() {
		return nItems;
	}
	
	public void printQueue() {
		System.out.print("{");
		for(int i = nItems - 1; i >= 0; i--) {
			System.out.print(queArray[i].elem);
			if(i != 0) {
				System.out.print(", ");
			}
		}
		System.out.println("}");
	}
}
