import java.util.*;

public class BSTMap<k extends Comparable<k>,v> implements Map61B<k,v> {
    private node BST;
    int size = 0;

    private class node {
        k key;
        v val;
        node left;
        node right;

        node(k k, v v, node l, node r) {
            key = k;
            val = v;
            left = l;
            right = r;
        }
    }

    @Override
    public void put(k key, v value) {
//        node temp = BST;
        BST=put_helper(key, value, BST);
    }

    private node put_helper(k key, v value, node temp){
        if(temp==null){
            size++;
            return new node(key,value,null,null);
        }
        int cmp=key.compareTo(temp.key);
        if(cmp<0){
            temp.left=put_helper(key,value,temp.left);
        } else if (cmp>0) {
            temp.right=put_helper(key,value,temp.right);
        }else{
            temp.val=value;
        }
        return temp;
    }

//    private void put_helper(k key, v value, node temp) {
//        if (temp == null) {
//            BST = new node(key, value, null, null);
//            size++;
//        } else if (key.compareTo(temp.key) == 0) {
//            temp.val = value;
//        } else if (key.compareTo(temp.key) < 0) {
//            if (temp.left == null) {
//                temp.left = new node(key, value, null, null);
//                size++;
//                return;
//            }
//            put_helper(key, value, temp.left);
//        } else if (key.compareTo(temp.key) > 0) {
//            if (temp.right == null) {
//                temp.right = new node(key, value, null, null);
//                size++;
//                return;
//            }
//            put_helper(key, value, temp.right);
//        }
//    }

    @Override
    public v get(k key) {
        node temp = helper(key,BST);
        return (temp!=null) ? temp.val : null;
    }

//    private v get_helper(k key, node temp) {
//        if (temp == null) {
//            return null;
//        } else if (key.compareTo(temp.key) == 0) {
//            return temp.val;
//        } else if (key.compareTo(temp.key) < 0) {
//            return get_helper(key, temp.left);
//        } else if (key.compareTo(temp.key) > 0) {
//            return get_helper(key, temp.right);
//        } else {
//            return null;
//        }
//    }

    @Override
    public boolean containsKey(k key) {
        return (helper(key,BST)!=null) ? true : false;
    }

//    private boolean containsKey_helper(k key,node temp){
//        if(temp==null) return false;
//        if(key.compareTo(temp.key)==0) {
//            return true;
//        } else if (key.compareTo(temp.key)<0) {
//            return containsKey_helper(key,temp.left);
//        } else if (key.compareTo(temp.key)>0) {
//            return containsKey_helper(key,temp.right);
//        } else{
//            return false;
//        }
//    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
//        node temp = BST;
//        clear_helper(temp);
        BST = null;
        size = 0;
    }

//    private void clear_helper(node temp) {
//        if (temp == null) {
//            return;
//        }
//        node left_child = temp.left;
//        node right_child = temp.right;
//        temp.left = null;
//        temp.right = null;
//        clear_helper(left_child);
//        clear_helper(right_child);
//    }

    private node helper(k key,node cur){
        if(cur==null) return null;
        int cmp=key.compareTo(cur.key);
        if (cmp<0) {
            return helper(key,cur.left);
        } else if (cmp>0) {
            return helper(key,cur.right);
        } else {
            return cur;
        }
    }

    @Override
    public Set<k> keySet() {
        Set<k> sets=new HashSet<>();
        inOrderTraversal(sets,BST);
        return sets;
    }

    private void inOrderTraversal(Set<k> sets,node cur){
        if(cur==null){
            return;
        }
        inOrderTraversal(sets,cur.left);
        sets.add(cur.key);
        inOrderTraversal(sets,cur.right);
    }

    //written by gemini:remove()
    // 完整 BSTMap 类的一部分，只包含 remove 方法和其辅助方法
    @Override
    public v remove(k key) {
        // 调用私有辅助方法，并更新根节点
        node nodeToRemove = helper(key,BST);
        if (nodeToRemove == null) {
            return null;
        }
        v value = nodeToRemove.val;
        BST= removeHelper(key,BST);
        return value;
    }

    private node removeHelper(k key,node cur) {
        if (cur == null) {
            return null;
        }

        int cmp = key.compareTo(cur.key);

        if (cmp < 0) {
            cur.left = removeHelper(key,cur.left);
        } else if (cmp > 0) {
            cur.right = removeHelper(key,cur.right);
        } else { // 找到了要删除的节点
            // 情况1: 没有子节点或只有一个子节点
            if (cur.left == null) {
                size--;
                return cur.right;
            }
            if (cur.right == null) {
                size--;
                return cur.left;
            }

            // 情况2: 有两个子节点
            // 找到右子树中最小的节点（或称为后继节点）
            node successor = findMin(cur.right);

            // 用后继节点的值替换当前节点的值
            cur.key = successor.key;
            cur.val = successor.val;

            // 递归地删除后继节点
            cur.right = removeHelper(successor.key,cur.right);
        }
        return cur;
    }

    // 辅助方法：找到子树中的最小节点
    private node findMin(node node) {
        if (node.left == null) {
            return node;
        }
        return findMin(node.left);
    }

    @Override
    public Iterator iterator() {
        return new BSTMapiter();
    }

    private class BSTMapiter implements Iterator<k>{
        Stack<node> stack;

        BSTMapiter(){
            stack=new Stack<>();
            pushleft(BST);
        }

        private void pushleft(node cur){
            if(cur==null){
                return;
            }
            stack.push(cur);
            pushleft(cur.left);
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public k next() {
            if(!hasNext()){throw new NoSuchElementException();}
            node temp=stack.pop();
            if(temp.right!=null) {
                pushleft(temp);
            }
            return temp.key;
        }

//        node cur=BST;
//
//        @Override
//        public boolean hasNext() {
//            return cur!=null;
//        }
//
//        @Override
//        public k next() {
//            k item=next_helper(cur);
//            return item;
//        }
//
//        private k next_helper(node cur){
//            if(cur.left!=null && cur.left.left==null && cur.left.right==null){
//                k temp=cur.left.key;
//                cur.left=null;
//                return temp;
//            }else if(cur.right!=null && cur.right.left==null && cur.right.right==null){
//                k temp=cur.right.key;
//                cur.right=null;
//                return temp;
//            }
//            k temp=next_helper(cur.left);
//            if(temp!=null){ return temp;}
//            temp=next_helper(cur.right);
//            if(temp!=null){
//                return temp;
//            } else{
//                return null;
//            }
//        }
    }

//    printInOrder()
}
