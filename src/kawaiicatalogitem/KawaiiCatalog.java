package kawaiicatalogitem;

import java.util.ArrayList;
import java.util.List;

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
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;



public class KawaiiCatalog {
	
	/* for creating rows in table , i need a row object , can be created through class
	 * static keyword:- standalone class, only need outer class context for structure
	 * techincal name of below class is:- KawaiiCatalog.CatalogItem
	 */
	static class CatalogItem {
		//instance variable (belong to object), empty boxes
		String name; String sku; String quantity;
		
		/*Constructors parameters (temporary, only when constructors run)
		 * this is the current object that is created
		 * this.name = this + empty boxes (Field)
		 * this.name = name means take the value passed in the constructor and store it in box
		 * 
		 * before constructor runs:- values stored in parameters, fields are empty
		 * after constructors runs:- values are permanent in fiels, parameters disappearp
		 */
		CatalogItem (String name, String sku, String quantity) {
			this.name = name; this.sku = sku; this.quantity = quantity;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		/*
	 🎀 Variables don’t store objects. They store references to objects.
     🎀 The reference is what links your dialog to its real parent window.
	 🎀 Display -> class data type
	 🎀 Display -> reference variable
		 */
		Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("🌸 Kawaii Item Catalog 🌸");
        shell.setSize(400, 300);
        
     // Layout for label + text field
     /*
     GridLayout(int numColumns, boolean makeColumnsEqualWidth)
     */
        shell.setLayout(new GridLayout(2, false));
        
        
        /* In-memory list :- data lives in RAM
         * List<CatalogItem> = A list that is ONLY allowed to store CatalogItem objects
         * ArrayList<>(); = java's array lists are flexible
         */
        List<CatalogItem> items = new ArrayList<>();
        
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
        
        //Table viewer
        /*
         * 🩷 Table viewer is a manager that:
         * creates a table (SWT)
         * connects data to rows
         * keeps UI and data in sync
         * | bitwise OR :- used for style flags
         * FULL_SELECTION:- highlight the whole row when clicked
         * getTable() :- gives access to created table
         * setHeaderVisible(true):- show column headers (false would have falsed it)
         * setLinesVisible(true):- this makes horizontal row lines visible. (false would false it)
         */
        TableViewer tableViewer = new TableViewer (shell, SWT.BORDER | SWT.FULL_SELECTION);
        tableViewer.getTable().setHeaderVisible(true);
        tableViewer.getTable().setLinesVisible(true);
        
        /*
         * GridData(horizontalAlignment, verticalAlignment, grabExcessHorizontalSpace, grabExcessVerticalSpace)
         * FILL = how much space you use
         */
        GridData tableData = new GridData (SWT.FILL, SWT.FILL, true, true);
        // widget should occupy both columns (without it , table would take half column)
        // horizontalSpan = how much space you are allowed
        tableData.horizontalSpan = 2;
        tableViewer.getTable().setLayoutData(tableData);
        /*
         * ContentProvider:- Given this input, how would i get individual row elements
         * Array content provider:- because we are using array list, creates one instance at a time
         * getInstance:- Give me the reusable singleton instance.
         */
        tableViewer.setContentProvider(ArrayContentProvider.getInstance());
        
        // Columns
        /*
         * tableViewer:- This column belongs to that table.
         * "name":- the text shown at the top of the column.
         * 200:- width in pixels.
         * Instructions for columns
         */
        createColumn (tableViewer, "Name", 200,
        		/*
        		 * why all this? Extract one specific value from the row object so the column can display it.
        		 * object → viewed with correct type → value extracted. its like pov changes.
        		 * item is the row object.
        		 * We don’t change it — we just tell Java what type it really is,
        		 * so we’re allowed to take the extract value we want from it,
        		 * and return that value to the column to display.”
        		 */
        		item -> ((CatalogItem) item). name);
        createColumn (tableViewer, "SKU", 150,
                item -> ((CatalogItem) item).sku);
        createColumn (tableViewer, "Quantity", 100,
        		item -> ((CatalogItem) item). quantity);
        
        //“Hey TableViewer, here is the list of objects you should display as rows.”
        // Handing over the entire list to the table.
        /*
         * after execution of below code line:-
         * Loops through the list (quietly, internally).
         * Treats each CatalogItem as one row.
         * For each column:
         * calls your lambda.
         * extracts the correct value (name, sku, quantity).
         * Displays everything on screen ✨.
         */
        tableViewer.setInput(items);
        		
        
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
                
                items.add(new CatalogItem (productName, sku, quantity));
                tableViewer.refresh();

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
        	// widgetSelected is for logic typing 
        	public void widgetSelected (SelectionEvent e) {
        		String currentQunatity = quantityText.getText().trim();
        		InputDialog dialog = new InputDialog (
        				shell,
        				"Edit Qunatity",
        				"Enter new quantity",
        				currentQunatity,
        				input -> input.matches("\\d+") ? null : "Only numbers allowed T^T"
        					);
        		// Only continue if the user confirmed the dialog.
        		/*
        		 * dialog.open() here is responsible for:
        		 * closing the main window
        		 * gets the new value
        		 * waits for ok/cancel
        		 * gives the value it stores.
        		 */
        		// agar iss sabh ka yeh matlabh hai toh syntax aisa kyun hia ??? T^T
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
	
	/*
	 * private:- only THIS class can use 
	 * static because just a utility function.
	 * TableViewer:- same ol table viewer
	 * String:- column header text.
	 * int width:- column width on screen.
	 * Function:- take one row object input, return a String to display
	 * creates one new column and attaches it to table viewer
	 * Actual column generattion
	 */
	private static void createColumn (
			TableViewer viewer, String title, int width,
			java.util.function.Function <Object, String> mapper) {
		TableViewerColumn column = new TableViewerColumn (viewer, SWT.NONE);
		column.getColumn().setText(title); column.getColumn().setWidth(width);
		
		/*
		 * below line gives column instruction
		 */
		column.setLabelProvider(new ColumnLabelProvider () {
			@Override
			/*
			 * element = one row's object; yes again :D
			 * It’s Object because the table doesn’t know the type.
			 * below function renders but only when called
			 */
			public String getText (Object element) {
				/*
				 * Use the function we were given to extract the text from this row object.
				 * element → row object
				 * mapper → your lambda
				 * result → string shown in column
				 */
				return mapper.apply(element);
			}
		});
	}

}


