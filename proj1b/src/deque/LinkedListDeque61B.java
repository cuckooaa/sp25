package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    private Node<T> sentinel;
    private int size;

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
    public boolean equals(Object o){
        if(this==o){return true;}
        if(o instanceof Deque61B<?>){
            if (this.size != ((Deque61B<?>) o).size()){ return false; }
            Iterator<?> itero=((Deque61B<?>) o).iterator();
            for (T x : this) {
                if (x!=itero.next()) {return false;}
            }
            return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new lldIterator();
    }

    private class lldIterator implements Iterator<T>{
        private Node<T> p;

        public lldIterator(){
            p=sentinel.next;
        }

        @Override
        public boolean hasNext() {
            if(size==0){
                return false;
            }
            return true;
        }

        @Override
        public T next() {
            T temp=p.item;
            p=p.next;
            return temp;
        }
    }

    private class Node<T>{
        T item;
        Node<T> next;
        Node<T> pre;

        public Node(T item){
            this.item=item;
        }
    }

    public LinkedListDeque61B(){
        sentinel=new Node<>(null);
        sentinel.next=sentinel;
        sentinel.pre=sentinel;
        size=0;
    }

    @Override
    public void addFirst(T x) {
        Node<T> a=new Node<>(x);
        a.pre=sentinel;
        a.next=sentinel.next;
        sentinel.next.pre=a;
        sentinel.next=a;
        size++;
    }

    @Override
    public void addLast(T x) {
        Node<T> a=new Node<>(x);
        a.pre=sentinel.pre;
        a.next=sentinel;
        sentinel.pre.next=a;
        sentinel.pre=a;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> list=new ArrayList<>();
        Node<T> temp=sentinel.next;
        for(int i=size;i>0;i--){
            list.add(temp.item);
            temp=temp.next;
        }
        return list;
    }

    @Override
    public boolean isEmpty() {
        return sentinel.next==sentinel && sentinel.pre==sentinel;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if(size>0){
            Node<T> temp=sentinel.next;
            sentinel.next=sentinel.next.next;
            sentinel.next.pre=sentinel;
            size--;
            return temp.item;
        }
        return null;
    }

    @Override
    public T removeLast() {
        if(size>0){
            Node<T> temp=sentinel.pre;
            sentinel.pre=sentinel.pre.pre;
            sentinel.pre.next=sentinel;
            size--;
            return temp.item;
        }
        return null;
    }

    @Override
    public T get(int index) {
        if(index<size && index>-1){
            Node<T> p=sentinel.next;
            for(int i=index;i>0;i--){
                p=p.next;
            }
            return p.item;
        }
        return null;
    }

    @Override
    public T getRecursive(int index) {
        if(index>=size || index<0){
            return null;
        }
        Node<T> p=sentinel.next;
        return helper(p,index);
    }

    public T helper(Node<T> p,int index){
        if(index==0) return p.item;
        return helper(p.next,index-1);
    }
}
