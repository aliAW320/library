import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class DataHolder {
    //reaching from library id to the library his self
    private HashMap<String,Library> IdTolibrary = new HashMap<>();
    //a list of all library
    private ArrayList<Library> libraries = new ArrayList<>();
    // reaching from a category id to category him self
    private HashMap <String,category> categoryIDtoName = new HashMap<>();
    //null category
    private category nullcategory = new category(null,null);
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

    public HashMap<String, Library> getIdTolibrary() {
        return IdTolibrary;
    }

    public ArrayList<Library> getLibraries() {
        return libraries;
    }

    public HashMap<String, category> getCategoryIDtoName() {
        return categoryIDtoName;
    }

    public category getNullcategory() {
        return nullcategory;
    }

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
        if(IdBorrowBook.containsKey(book.getId())){
            IdBorrowBook.get(book.getId()).add(borrowBook);
        }
        else{
            IdBorrowBook.put(book.getId(),new ArrayList<BorrowBook>());
            IdBorrowBook.get(book.getId()).add(borrowBook);

        }
    }

    public void addThesisBorrow(Thesis thesis,ThesisBorrow thesisBorrow) {
        if(IdBorrowThesis.containsKey(thesis.getId())){
            IdBorrowThesis.get(thesis.getId()).add(thesisBorrow);
        }
        else{
            IdBorrowThesis.put(thesis.getId(),new ArrayList<ThesisBorrow>());
            IdBorrowThesis.get(thesis.getId()).add(thesisBorrow);

        }
    }

    public void removeBorrowBookStudent(Book book, Student student, LocalDateTime dateTime) {
        ArrayList<BorrowBook> borrowBooks = IdBorrowBook.get(book.getId());
        long hours=1000 ;
        BorrowBook borrowBooktarget = null;
        for(BorrowBook i : borrowBooks){
            if(i.getStudent()!=null){
                if((i.getStudent().equals(student))&&(i.getBook().getLibrary().equals(book.getLibrary()))&&
                        (Duration.between(i.getBorrowDate(),dateTime)).toHours()>=0){
                    if((Duration.between(i.getBorrowDate(),dateTime)).toHours()<hours){
                        hours=(Duration.between(i.getBorrowDate(),dateTime)).toHours();
                        borrowBooktarget=i;
                    }
                }
            }

        }
        borrowBooks.remove(borrowBooktarget);
        if(IdBorrowBook.get(book.getId()).isEmpty()){
            IdBorrowBook.remove(book.getId());
        }
        if(hours>240){
            long penalty= (hours-240)*50;
            student.addpenalty(penalty);
            System.out.println(penalty);
        }
        else {
            System.out.println("success");
        }
    }

    public void removeBorrowThesisStudent(Thesis thesis, Student student, LocalDateTime dateTime) {
        ArrayList<ThesisBorrow> thesisBorrows = IdBorrowThesis.get(thesis.getId());
        long hours=1000 ;
        ThesisBorrow thesisBorrowtarget = null;
        for(ThesisBorrow i : thesisBorrows){
            if(i.getStudent()!=null){
                if(i.getStudent().equals(student)&&i.getThesis().getLibrary().equals(thesis.getLibrary())&&
                        (Duration.between(i.getBorrowDate(),dateTime)).toHours()>=0){
                    if((Duration.between(i.getBorrowDate(),dateTime)).toHours()<hours){
                        hours=(Duration.between(i.getBorrowDate(),dateTime)).toHours();
                        thesisBorrowtarget=i;
                    }
                }
            }

        }
        thesisBorrows.remove(thesisBorrowtarget);
        if(IdBorrowThesis.get(thesis.getId()).isEmpty()){
            IdBorrowThesis.remove(thesis.getId());
        }

        if(hours>168){
            long penalty= (hours-168)*50;
            student.addpenalty(penalty);

            System.out.println(penalty);
        }
        else {
            System.out.println("success");
        }
    }

    public void removeBorrowBookStaff(Book book, Staff staff, LocalDateTime dateTime) {
        ArrayList<BorrowBook> borrowBooks = IdBorrowBook.get(book.getId());
        long hours=1000 ;
        BorrowBook borrowBooktarget = null;
        for(BorrowBook i : borrowBooks){
            if(i.getStaff()!=null){
                if(i.getStaff().equals(staff)&&i.getBook().getLibrary().equals(book.getLibrary())&&
                        (Duration.between(i.getBorrowDate(),dateTime)).toHours()>0){
                    if((Duration.between(i.getBorrowDate(),dateTime)).toHours()<hours){
                        hours=(Duration.between(i.getBorrowDate(),dateTime)).toHours();
                        borrowBooktarget=i;
                    }
                }
            }

        }
        borrowBooks.remove(borrowBooktarget);
        if(IdBorrowBook.get(book.getId()).isEmpty()){
            IdBorrowBook.remove(book.getId());
        }
        if(hours>336){
            long penalty= (hours-336)*100;
            staff.addPenalty(penalty);;

            System.out.println(penalty);
        }
        else {
            System.out.println("success");
        }
    }

    public void removeBorrowThesisStaff(Thesis thesis, Staff staff, LocalDateTime dateTime) {
        ArrayList<ThesisBorrow> thesisBorrows = IdBorrowThesis.get(thesis.getId());
        long hours=1000 ;
        ThesisBorrow thesisBorrowtarget = null;
        for(ThesisBorrow i : thesisBorrows){
            if(i.getStaff()!=null){
                if(i.getStaff().equals(staff)&&i.getThesis().getLibrary().equals(thesis.getLibrary())&&
                        (Duration.between(i.getBorrowDate(),dateTime)).toHours()>=0){
                    if((Duration.between(i.getBorrowDate(),dateTime)).toHours()<hours){
                        hours=(Duration.between(i.getBorrowDate(),dateTime)).toHours();
                        thesisBorrowtarget=i;
                    }
                }
            }

        }
        thesisBorrows.remove(thesisBorrowtarget);
        if(IdBorrowThesis.get(thesis.getId()).isEmpty()){
            IdBorrowThesis.remove(thesis.getId());
        }

        if(hours>240){
            long penalty= (hours-240)*100;
            staff.addPenalty(penalty);

            System.out.println(penalty);
        }
        else {
            System.out.println("success");
        }
    }

    public ArrayList<String> searchinguser(String word) {
        String searchword = word.toLowerCase();
        ArrayList<String> result = new ArrayList<>();
        for(Student i : students){
            if(i.getname().toLowerCase().contains(searchword)||i.getFamily().toLowerCase().contains(searchword)){
                if (!result.contains(i.getid())){
                    result.add(i.getid());
                }
            }
        }
        for(Staff i : staffes){
            if(i.getname().toLowerCase().contains(searchword)||i.getFamily().toLowerCase().contains(searchword)){
                if (!result.contains(i.getid())){
                    result.add(i.getid());
                }
            }
        }
        Collections.sort(result);
        return result;
    }

    public category giveCategory(String id) {
        return categoryIDtoName.get(id);
    }

    public void reportPenalties() {
        long penalties = 0;
        for(Student i : students){
            penalties+= i.getPenalty();
        }
        for(Staff i : staffes){
            penalties += i.getPenalty();
        }
        System.out.println(penalties);
    }
    public HashMap<String,ArrayList<BorrowBook>> getIdBorrowBook(){
        return IdBorrowBook;
    }
    public HashMap<String,ArrayList<ThesisBorrow>> getIdBorrowThesis(){
        return IdBorrowThesis;
    }
}
