public class Dessert  {
    int flavor,price;
    static int numDessers=0;

    public Dessert(int flavor,int price){
        this.flavor=flavor;
        this.price=price;
        numDessers++;
    }

    public static void main(String[] args){
        System.out.println("I love dessert!");
    }

    public void printDessert(){
        System.out.println(flavor+" "+price+" "+numDessers);
    }
}