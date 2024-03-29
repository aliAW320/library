import java.time.Duration;
import java.time.LocalDateTime;

public class ThesisBorrow {
    private Thesis thesis;
    private Student student=null;
    private Staff staff=null;
    private LocalDateTime borrowDate;
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

    public int penaltyCal(LocalDateTime dateTime) {
        if(staff!=null){
            if(Duration.between(borrowDate,dateTime).toHours()>240&&dateTime.isAfter(borrowDate)){
                return 1;
            }
            else return 0;
        }
        else {
            if(Duration.between(borrowDate,dateTime).toHours()>168&&dateTime.isAfter(borrowDate)){

                return 1;
            }
            return 0;
        }
    }
}
