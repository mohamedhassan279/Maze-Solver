public class Stack {
SingleLinkedList stack = new SingleLinkedList();
    
    public Object pop(){
        if(stack.isEmpty())
            return null;
        Object temp = stack.get(0);
        stack.remove(0);
        return temp;
    }
  
    public Object peek(){
        if(stack.isEmpty())
            return null;
        return stack.get(0);
    }
  
    public void push(Object element){
        stack.insertFirst(element);
    }
  
    public boolean isEmpty(){
        return stack.isEmpty();
    }
  
    public int size(){
        return stack.size();
    }
    
    public void printStack(){
        stack.printList();
    }
}

class SingleLinkedList {
    public int length = 0;
    public static boolean flag;

    public class Node {
        Object item;
        Node next;
    }

    public Node head = null;
    public Node tail = null;

    public void insertFirst(Object element) {
        Node newNode = new Node();
        newNode.item = element;
        if (length == 0) {
            newNode.next = null;
            head = tail = newNode;
        } 
        else {
            newNode.next = head;
            head = newNode;
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
            length--;
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

    public int size() {
        return length;
    }
    
     public void printList() {
        Node current = head;
        if(current == null) {
            System.out.println("[]");
        }
        else {
            System.out.print("[" + current.item);
            current = current.next;
            while (current != null) {
                System.out.print(", " + current.item);
                current = current.next;
            }
            System.out.println("]");
        }
    }
}
