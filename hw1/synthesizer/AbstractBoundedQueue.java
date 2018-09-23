package synthesizer;

public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    protected int fillCount;
    protected int capacity;
    @Override
    public int capacity(){
        return capacity;
    }
    @Override
    public int fillCount(){
        return fillCount;
    }
    @Override
    public boolean isEmpty(){
        return BoundedQueue.super.isEmpty();
    }
    @Override
    public boolean isFull(){
        return BoundedQueue.super.isFull();
    }
    @Override
    public abstract T peek();
    @Override
    public abstract T dequeue();
    @Override
    public abstract void enqueue(T x);

}
