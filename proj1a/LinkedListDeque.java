public class LinkedListDeque <T>{
    private Node sentFront = new Node(null, null, null);
    private Node sentBack = new Node(null, null, null);
    private int size;

    private class Node{
        public Node head;
        public Node rear;
        T item;

        private Node(Node h, Node rr, T i){
            head = h;
            rear = rr;
            item = i;
        }
    }
    public LinkedListDeque(){
        sentFront.rear = sentBack;
        sentBack.head = sentFront;
        size = 0;
    }
    public void addFirst(T item){
        sentFront.rear = new Node(sentFront, sentFront.rear, item);
        sentFront.rear.rear.head = sentFront.rear;
        size++;
    }
    public void addLast(T item){
        sentBack.head = new Node(sentBack.head, sentBack, item);
        sentBack.head.head.rear = sentBack.head;
        size++;
    }
    public T removeFirst(){
        T temp = sentFront.rear.item;
        sentFront.rear.rear.head = sentFront;
        sentFront.rear = sentFront.rear.rear;
        size--;
        return temp;
    }
    public T removeLast(){
        T temp = sentBack.head.item;
        sentBack.head.head.rear = sentBack;
        sentBack.head = sentBack.head.head;
        size--;
        return temp;
    }
    public boolean isEmpty(){
        if (sentFront.rear == sentBack)
        {
            return true;
        }
        else
        {
            return  false;
        }
    }
    public int size(){
        return size;
    }
    public void printDeque(){
        Node p = sentFront.rear;

        while (p != null){
            System.out.println(p.item);
            p = p.rear;
        }
    }
    public T get(int index){
        Node p = sentFront.rear;
        for(int i = 0; i < index; i++){
            p = p.rear;
        }
        return p.item;
    }
    public T getRecursive(int index){
        Node p = sentFront.rear;
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

/*public class LinkedListDeque <T>{
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
    public void addFirst(T item){
        sentinal.rear = new Node(sentinal, sentinal.rear, item);
        sentinal.rear.rear.head = sentinal.rear;
        size++;
    }
    public void addLast(T item){
        sentinal.head = new Node(sentinal.head, sentinal, item);
        sentinal.head.head.rear = sentinal.head;
        size++;
    }
    public T removeFirst(){
        T temp = sentinal.rear.item;
        sentinal.rear = sentinal.rear.rear;
        sentinal.rear.head = sentinal;
        size--;
        return temp;
    }
    public T removeLast(){
        T temp = sentinal.head.item;
        sentinal.head = sentinal.head.head;
        sentinal.head.rear = sentinal;
        size--;
        return temp;
    }
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
    public int size(){
        return size;
    }
    public void printDeque(){
        Node p = sentinal.rear;

        while (p != sentinal){
            System.out.println(p.item);
            p = p.rear;
        }
    }
    public T get(int index){
        Node p = sentinal.rear;
        for(int i = 0; i < index; i++){
            p = p.rear;
        }
        return p.item;
    }
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
}*/