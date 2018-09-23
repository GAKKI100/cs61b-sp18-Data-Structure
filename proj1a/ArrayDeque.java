

public class ArrayDeque<T> {
    private T[] arr;
    private int size;
    public int sentFront;
    public int sentBack;

    public ArrayDeque(){
        arr = (T[]) new Object[8];
        size = 0;
        sentFront = 0;
        sentBack = 1;
    }
    private void resizing(int x)
    {
        if (x == (2 * arr.length))
        {
            T[] a = (T[]) new Object[x];
            if (sentFront < sentBack)
            {
                System.arraycopy(arr, sentFront+1, a, sentFront+1, size);
            }
            else{
                System.arraycopy(arr,0, a, 0, sentBack);
                System.arraycopy(arr,sentFront+1, a, sentFront+1+arr.length,
                        size - sentBack);
                sentFront = sentFront + arr.length;
                arr = a;
            }
        }
        else{
            T[] a = (T [])new Object[x];
            if (sentFront < sentBack)
            {
                System.arraycopy(arr, sentFront+1, a, sentFront+1, size);
            }
            else{
                System.arraycopy(arr,0, a, 0, sentBack);
                System.arraycopy(arr,sentFront+1, a, sentFront+1-arr.length,
                        size - sentBack);
                sentFront = sentFront - arr.length;
                arr = a;
            }
        }
    }
    public void addFirst(T item){
        arr[sentFront] = item;
        sentFront = (arr.length + sentFront - 1) % arr.length;
        size++;
        if (size == arr.length - 3){
            resizing(arr.length * 2);
        }
    }

    public void addLast(T item){
        arr[sentBack] = item;
        sentBack = (sentBack + 1) % arr.length;
        size++;
        if (size == arr.length - 3){
            resizing(arr.length * 2);
        }
    }


    public T removeFirst(){
        if (isEmpty() == true)
        {
            return null;
        }
        T item = arr[sentFront+1];
        sentFront = (sentFront + 1) % arr.length;
        size--;
        if ((size == (arr.length / 4)) && (arr.length > 16)){
            resizing(arr.length / 2);
        }
        return item;
    }

    public T removeLast(){
        if (isEmpty() == true)
        {
            return null;
        }
        T item = arr[sentBack-1];
        sentBack = (arr.length + sentBack - 1) % arr.length;
        size--;
        if ((size == (arr.length / 4)) && (arr.length > 16)){
            resizing(arr.length / 2);
        }
        return item;
    }
    public int size(){
        return size;
    }
    public void printDeque()
    {
        if (sentFront < sentBack)
        {
            for (int i = sentFront+1; i < sentBack; i++ ){
                System.out.println(arr[i]);
            }
        }
        else{
            for (int i = sentFront+1; i < arr.length; i++){
                System.out.println(arr[i]);
            }
            for (int i = 0; i < sentBack; i++)
            {
                System.out.println(arr[i]);
            }
        }
    }
    public boolean isEmpty()
    {
        if ((sentBack-sentFront+arr.length) % arr.length == 1)
        {
            return true;
        }
        else{
            return false;
        }
    }
    public T get(int index){
        if ((sentFront < sentBack) && (index > sentFront)&& (index < sentBack))
        {
            return arr[index];
        }
        else if((sentFront > sentBack) && ((index > sentFront) || (index < sentBack)))
            return arr[index];
        else{
            return null;
        }

    }
}
