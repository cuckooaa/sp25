package deque;

import java.util.ArrayList;
import java.util.Iterator;
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

    @Override
    public String toString(){
        if(isEmpty()) {return "[]";}
        Iterator<T> iterator = this.iterator();
        String result="[";
        if(iterator.hasNext()){
            result+=iterator.next();
        }
        while(iterator.hasNext()){
            result+=","+iterator.next();
        }
        return result+="]";
    }

    @Override
    public boolean equals(Object o) {
        if(this==o){return true;}
        if(o instanceof Deque61B<?>){
            if (this.size != ((Deque61B<?>) o).size()){ return false; }
            Iterator<?> itero= ((Deque61B<?>) o).iterator();
            for (T x : this) {
                if (x!=itero.next()) {return false;}
            }
            return true;
        }
        return false;
    }


    @Override
    public Iterator<T> iterator() {
        return new adIterator();
    }

    private class adIterator implements Iterator<T>{
        private int p;

        public adIterator(){
            p=nextfirst;
        }

        @Override
        public boolean hasNext() {
            if(size==0) {
                return false;
            }
            return true;
        }

        @Override
        public T next() {
            p=Math.floorMod(++p,a.length);
            return a[p];
        }
    }

    public int alength(){
        return a.length;
    }

    public T[] resizeup(T[] a){
        return helper(a,size*2);
    }

    public T[] resizedown(T[] a){
        return helper(a,a.length/2);
    }

    public T[] helper(T[] a,int x){
        T[] temp=(T[]) new Object[x];
        int f=0,l=1;
        for(int i=size;i>0;i--){
            temp[l++]=a[Math.floorMod(++nextfirst,size)];
        }
        nextfirst=f;
        nextlast=l;
        return temp;
    }

    @Override
    public void addFirst(T x) {
        if(size==a.length){
            a=resizeup(a);
        }
        a[nextfirst]=x;
        nextfirst=Math.floorMod(nextfirst-1,a.length);
        size++;
    }

    @Override
    public void addLast(T x) {
        if(size==a.length){
            a=resizeup(a);
        }
        a[nextlast]=x;
        nextlast=Math.floorMod(nextlast+1,a.length);
        size++;
    }

    @Override
    public List<T> toList() {
        if(size==0){
            return null;
        }
        List<T> returnList = new ArrayList<>();
        int f=nextfirst;
        while(Math.floorMod(++f,a.length)!=nextlast){
            returnList.add(a[Math.floorMod(f,a.length)]);
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if(size==0){
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
        if(isEmpty()){
            return null;
        }
        if(a.length>=16 && size*4<=a.length){
            a=resizedown(a);
        }
        nextfirst=Math.floorMod(nextfirst+1,a.length);
        T temp=a[nextfirst];
        size--;
        return temp;
    }

    @Override
    public T removeLast() {
        if(isEmpty()){
            return null;
        }
        if(a.length>=16 && size*4<=a.length){
            a=resizedown(a);
        }
        nextlast=Math.floorMod(nextlast-1,a.length);
        T temp=a[nextlast];
        size--;
        return temp;
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
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }
}


