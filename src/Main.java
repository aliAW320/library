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
    //null category
    category nullcategory = new category(null,null);



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
        return IdTolibrary.get(id) != null||id.equals("null");
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
    // a hashmap store id to books
    HashMap<String,Book> Idtobooks = new HashMap<>();

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
    //if book didn't exist return true
    public boolean checkBookInLibrary(String id){
        return !booksId.contains(id);
    }
    public void addBook(String Id,String name,String author,String publisher ,String year,int  number,
                    category bookcategory,String libraryId){
        booksId.add(Id);
        books.add(new Book(Id,name,author,publisher,year,number,bookcategory,libraryId));
        Idtobooks.put(Id,books.get(books.size()-1));
    }

    public void removeBookFromLibrary(String bookId) {
        books.remove(Idtobooks.get(bookId));
        booksId.remove(bookId);
        Idtobooks.remove(bookId);
    }

    public Book giveBook(String id) {
       return Idtobooks.get(id);
    }
}
class category {
    String id;
    String name;
    ArrayList <Book> books = new ArrayList<>();
    category(String id,String name ){
        this.id=id;
        this.name=name;
    }

    public void setBooks(Book book) {
        books.add(book);
    }
    public void removebook(Book book){
        books.remove(book);
    }
}
class Book{
    String Id;
    String name;
    String author;
    String publisher;
    String year;
    int number;
    category bookcategory;
    String libraryId;
    Book(String Id,String name,String author,String publisher,String year,int number,category bookcategory,String libraryId){
        this.Id=Id;
        this.name=name;
        this.author=author;
        this.publisher=publisher;
        this.year=year;
        this.number=number;
        this.bookcategory=bookcategory;
        this.libraryId=libraryId;
    }


    public static void edit(String[] info,Book book,DataHolder dataHolder) {
        if(!info[2].equals("-")){
            book.name=info[2];
        }
        if(!info[3].equals("-")){
            book.author=info[3];
        }
        if(!info[4].equals("-")){
            book.publisher=info[4];
        }
        if(!info[5].equals("-")){
            book.year=info[5];
        }
        if(!info[6].equals("-")){
            book.number=Integer.parseInt(info[6]);
        }
        if(!info[7].equals("-")){
            if(info[7].equals("null")){
                book.bookcategory.removebook(book);
                book.bookcategory= dataHolder.nullcategory;
                dataHolder.nullcategory.setBooks(book);
            }
            else{
                book.bookcategory.removebook(book);
                book.bookcategory=dataHolder.categoryIDtoName.get(info[7]);
                dataHolder.nullcategory.setBooks(book);
            }
        }
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
        } else if (order.contains("remove-book")) {
            removeBook(order,dataHolder);
        } else if (order.contains("edit-book")) {
            editbook(order,dataHolder);
        }


}

    private static void editbook(String order, DataHolder dataHolder) {
        String[] info = order.split("\\|");
        String[] id = info[0].split("\\#");
        if(dataHolder.checkLibraryExist(info[1])){
            Library lib= dataHolder.IdTolibrary.get(info[1]);
            if(!lib.checkBookInLibrary(id[1])){
                Book book =lib.Idtobooks.get(id[1]);
                Book.edit(info,book,dataHolder);
                System.out.println("success");
            }
            else{
                System.out.println("not-found");
            }
        }
        else{
            System.out.println("not-found");
        }
    }

    private static void removeBook(String order, DataHolder dataHolder) {
        String[] info = order.split("\\|");
        String[] id = info[0].split("\\#");
        if(dataHolder.IdTolibrary.containsKey(info[1])){
            Library lib= dataHolder.IdTolibrary.get(info[1]);
            if(!lib.checkBookInLibrary(id[1])){
                Book book = lib.giveBook(id[1]);
                if(book.bookcategory==dataHolder.nullcategory){
                    dataHolder.nullcategory.removebook(book);
                }
                else {
                    book.bookcategory.removebook(book);
                }
            lib.removeBookFromLibrary(id[1]);
            System.out.println("success");
            }
            else {
                System.out.println("not-found");
            }
        }
        else{
            System.out.println("not-found");
        }
    }

    private static void addbook(String order, DataHolder dataHolder) {
        String[] info = order.split("\\|");
        String[] id = info[0].split("\\#");
        if (dataHolder.checkLibraryExist(info[info.length-1])) {
            if (info[info.length - 2].equals("null")) {
                if (dataHolder.IdTolibrary.get(info[info.length - 1]).checkBookInLibrary(id[1])) {
                    dataHolder.IdTolibrary.get(info[info.length - 1]).addBook(id[1],
                            info[1], info[2], info[3], info[4], Integer.parseInt(info[5]),
                            dataHolder.nullcategory,info[7]);
                    System.out.println("success");
                } else {
                    System.out.println("duplicate-id");
                }
            } else if (dataHolder.checkCategoryExist(info[info.length - 2])) {
                if (dataHolder.IdTolibrary.get(info[info.length - 1]).checkBookInLibrary(id[1])) {
                    dataHolder.IdTolibrary.get(info[info.length - 1]).addBook(id[1],
                            info[1], info[2], info[3], info[4], Integer.parseInt(info[5]),
                            dataHolder.categoryIDtoName.get(info[6]) ,info[7]);
                    System.out.println("success");

                } else {
                    System.out.println("duplicate-id");
                }
            } else {
                System.out.println("not-found");
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