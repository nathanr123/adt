package com.cti.vpx.controls.graph;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.cti.vpx.controls.graph.example.WaterfallGraphDemo;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.view.VPX_ETHWindow;

public class VPX_WaterfallWindow extends JFrame implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3949787968608726203L;

	private JPanel contentPane;

	private JTextField txtRefreshRate;

	private JTextField txtProcessor;

	private JTextField txtMinValue;

	private JTextField txtMaxValue;

	private WaterfallGraphDemo newWaterfallGraph = new WaterfallGraphDemo();

	private VPX_ETHWindow parent;

	private String currentip;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VPX_WaterfallWindow frame = new VPX_WaterfallWindow(null);
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
	public VPX_WaterfallWindow(VPX_ETHWindow parnt) {

		this.parent = parnt;

		init();

		loadComponents();

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {

					try {
						loadData(getFile());

						Thread.sleep(100);
					} catch (Exception e) {

					}
				}

			}
		});

		// th.start();
	}

	Random random = new Random();

	String[] names = { "D://tem0.bin", "D://tem1.bin", "D://tem2.bin", "D://tem3.bin", "D://tem4.bin", "D://tem5.bin" };
	boolean b = false;

	public float[] getFile(String fileName) {
		float[] f = null;
		try {
			Path path = Paths.get("D://tem.bin");

			int randomNumber = random.nextInt(5) + 0;

			path = Paths.get(names[randomNumber]);

			byte[] data = Files.readAllBytes(path);

			f = toFloatArray(data);

		} catch (IOException e) {

			e.printStackTrace();
		}

		return f;

	}

	private static float[] toFloatArray(byte[] bytes) {

		float[] floatArray = new float[bytes.length];

		for (int i = 0; i < floatArray.length; i++) {

			floatArray[i] = (float) (bytes[i] & 0x0ff) / 100;
		}

		return floatArray;
	}

	public byte[] getFile() {

		byte[] f = null;

		try {
			Path path = Paths.get("D://tem.bin");

			int randomNumber = random.nextInt(5) + 0;

			path = Paths.get(names[randomNumber]);

			f = Files.readAllBytes(path);

		} catch (IOException e) {

			e.printStackTrace();
		}

		return f;

	}

	public void init() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 820, 597);

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

		txtRefreshRate.setText("5 Secs.");

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

		setTitle("Waterfall Graph - " + ip);

		txtProcessor.setText(ip);

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

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {

		parent.sendWaterfallInterrupt(currentip);

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}
}
