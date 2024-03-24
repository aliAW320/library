import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
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
    // a list of thesis in library
    ArrayList <Thesis> thesises = new ArrayList<>();
    //a hashmap store id to thesises
    HashMap <String,Thesis> idtothesises = new HashMap<>();

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
    public void addBook(String Id, String name, String author, String publisher , String year, int  number,
                        category bookcategory, Library library){
        booksId.add(Id);
        Book newbook= new Book(Id,name,author,publisher,year,number,bookcategory,library);
        books.add(newbook);
        Idtobooks.put(Id,newbook);
        bookcategory.setBooks(newbook);
    }

    public void removeBookFromLibrary(String bookId) {
        books.remove(Idtobooks.get(bookId));
        booksId.remove(bookId);
        Idtobooks.remove(bookId);
    }

    public Book giveBook(String id) {
        return Idtobooks.get(id);
    }

    public void addThesis(String Id, String name, String writer, String profossor , int  year,
                          category bookcategory, Library library, DataHolder dataHolder)
    {
        Thesis newthesis= new Thesis(Id,name,writer,profossor,year,bookcategory,library);
        thesises.add(newthesis);
        idtothesises.put(Id,newthesis);
        bookcategory.setTheses(newthesis);


    }

    public boolean checkThesisInLibrary(String id) {
        if(idtothesises.containsKey(id)){
            return false;
        }
        else {
            return true;
        }
    }

    public Thesis giveThesis(String id) {
        return idtothesises.get(id);
    }
}
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
    // a hashmap store id and student
    private HashMap<String,Student> IdtoStudent = new HashMap<>();
    // a list of Students
    private ArrayList<Student> students = new ArrayList<>();
    // a hashmap store id and student
    private HashMap<String,Staff> IdtoStaffe = new HashMap<>();
    // a list of Students
    private ArrayList<Staff> staffes = new ArrayList<>();

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
        if((categoryIDtoName.get(id) != null)||(id.equals("null"))){
            return true;
        }
        else return false;
    }


    public boolean checkStudentExist(String id) {
        if(IdtoStudent.containsKey(id)){
            return true;
        }
        return false;
    }

    public void addStudent(Student student, String id) {
        IdtoStudent.put(id,student);
        students.add(student);
    }

    public Student getStudent(String id) {
        return IdtoStudent.get(id);
    }

    public void removeStudent(Student student) {
        students.remove(student);
        IdtoStudent.remove(student.getId(student));
    }
    public boolean checkStaffExist(String id) {
        if(IdtoStaffe.containsKey(id)){
            return true;
        }
        return false;
    }

    public void addStaff(Staff staff, String id) {
        IdtoStaffe.put(id,staff);
        staffes.add(staff);
    }

    public Staff getStaff(String id) {
        return IdtoStaffe.get(id);
    }

    public void removeStaff(Staff staff) {
        staffes.remove(staff);
        IdtoStaffe.remove(staff.getId(staff));
    }
}

class category {
    String id;
    String name;
    ArrayList <Book> books = new ArrayList<>();
    ArrayList <Thesis> theses = new ArrayList<>();
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

    public void setTheses(Thesis thesis) {
        theses.add(thesis);
    }
    public void removeTheses(Thesis thesis) {
        theses.remove(thesis);
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
    Library library;
    Book(String Id,String name,String author,String publisher,String year,int number,category bookcategory,Library library){
        this.Id=Id;
        this.name=name;
        this.author=author;
        this.publisher=publisher;
        this.year=year;
        this.number=number;
        this.bookcategory=bookcategory;
        this.library=library;
    }


    public void edit(String[] info,Book book,DataHolder dataHolder) {
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
                bookcategory.setBooks(book);
            }
        }
    }
}
class Thesis {
    String id;
    String name;
    String writer;
    String professor;
    int year ;
    category theiscategory;
    Library library;
    boolean inBorrow=false;

    Thesis(String id, String name, String writer, String professor, int year, category theiscategory, Library library){
        this.id=id;
        this.name=name;
        this.writer=writer;
        this.professor=professor;
        this.year=year;
        this.theiscategory=theiscategory;
        this.library=library;
    }

    public void edit(String[] info, Thesis thesis, DataHolder dataHolder) {
        if (!info[2].equals("-")) {
            name = info[2];
        }
        if (!info[3].equals("-")) {
            writer = info[3];
        }
        if (!info[4].equals("-")) {
            professor = info[4];
        }
        if (!info[5].equals("-")) {
            year = Integer.parseInt(info[5]);
        }
        if (!info[6].equals("-")) {
            if(info[6].equals("null")){
                theiscategory.removeTheses(thesis);
                theiscategory = dataHolder.nullcategory;
                dataHolder.nullcategory.setTheses(thesis);

            }
            else {
                theiscategory.removeTheses(thesis);
                theiscategory =dataHolder.categoryIDtoName.get(info[6]);
                theiscategory.setTheses(thesis);
            }
        }

    }

    public void remove(Thesis thesis) {
        theiscategory.removeTheses(thesis);
        library.idtothesises.remove(id);
        library.thesises.remove(thesis);
    }
}
class Student{
    String Id;
    String password;
    String name,family;
    String nationalCode;
    int year;
    String address;
    
    Student(String Id,String password,String name,String family,String nationalCode,int year,String address){
        this.Id=Id;
        this.password=password;
        this.name=name;
        this.family=family;
        this.nationalCode=nationalCode;
        this.year=year;
        this.address=address;
    }


    public void edit(String[] info) {
        if (!info[1].equals("-")) {
                password = info[1];
            }
        if (!info[2].equals("-")) {
            name = info[2];
        }        
        if (!info[3].equals("-")) {
            family = info[3];
        }
        if (!info[4].equals("-")) {
            nationalCode = info[4];
        }
        if (!info[5].equals("-")) {
            year = Integer.parseInt(info[5]);
        }
        if (!info[6].equals("-")) {
            address = (info[6]);
        }
    }

    public String getId(Student student) {
        return student.Id;
    }
}

class Staff{
    String Id;
    String password;
    String name,family;
    String nationalCode;
    int year;
    String address;

    Staff(String Id,String password,String name,String family,String nationalCode,int year,String address){
        this.Id=Id;
        this.password=password;
        this.name=name;
        this.family=family;
        this.nationalCode=nationalCode;
        this.year=year;
        this.address=address;
    }
    public void edit(String[] info) {
        if (!info[1].equals("-")) {
            password = info[1];
        }
        if (!info[2].equals("-")) {
            name = info[2];
        }
        if (!info[3].equals("-")) {
            family = info[3];
        }
        if (!info[4].equals("-")) {
            nationalCode = info[4];
        }
        if (!info[5].equals("-")) {
            year = Integer.parseInt(info[5]);
        }
        if (!info[6].equals("-")) {
            address = (info[6]);
        }
    }

    public String getId(Staff staff) {
       return staff.Id;
    }
}
public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String order= new String();
        DataHolder dataHolder = new DataHolder();
        while (!order.equals("finish")){
            order= input.nextLine();
            if (order.equals("finish")){
                break;
            }
            findOrder(order,dataHolder);

        }
        System.exit(0);
    }
    public static void findOrder(String order,DataHolder dataHolder) {
        if (order.equals("finish")){
            System.exit(0);
        } else if (order.contains("add-library")){
            addLibrary(order,dataHolder);
        } else if (order.contains("add-category")) {
            addcategory(order,dataHolder);
        } else if (order.contains("add-book")) {
            addbook(order,dataHolder);
        } else if (order.contains("remove-book")) {
            removeBook(order,dataHolder);
        } else if (order.contains("edit-book")) {
            editbook(order,dataHolder);
        } else if (order.contains("add-thesis")){
            addThesis(order,dataHolder);
        } else if (order.contains("edit-thesis")) {
            editThesis(order,dataHolder);
        } else if (order.startsWith("remove-thesis")){
            removeThesis(order,dataHolder);
        } else if (order.startsWith("add-student")){
            addStudent(order,dataHolder);
        } else if (order.startsWith("edit-student")){
            editStudent(order,dataHolder);
        } else if (order.startsWith("remove-student")) {
            removeStudent(order,dataHolder);
        }else if (order.startsWith("add-staff")){
            addStaff(order,dataHolder);
        } else if (order.startsWith("edit-staff")){
            editStaff(order,dataHolder);
        } else if (order.startsWith("remove-staff")) {
            removeStaff(order,dataHolder);
        }
    }

    private static void removeStaff(String order, DataHolder dataHolder) {
        String[] id = order.split("\\#");
        if(!dataHolder.checkStaffExist(id[1])){
            System.out.println("not-found");
            return;
        }
        Staff staff = dataHolder.getStaff(id[1]);
        dataHolder.removeStaff(staff);
        System.out.println("success");
    }

    private static void editStaff(String order, DataHolder dataHolder) {
        String[] info = order.split("\\|");
        String[] id = info[0].split("\\#");
        if(!dataHolder.checkStaffExist(id[1])){
            System.out.println("not-found");
            return;
        }
        Staff staff = dataHolder.getStaff(id[1]);
        staff.edit(info);
        System.out.println("success");
    }

    private static void addStaff(String order, DataHolder dataHolder) {
        String[] info = order.split("\\|");
        String[] id = info[0].split("\\#");
        if(dataHolder.checkStaffExist(id[1])){
            System.out.println("duplicate-id");
            return;
        }
        Staff staff = new Staff(id[1],info[1],info[2],info[3],info[4],Integer.parseInt(info[5]),info[6] );
        dataHolder.addStaff(staff,id[1]);
        System.out.println("success");
    }

    private static void removeStudent(String order, DataHolder dataHolder) {
        String[] id = order.split("\\#");
        if(!dataHolder.checkStudentExist(id[1])){
            System.out.println("not-found");
            return;
        }
        Student student = dataHolder.getStudent(id[1]);
        dataHolder.removeStudent(student);
        System.out.println("success");
    }

    private static void editStudent(String order, DataHolder dataHolder) {
        String[] info = order.split("\\|");
        String[] id = info[0].split("\\#");
        if(!dataHolder.checkStudentExist(id[1])){
            System.out.println("not-found");
            return;
        }
        Student student = dataHolder.getStudent(id[1]);
        student.edit(info);
        System.out.println("success");
    }

    private static void addStudent(String order, DataHolder dataHolder) {
        String[] info = order.split("\\|");
        String[] id = info[0].split("\\#");        
        if(dataHolder.checkStudentExist(id[1])){
            System.out.println("duplicate-id");
            return;
        }
        Student student = new Student(id[1],info[1],info[2],info[3],info[4],Integer.parseInt(info[5]),info[6] );
        dataHolder.addStudent(student,id[1]);
        System.out.println("success");
    }

    private static void removeThesis(String order, DataHolder dataHolder) {
        String[] info = order.split("\\|");
        String[] id = info[0].split("\\#");
        if(dataHolder.checkLibraryExist(info[1])){
            Library lib = dataHolder.IdTolibrary.get(info[1]);
            if(!lib.checkThesisInLibrary(id[1])){
                Thesis thesis = lib.giveThesis(id[1]);
                if(!thesis.inBorrow){
                    thesis.remove(thesis);
                    System.out.println("success");
                }
            }
            else{
                System.out.println("not-found");
            }
        }
        else {
            System.out.println("not-found");
        }
    }

    private static void editThesis(String order, DataHolder dataHolder) {
        String[] info = order.split("\\|");
        String[] id = info[0].split("\\#");
        if(dataHolder.checkLibraryExist(info[1])){
            Library lib = dataHolder.IdTolibrary.get(info[1]);
            if(dataHolder.checkCategoryExist(info[info.length-1])){
                if (!lib.checkThesisInLibrary(id[1])){
                    Thesis thesis = lib.giveThesis(id[1]);
                    thesis.edit(info,thesis,dataHolder);
                    System.out.println("success");
                }
                else {
                    System.out.println("not-found");
                }
            }
            else {
                System.out.println("not-found");
            }
        }
        else {
            System.out.println("not-found");
        }

    }

    private static void addThesis(String order, DataHolder dataHolder) {
        String[] info = order.split("\\|");
        String[] id = info[0].split("\\#");
        if(dataHolder.checkLibraryExist(info[info.length-1])){
            Library lib= dataHolder.IdTolibrary.get(info[info.length-1]);
            if(lib.checkThesisInLibrary(id[1])){
                if(info[info.length-2].equals("null")){
                    lib.addThesis(id[1],info[1],info[2],info[3],Integer.parseInt(info[4]),
                            dataHolder.nullcategory,lib,dataHolder );
                    System.out.println("success");

                } else if (dataHolder.checkCategoryExist(info[info.length-2])) {
                    lib.addThesis(id[1],info[1],info[2],info[3],Integer.parseInt(info[4]),
                            dataHolder.categoryIDtoName.get(info[5]),lib,dataHolder );
                    System.out.println("success");
                }
                else {
                    System.out.println("not-found");
                }
            }
            else {
                System.out.println("duplicate-id");
            }

        }
        else {
            System.out.println("not-found");
        }

    }

    private static void editbook(String order, DataHolder dataHolder) {
        String[] info = order.split("\\|");
        String[] id = info[0].split("\\#");
        if (!dataHolder.checkLibraryExist(info[1])) {
            System.out.println("not-found");
            return;
        }
        Library lib = dataHolder.IdTolibrary.get(info[1]);
        if (lib == null) {
            System.out.println("not-found");
            return;
        }
        if (lib.checkBookInLibrary(id[1])) {
            System.out.println("not-found");
            return;
        }
        Book book = lib.giveBook(id[1]);
        if (book == null) {
            System.out.println("not-found");
            return;
        }
        if (!dataHolder.checkCategoryExist(info[info.length-1])){
            System.out.println("not-found");
            return;
        }
        book.edit(info, book, dataHolder);
        System.out.println("success");
    }


    private static void removeBook(String order, DataHolder dataHolder) {
        String[] info = order.split("\\|");
        String[] id = info[0].split("\\#");
        if(dataHolder.IdTolibrary.containsKey(info[1])){
            Library lib= dataHolder.IdTolibrary.get(info[1]);
            if(!lib.checkBookInLibrary(id[1])){
                Book book = lib.giveBook(id[1]);
                book.bookcategory.removebook(book);
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
            Library lib = dataHolder.IdTolibrary.get(info[info.length-1]);
            if (info[info.length - 2].equals("null")) {
                if (dataHolder.IdTolibrary.get(info[info.length - 1]).checkBookInLibrary(id[1])) {
                    dataHolder.IdTolibrary.get(info[info.length - 1]).addBook(id[1],
                            info[1], info[2], info[3], info[4], Integer.parseInt(info[5]),
                            dataHolder.nullcategory,lib);

                    System.out.println("success");
                } else {
                    System.out.println("duplicate-id");
                }
            } else if (dataHolder.checkCategoryExist(info[info.length - 2])) {
                if (dataHolder.IdTolibrary.get(info[info.length - 1]).checkBookInLibrary(id[1])) {
                    dataHolder.IdTolibrary.get(info[info.length - 1]).addBook(id[1],
                            info[1], info[2], info[3], info[4], Integer.parseInt(info[5]),
                            dataHolder.categoryIDtoName.get(info[6]) ,lib);
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