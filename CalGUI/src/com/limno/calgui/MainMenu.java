package com.limno.calgui;

import java.awt.Component;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;

import org.swixml.SwingEngine;


public class MainMenu implements ActionListener, ItemListener {
	private SwingEngine swix;

	//Declare public Objects
	JPanel mainmenu;
	JPanel regulations;
	DataFileTable dtable;
	
	//ExcelAdapter myAd;
	JTable table;
	JMenuBar menu;

	public MainMenu() throws Exception {
		
      ReadGUILinks rg = new ReadGUILinks();
      rg.ReadIn("Lookups");

		String currentDir =  System.getProperty("user.dir");
		System.out.println(currentDir);
		swix = new SwingEngine();
		swix.render( new File("MainMenu.xml") ).setVisible(true);


		JRadioButton r;
		r = (JRadioButton)swix.find("rdbU1"); r.addItemListener(this);
		r = (JRadioButton)swix.find("rdbU2"); r.addItemListener(this);
		r = (JRadioButton)swix.find("rdbU3"); r.addItemListener(this);
		r = (JRadioButton)swix.find("rdbU4"); r.addItemListener(this);
		r = (JRadioButton)swix.find("rdbU5"); r.addItemListener(this);
		r = (JRadioButton)swix.find("rdbU6"); r.addItemListener(this);
		r = (JRadioButton)swix.find("rdbU7"); r.addItemListener(this);
		r = (JRadioButton)swix.find("rdbU8"); r.addItemListener(this);
		r = (JRadioButton)swix.find("rdbU9"); r.addItemListener(this);
		r = (JRadioButton)swix.find("rdbU10"); r.addItemListener(this);
		
		JCheckBox ckb;
		
		ckb = (JCheckBox) swix.find("ckb1"); ckb.addItemListener(this);
		ckb = (JCheckBox) swix.find("ckb2"); ckb.addItemListener(this);
		ckb = (JCheckBox) swix.find("ckb3"); ckb.addItemListener(this);
		ckb = (JCheckBox) swix.find("ckb4"); ckb.addItemListener(this);
		ckb = (JCheckBox) swix.find("ckb5"); ckb.addItemListener(this);
		ckb = (JCheckBox) swix.find("ckb6"); ckb.addItemListener(this);
		ckb = (JCheckBox) swix.find("ckb7"); ckb.addItemListener(this);
		ckb = (JCheckBox) swix.find("ckb8"); ckb.addItemListener(this);
		ckb = (JCheckBox) swix.find("ckb9"); ckb.addItemListener(this);
		ckb = (JCheckBox) swix.find("ckb10"); ckb.addItemListener(this);
		ckb = (JCheckBox) swix.find("ckb11"); ckb.addItemListener(this);
		ckb = (JCheckBox) swix.find("ckb12"); ckb.addItemListener(this);
		ckb = (JCheckBox) swix.find("ckb13"); ckb.addItemListener(this);
		ckb = (JCheckBox) swix.find("ckb14"); ckb.addItemListener(this);
		ckb = (JCheckBox) swix.find("ckb15"); ckb.addItemListener(this);


		swix.setActionListener( menu, this );
		swix.setActionListener( regulations, this );

	}

	public static void main(String [] args) throws Exception {
		new MainMenu();


	}
	//React to check box selections.
	
	
	public void itemStateChanged(ItemEvent e) {
		JComponent component = (JComponent) e.getItem();
		//was "e.getItemSelected"
		String cName = component.getName();
		if (cName.startsWith("ckb")) {
			// CheckBox in Regulations panel changed
			String cID = cName.substring(3);
			component = (JComponent) swix.find("rdbD"+cID); if (component != null) component.setEnabled((e.getStateChange() == ItemEvent.SELECTED));
			component = (JComponent) swix.find("rdbB"+cID); if (component != null) component.setEnabled((e.getStateChange() == ItemEvent.SELECTED));
			component = (JComponent) swix.find("rdbU"+cID); if (component != null) component.setEnabled((e.getStateChange() == ItemEvent.SELECTED));
			component = (JComponent) swix.find("btnU"+cID); if (component != null) component.setEnabled((e.getStateChange() == ItemEvent.SELECTED));
		}
		else if (cName.startsWith("rdb")) {
			// RadioButton in Regulations panel changed
			JButton button = (JButton) swix.find("btnU" + cName.substring(4));
			if (cName.startsWith("rdbU")) {
				button.setText("Edit");
			} 
			else {
				button.setText("View");
			}
		}
	}


	//React to menu selections.
	public void actionPerformed(ActionEvent e) {
		if ("UD_Table".equals(e.getActionCommand())) { 
			createDTableFrame("UD_Table.txt", "User Defined Values");
		} else if ("AC_RUN".equals(e.getActionCommand()))  {
/*
 * 
 */
			int cbct=0;
			Vector regIDs = new Vector();
			Component[] components = regulations.getComponents( );
			for (int i = 0; i < components.length; i++) {
				if (components[i] instanceof JCheckBox) {
					MyCheckBox cb = (MyCheckBox) components[i];
					//regIDs.addElement(cb.returnSID());
					cbct=cbct+1;
					System.out.println(cb.switchID);
				}
			}
		}

			
/*
			// Dump current status to console
			ArrayList<Integer> statuses = new ArrayList<Integer>();
			ArrayList<String> names = new ArrayList<String>();
			//Iterator<JComponent> allComponents = (Iterator<JComponent>) swix.getIdComponentItertor();
			Component[] allComponents = regulations.getComponents( );
			for (int n = 0; n < allComponents.length; n++) {
			//while (allComponents.hasNext()) {
				//JComponent c = (JComponent) allComponents.next();
				JComponent c = (JComponent)allComponents[n];
				if (c.getName() != null) {
					if (c.getName().startsWith("ckb")){
						String cID = c.getName().substring(3);
						int i = Integer.parseInt(cID); // Correct to put in right order
						String name= c.getName();
						int status = i;
						JCheckBox ckb = (JCheckBox) c;
						if (!ckb.isSelected()) {
							status = 0;
						} 
						else {
							JRadioButton rb = (JRadioButton) swix.find("rdbD"+cID);
							if (rb == null || !rb.isVisible()) {
								status = 1; // No radiobuttons -> just a checkbox
							}
							else if (rb.isSelected()) {
								status = 1; // D1641 is selected
							}
							else {
								rb = (JRadioButton) swix.find("rdbU" + cID);
								if (rb.isSelected() || !rb.isEnabled()) {
									status = 2;
								}
								else {
									rb = (JRadioButton) swix.find("rdbB" + cID);
									if (rb.isSelected()) {
										status = 3;
									}
									else {
										status = 1;
									}
								}
							}
						}
						names.add(name);
						statuses.add(status);
					}
				}
			}
			 
//			for (int i = 0; i < statuses.size(); i++)
	
				
//				System.out.println(Integer.toString(i)+ " " + names.get(i) + " " + Integer.toString(statuses.get(i)));
//		}

		
		OutputStream outputStream = null;
		try 
		{
			outputStream = new FileOutputStream("gui_dltregulation.table");
		}
		catch (FileNotFoundException e1) {
			System.out.println("Cannot open input file " + "gui_dltregulation.table");
		} 
		PrintStream output = new PrintStream(outputStream);
		output.println("SWITCHID     OPTION");
		for (int i = 0; i < statuses.size(); i++){
			output.println(Integer.toString(i)+ "               " + Integer.toString(statuses.get(i)));
		}
	output.close();
	try {
		outputStream.close();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
		}

	*/

	}


	// This method returns the selected radio button in a button group
	public static JRadioButton getSelection(ButtonGroup group) {
		for (Enumeration e=group.getElements(); e.hasMoreElements(); ) {
			JRadioButton b = (JRadioButton)e.nextElement();
			if (b.getModel() == group.getSelection()) {
				return b;
			}
		}
		return null;
	}

	protected void createDTableFrame(String filestr, String titlestr) {
		MyInternalFrame frame = new MyInternalFrame(titlestr);

		frame.setVisible(true); //necessary as of 1.3

		frame.setResizable(true);
		frame.setBounds(0, 0, 0, 0);
		frame.setClosable(true);
		frame.setSize(50,50);

		DataFileTable dtable = new DataFileTable(filestr);
		frame.getContentPane().add(dtable);

		frame.setVisible(true);
		mainmenu.add(frame);
		mainmenu.setComponentZOrder(frame, 0);


		try {
			frame.setSelected(true);
		} catch (java.beans.PropertyVetoException e) {}

	}



}