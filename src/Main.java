import java.util.Scanner;
class Library {
    String name ;
    int year;
    int numberOfDesk;
    String adrres;

    Library(String name,int year,int numberOfDesk,String adrres){
        this.name=name;
        this.year=year;
        this.numberOfDesk=numberOfDesk;
        this.adrres=adrres;
    }
}
public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String order="";
        while (!(order.equals("Finish"))){
            order= input.nextLine();
            findOrder(order);

        }
    }
public static void findOrder(String order){
        if (order.contains("Add-library")){

        }
}

}