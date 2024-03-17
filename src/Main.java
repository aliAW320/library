import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
class DataHolder{
    //reaching from library id to the library his self
    public HashMap <String,Library> IdTolibrary = new HashMap<>();
    //a list of all library
    public ArrayList<Library> libraries = new ArrayList<>();
    // reaching from a category id to category him self
    public HashMap <String,category> categoryIDtoName = new HashMap<>();
    // reaching from a name of category to the category him self
    public HashMap <String,category> NametoCategory = new HashMap<>();
    // all books in a




    public void setLibraries(Library newLibrary) {
        libraries.add(newLibrary);
    }

    public void setIdTolibrary(String Id,Library NewLibrary) {
        IdTolibrary.put(Id,NewLibrary);
    }

    public void setCategoryIDtoName(String id,category newCategory) {
        categoryIDtoName.put(id,newCategory);
    }
    public boolean checkLibraryExist(String id){
        return IdTolibrary.get(id) != null;
    }

    public boolean checkCategoryExist(String id) {
        return categoryIDtoName.get(id) != null;
    }

}
class Library {
    String id;
    String name ;
    String year;
    String numberOfDesk;
    String address;
    // an array list store books id
    public ArrayList <String> booksId = new ArrayList<>();
    //an array list store books
    public ArrayList <Book> books = new ArrayList<>();

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
    public boolean checkBookInLibrary(String id){
        return !booksId.contains(id);
    }
    public void addBook(String Id,String name,String author,String publisher ,String year,int  number,String libraryId){
        booksId.add(Id);
        books.add(new Book(Id,name,author,publisher,year,number,libraryId));
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
class Book{
    String Id;
    String name;
    String author;
    String publisher;
    String year;
    int number;
    String libraryId;
    Book(String Id,String name,String author,String publisher,String year,int number,String libraryId){
        this.Id=Id;
        this.name=name;
        this.author=author;
        this.publisher=publisher;
        this.year=year;
        this.number=number;
        this.libraryId=libraryId;
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
        } else if (order.contains("add-book")) {
            addbook(order,dataHolder);
        }


}

    private static void addbook(String order, DataHolder dataHolder) {
        String[] info = order.split("\\|");
        String[] id = info[0].split("\\#");
        if (dataHolder.checkLibraryExist(info[info.length-1])) {
            if (info[info.length - 2].equals("null")) {
                if (dataHolder.IdTolibrary.get(info[info.length - 1]).checkBookInLibrary(id[1])) {
                    dataHolder.IdTolibrary.get(info[info.length - 1]).addBook(id[1],
                            info[1], info[2], info[3], info[4], Integer.parseInt(info[5]), info[6]);
                    System.out.println("success");
                } else {
                    System.out.println("duplicate-id");
                }
            } else if (dataHolder.checkCategoryExist(info[info.length - 2])) {
                if (dataHolder.IdTolibrary.get(info[info.length - 1]).checkBookInLibrary(id[1])) {
                    dataHolder.IdTolibrary.get(info[info.length - 1]).addBook(id[1],
                            info[1], info[2], info[3], info[4], Integer.parseInt(info[5]), info[6]);
                    System.out.println("success");

                } else {
                    System.out.println("duplicate-id");
                }
            } else {
                System.out.println("not-find");
            }

        }
        else {
            System.out.println("not-found");
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
        if(!(dataHolder.IdTolibrary.containsKey(id[1]))){
            dataHolder.setLibraries(new Library(id[1],info[1],info[2],info[3],info[4]));
            dataHolder.setIdTolibrary(id[1],dataHolder.libraries.get(dataHolder.libraries.size()-1));
            System.out.println("success");
        }
        else{
            System.out.println("duplicate-id");
        }

    }

}