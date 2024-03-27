import java.time.Duration;
import java.time.LocalDateTime;
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
    ArrayList<Book> booksinborrow = new ArrayList<>();
    ArrayList <Thesis> thesisInBorrow = new ArrayList<>();

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

    public void borrowBookStudent(Book book, Student student, String date, String time, DataHolder dataHolder) {
        String[] fulldate = date.split("\\-");
        String[] fulltime= time.split("\\:");
        LocalDateTime dateTime = LocalDateTime.of(Integer.parseInt(fulldate[0]),Integer.parseInt(fulldate[1]),
                Integer.parseInt(fulldate[2]),Integer.parseInt(fulltime[0]),Integer.parseInt(fulltime[1]));
        booksinborrow.add(book);
        BorrowBook borrowBook = new BorrowBook(dateTime,book,student);
        book.addinrent();
        student.borrow++;
        student.borrowedBookadd(book);
        dataHolder.setBorrowbook(book,borrowBook);
    }

    public void borrowThesisStudent(Thesis thesis, Student student, String date, String time, DataHolder dataHolder)
    {
        String[] fulldate = date.split("\\-");
        String[] fulltime= time.split("\\:");
        LocalDateTime dateTime = LocalDateTime.of(Integer.parseInt(fulldate[0]),Integer.parseInt(fulldate[1]),
                Integer.parseInt(fulldate[2]),Integer.parseInt(fulltime[0]),Integer.parseInt(fulltime[1]));
        thesisInBorrow.add(thesis);
        thesis.canBorrow=false;
        student.borrow++;
        ThesisBorrow thesisBorrow = new ThesisBorrow(dateTime,thesis,student);
        dataHolder.addThesisBorrow(thesis,thesisBorrow);
        student.thesesinBorrow.add(thesis);
    }

    public void borrowBookStaff(Book book, Staff staff, String date, String time, DataHolder dataHolder) {
        String[] fulldate = date.split("\\-");
        String[] fulltime= time.split("\\:");
        LocalDateTime dateTime = LocalDateTime.of(Integer.parseInt(fulldate[0]),Integer.parseInt(fulldate[1]),
                Integer.parseInt(fulldate[2]),Integer.parseInt(fulltime[0]),Integer.parseInt(fulltime[1]));
        booksinborrow.add(book);
        BorrowBook borrowBook = new BorrowBook(dateTime,book,staff);
        book.addinrent();
        staff.inborrow++;
        dataHolder.setBorrowbook(book,borrowBook);
        staff.borrowedBookadd(book);

    }

    public void borrowThesisStaff(Thesis thesis, Staff staff, String date, String time, DataHolder dataHolder) {
        String[] fulldate = date.split("\\-");
        String[] fulltime= time.split("\\:");
        LocalDateTime dateTime = LocalDateTime.of(Integer.parseInt(fulldate[0]),Integer.parseInt(fulldate[1]),
                Integer.parseInt(fulldate[2]),Integer.parseInt(fulltime[0]),Integer.parseInt(fulltime[1]));
        thesisInBorrow.add(thesis);
        thesis.canBorrow=false;
        staff.inborrow++;
        ThesisBorrow thesisBorrow = new ThesisBorrow(dateTime,thesis,staff);
        dataHolder.addThesisBorrow(thesis,thesisBorrow);
        staff.thesesinBorrow.add(thesis);

    }

    public void returnBookStudent(Book book, Student student, String date, String time, DataHolder dataHolder) {
        String[] fulldate = date.split("\\-");
        String[] fulltime= time.split("\\:");
        LocalDateTime dateTime = LocalDateTime.of(Integer.parseInt(fulldate[0]),Integer.parseInt(fulldate[1]),
                Integer.parseInt(fulldate[2]),Integer.parseInt(fulltime[0]),Integer.parseInt(fulltime[1]));
        booksinborrow.remove(book);
        book.removeFromRent();
        student.borrow--;
        student.borrowedBookremove(book);
        dataHolder.removeBorrowBookStudent(book,student,dateTime);
    }

    public void returnThesisStudent(Thesis thesis, Student student, String date, String time, DataHolder dataHolder) {
        String[] fulldate = date.split("\\-");
        String[] fulltime= time.split("\\:");
        LocalDateTime dateTime = LocalDateTime.of(Integer.parseInt(fulldate[0]),Integer.parseInt(fulldate[1]),
                Integer.parseInt(fulldate[2]),Integer.parseInt(fulltime[0]),Integer.parseInt(fulltime[1]));
        thesisInBorrow.remove(thesis);
        thesis.canBorrow=true;
        student.borrow--;
        student.thesesinBorrow.remove(thesis);
        dataHolder.removeBorrowThesisStudent(thesis,student,dateTime);
    }

    public void returnBookStaff(Book book, Staff staff, String date, String time, DataHolder dataHolder) {
        String[] fulldate = date.split("\\-");
        String[] fulltime= time.split("\\:");
        LocalDateTime dateTime = LocalDateTime.of(Integer.parseInt(fulldate[0]),Integer.parseInt(fulldate[1]),
                Integer.parseInt(fulldate[2]),Integer.parseInt(fulltime[0]),Integer.parseInt(fulltime[1]));
        booksinborrow.remove(book);
        book.removeFromRent();
        staff.inborrow--;
        staff.borrowedBookremove(book);
        dataHolder.removeBorrowBookStaff(book,staff,dateTime);
    }

    public void returnThesisStaff(Thesis thesis, Staff staff, String date, String time, DataHolder dataHolder) {
        String[] fulldate = date.split("\\-");
        String[] fulltime= time.split("\\:");
        LocalDateTime dateTime = LocalDateTime.of(Integer.parseInt(fulldate[0]),Integer.parseInt(fulldate[1]),
                Integer.parseInt(fulldate[2]),Integer.parseInt(fulltime[0]),Integer.parseInt(fulltime[1]));
        thesisInBorrow.remove(thesis);
        thesis.canBorrow=true;
        staff.inborrow--;
        staff.thesesinBorrow.remove(thesis);
        dataHolder.removeBorrowThesisStaff(thesis,staff,dateTime);
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

    private HashMap<String,ArrayList<BorrowBook>> IdBorrowBook = new HashMap<>();
    private HashMap<String,ArrayList<ThesisBorrow>> IdBorrowThesis = new HashMap<>();

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

    public void setBorrowbook(Book book, BorrowBook borrowBook) {
        if(IdBorrowBook.containsKey(book.Id)){
            IdBorrowBook.get(book.Id).add(borrowBook);
        }
        else{
            IdBorrowBook.put(book.Id,new ArrayList<BorrowBook>());
            IdBorrowBook.get(book.Id).add(borrowBook);

        }
    }

    public void addThesisBorrow(Thesis thesis,ThesisBorrow thesisBorrow) {
        if(IdBorrowThesis.containsKey(thesis.id)){
            IdBorrowThesis.get(thesis.id).add(thesisBorrow);
        }
        else{
            IdBorrowThesis.put(thesis.id,new ArrayList<ThesisBorrow>());
            IdBorrowThesis.get(thesis.id).add(thesisBorrow);

        }
    }

    public void removeBorrowBookStudent(Book book, Student student, LocalDateTime dateTime) {
        ArrayList<BorrowBook> borrowBooks = IdBorrowBook.get(book.Id);
        long hours=1000 ;
        BorrowBook borrowBooktarget = null;
        for(BorrowBook i : borrowBooks){
            if(i.getStudent()!=null){
                if((i.getStudent().equals(student))&&(i.getBook().library.equals(book.library))&&
                        (Duration.between(i.getBorrowDate(),dateTime)).toHours()>=0){
                    if((Duration.between(i.getBorrowDate(),dateTime)).toHours()<hours){
                        hours=(Duration.between(i.getBorrowDate(),dateTime)).toHours();
                        borrowBooktarget=i;
                    }
                }
            }

        }
        borrowBooks.remove(borrowBooktarget);
        if(IdBorrowBook.get(book.Id).isEmpty()){
            IdBorrowBook.remove(book.Id);
        }
        if(hours>240){
            long penalty= (hours-240)*50;
            student.penalty=+ penalty;
            System.out.println(penalty);
        }
        else {
            System.out.println("success");
        }
    }

    public void removeBorrowThesisStudent(Thesis thesis, Student student, LocalDateTime dateTime) {
        ArrayList<ThesisBorrow> thesisBorrows = IdBorrowThesis.get(thesis.id);
        long hours=1000 ;
        ThesisBorrow thesisBorrowtarget = null;
        for(ThesisBorrow i : thesisBorrows){
            if(i.getStudent()!=null){
                if(i.getStudent().equals(student)&&i.getThesis().library.equals(thesis.library)&&
                        (Duration.between(i.getBorrowDate(),dateTime)).toHours()>=0){
                    if((Duration.between(i.getBorrowDate(),dateTime)).toHours()<hours){
                        hours=(Duration.between(i.getBorrowDate(),dateTime)).toHours();
                        thesisBorrowtarget=i;
                    }
                }
            }

        }
        thesisBorrows.remove(thesisBorrowtarget);
        if(IdBorrowThesis.get(thesis.id).isEmpty()){
            IdBorrowThesis.remove(thesis.id);
        }

        if(hours>168){
            long penalty= (hours-168)*50;
            student.penalty=+ penalty;

            System.out.println(penalty);
        }
        else {
            System.out.println("success");
        }
    }

    public void removeBorrowBookStaff(Book book, Staff staff, LocalDateTime dateTime) {
        ArrayList<BorrowBook> borrowBooks = IdBorrowBook.get(book.Id);
        long hours=1000 ;
        BorrowBook borrowBooktarget = null;
        for(BorrowBook i : borrowBooks){
            if(i.getStaff()!=null){
                if(i.getStaff().equals(staff)&&i.getBook().library.equals(book.library)&&
                        (Duration.between(i.getBorrowDate(),dateTime)).toHours()>0){
                    if((Duration.between(i.getBorrowDate(),dateTime)).toHours()<hours){
                        hours=(Duration.between(i.getBorrowDate(),dateTime)).toHours();
                        borrowBooktarget=i;
                    }
                }
            }

        }
        borrowBooks.remove(borrowBooktarget);
        if(IdBorrowBook.get(book.Id).isEmpty()){
            IdBorrowBook.remove(book.Id);
        }
        if(hours>336){
            long penalty= (hours-336)*100;
            staff.penalty=+ penalty;

            System.out.println(penalty);
        }
        else {
            System.out.println("success");
        }
    }

    public void removeBorrowThesisStaff(Thesis thesis, Staff staff, LocalDateTime dateTime) {
        ArrayList<ThesisBorrow> thesisBorrows = IdBorrowThesis.get(thesis.id);
        long hours=1000 ;
        ThesisBorrow thesisBorrowtarget = null;
        for(ThesisBorrow i : thesisBorrows){
            if(i.getStaff()!=null){
                if(i.getStaff().equals(staff)&&i.getThesis().library.equals(thesis.library)&&
                        (Duration.between(i.getBorrowDate(),dateTime)).toHours()>=0){
                    if((Duration.between(i.getBorrowDate(),dateTime)).toHours()<hours){
                        hours=(Duration.between(i.getBorrowDate(),dateTime)).toHours();
                        thesisBorrowtarget=i;
                    }
                }
            }

        }
        thesisBorrows.remove(thesisBorrowtarget);
        if(IdBorrowThesis.get(thesis.id).isEmpty()){
            IdBorrowThesis.remove(thesis.id);
        }

        if(hours>240){
            long penalty= (hours-240)*100;
            staff.penalty=+ penalty;

            System.out.println(penalty);
        }
        else {
            System.out.println("success");
        }
    }
}

class BorrowBook{
    private LocalDateTime borrowDate;
    private Book book;
    private Student student= null;
    private Staff staff = null;

    BorrowBook(LocalDateTime borrowDate,Book book,Student student){
        this.borrowDate=borrowDate;
        this.student=student;
        this.book=book;
    }
    BorrowBook(LocalDateTime borrowDate,Book book,Staff staff){
        this.borrowDate=borrowDate;
        this.staff=staff;
        this.book=book;
    }

    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }
    public Book getBook (){
        return book;
    }

    public Object getStudent() {
        return student;
    }

    public Object getStaff() {
        return staff;
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
    int inborrow = 0;
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

    public void addinrent() {
        inborrow++;
    }

    public boolean canborrow() {
        return inborrow<number;
    }

    public void removeFromRent() {
        inborrow--;
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
    boolean canBorrow=true;

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

    public boolean canBorrow() {
        return canBorrow;
    }
}
class Student{
    String Id;
    String password;
    String name,family;
    String nationalCode;
    int year;
    String address;
    int borrow=0 ;
    public long penalty = 0;
    ArrayList<Book> bookinborrow = new ArrayList<>();
    ArrayList<Thesis> thesesinBorrow = new ArrayList<>();

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

    public boolean checkpass(String pass) {
        return pass.equals(password);
    }

    public boolean canBorrow(Student student) {
        return borrow<3;
    }

    public void borrowedBookadd(Book book) {
        bookinborrow.add(book);
    }

    public boolean checkBorrow(Book book) {
        return bookinborrow.contains(book);
    }

    public void borrowedBookremove(Book book) {
        bookinborrow.remove(book);
    }

    public boolean checkBorrow(Thesis thesis) {
        return thesesinBorrow.contains(thesis);
    }
}

class Staff{
    String Id;
    String password;
    String name,family;
    String nationalCode;
    int year;
    String address;
    public int inborrow=0;
    public long penalty = 0;
    ArrayList<Book> borrowBook = new ArrayList<>();
    ArrayList <Thesis> thesesinBorrow = new ArrayList<>();
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

    public boolean checkpass(String pass) {
        return pass.equals(password);
    }

    public boolean canBorrow() {
        return inborrow<5;
    }

    public void borrowedBookadd(Book book) {
        borrowBook.add(book);
    }

    public boolean checkBorrow(Book book) {
        return borrowBook.contains(book);
    }

    public void borrowedBookremove(Book book) {
        borrowBook.remove(book);
    }

    public boolean checkBorrow(Thesis thesis) {
        return thesesinBorrow.contains(thesis);
    }
}
class ThesisBorrow{
    Thesis thesis;
    Student student;
    Staff staff;
    LocalDateTime borrowDate;
    ThesisBorrow (LocalDateTime borrowDate,Thesis thesis,Student student){
        this.borrowDate=borrowDate;
        this.student=student;
        this.thesis=thesis;
    }
    ThesisBorrow(LocalDateTime borrowDate,Thesis thesis,Staff staff){
        this.borrowDate=borrowDate;
        this.staff=staff;
        this.thesis= thesis;
    }

    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }
    public Thesis getThesis (){
        return thesis;
    }

    public Object getStudent() {
        return student;
    }

    public Object getStaff() {
        return staff;
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
        } else if(order.startsWith("borrow")){
            borrow(order,dataHolder);
        } else if (order.startsWith("return")) {
            BorrowReturn(order,dataHolder);
        }
    }

    private static void BorrowReturn(String order, DataHolder dataHolder) {
        String[] info = order.split("\\|");
        String[] id = info[0].split("\\#");
        if (dataHolder.checkStudentExist(id[1])) {
            Student student = dataHolder.getStudent(id[1]);
            if (!student.checkpass(info[1])) {
                System.out.println("invalid-pass");
                return;
            }
            if (!dataHolder.checkLibraryExist(info[2])) {
                System.out.println("not-found");
                return;
            }
            Library lib = dataHolder.IdTolibrary.get(info[2]);
            if (lib.checkThesisInLibrary(info[3]) && lib.checkBookInLibrary(info[3])) {
                System.out.println("not-found");
                return;
            }
            else if (!lib.checkBookInLibrary(info[3])) {
                Book book = lib.giveBook(info[3]);
                if(!student.checkBorrow(book)){
                    System.out.println("not-found");
                    return;
                }
                lib.returnBookStudent(book,student,info[4],info[5],dataHolder);

            } else {
                Thesis thesis = lib.giveThesis(info[3]);
                if(!student.checkBorrow(thesis)){
                    System.out.println("not-found");
                    return;
                }
                lib.returnThesisStudent(thesis,student,info[4],info[5],dataHolder);
            }

        }
        //check staff
        else if (dataHolder.checkStaffExist(id[1])) {
            Staff staff = dataHolder.getStaff(id[1]);
            if(!staff.checkpass(info[1])){
                System.out.println("invalid-pass");
                return;
            }
            if (!dataHolder.checkLibraryExist(info[2])) {
                System.out.println("not-found");
                return;
            }
            Library lib = dataHolder.IdTolibrary.get(info[2]);
            if (lib.checkThesisInLibrary(info[3]) && lib.checkBookInLibrary(info[3])) {
                System.out.println("not-found");
                return;
            }
            else if (!lib.checkBookInLibrary(info[3])) {
                Book book = lib.giveBook(info[3]);
                if(!staff.checkBorrow(book)){
                    System.out.println("not-found");
                    return;
                }
                lib.returnBookStaff(book,staff,info[4],info[5],dataHolder);

            } else {
                Thesis thesis = lib.giveThesis(info[3]);
                if(!staff.checkBorrow(thesis)){
                    System.out.println("not-found");
                    return;
                }
                lib.returnThesisStaff(thesis,staff,info[4],info[5],dataHolder);
            }

        }
        else {
            System.out.println("not-found");
            return;
        }


    }
    private static void borrow(String order, DataHolder dataHolder) {
        String[] info = order.split("\\|");
        String[] id = info[0].split("\\#");
        // check for student
        if(dataHolder.checkStudentExist(id[1])){
            Student student = dataHolder.getStudent(id[1]);
            if(!student.checkpass(info[1])){
                System.out.println("invalid-pass");
                return;
            }
            if(!dataHolder.checkLibraryExist(info[2])){
                System.out.println("not-found");
                return;
            }
            Library lib = dataHolder.IdTolibrary.get(info[2]);
            if(lib.checkThesisInLibrary(info[3]) && lib.checkBookInLibrary(info[3])){
                System.out.println("not-found");
                return;
            }
            else if(!lib.checkBookInLibrary(info[3])){
                Book book = lib.giveBook(info[3]);
                if(student.canBorrow(student)&&book.canborrow()){
                    lib.borrowBookStudent(book,student,info[4],info[5],dataHolder);
                    System.out.println("success");
                }
                else{
                    System.out.println("not-allowed");
                }

            } else if (!lib.checkThesisInLibrary(info[3])) {
                Thesis thesis = lib.giveThesis(info[3]);
                if (student.canBorrow(student)&&thesis.canBorrow()){
                    lib.borrowThesisStudent(thesis,student,info[4], info [5],dataHolder);
                    System.out.println("success");
                }
                else{
                    System.out.println("not-allowed");
                }

            }
            else {
                System.out.println("not-found");
            }
            // check for staff
        } else if (dataHolder.checkStaffExist(id[1])) {
            Staff staff = dataHolder.getStaff(id[1]);
            if(!staff.checkpass(info[1])){
                System.out.println("invalid-pass");
                return;
            }
            if(!dataHolder.checkLibraryExist(info[2])){
                System.out.println("not-found");
                return;
            }
            Library lib = dataHolder.IdTolibrary.get(info[2]);
            if(lib.checkThesisInLibrary(info[3]) && lib.checkBookInLibrary(info[3])){
                System.out.println("not-found");
                return;
            }
            else if(!lib.checkBookInLibrary(info[3])){
                Book book = lib.giveBook(info[3]);
                if(staff.canBorrow()&& book.canborrow()){
                    lib.borrowBookStaff(book,staff,info[4],info[5],dataHolder);
                    System.out.println("success");
                }
                else{
                    System.out.println("not-allowed");
                }


            } else if (!lib.checkThesisInLibrary(info[3])) {
                Thesis thesis = lib.giveThesis(info[3]);
                if(staff.canBorrow()&& thesis.canBorrow()){
                    lib.borrowThesisStaff(thesis,staff,info[4], info [5],dataHolder);
                    System.out.println("success");
                }
                else{
                    System.out.println("not-allowed");
                }

            }
        }
        else {
            System.out.println("not-found");
        }

    }
    private static void removeStaff(String order, DataHolder dataHolder) {
        String[] id = order.split("\\#");
        if(!dataHolder.checkStaffExist(id[1])){
            System.out.println("not-found");
            return;
        }
        Staff staff = dataHolder.getStaff(id[1]);
        if((staff.inborrow!=0)||staff.penalty!=0){
            System.out.println("not-allowed");
            return;
        }
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
        if((student.borrow!=0)||student.penalty!=0){
            System.out.println("not-allowed");
            return;
        }
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
                if(thesis.canBorrow){
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
        if (!dataHolder.checkCategoryExist(info[info.length-1])&&!info[info.length-1].equals("-")){
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
                if(book.inborrow!=0){
                    System.out.println("not-allowed");
                    return;
                }
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