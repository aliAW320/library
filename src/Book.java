public class Book {
    private String Id;
    private String name;
    private String author;
    private String publisher;
    private String year;
    private int number;
    private category bookcategory;
    private Library library;
    private int inborrow = 0;
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

    public String getId() {
        return Id;
    }
    public String getName(){
        return name;
    }
    public String getAuthor(){
        return author;
    }
    public String getPublisher (){
        return publisher;
    }
    public int getNumber(){
        return number;
    }
    public int getInborrow(){
        return inborrow;
    }
    public category getBookcategory(){
        return bookcategory;
    }

    public Library getLibrary() {
        return library;
    }

    public void edit(String[] info, Book book, DataHolder dataHolder) {
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
                book.bookcategory= dataHolder.getNullcategory();
                dataHolder.getNullcategory().setBooks(book);
            }
            else{
                book.bookcategory.removebook(book);
                book.bookcategory=dataHolder.getCategoryIDtoName().get(info[7]);
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
