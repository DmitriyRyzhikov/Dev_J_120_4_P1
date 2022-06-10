
package Dev_J_120;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class BookStorage {
    
    private static final BookStorage instanceBookStorage = new BookStorage();
    private List<BookInfo> bookList = new ArrayList<>();
    private Set<String> internalBookCodeSet = new HashSet<>();

    private BookStorage() {
    }
    
    public void addBook(String internalBookCode, String ISBN, String bookName, 
                        String authors, int yearOfPublication) 
    {
        if(internalBookCodeSet.contains(internalBookCode))
            throw new IllegalArgumentException
                      ("A book with this account number has already been registered.");
        bookList.add(new BookInfo(internalBookCode, ISBN, bookName, 
                         authors, yearOfPublication));
        internalBookCodeSet.add(internalBookCode);
    }
    
    public void removeBook(int index){
        BookInfo book = bookList.get(index);
        internalBookCodeSet.remove(book.getInternalBookCode());
        bookList.remove(index);
    }
    public int getBookCount(){
        return bookList.size();
    }
    public BookInfo getBook(int index){
        return bookList.get(index);
    }
    public static BookStorage getBookStorage(){
        return instanceBookStorage;
    }

    public List<BookInfo> getBookList() {
        return bookList;
    }
}
