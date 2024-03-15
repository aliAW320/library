import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
class DataHolder{
    public HashMap <String,Library> libraryNameAndId = new HashMap<>();
    public ArrayList<Library> libraries = new ArrayList<>();
    public HashMap <String,category> categoryIDtoName = new HashMap<>();

    public void setLibraries(Library newLibrary) {
        libraries.add(newLibrary);
    }

    public void setLibraryNameAndId(String Id,Library NewLibrary) {
        libraryNameAndId.put(Id,NewLibrary);
    }

    public void setCategoryIDtoName(String id,category newCategory) {
        categoryIDtoName.put(id,newCategory);
    }
}
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
class category {
    String id;
    String name;
    category(String id,String name ){
        this.id=id;
        this.name=name;
    }

}
public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String order="";
        DataHolder dataHolder = new DataHolder();
        while (!(order.equals("finish"))){
            order= input.nextLine();
            findOrder(order,dataHolder);

        }
    }
public static void findOrder(String order,DataHolder dataHolder){
        if (order.contains("add-library")){
            addLibrary(order,dataHolder);
        } else if (order.contains("add-category")) {
            addcategory(order,dataHolder);
        }
}

    private static void addcategory(String order, DataHolder dataHolder) {
        String[] info = order.split("\\|");
        String [] id = info[0].split("\\#");
        if(!(dataHolder.categoryIDtoName.containsKey(id[1]))){
            dataHolder.setCategoryIDtoName(id[1],new category(id[1],info[1]));
            System.out.println("success");
        }
        else {
            System.out.println("duplicate-id");
        }
    }

    private static void addLibrary(String order,DataHolder dataHolder) {
        String[] info = order.split("\\|");
        String[] id= info[0].split("\\#");
        boolean duplicate = false;
        if(!(dataHolder.libraryNameAndId.containsKey(id[1]))){
            dataHolder.setLibraries(new Library(id[1],info[1],info[2],info[3],info[4]));
            dataHolder.setLibraryNameAndId(id[1],dataHolder.libraries.get(dataHolder.libraries.size()-1));
            System.out.println("success");
        }
        else{
            System.out.println("duplicate-id");
        }

    }

}