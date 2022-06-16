package Dev_J_120;

import java.time.LocalDate;

public class BookInfo { 
    
    private  final String internalBookCode;
    private String ISBN;
    private String bookName;
    private String authors;
    private Integer yearOfPublication;

    public BookInfo(String internalBookCode, String ISBN, String bookName, 
                    String authors, Integer yearOfPublication) throws IllegalArgumentException{
        if(internalBookCode != null && !internalBookCode.trim().isEmpty())
           this.internalBookCode = internalBookCode;
        else
            throw new IllegalArgumentException(); 
        setISBN(ISBN); 
        setBookName(bookName); 
        setAuthors(authors); 
        setYearOfPublication(yearOfPublication); 
    }
/*
 ISBN - довольно сложная для контроля (с точки зрения IllegalArgumentException)штука. Если 
книга издана до 1970 г. (в России до 1987 г), она вообще не имеет этот номер и позиция ISBN 
в учетных документах библиотек остается пустой. Если ISBN присутсвует, то состоит он только 
из цифр и обязательных разделителей. Цифр может быть либо 10 (старый формат), либо 13(новый формат) 
+ 4 разделителя (обычно пробел или дефис). Таким образом, количество символов в строке ISBN 
должно быть от 14 до 17, либо 0. Наконец, проблему усугубляет то, что разделители не имеют 
стационарной позиции в номере, что делает невозможным использование форматированной маски 
в поле для ввода номера.    
    */    
    
    public final void setISBN(String ISBN) {
        if(ISBN != null && (ISBN.equals("") || (ISBN.trim().length()>=14 && ISBN.trim().length()<=17))) {
           char[] num = ISBN.trim().toCharArray();
           for(char ch : num) {
               if(Character.isLetter(ch))
                  throw new IllegalArgumentException(); }
           this.ISBN = ISBN.trim();  }  
        else
            throw new IllegalArgumentException(); 
    }
    public final void setBookName(String bookName) {
        if(bookName != null && !bookName.trim().isEmpty())
           this.bookName = bookName.trim();
        else
           throw new IllegalArgumentException(); 
    }
    public final void setAuthors(String authors) {
        if(authors != null)
           this.authors = authors;
        else
            throw new IllegalArgumentException ();  
    }
    public final void setYearOfPublication(Integer yearOfPublication) {
        if(yearOfPublication > 1475 && 
           yearOfPublication <= LocalDate.now().getYear()) 
           this.yearOfPublication = yearOfPublication;
        else
           throw new IllegalArgumentException();  
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
