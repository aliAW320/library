import java.util.ArrayList;

public class Staff {
    private String Id;
    private String password;
    private String name,family;
    private String nationalCode;
    private int year;
    private String address;
    private int inborrow=0;
    private long penalty = 0;
    private ArrayList<Book> borrowBook = new ArrayList<>();
    private ArrayList <Thesis> thesesinBorrow = new ArrayList<>();
    Staff(String Id,String password,String name,String family,String nationalCode,int year,String address){
        this.Id=Id;
        this.password=password;
        this.name=name;
        this.family=family;
        this.nationalCode=nationalCode;
        this.year=year;
        this.address=address;
    }
    public String getname() {
        return name;
    }
    public String getFamily(){
        return family;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return Id;
    }

    public ArrayList<Book> getBorrowBook() {
        return borrowBook;
    }

    public ArrayList<Thesis> getThesesinBorrow() {
        return thesesinBorrow;
    }

    public int getInborrow() {
        return inborrow;
    }

    public long getPenalty() {
        return penalty;
    }
    public void addInborrow(int num){
        inborrow += num;
    }
    public void addPenalty(long pen){
        penalty += pen;
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

    public String getid() {
        return Id;
    }
}
