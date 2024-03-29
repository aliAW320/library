import java.time.Duration;
import java.time.LocalDateTime;

public class BorrowBook {
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

    public int penaltyCal(LocalDateTime dateTime) {

        if(staff!=null){
            if(Duration.between(borrowDate,dateTime).toHours()>336/*&&dateTime.isAfter(borrowDate)*/){
                return 1;
            }
            else return 0;
        }
        else {
            if(Duration.between(borrowDate,dateTime).toHours()>240/*&& dateTime.isAfter(borrowDate)*/){
                return 1;
            }
            return 0;
        }
    }
}
