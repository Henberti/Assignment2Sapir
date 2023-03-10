package Task1.ADT;
public class Node<T> {//HEN BERTI 201381407 && ELIRAN BALAISH 207598467
    T data;
    Node<T> next;

    public Node(T data, Node<T> next){
        this.data = data;
        this.next = next;
    }

    public Node(T data){
        this(data, null);
    }

    public T getData() {
        return data;
    }
    public Node<T> getNext() {
        return next;
    }
    public void setNext(Node<T> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return data+"->"+next;
    }
    public boolean hasNext(){
        return next != null;
    }
    
}
