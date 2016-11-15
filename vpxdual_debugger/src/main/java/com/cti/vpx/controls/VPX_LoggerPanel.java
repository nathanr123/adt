package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

import com.cti.vpx.util.VPXComponentFactory;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXSessionManager;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

public class VPX_LoggerPanel extends JPanel implements ClipboardOwner, FindController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2395447790825321429L;
	
	private JTextArea txtA_Log;
	
	private FileWriter fw;
	
	private VPX_ETHWindow parent;
	
	private final JPopupMenu vpxLogContextMenu = new JPopupMenu();
	
	private JMenuItem vpxLogContextMenu_Clear;
	
	private JMenuItem vpxLogContextMenu_Find;
	
	private JMenuItem vpxLogContextMenu_Copy;
	
	private JMenuItem vpxLogContextMenu_Save;
	
	private int idx = 0;
	
	private VPX_FindLog find = new VPX_FindLog(this, parent);
	
	private Highlighter highlighter;
	
	private HighlightPainter painter;
	
	/**
	 * Create the panel.
	 */
	public VPX_LoggerPanel(VPX_ETHWindow parnt) {
		
		this.parent = parnt;
		
		init();
		
		loadComponents();
		
		createHighlighter();
	}
	
	private void init() {
		
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		setLayout(new BorderLayout(0, 0));
		
	}
	
	private void loadComponents() {
		
		JPanel log_Panel = VPXComponentFactory.createJPanel();
		
		FlowLayout flowLayout = (FlowLayout) log_Panel.getLayout();
		
		flowLayout.setAlignment(FlowLayout.RIGHT);
		
		add(log_Panel, BorderLayout.NORTH);
		
		JButton btn_Log_Find = VPXComponentFactory.createJButton(new FindAction("Find"));
		
		btn_Log_Find.setPreferredSize(new Dimension(22, 22));
		
		btn_Log_Find.setFocusPainted(false);
		
		btn_Log_Find.setBorderPainted(false);
		
		log_Panel.add(btn_Log_Find);
		
		JButton btn_Log_Clear = VPXComponentFactory.createJButton(new ClearAction("Clear"));
		
		btn_Log_Clear.setFocusPainted(false);
		
		btn_Log_Clear.setBorderPainted(false);
		
		btn_Log_Clear.setPreferredSize(new Dimension(22, 22));
		
		log_Panel.add(btn_Log_Clear);
		
		JButton btn_Log_Copy = VPXComponentFactory.createJButton(new CopyAction("Copy"));
		
		btn_Log_Copy.setFocusPainted(false);
		
		btn_Log_Copy.setBorderPainted(false);
		
		btn_Log_Copy.setPreferredSize(new Dimension(22, 22));
		
		log_Panel.add(btn_Log_Copy);
		
		JButton btn_Log_Save = VPXComponentFactory.createJButton(new SaveAction("Save"));
		
		btn_Log_Save.setFocusPainted(false);
		
		btn_Log_Save.setBorderPainted(false);
		
		btn_Log_Save.setPreferredSize(new Dimension(22, 22));
		
		log_Panel.add(btn_Log_Save);
		
		JScrollPane scrl_Log = VPXComponentFactory.createJScrollPane();
		
		add(scrl_Log, BorderLayout.CENTER);
		
		txtA_Log = VPXComponentFactory.createJTextArea();
		
		txtA_Log.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
				if (e.getButton() == 3) {
					
					showPopupMenu(e.getX(), e.getY());
				}
				
			}
		});
		
		scrl_Log.setViewportView(txtA_Log);
		
		txtA_Log.setEditable(false);
		
		createContextMenus();
	}
	
	private void createContextMenus() {
		
		vpxLogContextMenu_Clear = VPXComponentFactory.createJMenuItem("Clear All");
		
		vpxLogContextMenu_Clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				clearContents();
			}
		});
		
		vpxLogContextMenu_Find = VPXComponentFactory.createJMenuItem("Find");
		
		vpxLogContextMenu_Find.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				showFind();
			}
		});
		vpxLogContextMenu_Copy = VPXComponentFactory.createJMenuItem("Copy");
		
		vpxLogContextMenu_Copy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				copyContents();
			}
		});
		
		vpxLogContextMenu_Save = VPXComponentFactory.createJMenuItem("Save");
		
		vpxLogContextMenu_Save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				saveLogtoFile();
			}
		});
		
		vpxLogContextMenu.add(vpxLogContextMenu_Find);
		
		vpxLogContextMenu.add(VPXComponentFactory.createJSeparator());
		
		vpxLogContextMenu.add(vpxLogContextMenu_Clear);
		
		vpxLogContextMenu.add(VPXComponentFactory.createJSeparator());
		
		vpxLogContextMenu.add(vpxLogContextMenu_Copy);
		
		vpxLogContextMenu.add(vpxLogContextMenu_Save);
		
	}
	
	private void showPopupMenu(int x, int y) {
		
		vpxLogContextMenu.show(this, x, y);
		
	}
	
	public void appendLog(String log) {
		
		if (log.length() > 0) {
			
			int a = txtA_Log.getText().length();
			
			if (txtA_Log.getCaretPosition() > 0)
				
				txtA_Log.append("\n");
			
			txtA_Log.append(log);
			
			txtA_Log.setCaretPosition(a + 1);
		}
	}
	
	private void copyContents() {
		
		setClipboardContents(txtA_Log.getText());
		
		VPXUtilities.showPopup("Contents copied to clipboard");
		
	}
	
	private void clearContents() {
		
		txtA_Log.setText("");
		
	}
	
	private void setClipboardContents(String aString) {
		
		StringSelection stringSelection = new StringSelection(aString);
		
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		
		clipboard.setContents(stringSelection, this);
	}
	
	private void saveLogtoFile() {
		
		try {
			
			String path = VPXSessionManager.getEventPath() + "/"
					+ VPXUtilities.getPropertyValue(VPXConstants.ResourceFields.LOG_FILEPATH) + "_"
					+ VPXUtilities.getCurrentTime(3) + ".log";
			
			fw = new FileWriter(new File(path), true);
			
			txtA_Log.write(fw);
			
			fw.close();
			
			VPXUtilities.showPopup("File Saved at " + path, path);
			
		}
		catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	private void showFind() {
		
		find.showFindWindow();
		
	}
	
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		
	}
	
	class SaveAction extends AbstractAction {
		
		/**
		 * 
		 */
		
		public SaveAction(String name) {
			
			putValue(Action.SHORT_DESCRIPTION, name);
			
			putValue(Action.SMALL_ICON, VPXConstants.Icons.ICON_SAVE);
		}
		
		private static final long serialVersionUID = -780929428772240491L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			saveLogtoFile();
		}
	}
	
	class FindAction extends AbstractAction {
		
		/**
		 * 
		 */
		
		public FindAction(String name) {
			
			putValue(Action.SHORT_DESCRIPTION, name);
			
			putValue(Action.SMALL_ICON, VPXConstants.Icons.ICON_SEARCH);
		}
		
		private static final long serialVersionUID = -780929428772240491L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			showFind();
		}
	}
	
	class ClearAction extends AbstractAction {
		
		/**
		 * 
		 */
		
		public ClearAction(String name) {
			
			putValue(Action.SHORT_DESCRIPTION, name);
			
			putValue(Action.SMALL_ICON, VPXConstants.Icons.ICON_CLEAR);
		}
		
		private static final long serialVersionUID = -780929428772240491L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			clearContents();
		}
	}
	
	class CopyAction extends AbstractAction {
		
		/**
		 * 
		 */
		
		public CopyAction(String name) {
			
			putValue(Action.SHORT_DESCRIPTION, name);
			
			putValue(Action.SMALL_ICON, VPXConstants.Icons.ICON_COPY);
		}
		
		private static final long serialVersionUID = -780929428772240491L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			copyContents();
		}
	}
	
	private void createHighlighter() {
		
		highlighter = txtA_Log.getHighlighter();
		
		painter = new DefaultHighlighter.DefaultHighlightPainter(Color.GRAY);
		
	}
	
	@Override
	public void find(String value) {
		
		try {
			
			String str = txtA_Log.getText();
			
			if (value.length() > 0)
				idx = str.indexOf(value, idx);
			else
				idx = -1;
			
			highlighter.removeAllHighlights();
			
			if (idx > -1) {
				
				find.updateStatus("Found at " + idx);
				
				highlighter.addHighlight(idx, (idx + value.length()), painter);
				
				idx = idx + value.length();
			} else {
				find.updateStatus("Search Finished");
			}
			
		}
		catch (BadLocationException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void clearFind() {
		
		idx = 0;
		
		txtA_Log.select(0, 0);
	}
	
}