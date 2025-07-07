package deque;

import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] a;
    private int nextfirst;
    private int nextlast;
    private int size;

    public ArrayDeque61B() {
        a=(T[]) new Object[8];
        nextfirst=0;
        nextlast=1;
        size=0;
    }

    public T[] resize(T[] a){
        T[] temp=(T[]) new Object[size*2];
        int f=0,l=1;
        while(nextfirst!=nextlast){
            temp[l++]=a[Math.floorMod(++nextfirst,size)];
        }
        a=temp;
        nextfirst=f;
        nextlast=l;
        return a;
    }

    @Override
    public void addFirst(T x) {
        if(size==a.length){
            resize(a);
        }
        a[nextfirst]=x;
        nextfirst=Math.floorMod(nextfirst-1,a.length);
        size++;
    }

    @Override
    public void addLast(T x) {
        if(size==a.length){
            resize(a);
        }
        a[nextlast]=x;
        nextlast=Math.floorMod(nextlast+1,a.length);
        size++;
    }

    @Override
    public List<T> toList() {
        return List.of();
    }

    @Override
    public boolean isEmpty() {
        if(Math.floorMod(nextfirst+1,a.length)==nextlast){
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        return null;
    }

    @Override
    public T removeLast() {
        return null;
    }

    @Override
    public T get(int index) {
        if(index<0 || index>=size){
            return null;
        }
        return a[Math.floorMod(nextfirst+index+1,a.length)];
    }

    @Override
    public T getRecursive(int index) {
        return null;
    }
}
