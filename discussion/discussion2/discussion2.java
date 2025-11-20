public static IntList[] interweave(IntList lst, int k) {
    IntList[] array = new IntList[k];
    int index = k-1;
    IntList L = reverse(lst);

    while(L!=null){
        if(array[index]!=null){
            L.rest=array[index];
        }
        array[index]=L;
        L=L.rest;
    }





    while(index<k){
        if(array[index]!=null){
            L.rest=array[index];
        }

        array[index]
    }
}