
/*对于索引减去一的情况而言，最佳实现为（index+length-1）%length
对于索引加上一的情况而言，最佳实现为（index+1）%length
以上都可以得到想要的索引序号
* */
public class ArrayDeque<T> implements Deque<T>{
//  length为array的长度，而size为填充的内容的大小
    private T[] items;
    private int size;
    private int length;
    private int nextFirst;
    private int nextLast;
    public ArrayDeque(){
        items = (T[]) new Object[8];
        size =0;
        length = 8;
        nextFirst = 0;
        nextLast = 1;
    }
    @Override
    public void addFirst(T x){

        if (size == length) {
            this.expand();
        }
        items[nextFirst] = x;
        nextFirst = minusOne(nextFirst,length);
        size += 1;
    }
    @Override
    public void addLast(T x){
        if (size == length) {
            this.expand();
        }
        items[nextLast] = x;
        nextLast = plusOne(nextLast,length);
        size +=1;
    }
    @Override
    public boolean isEmpty(){
        if (size ==0){
            return true;
        }
        return false;
    }
    @Override
    public int size(){
        return size;
    }
    @Override
    public void printDeque(){
        if (this.isEmpty()){
            return;
        }
        else{
            String s = "";
            if ((nextFirst + 1) % length == 0 || nextFirst <= nextLast) {
                for (int i = (nextFirst + 1) % length; i <= nextLast - 1; i ++) {
                    T ret = items[i];
                    s = s + ret + " ";
                }
            }
            System.out.print(s);
        }
    }
    @Override
    public T removeFirst(){
        if (size == 0){
            return null;
        }
        double dsize = Double.valueOf(size);
        double dlength = Double.valueOf(length);
        if (((dsize-1)/dlength<=0.25)&&length>=16){
            this.shrink();
        }
        int point = plusOne(nextFirst,length);
        T ret = items[point];
        items[point] = null;
        nextFirst = point;
        size -= 1;
        return ret;
    }
    @Override
    public T removeLast(){
        if (this.isEmpty()){
            return null;
        }
        double dsize = Double.valueOf(size);
        double dlength = Double.valueOf(length);
        if (((dsize-1)/dlength<=0.25)&&length>=16){
            this.shrink();
        }

        int point = minusOne(nextLast,length);
        T ret = items[point];
        items[point] = null;
        nextLast = point;
        size -= 1;
        return ret;

    }
    @Override
    public T get(int index){
        if (index>=size){
            return null;
        }
        int ptr = nextFirst;
        for (int i =0; i<=index;i++){
            ptr = plusOne(ptr,length);

        }
        return items[ptr];
    }

    private void expand(){
//        数组膨胀，当size的大小超过length时，length*2

        T[] new_items = (T[]) new Object[length*2];
        int front = plusOne(nextFirst,length);

        int ptr1 = front;
        int ptr2 = ptr1;
        for (int i = 0; i<size;i++){
            new_items[ptr2] = items[ptr1];
            ptr1 = plusOne(ptr1,length);
            ptr2 = plusOne(ptr2,length*2);
        }
        nextFirst = minusOne(front,length*2);
        nextLast = ptr2;
        length = length*2;
        items = new_items;
    }
    private void shrink(){

//        在 size-1/length<=0.25时缩水,缩水为原来数组大小的一半
        T[] new_items = (T[]) new Object[length/2];
        int ptr1 = plusOne(nextFirst,length);
        int ptr2 = length/4;
        for (int i=0; i<size; i++){
            new_items[ptr2] = items[ptr1];
            ptr1 = plusOne(ptr1,length);
            ptr2 = plusOne(ptr2,length/2);
        }
        nextFirst = minusOne(length/4,length/2);
        nextLast = ptr2;
        length = length/2;
        items = new_items;
    }

    private int plusOne(int index,int len){
        if ((index+1)%len == 0){
            return 0;
        }
        return index+1;
    }
    private int minusOne(int index, int len){
        if (index==0){
            return len-1;
        }
        return index-1;
    }
}
