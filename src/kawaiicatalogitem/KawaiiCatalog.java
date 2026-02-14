package kawaiicatalogitem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class KawaiiCatalog {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		
		Display display = new Display();
        Shell shell = new Shell(display);

        shell.setText("Kawaii Item Catalog");
        shell.setSize(400, 300);
        
     // Layout for label + text field
        shell.setLayout(new GridLayout(2, false));
        /*
         GridLayout(int numColumns, boolean makeColumnsEqualWidth)
         */
        
     // Label
     // nameLabel is reference variable
     // calling the constructor, which creates object
     // object is stored in reference variable   
        Label nameLabel = new Label(shell, SWT.NONE);
        nameLabel.setText("Product name:");
        
     // Text field
        Text nameText = new Text(shell, SWT.BORDER);
        nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        /*
         nameText.setLayoutData(...) 
         ⬆ this controls how THIS Text behaves inside that grid
         GridData(horizontalAlignment,
         verticalAlignment,
         grabExcessHorizontalSpace,
         grabExcessVerticalSpace)
         */
        
        
        // anonymus object creation / inline construction
        new Label (shell,SWT.NONE).setText("SKU:");
        new Text(shell,SWT.BORDER).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        

        new Label (shell, SWT.NONE).setText("Quantity:");
        Text quantityText = new Text (shell, SWT.BORDER);
        quantityText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        quantityText.addVerifyListener(e-> {
        	if (!e.text.matches("[0-9]*")) {
        		e.doit = false;
        	}
        });
        
        /*
        Button printButton = new Button(shell, SWT.PUSH);
        printButton.setText("Print Product Name");
        
        GridData buttonData = new GridData(SWT.CENTER, SWT.CENTER, false, false);
        buttonData.horizontalSpan = 2;
        printButton.setLayoutData(buttonData);
        */
        
     // Button click handler
     // SelectionListener -> SelecetionAdapter -> widgetSelected
     /*
      * SelectionListener → rule
      						defines contract
		SelectionAdapter → helper implementation
						   provides empty defaults
		addSelectionListener → registration step
							   registers behaviour.
		widgetSelected → callback executed by SWT
						 overridden to define our action
      */
     // SelectionAdapter is a SelectionListener which implements SelectionListener
        /*
         * This is called an anonymous inner class:
         * anonymous → no name
         * inner → written inside another call
         * Nothing magical — just compact syntax 🌷
         */
        /*
        printButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                System.out.println(nameText.getText());
            }
        });
        */
        

        shell.open();
        
      // Event loop bas direct karta hai events ko
      // listeners hote hai jo active hotein hai 
        while (!shell.isDisposed()) { //event loop in a way laptop fan doesnt cry
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
      // no shell close because it is user driven
        display.dispose();


	}

// control + R
}
