
package Dev_J_120;

import java.util.HashSet;
import java.util.Set;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;


public class BookStorageTableModel implements TableModel{
    
    private static final String[] COLUMN_HEADERS = new String[]{
            "<html><b>Book сode</b></html>",
            "<html><b>ISBN</b></html>",
            "<html><b>Book name</b></html>",
            "<html><b>Authors</b></html>",
            "<html><b>Publication year</b></html>" };
    
    private final Set<TableModelListener> modelListeners = new HashSet<>();

    @Override
    public int getRowCount() {
       return BookStorage.getBookStorage().getBookCount();
    }

    @Override
    public int getColumnCount() {
      return COLUMN_HEADERS.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_HEADERS[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
       switch(columnIndex){
           case 0:
           case 1:
           case 2:
           case 3:
           case 4:
               return String.class;
           case 5:
               return Integer.class;
       }
       throw new IllegalArgumentException
                 ("There is no column with this number.");
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
       return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        BookInfo book = getBook(rowIndex); 
        switch(columnIndex){
            case 0:
                return book.getInternalBookCode();
            case 1:
                return book.getISBN();
            case 2:
                return book.getBookName();
            case 3:
                return book.getAuthors();
            case 4:
                return book.getYearOfPublication();
        }
        throw new IllegalArgumentException
                 ("There is no column with this number.");
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) { }

    @Override
    public void addTableModelListener(TableModelListener l) {
       modelListeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
       modelListeners.remove(l); 
    }
    public BookInfo getBook (int rowIndex){
        return BookStorage.getBookStorage().getBook(rowIndex);
    }
    public void addBook(String internalBookCode, String ISBN, String bookName, 
                        String authors, int yearOfPublication)
    {
        BookStorage.getBookStorage().addBook(internalBookCode, ISBN, bookName, authors, yearOfPublication);
        int rowNum = BookStorage.getBookStorage().getBookCount() - 1;
        fireTableModelEvent(rowNum, TableModelEvent.INSERT);
    }
    public void bookChanged(int index){
        fireTableModelEvent(index, TableModelEvent.UPDATE);
    }
    public void removeBook(int index){
        BookStorage.getBookStorage().removeBook(index);
        fireTableModelEvent(index, TableModelEvent.DELETE);
    }
    private void fireTableModelEvent(int rowNum, int evtType) {
        TableModelEvent tme = new TableModelEvent(this, rowNum, rowNum,
                TableModelEvent.ALL_COLUMNS, evtType);
        for (TableModelListener l : modelListeners) {
            l.tableChanged(tme);
        }
    }
    
    //следующие методы подгуглены
    
    //задает ширину столбцов таблицы. 
    public static void setJTableColumnsWidth(JTable table, int tablePreferredWidth,
        double... percentages) {
    double total = 0;
    for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
        total += percentages[i];
    } 
    for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
        TableColumn column = table.getColumnModel().getColumn(i);
        column.setPreferredWidth((int)
                (tablePreferredWidth * (percentages[i] / total)));
        }
    }
    //метод выравнивает содержимое ячейки таблицы, в данном случае - по центру
    public static void alignCenter(JTable table, int column) {
         DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
         centerRenderer.setHorizontalAlignment(JLabel.CENTER);
         table.getColumnModel().getColumn(column).setCellRenderer(centerRenderer); 
    }   
}
