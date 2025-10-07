package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int key_num=0;
    private double loadFactor=0.75;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        int initialCapacity=16;
        buckets=new Collection[initialCapacity];
        for(int i = 0; i < buckets.length; i++){
            buckets[i] = createBucket();
        }
    }

    public MyHashMap(int initialCapacity) {
        buckets=new Collection[initialCapacity];
        for(int i = 0; i < buckets.length; i++){
            buckets[i] = createBucket();
        }
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.loadFactor=loadFactor;
        buckets=new Collection[initialCapacity];
        for(int i = 0; i < buckets.length; i++){
            buckets[i] = createBucket();
        }
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        // TODO: Fill in this method.
        return new LinkedList<>();
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    // resize the length of buckets
    private void resizeBuckets(){
        Collection<Node>[] temp= new Collection[buckets.length*2];
        for(int i = 0; i < temp.length; i++){
            temp[i] = createBucket();
        }
        for(int i = 0; i < buckets.length; i++){
            for(Node node :buckets[i]){
                temp[Math.floorMod(node.key.hashCode(), temp.length)].add(node);
            }
        }
        buckets=temp;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
//        assert value!=null;
        if(containsKey(key)){
            Collection<Node> temp=buckets[Math.floorMod(key.hashCode(), buckets.length)];
            for(Node node: temp){
                if(node.key.equals(key)){
                    node.value=value;
                }
            }
            return;
        }
        Node node=new Node(key,value);
        buckets[Math.floorMod(key.hashCode(), buckets.length)].add(node);
        key_num++;
        if((double) key_num /buckets.length>loadFactor){
            resizeBuckets();
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        Collection<Node> temp=buckets[Math.floorMod(key.hashCode(), buckets.length)];
        for(Node node: temp){
            if(node.key.equals(key)){
                return node.value;
            }
        }
        return null;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        Collection<Node> temp=buckets[Math.floorMod(key.hashCode(), buckets.length)];
        for(Node node: temp){
            if(node.key.equals(key)){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return key_num;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        for(int i=0;i< buckets.length;i++){
            buckets[i].clear();
        }
        key_num=0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for this lab.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
//        return Set.of();
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
//        return null;
        throw new UnsupportedOperationException();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator(){
        return new MyHashMapIte();
    }

    private class MyHashMapIte implements Iterator<K>{
        int i=0;

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return i< buckets.length;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public K next() {
            if(hasNext()) {
                Iterator iteretor = buckets[i].iterator();
                if (iteretor.hasNext()) {
                    return (K) iteretor.next();
                } else {
                    i++;
                    next();
                }
            }
            return null;
        }
    }
}
