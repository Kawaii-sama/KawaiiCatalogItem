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
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;



public class KawaiiCatalog {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		/*
	 🎀 Variables don’t store objects. They store references to objects.
     🎀 The reference is what links your dialog to its real parent window.
	 🎀 Display -> class data type
	 🎀 Display -> reference variable
		 */
		Display display = new Display();
		// object -> reference variable
        Shell shell = new Shell(display);

        shell.setText("🌸 Kawaii Item Catalog 🌸");
        shell.setSize(400, 300);
        
     // Layout for label + text field
     /*
     GridLayout(int numColumns, boolean makeColumnsEqualWidth)
     */
        shell.setLayout(new GridLayout(2, false));
        
     // Label
     // nameLabel is reference variable
     // calling the constructor, which creates object
     // object is stored in reference variable   
        // shell(argument -> as a reference) , here is used as an argument
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
        //new Label (shell,SWT.NONE).setText("SKU:");
        //new Text(shell,SWT.BORDER).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        
        new Label (shell, SWT.NONE).setText("SKU:");
        Text skuText = new Text(shell, SWT.BORDER);
        skuText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        

        new Label (shell, SWT.NONE).setText("Quantity:");
        Text quantityText = new Text (shell, SWT.BORDER);
        quantityText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        quantityText.addVerifyListener(e-> {
        	if (!e.text.matches("[0-9]*")) {
        		e.doit = false;
        	}
        });
        
        Button clearButton = new Button (shell, SWT.PUSH);
        clearButton.setText("Clear");
        clearButton.addSelectionListener(new SelectionAdapter() {
        @Override
        public void widgetSelected (SelectionEvent e) {
        	nameText.setText("");
        	skuText.setText("");
        	quantityText.setText("");
        }
        
        });
        
        Button saveButton = new Button(shell, SWT.PUSH);
        saveButton.setText("Save");
        saveButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	// .trim() used for trimming spaces
                String productName = nameText.getText().trim();
                String sku = skuText.getText().trim();
                String quantity = quantityText.getText().trim();
                
                // short-circuit OR:
                if (productName.isEmpty() || sku.isEmpty() || quantity.isEmpty()) {
                    MessageDialog.openWarning(
                        shell,
                        "Missing Information",
                        "Oops! looks like you forgot some details <3 🌸"
                    );
                    return; // button click handling stops immediately
                }

                // later: actual save logic
                System.out.println("Saved:");
                System.out.println("Name: " + productName);
                System.out.println("SKU: " + sku);
                System.out.println("Quantity: " + quantity);
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
        
        Button searchButton = new Button (shell, SWT.PUSH);
        searchButton.setText("Search");
        // here listens opens up the dialog. so till its not open main window will handle everything.
        // after it opens up control would go to dialog
        searchButton.addSelectionListener(new SelectionAdapter () {
        	@Override
        	public void widgetSelected (SelectionEvent e) {
        		CustomDialog dialog = new CustomDialog (shell);
        		/*
        		 * new CustomDialog(shell); is essentially:-
        		 1. Allocate memory for CustomDialog
				 2. Call Dialog(parentShell) ← this is super(parentShell)
				 3. Set up dialog–parent relationship
				 4. Then run your CustomDialog constructor body
        		 */
        		dialog.open();
        		// post dialog logic
        		String enteredSku = dialog.getSkuValue();
        		if (enteredSku != null && !enteredSku.isEmpty()) {
        			System.out.println("Using SKU later: " + enteredSku);
        		}
        	}
        	
        });
        
        Button editButton = new Button (shell, SWT.PUSH);
        editButton.setText("Kawaii's Edit Button");
        /*
         * why do i need a listener for that?
         * ah well babe, because its a button click!
         */
        editButton.addSelectionListener(new SelectionAdapter() {
        	@Override
        	// widgetSelected is for logic typing3 
        	public void widgetSelected (SelectionEvent e) {
        		String currentQunatity = quantityText.getText().trim();
        		/*
        		 * dialog here is responsible for:
        		 * closing the main window
        		 * gets the new value
        		 * waits for ok/cancel
        		 * gives the value it stores.
        		 */
        		InputDialog dialog = new InputDialog (
        				shell,
        				"Edit Qunatity",
        				"Enter new quantity",
        				currentQunatity,
        				input -> input.matches("\\d+") ? null : "Only numbers allowed T^T"
        					);
        		
        		if (dialog.open() == Window.OK) {
        			String newQuantity = dialog.getValue();
        			quantityText.setText(newQuantity);
        		}
        	}
        });
        
        

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

}
