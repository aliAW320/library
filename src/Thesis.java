public class Thesis
{
    private String id;
    private String name;
    private String writer;
    private String professor;
    private int year ;
    private category theiscategory;
    private Library library;
    private boolean canBorrow=true;

    Thesis(String id, String name, String writer, String professor, int year, category theiscategory, Library library){
        this.id=id;
        this.name=name;
        this.writer=writer;
        this.professor=professor;
        this.year=year;
        this.theiscategory=theiscategory;
        this.library=library;
    }
    public String getId() {
        return id;
    }
    public String getName(){
        return name;
    }
    public String getWriter(){
        return writer;
    }
    public String getProfessor (){
        return professor;
    }
    public boolean getCanborrow(){
        return canBorrow;
    }

    public Library getLibrary() {
        return library;
    }

    public category getTheiscategory() {
        return theiscategory;
    }
    public void makeCanborrowTrue(){
        canBorrow = true;
    }
    public void makeCanborrowFalse(){
        canBorrow = false;
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
                theiscategory = dataHolder.getNullcategory();
                dataHolder.getNullcategory().setTheses(thesis);

            }
            else {
                theiscategory.removeTheses(thesis);
                theiscategory =dataHolder.getCategoryIDtoName().get(info[6]);
                theiscategory.setTheses(thesis);
            }
        }

    }

    public void remove(Thesis thesis) {
        theiscategory.removeTheses(thesis);
        library.getIdtothesises().remove(id);
        library.getThesises().remove(thesis);
    }

    public boolean canBorrow() {
        return canBorrow;
    }
}
