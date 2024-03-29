import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Library {
    private String id;
    private String name ;
    private String year;
    private String numberOfDesk;
    private String address;
    // an array list store books id
    private ArrayList<String> booksId = new ArrayList<>();

    //an array list store books
    private ArrayList <Book> books = new ArrayList<>();
    // a hashmap store id to books
    private HashMap<String,Book> Idtobooks = new HashMap<>();
    // a list of thesis in library
    private ArrayList <Thesis> thesises = new ArrayList<>();
    //a hashmap store id to thesises
    private HashMap <String,Thesis> idtothesises = new HashMap<>();
    private ArrayList<Book> booksinborrow = new ArrayList<>();
    private ArrayList <Thesis> thesisInBorrow = new ArrayList<>();

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

    public ArrayList<Book> getBooks() {
        return books;
    }

    public HashMap<String, Thesis> getIdtothesises() {
        return idtothesises;
    }

    public ArrayList<Thesis> getThesises() {
        return thesises;
    }

    public ArrayList<Book> getBooksinborrow() {
        return booksinborrow;
    }

    public ArrayList<Thesis> getThesisInBorrow() {
        return thesisInBorrow;
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
        student.addInborrow();
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
        thesis.makeCanborrowFalse();
        student.addInborrow();
        ThesisBorrow thesisBorrow = new ThesisBorrow(dateTime,thesis,student);
        dataHolder.addThesisBorrow(thesis,thesisBorrow);
        student.getThesesinBorrow().add(thesis);
    }

    public void borrowBookStaff(Book book, Staff staff, String date, String time, DataHolder dataHolder) {
        String[] fulldate = date.split("\\-");
        String[] fulltime= time.split("\\:");
        LocalDateTime dateTime = LocalDateTime.of(Integer.parseInt(fulldate[0]),Integer.parseInt(fulldate[1]),
                Integer.parseInt(fulldate[2]),Integer.parseInt(fulltime[0]),Integer.parseInt(fulltime[1]));
        booksinborrow.add(book);
        BorrowBook borrowBook = new BorrowBook(dateTime,book,staff);
        book.addinrent();
        staff.addInborrow(1);
        dataHolder.setBorrowbook(book,borrowBook);
        staff.borrowedBookadd(book);

    }

    public void borrowThesisStaff(Thesis thesis, Staff staff, String date, String time, DataHolder dataHolder) {
        String[] fulldate = date.split("\\-");
        String[] fulltime= time.split("\\:");
        LocalDateTime dateTime = LocalDateTime.of(Integer.parseInt(fulldate[0]),Integer.parseInt(fulldate[1]),
                Integer.parseInt(fulldate[2]),Integer.parseInt(fulltime[0]),Integer.parseInt(fulltime[1]));
        thesisInBorrow.add(thesis);
        thesis.makeCanborrowFalse();
        staff.addInborrow(1);
        ThesisBorrow thesisBorrow = new ThesisBorrow(dateTime,thesis,staff);
        dataHolder.addThesisBorrow(thesis,thesisBorrow);
        staff.getThesesinBorrow().add(thesis);

    }

    public void returnBookStudent(Book book, Student student, String date, String time, DataHolder dataHolder) {
        String[] fulldate = date.split("\\-");
        String[] fulltime= time.split("\\:");
        LocalDateTime dateTime = LocalDateTime.of(Integer.parseInt(fulldate[0]),Integer.parseInt(fulldate[1]),
                Integer.parseInt(fulldate[2]),Integer.parseInt(fulltime[0]),Integer.parseInt(fulltime[1]));
        booksinborrow.remove(book);
        book.removeFromRent();
        student.decreesInborrow();
        student.borrowedBookremove(book);
        dataHolder.removeBorrowBookStudent(book,student,dateTime);
    }

    public void returnThesisStudent(Thesis thesis, Student student, String date, String time, DataHolder dataHolder) {
        String[] fulldate = date.split("\\-");
        String[] fulltime= time.split("\\:");
        LocalDateTime dateTime = LocalDateTime.of(Integer.parseInt(fulldate[0]),Integer.parseInt(fulldate[1]),
                Integer.parseInt(fulldate[2]),Integer.parseInt(fulltime[0]),Integer.parseInt(fulltime[1]));
        thesisInBorrow.remove(thesis);
        thesis.makeCanborrowTrue();
        student.decreesInborrow();
        student.getThesesinBorrow().remove(thesis);
        dataHolder.removeBorrowThesisStudent(thesis,student,dateTime);
    }

    public void returnBookStaff(Book book, Staff staff, String date, String time, DataHolder dataHolder) {
        String[] fulldate = date.split("\\-");
        String[] fulltime= time.split("\\:");
        LocalDateTime dateTime = LocalDateTime.of(Integer.parseInt(fulldate[0]),Integer.parseInt(fulldate[1]),
                Integer.parseInt(fulldate[2]),Integer.parseInt(fulltime[0]),Integer.parseInt(fulltime[1]));
        booksinborrow.remove(book);
        book.removeFromRent();
        staff.addInborrow(-1);
        staff.borrowedBookremove(book);
        dataHolder.removeBorrowBookStaff(book,staff,dateTime);
    }

    public void returnThesisStaff(Thesis thesis, Staff staff, String date, String time, DataHolder dataHolder) {
        String[] fulldate = date.split("\\-");
        String[] fulltime= time.split("\\:");
        LocalDateTime dateTime = LocalDateTime.of(Integer.parseInt(fulldate[0]),Integer.parseInt(fulldate[1]),
                Integer.parseInt(fulldate[2]),Integer.parseInt(fulltime[0]),Integer.parseInt(fulltime[1]));
        thesisInBorrow.remove(thesis);
        thesis.makeCanborrowTrue();
        staff.addInborrow(-1);
        staff.getThesesinBorrow().remove(thesis);
        dataHolder.removeBorrowThesisStaff(thesis,staff,dateTime);
    }

    public ArrayList<String> searchInlibrary(String searchword) {
        ArrayList <String> result = new ArrayList<>();
        for(Book i : books){
            if(i.getId().toLowerCase().contains(searchword)||i.getName().toLowerCase().contains(searchword)||
                    i.getAuthor().toLowerCase().contains(searchword)||i.getPublisher().toLowerCase().contains(searchword)){
                result.add(i.getId());
            }
        }
        for(Thesis i : thesises){
            if(i.getId().toLowerCase().contains(searchword)||i.getName().toLowerCase().contains(searchword)||
                    i.getWriter().toLowerCase().contains(searchword)||
                    i.getProfessor().toLowerCase().contains(searchword)){
                result.add(i.getId());
            }
        }
        return result;
    }

    public void report(LocalDateTime dateTime, DataHolder dataHolder, Library library) {
        if(booksinborrow.isEmpty()&&thesisInBorrow.isEmpty()){
            System.out.println("none");
            return;
        }
        ArrayList<String> result = new ArrayList<>();
        HashMap<String,ArrayList<BorrowBook>> idtoBorrowBook = dataHolder.getIdBorrowBook();
        HashMap<String,ArrayList<ThesisBorrow>> idtoborrowThesis = dataHolder.getIdBorrowThesis();
        for(Book i: books){
            if(idtoBorrowBook.containsKey(i.getId())){
                ArrayList<BorrowBook> target = idtoBorrowBook.get(i.getId());
                for (BorrowBook j : target){
                    if((j.penaltyCal(dateTime)>0)&&!(result.contains(j.getBook().getId()))){
                        result.add(j.getBook().getId());
                    }
                }

            }
        }
        for(Thesis i : thesises){
            if(idtoborrowThesis.containsKey(i.getId())){
                ArrayList<ThesisBorrow> target = idtoborrowThesis.get(i.getId());
                for(ThesisBorrow j : target){
                    if(j.penaltyCal(dateTime)>0&& !result.contains(j.getThesis().getId())){
                        result.add(j.getThesis().getId());
                    }
                }
            }
        }
        printing(result);
    }

    private void printing(ArrayList<String> result) {
        Collections.sort(result);
        if(result.isEmpty()){
            System.out.println("none");
            return;
        }
        for (int i=0;i<result.size();i++){
            System.out.print(result.get(i));
            if(i!=result.size()-1){
                System.out.print("|");
            }

        }
        System.out.println();
    }
}
