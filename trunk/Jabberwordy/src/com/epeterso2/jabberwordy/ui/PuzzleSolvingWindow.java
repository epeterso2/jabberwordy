package com.epeterso2.jabberwordy.ui;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

import com.epeterso2.jabberwordy.model.PuzzleModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.DataFormatException;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * This code was edited or generated using CloudGarden's Jigloo
 * SWT/Swing GUI Builder, which is free for non-commercial
 * use. If Jigloo is being used commercially (ie, by a corporation,
 * company or business for any purpose whatever) then you
 * should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details.
 * Use of Jigloo implies acceptance of these licensing terms.
 * A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
 * THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
 * LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
@SuppressWarnings("serial")
public class PuzzleSolvingWindow extends javax.swing.JFrame {

	private PuzzleModel model = null;
	private JMenu View;
	private JMenuItem printMenuItem;
	private JMenuItem saveAsMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem closeMenuItem;
	private JMenuItem propertiesMenuItem;
	private JMenuItem viewNotepadMenuItem;
	private JMenu jMenu1;
	private JMenuBar jMenuBar1;

	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				PuzzleSolvingWindow inst = new PuzzleSolvingWindow();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	public PuzzleSolvingWindow() {
		super();
		initGUI();
	}

	public PuzzleSolvingWindow( PuzzleModel model )
	{
		this();
		setModel( model );
	}

	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			pack();
			setSize(500, 400);
			setTitle( ApplicationContext.getAppTitle() );
			{
				jMenuBar1 = new JMenuBar();
				setJMenuBar(getJMenuBar1());
				{
					jMenu1 = new JMenu();
					jMenuBar1.add(jMenu1);
					jMenu1.setText("File");
					{
						saveMenuItem = new JMenuItem();
						jMenu1.add(saveMenuItem);
						saveMenuItem.setText("Save");
					}
					{
						saveAsMenuItem = new JMenuItem();
						jMenu1.add(saveAsMenuItem);
						saveAsMenuItem.setText("Save As ...");
					}
					{
						printMenuItem = new JMenuItem();
						jMenu1.add(printMenuItem);
						printMenuItem.setText("Print ...");
						printMenuItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								printMenuItemActionPerformed(evt);
							}
						});
					}
					{
						closeMenuItem = new JMenuItem();
						jMenu1.add(closeMenuItem);
						closeMenuItem.setText("Close");
						closeMenuItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								closeMenuItemActionPerformed(evt);
							}
						});
					}
				}
				{
					View = new JMenu();
					jMenuBar1.add(View);
					View.setText("View");
					{
						viewNotepadMenuItem = new JMenuItem();
						View.add(viewNotepadMenuItem);
						viewNotepadMenuItem.setText("Notepad");
						viewNotepadMenuItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								viewNotepadMenuItemActionPerformed(evt);
							}
						});
					}
					{
						propertiesMenuItem = new JMenuItem();
						View.add(propertiesMenuItem);
						propertiesMenuItem.setText("Properties");
						propertiesMenuItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								propertiesMenuItemActionPerformed(evt);
							}
						});
					}
				}
			}
		} catch (Exception e) {
			//add your error handling code here
			e.printStackTrace();
		}
	}

	public void setModel(PuzzleModel model) {
		this.model = model;
		initUIFromModel();
	}

	private void initUIFromModel()
	{
		StringBuilder builder = new StringBuilder();
		builder.append( ApplicationContext.getAppTitle() );
		builder.append( " - " );
		builder.append( model.getTitle() );
		builder.append( " by " );
		builder.append( model.getAuthor() );
		setTitle( builder.toString() );

		viewNotepadMenuItem.setEnabled( model.getNotes() != null && model.getNotes().length() > 0 );
	}

	public PuzzleModel getModel() {
		return model;
	}

	public JMenuBar getJMenuBar1() {
		return jMenuBar1;
	}

	private void viewNotepadMenuItemActionPerformed(ActionEvent evt)
	{
		JOptionPane.showMessageDialog( PuzzleSolvingWindow.this, model.getNotes(), "Notepad", JOptionPane.INFORMATION_MESSAGE );
	}

	private DateFormat format = new SimpleDateFormat( "HH:MM:SS" );

	private void propertiesMenuItemActionPerformed(ActionEvent evt)
	{
		StringBuilder builder = new StringBuilder();

		builder.append( model.getFile() );
		builder.append( "\n" );
		builder.append( model.getTitle() );
		builder.append( "\nBy " );
		builder.append( model.getAuthor() );
		builder.append( "\nCopyright " );
		builder.append( model.getCopyright() );
		builder.append( "\nHeight: " );
		builder.append( model.getHeight() );
		builder.append( ", Width: " );
		builder.append( model.getWidth() );
		builder.append( "\nElapsed Time: " );
		builder.append( model.getElapsedTime() / 1000 );
		builder.append( " seconds" );

		JOptionPane.showMessageDialog( PuzzleSolvingWindow.this, builder.toString(), "Properties", JOptionPane.INFORMATION_MESSAGE );
	}

	@Override
	public void dispose()
	{
		super.dispose();
		PuzzleWindowManager.deregisterPuzzleSolvingWindow( this );
	}

	private void closeMenuItemActionPerformed(ActionEvent evt) {
		dispose();
	}

	private void printMenuItemActionPerformed(ActionEvent evt)
	{
		new PuzzlePrintDialog( this ).setVisible( true );
	}

}
