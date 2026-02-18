package kawaiicatalogitem;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;



public class CustomDialog extends Dialog {
	// private:- only same class access
	
	private Text skuText;
	private String skuValue;
	
	//by receiving shell reference in constructor it knows which parent window it belongs to
	//reference points to the actual shell object
	public CustomDialog (Shell parentShell) {
	
		super (parentShell);
	/*super(parentShell) passes the parent window to the Dialog part of your 
	 *object so the dialog knows who it belongs to.
	*/
	// “Call the constructor of my parent class (Dialog) and pass parentShell to it.”
	// since CustomDialog IS-A Dialog --> Dialog cannot exist without knowing which Shell is its parent
	}
	
	@Override

	protected void configureShell (Shell shell) {
		// protected -> accessible to subclasses
		/*
		 * This Shell is the dialog’s own shell
		   JFace internally creates a new shell for the dialog
		   Then it calls: configureShell(dialogShell)
		 */
		super.configureShell(shell);
		//Call Dialog’s version of configureShell()
		// configureShell() --> builds contents
		// the opens dialog
		shell.setText("Search by SKU"); // puchna hai bhaiya se
		
	}
	
	@Override
	// Control --> returning some UI thing
	// Composite = a container that can hold other controls
	/*
	   A dialog has a shell
       Inside the shell → composites
       Inside composites → labels, text fields, buttons
	 */
	protected Control createDialogArea (Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout (2, false));
		
		Label label = new Label (container, SWT.NONE);
		label.setText("SKU:");
		
		skuText = new Text (container, SWT.BORDER);
		skuText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		return container;
	}
	
	@Override
	protected void okPressed() {
		skuValue = skuText.getText().trim();
		super.okPressed();
	}
	
	public String getSkuValue() {
		return skuValue;
	}
}