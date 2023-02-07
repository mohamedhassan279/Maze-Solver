public class Queue {
	DoubleLinkedList Q = new DoubleLinkedList();

	public void enqueue(Object item){
		Q.add(item);
	}

	public Object dequeue(){
		Object temp = Q.get(0);
		Q.remove(0);
		return temp;
	}

	public boolean isEmpty(){
		return Q.isEmpty();
	}

	public int size(){
		return Q.size();
	}

	public void printQueue(){
		Q.printList();
	}
}
class DoubleLinkedList {
    public int length = 0;
    public static boolean flag;

    public class Node {
        Object item;
        Node next;
        Node prev;
    }

    public Node head = null;
    public Node tail = null;

    public void printList() {
        Node current = tail;
        if(head == null) {
            System.out.println("[]");
        }
        else {
            System.out.print("[" + current.item);
            current = current.prev;
            while (current != null) {
                System.out.print(", " + current.item);
                current = current.prev;
            }
            System.out.println("]");
        }
    }

    public void add(Object element) {
        Node newNode = new Node();
        newNode.item = element;
        if (length == 0) {
            newNode.next =newNode.prev= null;
            head = tail = newNode;
        }
        else {
            newNode.next = null;
            newNode.prev =tail;
            tail.next = newNode;
            tail = newNode;
        }
        length++;
    }

    public Object get(int index) {
        if(index < 0 || index >= length) {
            return 0;
        }
        else if(index == 0) {
            return head.item;
        }
        else if(index == length - 1) {
            return tail.item;
        }
        else {
            Node current = head;
            for(int i = 0; i < index; i++) {
                current = current.next;
            }
            return current.item;
        }
    }

    public boolean isEmpty() {
        if (length == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public void remove(int index) {
        flag = true;
        Node current = head;
        if(index < 0 || index >= length) {
            flag = false;
        }
        else if(length == 1) {
            head = tail = null;
            length--;
        }
        else if (index == 0) {
            head = head.next;
            head.prev=null;
            length--;
        }
        else if (index == length - 1) {
            for(int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            current.next = null;
            tail = current;
            length--;
        }
        else {
            for(int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            current.next = current.next.next;
            current.next.next.prev= current;
            length--;
        }
    }

    public int size() {
        return length;
    }
}
