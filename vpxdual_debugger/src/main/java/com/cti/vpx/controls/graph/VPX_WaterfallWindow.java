package com.cti.vpx.controls.graph;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.cti.vpx.controls.graph.example.WaterfallGraphPanel;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

public class VPX_WaterfallWindow extends JFrame implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3949787968608726203L;

	private JPanel contentPane;

	private int waterfallID = -1;

	private JTextField txtRefreshRate;

	private JTextField txtProcessor;

	private JTextField txtMinValue;

	private JTextField txtMaxValue;

	private WaterfallGraphPanel newWaterfallGraph = null;

	private VPX_ETHWindow parent;

	private String currentip = "";

	/**
	 * Create the frame.
	 */
	public VPX_WaterfallWindow(VPX_ETHWindow parnt, int idx) {

		this.waterfallID = idx;

		this.parent = parnt;

		newWaterfallGraph = new WaterfallGraphPanel();

		init();

		loadComponents();

	}

	public void init() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setBounds(100, 100, (int) (VPXUtilities.getScreenWidth() * .60), (int) (VPXUtilities.getScreenHeight() * .70));

		setIconImage(VPXConstants.Icons.ICON_WATERFALL.getImage());

		addWindowListener(this);

		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		contentPane.setLayout(new BorderLayout(0, 0));

	}

	public void loadComponents() {

		JPanel controlPanels = new JPanel();

		controlPanels.setPreferredSize(new Dimension(10, 75));

		contentPane.add(controlPanels, BorderLayout.NORTH);

		controlPanels.setLayout(new GridLayout(3, 4, 5, 5));

		JLabel lblProcessor = new JLabel("Processor");

		lblProcessor.setHorizontalAlignment(SwingConstants.CENTER);

		controlPanels.add(lblProcessor);

		JLabel lblMinVal = new JLabel("Min Value");

		lblMinVal.setHorizontalAlignment(SwingConstants.CENTER);

		controlPanels.add(lblMinVal);

		JLabel lblMaxVal = new JLabel("Max Value");

		lblMaxVal.setHorizontalAlignment(SwingConstants.CENTER);

		controlPanels.add(lblMaxVal);

		JLabel lblRefreshRate = new JLabel("Refresh Rate");

		lblRefreshRate.setHorizontalAlignment(SwingConstants.CENTER);

		controlPanels.add(lblRefreshRate);

		txtProcessor = new JTextField();

		txtProcessor.setHorizontalAlignment(SwingConstants.CENTER);

		txtProcessor.setEditable(false);

		controlPanels.add(txtProcessor);

		txtProcessor.setColumns(10);

		txtMinValue = new JTextField();

		txtMinValue.setHorizontalAlignment(SwingConstants.CENTER);

		txtMinValue.setEditable(false);

		controlPanels.add(txtMinValue);

		txtMinValue.setColumns(10);

		txtMaxValue = new JTextField();

		txtMaxValue.setHorizontalAlignment(SwingConstants.CENTER);

		txtMaxValue.setEditable(false);

		controlPanels.add(txtMaxValue);

		txtMaxValue.setColumns(10);

		txtRefreshRate = new JTextField();

		txtRefreshRate.setHorizontalAlignment(SwingConstants.CENTER);

		txtRefreshRate.setText("2 Secs.");

		txtRefreshRate.setEditable(false);

		controlPanels.add(txtRefreshRate);

		txtRefreshRate.setColumns(10);

		JLabel lblDumm1 = new JLabel("");

		controlPanels.add(lblDumm1);

		JLabel lblDummy2 = new JLabel("");

		controlPanels.add(lblDummy2);

		JPanel waterfallPanel = new JPanel();

		contentPane.add(waterfallPanel, BorderLayout.CENTER);

		waterfallPanel.setLayout(new BorderLayout(0, 0));

		waterfallPanel.add(newWaterfallGraph, BorderLayout.CENTER);
	}

	public void showWaterFall(String ip) {

		this.currentip = ip;

		newWaterfallGraph.clearAlldata();

		setTitle(String.format("Waterfall Graph (%d) :: %s", (waterfallID + 1), currentip));

		txtProcessor.setText(currentip);

		setVisible(true);
	}

	public void loadData(byte[] bytes) {

		setMinMaxValues(bytes);

		newWaterfallGraph.addAmplitudeData(bytes);
	}

	private void setMinMaxValues(byte[] bytes) {

		// assign first element of an array to largest and smallest
		byte smallest = (byte) (bytes[0] & 0x0ff);

		byte largetst = (byte) (bytes[0] & 0x0ff);

		for (int i = 1; i < bytes.length; i++) {

			byte b = (byte) (bytes[i] & 0x0ff);

			if (b > largetst)
				largetst = b;
			else if (b < smallest)
				smallest = b;

		}

		txtMaxValue.setText(String.format("%02X", largetst));

		txtMinValue.setText(String.format("%02X", smallest));

	}

	public int getWaterfallID() {

		return waterfallID;
	}

	public void setWaterfallID(int waterfallID) {

		this.waterfallID = waterfallID;
	}

	public String getIP() {

		return this.currentip;

	}

	@Override
	public void windowOpened(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {

		parent.sendWaterfallInterrupt(currentip);

	}

	@Override
	public void windowClosed(WindowEvent e) {

		this.currentip = "";

		parent.reindexWaterfallIndex();
	}

	@Override
	public void windowIconified(WindowEvent e) {

	}

	@Override
	public void windowDeiconified(WindowEvent e) {

	}

	@Override
	public void windowActivated(WindowEvent e) {

	}

	@Override
	public void windowDeactivated(WindowEvent e) {

	}
}
