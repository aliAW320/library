import java.util.ArrayList;

public class Student {
    private String Id;
    private String password;
    private String name,family;
    private String nationalCode;
    private int year;
    private String address;
    private int borrow=0 ;
    private long penalty = 0;
    private ArrayList<Book> bookinborrow = new ArrayList<>();
    private ArrayList<Thesis> thesesinBorrow = new ArrayList<>();

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

    public String getname() {
        return name;
    }
    public String getFamily(){
        return family;
    }

    public String getid() {
        return Id;
    }

    public ArrayList<Thesis> getThesesinBorrow() {
        return thesesinBorrow;
    }

    public long getPenalty() {
        return penalty;
    }

    public ArrayList<Book> getBookinborrow() {
        return bookinborrow;
    }
    public void addpenalty(long pen){
        penalty += pen;
    }
    public void addInborrow(){
        borrow ++;
    }
    public void decreesInborrow(){
        borrow--;
    }
    public int getinborrow(){
        return borrow;
    }
}
