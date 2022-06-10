
package Dev_J_120;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class BookDialog extends JDialog{
    private final JTextField internalBookCodeField;
    private final JTextField ISBNField;
    private final JTextField bookNameField;
    private final JTextField authorsField;
    private final JTextField yearOfPublicationField;
    private boolean okPressed;
    
    public BookDialog(JFrame owner) {
	super(owner, true);
		
        internalBookCodeField = new JTextField();
        ISBNField = new JTextField();
	bookNameField = new JTextField();
	authorsField = new JTextField();
	yearOfPublicationField = new JTextField();
		
	initLayout();
		
	setResizable(false);
	}
    private void initLayout() {
	initControls();
	initOkCancelButtons();
	}
    private void initControls() {
       
    	JPanel controlsPane = new JPanel(null);
	controlsPane.setLayout(new BoxLayout(controlsPane, BoxLayout.Y_AXIS));
        controlsPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
	
	JPanel p1 = new JPanel(); 
        createPanelProperties(p1);
        JLabel lbl = new JLabel("Internal book code:");
	lbl.setLabelFor(internalBookCodeField);
        internalBookCodeField.setMaximumSize(new Dimension(80, 20));
        p1.add(Box.createRigidArea(new Dimension(2,20)));
	p1.add(lbl);
        p1.add(Box.createRigidArea(new Dimension(5,20)));
        p1.add(internalBookCodeField);
        p1.add(Box.createRigidArea(new Dimension(433,20)));       
	controlsPane.add(p1);

        JPanel p2 = new JPanel(); 
        createPanelProperties(p2);
	JLabel lb2 = new JLabel("ISBN:");
	lb2.setLabelFor(ISBNField);
	p2.add(Box.createRigidArea(new Dimension(80,20)));
        p2.add(lb2);
        p2.add(Box.createRigidArea(new Dimension(5,20)));
	p2.add(ISBNField);
        p2.add(Box.createRigidArea(new Dimension(380,20)));
	controlsPane.add(p2);
		
        JPanel p3 = new JPanel(); 
        createPanelProperties(p3);
	JLabel lb3 = new JLabel("Book name:");
	lb3.setLabelFor(bookNameField);
        p3.add(Box.createRigidArea(new Dimension(42,20)));
	p3.add(lb3);
        p3.add(Box.createRigidArea(new Dimension(5,20)));
	p3.add(bookNameField);
	controlsPane.add(p3);
                
        JPanel p4 = new JPanel(); 
        createPanelProperties(p4);
	JLabel lb4 = new JLabel("Authors:");
	lb4.setLabelFor(authorsField);
        p4.add(Box.createRigidArea(new Dimension(60,20)));
	p4.add(lb4);
        p4.add(Box.createRigidArea(new Dimension(5,20)));
	p4.add(authorsField);
	controlsPane.add(p4);
                
        JPanel p5 = new JPanel(); 
        createPanelProperties(p5);
	JLabel lb5 = new JLabel("Year of publication:");
	lbl.setLabelFor(yearOfPublicationField);
	p5.add(lb5);
        p5.add(Box.createRigidArea(new Dimension(5,20)));
        yearOfPublicationField.setPreferredSize(new Dimension(40, 20));
	p5.add(yearOfPublicationField);
        p5.add(Box.createRigidArea(new Dimension(470,20)));
	controlsPane.add(p5);
		
	add(controlsPane, BorderLayout.CENTER);       
	}
    
    private void initOkCancelButtons() {
	JPanel btnsPane = new JPanel();
	
	JButton okBtn = new JButton("OK");
	okBtn.addActionListener(e -> {
	    okPressed = true;
	    setVisible(false);
	    });
	okBtn.setDefaultCapable(true);
	btnsPane.add(okBtn);
		
	Action cancelDialogAction = new AbstractAction("Cancel") {
	    @Override
	public void actionPerformed(ActionEvent e) {
	            setVisible(false);
			}
		}; 		
	JButton cancelBtn = new JButton(cancelDialogAction);
	btnsPane.add(cancelBtn);
		
	add(btnsPane, BorderLayout.SOUTH);
		
	final String esc = "escape";
	getRootPane()
	    .getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
	    .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), esc);
	getRootPane().getActionMap().put(esc, cancelDialogAction);
	}
    public boolean showModal() {
	pack();
	setLocationRelativeTo(getOwner());
	if(internalBookCodeField.isEnabled())
		internalBookCodeField.requestFocusInWindow();
	else
		bookNameField.requestFocusInWindow();
	okPressed = false;
	setVisible(true);
	return okPressed;
    }
    public void prepareForAdd() {
	setTitle("New book registration");
		
	internalBookCodeField.setText("");
	ISBNField.setText("");
	bookNameField.setText("");
	authorsField.setText("");
        yearOfPublicationField.setText("");
		
	internalBookCodeField.setEnabled(true);
    }
    public void prepareForChange(BookInfo bi) {
	setTitle("Client properties change");
		
	internalBookCodeField.setText(bi.getInternalBookCode());
	ISBNField.setText(bi.getISBN());
	bookNameField.setText(bi.getBookName());
	authorsField.setText(bi.getAuthors());
        yearOfPublicationField.setText(bi.getYearOfPublication().toString());
		
	internalBookCodeField.setEnabled(false);
	}
    
    public String getInternalBookCode() {
	return internalBookCodeField.getText();
	}	
/*
Возвращает ISBN, введенный пользователем.
*/
    public String getISBN() {
	return ISBNField.getText();
    }
	
/*
Возвращает название книги, введенное пользователем.
*/
    public String getBookName() {
	return bookNameField.getText();
    }
	
/*
Возвращает авторов, введенных пользователем.
*/
    public String getAuthors() {
	return authorsField.getText();
    }
/*
Возвращает год публикации, введенный пользователем.
*/
    public Integer getYearOfPublication() {
        Integer year = 0;
      try {  
           year = Integer.valueOf(yearOfPublicationField.getText());
          }
      catch(NumberFormatException ne) {
           JOptionPane.showMessageDialog(MainFrame.findLatestWindow(), 
                    "Numbers required.",
                    "Error entering the year of publication.",
			JOptionPane.ERROR_MESSAGE);
      }
	return year;
    }
    
    public void createPanelProperties(JComponent p){
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); 
    }
}
