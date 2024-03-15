import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
class Library {
    String id;
    String name ;
    String year;
    String numberOfDesk;
    String address;

    Library(String id,String name,String year,String numberOfDesk,String address){
        this.id=id;
        this.name=name;
        this.year=year;
        this.numberOfDesk=numberOfDesk;
        this.address=address;
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
        HashMap <String,Library> nameAndId = new HashMap<>();
        while (!(order.equals("finish"))){
            order= input.nextLine();
            findOrder(order,libraries,nameAndId);

        }
    }
public static void findOrder(String order, ArrayList<Library> libraries,HashMap <String,Library> nameAndId){
        if (order.contains("add-library")){
            addLibrary(order,libraries,nameAndId);
        }
}

    private static void addLibrary(String order, ArrayList<Library> libraries,HashMap <String,Library
            > nameAndId) {
        String[] info = order.split("\\|");
        String[] id= info[0].split("\\#");
        boolean duplicate = false;
        if(!(nameAndId.containsKey(id[1]))){
            libraries.add(new Library(id[1],info[1],info[2],info[3],info[4]));
            nameAndId.put(id[1],libraries.get(libraries.size()-1));
            System.out.println("success");
        }
        else{
            System.out.println("duplicate-id");
        }

    }

}