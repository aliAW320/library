import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
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
        } else if (order.startsWith("search-user")) {
            searchUser(order,dataHolder);
        } else if (order.startsWith("search")) {
            searching(order,dataHolder);
        } else if (order.startsWith("category-report")){
            categoryreport(order,dataHolder);
        } else if(order.startsWith("library-report")){
            libraryReport(order,dataHolder);
        } else if(order.startsWith("report-passed-deadline")){
            reportPassedDeadline(order,dataHolder);
        } else if(order.equals("report-penalties-sum")){
            dataHolder.reportPenalties();
        }
    }



    private static void reportPassedDeadline(String order, DataHolder dataHolder) {
        String[] info = order.split("\\|");
        String[] id = info[0].split("\\#");
        if(dataHolder.checkLibraryExist(id[1])){
            Library library = dataHolder.getIdTolibrary().get(id[1]);
            String[] fulldate = info[1].split("\\-");
            String[] fulltime= info[2].split("\\:");
            LocalDateTime dateTime = LocalDateTime.of(Integer.parseInt(fulldate[0]),Integer.parseInt(fulldate[1]),
                    Integer.parseInt(fulldate[2]),Integer.parseInt(fulltime[0]),Integer.parseInt(fulltime[1]));
            library.report(dateTime,dataHolder,library);
        }
        else {
            System.out.println("not-found");
        }
    }

    private static void libraryReport(String order, DataHolder dataHolder) {
        String[] id= order.split("\\#");
        if(dataHolder.checkLibraryExist(id[1])){
            Library library = dataHolder.getIdTolibrary().get(id[1]);
            int booknum=0;
            for(Book i: library.getBooks()){
                booknum+= i.getNumber();
            }
            System.out.println(booknum+" "+library.getThesises().size()+" "+library.getBooksinborrow().size()
                    +" "+library.getThesisInBorrow().size());
        }
        else {
            System.out.println("not-found");
        }
    }

    private static void categoryreport(String order, DataHolder dataHolder) {
        String[] word = order.split("\\#");
        if((dataHolder.checkCategoryExist(word[1]))||word[1].equals("null")){
            category categorytarget ;
            if(word[1].equals("null")){
                categorytarget=dataHolder.getNullcategory();
            }
            else {
                categorytarget=dataHolder.giveCategory(word[1]);
            }
            int booknum = 0;
            for(Book i : categorytarget.getBooks()){
                booknum += i.getNumber();
            }
            System.out.println(booknum+" "+ categorytarget.getTheses().size());
        }
        else{
            System.out.println("not-found");
        }
    }

    private static void searchUser(String order, DataHolder dataHolder) {
        String[] info = order.split("\\|");
        String[] id = info[0].split("\\#");
        ArrayList<String> result = new ArrayList<>();
        if(dataHolder.checkStudentExist(id[1])){
            Student student = dataHolder.getStudent(id[1]);
            if(!student.checkpass(info[1])){
                System.out.println("invalid-pass");
                return;
            }
            result = dataHolder.searchinguser(info[2]);
            printingResult(result);
        }
        //staff check
        else if (dataHolder.checkStaffExist(id[1])) {
            Staff staff = dataHolder.getStaff(id[1]);
            if(!staff.checkpass(info[1])){
                System.out.println("invalid-pass");
                return;
            }
            result = dataHolder.searchinguser(info[2]);
            printingResult(result);
        }
        else {
            System.out.println("not-found");
        }
    }





    private static void searching(String order, DataHolder dataHolder) {
        String[] word = order.split("\\#");
        String searchword = word[1].toLowerCase();
        ArrayList<String> result = new ArrayList<>();
        for(Library i : dataHolder.getLibraries()){
            result.addAll(i.searchInlibrary(searchword));
        }
        result = removeDuplicate(result);
        printingResult(result);
    }

    private static ArrayList<String> removeDuplicate(ArrayList<String> result) {
        ArrayList<String > newres = new ArrayList<>();
        for(String i : result){
            if(!newres.contains(i)){
                newres.add(i);
            }
        }
        return newres;
    }

    private static void printingResult(ArrayList<String> result) {
        if(result.isEmpty()){
            System.out.println("not-found");
        }
        else {
            Collections.sort(result);
            for(int i = 0 ; i< result.size();i++){
                System.out.print(result.get(i));
                if(i!= result.size()-1){
                    System.out.print("|");
                }
            }
            System.out.println();
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
            Library lib = dataHolder.getIdTolibrary().get(info[2]);
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
            Library lib = dataHolder.getIdTolibrary().get(info[2]);
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
            Library lib = dataHolder.getIdTolibrary().get(info[2]);
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
            Library lib = dataHolder.getIdTolibrary().get(info[2]);
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
        if((staff.getInborrow()!=0)||staff.getPenalty()!=0){
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
        if((student.getinborrow()!=0)||student.getPenalty()!=0){
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
            Library lib = dataHolder.getIdTolibrary().get(info[1]);
            if(!lib.checkThesisInLibrary(id[1])){
                Thesis thesis = lib.giveThesis(id[1]);
                if(thesis.getCanborrow()){
                    thesis.remove(thesis);
                    System.out.println("success");
                }
                else {
                    System.out.println("not-allowed");
                    return;
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
            Library lib = dataHolder.getIdTolibrary().get(info[1]);
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
            Library lib= dataHolder.getIdTolibrary().get(info[info.length-1]);
            if(lib.checkThesisInLibrary(id[1])){
                if(info[info.length-2].equals("null")){
                    lib.addThesis(id[1],info[1],info[2],info[3],Integer.parseInt(info[4]),
                            dataHolder.getNullcategory(),lib,dataHolder );
                    System.out.println("success");

                } else if (dataHolder.checkCategoryExist(info[info.length-2])) {
                    lib.addThesis(id[1],info[1],info[2],info[3],Integer.parseInt(info[4]),
                            dataHolder.getCategoryIDtoName().get(info[5]),lib,dataHolder );
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
        Library lib = dataHolder.getIdTolibrary().get(info[1]);
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
        if(dataHolder.getIdTolibrary().containsKey(info[1])){
            Library lib= dataHolder.getIdTolibrary().get(info[1]);
            if(!lib.checkBookInLibrary(id[1])){
                Book book = lib.giveBook(id[1]);
                if(book.getInborrow()!=0){
                    System.out.println("not-allowed");
                    return;
                }
                book.getBookcategory().removebook(book);
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
            Library lib = dataHolder.getIdTolibrary().get(info[info.length-1]);
            if (info[info.length - 2].equals("null")) {
                if (dataHolder.getIdTolibrary().get(info[info.length - 1]).checkBookInLibrary(id[1])) {
                    dataHolder.getIdTolibrary().get(info[info.length - 1]).addBook(id[1],
                            info[1], info[2], info[3], info[4], Integer.parseInt(info[5]),
                            dataHolder.getNullcategory(),lib);

                    System.out.println("success");
                } else {
                    System.out.println("duplicate-id");
                }
            } else if (dataHolder.checkCategoryExist(info[info.length - 2])) {
                if (dataHolder.getIdTolibrary().get(info[info.length - 1]).checkBookInLibrary(id[1])) {
                    dataHolder.getIdTolibrary().get(info[info.length - 1]).addBook(id[1],
                            info[1], info[2], info[3], info[4], Integer.parseInt(info[5]),
                            dataHolder.getCategoryIDtoName().get(info[6]) ,lib);
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
        if(!(dataHolder.getCategoryIDtoName().containsKey(id[1]))){
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
        if(!(dataHolder.getIdTolibrary().containsKey(id[1]))){
            dataHolder.setLibraries(new Library(id[1],info[1],info[2],info[3],info[4]));
            dataHolder.setIdTolibrary(id[1],dataHolder.getLibraries().get(dataHolder.getLibraries().size()-1));
            System.out.println("success");
        }
        else{
            System.out.println("duplicate-id");
        }

    }

}