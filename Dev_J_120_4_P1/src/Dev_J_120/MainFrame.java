
package Dev_J_120;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;

public class MainFrame extends JFrame{
    
    private final BookStorageTableModel bookTableModel = new BookStorageTableModel();
    private final FileService fs = new FileService();
    private final JTable booksTable = new JTable();	
    private final BookDialog bookDialog = new BookDialog(this);
    
    public MainFrame() {
        super("Book accounting system");
		
	initMenu();
	initLayout();	
	setBounds(150, 100, 700, 500);
        startApp();
        closeApp();
    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu operations = new JMenu("Operations");
        operations.setMnemonic('O');
        menuBar.add(operations);

        addMenuItemTo(operations, "Add", 'A',
                KeyStroke.getKeyStroke('A', InputEvent.ALT_DOWN_MASK),
                e -> addBook());

        addMenuItemTo(operations, "Change", 'C',
                KeyStroke.getKeyStroke('C', InputEvent.ALT_DOWN_MASK),
                e -> changeBook());

        addMenuItemTo(operations, "Delete", 'D',
                KeyStroke.getKeyStroke('D', InputEvent.ALT_DOWN_MASK),
                e -> delBook());

        setJMenuBar(menuBar);
	}
    private void addMenuItemTo(JMenu parent, String text, char mnemonic,
        KeyStroke accelerator, ActionListener al) {
        JMenuItem mi = new JMenuItem(text, mnemonic);
        mi.setAccelerator(accelerator);
        mi.addActionListener(al);
        parent.add(mi);
    }
    private void initLayout() {
	booksTable.setModel(bookTableModel);
	booksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
	add(booksTable.getTableHeader(), BorderLayout.NORTH);
	add(new JScrollPane(booksTable), BorderLayout.CENTER);
    }
    private void addBook() {
	bookDialog.prepareForAdd();
	while(bookDialog.showModal()) {
	    try {
		bookTableModel.addBook(bookDialog.getInternalBookCode(), bookDialog.getISBN(), 
                bookDialog.getBookName(), bookDialog.getAuthors(), bookDialog.getYearOfPublication());
		    return;
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(this, "The registration form is not filled in correctly.", "Book registration error",
			JOptionPane.ERROR_MESSAGE);
	    }
	}
    }
    private void changeBook() {
	int seldRow = booksTable.getSelectedRow();
	if(seldRow == -1)
	    return;
		
	BookInfo bi = bookTableModel.getBook(seldRow); 
	bookDialog.prepareForChange(bi);
	if(bookDialog.showModal()) {
	    bi.setISBN(bookDialog.getISBN());	
            bi.setBookName(bookDialog.getBookName());
            bi.setAuthors(bookDialog.getAuthors());
            bi.setYearOfPublication(bookDialog.getYearOfPublication());
            bookTableModel.bookChanged(seldRow);
	}
    }
    private void delBook() {
		int seldRow = booksTable.getSelectedRow();
		if(seldRow == -1)
			return;
		
		BookInfo bi = bookTableModel.getBook(seldRow);
		if(JOptionPane.showConfirmDialog(this, 
				"Are you sure you want to delete book\n"
					+ "with Internal book code " + bi.getInternalBookCode() + "?", 
				"Delete confirmation", 
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			bookTableModel.removeBook(seldRow);
		}
	}
    private void closeApp(){
        addWindowListener(new WindowAdapter() { 
            @Override
            public void windowClosing(WindowEvent e) {
                int n = JOptionPane
                        .showConfirmDialog(e.getWindow(), "Closing the app? Are you sure?",
                                "Confirmation of closing", JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE);
                if (n == 0) {
                    List<BookInfo> list = BookStorage.getBookStorage().getBookList();
                    try { 
                        fs.saveBooksToFile(list);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(new MainFrame(), 
                                "An error occurred while writing the file.", "Error. The application will be stopped.",
			JOptionPane.ERROR_MESSAGE);
                    }
                    e.getWindow().setVisible(false);
                    System.exit(0);
                }
                else
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        });
    }
    private void startApp(){
        addWindowListener(new WindowAdapter() { 
            @Override
        public void windowOpened(WindowEvent e) { 
            List<String> sourceList = new ArrayList<>();
                try {
                    sourceList = fs.extractBooksFromFile();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(new MainFrame(), 
                    "The data source file is missing or reading from it is impossible.",
                    "Error. An error occurred while reading the file.",
			JOptionPane.ERROR_MESSAGE);
                }
             sourceList.forEach(x -> {
            String[] temp = x.split("\u0009");
            bookTableModel.addBook(temp[0], temp[1], temp[2], temp[3], Integer.parseInt(temp[4]));   
            });
        }
    });
}
}

