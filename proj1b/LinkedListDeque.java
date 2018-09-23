public class LinkedListDeque <T> implements Deque<T>{
    private Node sentinal = new Node(null, null, null);
    private int size;

    private class Node{
        T item;
        public Node head;
        public Node rear;

        private Node(Node h, Node r, T i){
            head = h;
            rear = r;
            item = i;
        }
    }
    public LinkedListDeque(){
        sentinal.rear = sentinal;
        sentinal.head = sentinal;
        size = 0;
    }
    @Override
    public void addFirst(T item){
        sentinal.rear = new Node(sentinal, sentinal.rear, item);
        sentinal.rear.rear.head = sentinal.rear;
        size++;
    }
    @Override
    public void addLast(T item){
        sentinal.head = new Node(sentinal.head, sentinal, item);
        sentinal.head.head.rear = sentinal.head;
        size++;
    }
    @Override
    public T removeFirst(){
        T temp = sentinal.rear.item;
        sentinal.rear = sentinal.rear.rear;
        sentinal.rear.head = sentinal;
        size--;
        return temp;
    }
    @Override
    public T removeLast(){
        T temp = sentinal.head.item;
        sentinal.head = sentinal.head.head;
        sentinal.head.rear = sentinal;
        size--;
        return temp;
    }
    @Override
    public boolean isEmpty(){
        if (sentinal.rear == sentinal)
        {
            return true;
        }
        else
        {
            return  false;
        }
    }
    @Override
    public int size(){
        return size;
    }
    @Override
    public void printDeque(){
        Node p = sentinal.rear;

        while (p != sentinal){
            System.out.println(p.item);
            p = p.rear;
        }
    }
    @Override
    public T get(int index){
        Node p = sentinal.rear;
        for(int i = 0; i < index; i++){
            p = p.rear;
        }
        return p.item;
    }
    @Override
    public T getRecursive(int index){
        Node p = sentinal.rear;
        p = getRecursiveHelper(index, p);
        return p.item;
    }
    private Node getRecursiveHelper(int index, Node p){
        if (index == 0)
        {
            return p;

        }
        p = p.rear;
        p = getRecursiveHelper(index - 1, p);
        return p;
    }
}