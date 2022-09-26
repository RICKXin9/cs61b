public class LinkedListDeque<T> {

    private TNode sentinel;
    private int size = 0;
    public LinkedListDeque(){
        sentinel = new TNode(null,null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }
    public void addFirst(T x){
        TNode newNode = new TNode(x,sentinel,sentinel.next);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size += 1;
    }
    public T removeFirst(){
        if (size == 0){
            return null;
        }
        T remain = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return remain;
    }
    public void addLast(T x){
        TNode newNode = new TNode(x,sentinel.prev,sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size += 1;
    }
    public T removeLast(){
        T item = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size -= 1;
        return item;
    }
    public int size(){
        return size;
    }
    public boolean isEmpty(){
        if (size == 0){
            return true;
        }
        return false;
    }
    public void printDeque(){
        String s = "";
        if (isEmpty()){
            return;
        }
        TNode p = sentinel.next;
        while (p != sentinel){
            s = p.item + s + " ";
            p = p.next;
        }
        System.out.print(s);
    }
    public T get(int index){
        if (index>size-1){
            return null;
        }
        int num = index;
        TNode p = sentinel.next;
        while (num != 0){
            num -= 1;
            p = p.next;
        }
        return p.item;
    }

    public T getRecursiveHelp(TNode start,int index){
        if (index==0){
            return start.item;
        }
        return getRecursiveHelp(start.next,index-1);
    }
    public T getRecursive(TNode t,int index){
        if (index>size-1){
            return null;
        }
        return getRecursiveHelp(sentinel.next,index);
    }
    private class TNode{
        public T item;
        public TNode prev;
        public TNode next;
        public TNode(T i ,TNode p, TNode n){
            item = i;
            prev = p;
            next = n;
        }
        public TNode(TNode p,TNode n){
            prev = p;
            next = n;
        }

    }
}
