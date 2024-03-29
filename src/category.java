import java.util.ArrayList;

public class category {
    private String id;
    private String name;
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList <Thesis> theses = new ArrayList<>();
    category(String id,String name ){
        this.id=id;
        this.name=name;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public ArrayList<Thesis> getTheses() {
        return theses;
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
