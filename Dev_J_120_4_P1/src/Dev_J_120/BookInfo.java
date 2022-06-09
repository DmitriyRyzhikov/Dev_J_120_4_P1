package Dev_J_120;

import java.time.LocalDate;

public class BookInfo { 
    
    private  final String internalBookCode;
    private String ISBN;
    private String bookName;
    private String authors;
    private Integer yearOfPublication;

    public BookInfo(String internalBookCode, String ISBN, String bookName, 
                    String authors, int yearOfPublication) {
        if(internalBookCode != null && !internalBookCode.trim().isEmpty())
           this.internalBookCode = internalBookCode;
        else
            throw new IllegalArgumentException
                     ("Поле \"Внутрибиблиотечный код книги\" должно быть заполнено."); 
        setISBN(ISBN); 
        setBookName(bookName); 
        setAuthors(authors); 
        setYearOfPublication(yearOfPublication); 
    }
    public final void setISBN(String ISBN) {
        if(ISBN != null && !ISBN.trim().isEmpty() && 
             (ISBN.trim().length()>=10 && ISBN.trim().length()<=18)) {
           this.ISBN = ISBN.trim();  }  
        else
            throw new IllegalArgumentException
                     ("Не введен номер ISBN или введенный номер не соответствует стандарту."); 
    }
    public final void setBookName(String bookName) {
        if(bookName != null && !bookName.trim().isEmpty())
           this.bookName = bookName.trim();
        else
           throw new IllegalArgumentException
                     ("Поле \"Название книги\" должно быть заполнено."); 
    }
    public final void setAuthors(String authors) {
        if(authors != null)
           this.authors = authors;
        else
            throw new IllegalArgumentException 
                      ("Поле \"Авторы\" не может быть Null.");  
    }
    public final void setYearOfPublication(int yearOfPublication) {
        if(yearOfPublication > 1475 && 
           yearOfPublication <= LocalDate.now().getYear()) 
           this.yearOfPublication = yearOfPublication;
        else
           throw new IllegalArgumentException
                     ("Поле \"Год издания\" не заполнен или заполнен некорректно.");  
    }

    public String getInternalBookCode() {
        return internalBookCode;
    }
    
    public String getISBN() {
        return ISBN; }
    
    public String getBookName() {
       return bookName;  }
    
    public String getAuthors() {
       return authors; }
    
    public Integer getYearOfPublication() {
       return yearOfPublication;  }

    @Override
    public String toString() {
        
        final String CS = "\u0009"; //column separator
        String s = internalBookCode+CS+ISBN+CS+bookName+CS+authors+CS+yearOfPublication;
        return s;
    }
    
    
}
