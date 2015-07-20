package com.cti.vpx.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JComboBox;

import java.awt.FlowLayout;
import java.util.ResourceBundle;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.cti.vpx.util.VPXUtilities;

public class VPX_UARTWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5022638845933883226L;

	private ResourceBundle rBundle;

	private JPanel contentPane;
	private JTextField txtConsoleMsg;
	private JTextArea txtAConsole;
	private JTextArea txtAMessage;
	private JComboBox<String> cmbConsoleCoresFilter;
	private JComboBox<String> cmbMessagesCoresFilter;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VPX_UARTWindow frame = new VPX_UARTWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VPX_UARTWindow() {

		rBundle = VPXUtilities.getResourceBundle();

		init();

		loadComponents();

		centerFrame();

		setVisible(true);
	}

	private void init() {

		setTitle(rBundle.getString("App.title.name") + " - " + rBundle.getString("App.title.version"));

		setPreferredSize(new Dimension(800, 600));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(600, 700);
	}

	private void loadComponents() {

		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		contentPane.setLayout(new GridLayout(2, 1, 0, 0));

		JPanel consolePanel = new JPanel();

		consolePanel.setBorder(new TitledBorder(null, "Console", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		contentPane.add(consolePanel);

		consolePanel.setLayout(new BorderLayout(0, 0));

		JPanel consoleFilterPanel = new JPanel();

		FlowLayout fl_consoleFilterPanel = (FlowLayout) consoleFilterPanel.getLayout();

		fl_consoleFilterPanel.setAlignment(FlowLayout.RIGHT);

		consolePanel.add(consoleFilterPanel, BorderLayout.NORTH);

		cmbConsoleCoresFilter = new JComboBox<String>();

		cmbConsoleCoresFilter.setPreferredSize(new Dimension(130, 20));

		consoleFilterPanel.add(cmbConsoleCoresFilter);

		JScrollPane scrConsole = new JScrollPane();

		consolePanel.add(scrConsole, BorderLayout.CENTER);

		txtAConsole = new JTextArea();

		scrConsole.setViewportView(txtAConsole);

		JPanel messagesControlPanel = new JPanel();

		contentPane.add(messagesControlPanel);

		messagesControlPanel.setLayout(new BorderLayout(0, 0));

		JPanel ControlsPanel = new JPanel();

		messagesControlPanel.add(ControlsPanel, BorderLayout.SOUTH);

		ControlsPanel.setLayout(new BorderLayout(0, 0));

		JPanel sendOptionPanel = new JPanel();

		ControlsPanel.add(sendOptionPanel, BorderLayout.CENTER);

		sendOptionPanel.setLayout(new MigLayout("", "[93.00,left][grow,fill]", "[]"));

		cmbMessagesCoresFilter = new JComboBox<String>();

		cmbMessagesCoresFilter.setPreferredSize(new Dimension(130, 20));

		sendOptionPanel.add(cmbMessagesCoresFilter, "flowx,cell 0 0");

		txtConsoleMsg = new JTextField();

		sendOptionPanel.add(txtConsoleMsg, "cell 1 0");

		txtConsoleMsg.setColumns(10);

		JPanel buttonPanel = new JPanel();

		FlowLayout fl_buttonPanel = (FlowLayout) buttonPanel.getLayout();

		fl_buttonPanel.setAlignment(FlowLayout.LEFT);

		ControlsPanel.add(buttonPanel, BorderLayout.SOUTH);

		JButton btnSend = new JButton("Send");

		btnSend.setHorizontalAlignment(SwingConstants.LEFT);

		buttonPanel.add(btnSend);

		JButton btnClear = new JButton("Clear");

		buttonPanel.add(btnClear);

		JPanel messagePanel = new JPanel();

		messagePanel.setBorder(new TitledBorder(null, "Messages", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		messagesControlPanel.add(messagePanel, BorderLayout.CENTER);

		messagePanel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrMessages = new JScrollPane();

		messagePanel.add(scrMessages, BorderLayout.CENTER);

		txtAMessage = new JTextArea();

		scrMessages.setViewportView(txtAMessage);
	}

	private void centerFrame() {

		Dimension windowSize = getSize();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - windowSize.width / 2;

		int dy = centerPoint.y - windowSize.height / 2;

		setLocation(dx, dy);
	}

}
