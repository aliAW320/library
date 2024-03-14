import java.util.ArrayList;
import java.util.Scanner;
class Library {
    String id;
    String name ;
    String year;
    String numberOfDesk;
    String adrres;

    Library(String id,String name,String year,String numberOfDesk,String adrres){
        this.id=id;
        this.name=name;
        this.year=year;
        this.numberOfDesk=numberOfDesk;
        this.adrres=adrres;
    }

    public String getId() {
        return id;
    }
}
public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String order="";
        ArrayList<Library> libraries = new ArrayList<>();
        while (!(order.equals("Finish"))){
            order= input.nextLine();
            findOrder(order,libraries);

        }
    }
public static void findOrder(String order, ArrayList<Library> libraries){
        if (order.contains("Add-library")){
            addLibrary(order,libraries);
        }
}

    private static void addLibrary(String order, ArrayList<Library> libraries) {
        String[] info = order.split("\\|");
        String[] id= info[0].split("\\#");
        boolean duplicate = false;
        for (int i=0;i<libraries.size();i++){

        }
        libraries.add(new Library(id[1],info[1],info[2],info[3],info[4]));

    }

}