package com.cti.vpx.controls.graph;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.cti.vpx.controls.graph.example.WaterfallGraphPanel;
import com.cti.vpx.controls.graph.utilities.ui.graphs.graphBase.DrawSurface;
import com.cti.vpx.model.Processor;
import com.cti.vpx.model.VPXSubSystem;
import com.cti.vpx.model.VPXSystem;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXSessionManager;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

import net.miginfocom.swing.MigLayout;
import javax.swing.DefaultComboBoxModel;

public class VPX_SpectrumWindow extends JFrame implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7945447785324008590L;

	private String currentProcIP = "";

	private int currentCore = -1;

	private final JPanel contentPanel = new JPanel();

	private JComboBox<String> cmbGraphObject;

	public XYSeries series;

	private XYSeriesCollection dataset;

	private WaterfallGraphPanel newWaterfallGraph = null;

	private int spectrumID = -1;

	private VPX_ETHWindow parent;

	private VPXSystem vpxSystem;

	private JComboBox<String> cmbSubSystem;

	private JComboBox<String> cmbProcessor;

	private JLabel lblMinVal;

	private JLabel lblMaxVal;

	private JCheckBox chkWaterfall;

	private Crosshair xCrosshair;

	private Crosshair yCrosshair;

	private JButton btnClear;

	private JButton btnClearAll;

	private JButton btnStart;

	private XYPlot xyplot;

	private JCheckBox chkColorScale;

	private XYItemRenderer renderer;
	private JLabel lblLineColor;
	private JComboBox<String> cmbLineColor;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			VPX_SpectrumWindow dialog = new VPX_SpectrumWindow(null, 0);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public VPX_SpectrumWindow(VPX_ETHWindow parent, int id) {

		this.spectrumID = id;

		this.parent = parent;

		setTitle("Data Graph " + (spectrumID + 1));

		newWaterfallGraph = new WaterfallGraphPanel();

		init();

		loadComponents();

		updateProcessorDetail();

		loadGraphObjects();

		clearData();

		// startLoadtData();

	}

	private void init() {

		setIconImage(VPXConstants.Icons.ICON_SPECTRUM.getImage());

		setBounds(0, 0, VPXUtilities.getScreenWidth(), VPXUtilities.getScreenHeight());

		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.CENTER);

		contentPanel.setLayout(new BorderLayout(0, 0));

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		addWindowListener(this);
	}

	private void loadComponents() {

		JPanel panelFilter = new JPanel();

		panelFilter.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Filter",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		panelFilter.setPreferredSize(new Dimension(175, 100));

		contentPanel.add(panelFilter, BorderLayout.EAST);
		panelFilter.setLayout(new MigLayout("", "[59px,grow,fill][13px,grow,fill][48px,grow,fill]",
				"[20px][20px][20px][20px][20px][20px][20px][][][][][20px][20px][20px][][25px][25px]"));

		JLabel lblSubSystem = new JLabel("Sub System");
		lblSubSystem.setHorizontalAlignment(SwingConstants.LEFT);

		panelFilter.add(lblSubSystem, "cell 0 0 3 1,grow");

		cmbSubSystem = new JComboBox<String>();

		cmbSubSystem.setPreferredSize(new Dimension(120, 20));

		cmbSubSystem.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {

				if (arg0.getSource().equals(cmbSubSystem)) {

					if (arg0.getStateChange() == ItemEvent.SELECTED) {

						loadProcessors();
					}
				}
			}
		});

		panelFilter.add(cmbSubSystem, "cell 0 1 3 1,alignx left,aligny top");

		chkColorScale = new JCheckBox("Color Scale");

		chkColorScale.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				lblLineColor.setEnabled(chkColorScale.isSelected());

				cmbLineColor.setEnabled(chkColorScale.isSelected());
			}
		});

		chkColorScale.setVisible(true);

		chkColorScale.setSelected(true);

		panelFilter.add(chkColorScale, "cell 0 8");

		lblLineColor = new JLabel("Line Color");
		panelFilter.add(lblLineColor, "cell 0 9");

		cmbLineColor = new JComboBox<String>();

		cmbLineColor
				.setModel(new DefaultComboBoxModel<String>(new String[] { "Red", "Green", "Blue", "Yellow", "Cyan" }));

		cmbLineColor.setSelectedIndex(3);

		panelFilter.add(cmbLineColor, "cell 1 9 2 1,growx");

		label = new JLabel("             ");

		panelFilter.add(label, "cell 0 10");

		lblMinVal = new JLabel("");

		lblMinVal.setFont(new Font("Tahoma", Font.BOLD, 12));

		lblMinVal.setPreferredSize(new Dimension(45, 25));

		panelFilter.add(lblMinVal, "cell 2 16,alignx center,aligny top");

		JLabel lblProcessor = new JLabel("Processor");

		lblProcessor.setHorizontalAlignment(SwingConstants.LEFT);

		panelFilter.add(lblProcessor, "cell 0 2 3 1,grow");

		JLabel lblGraphObject = new JLabel("Graph Object");

		panelFilter.add(lblGraphObject, "cell 0 4 3 1,grow");

		cmbGraphObject = new JComboBox<String>();

		panelFilter.add(cmbGraphObject, "cell 0 5 3 1,growx,aligny top");

		cmbProcessor = new JComboBox<String>();

		cmbProcessor.setPreferredSize(new Dimension(120, 20));

		panelFilter.add(cmbProcessor, "cell 0 3 3 1,alignx left,aligny top");

		btnClear = new JButton("Clear Graph");

		btnClear.setEnabled(false);

		btnClear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				VPX_SpectrumWindow.this.series.clear();

				VPX_SpectrumWindow.this.dataset.removeSeries(series);

				newWaterfallGraph.clearAlldata();

				lblMaxVal.setText("");

				lblMinVal.setText("");

			}
		});

		btnStart = new JButton("Apply");

		btnStart.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				startPopulateData();

			}
		});
		panelFilter.add(btnStart, "cell 0 11 3 1,grow");

		panelFilter.add(btnClear, "cell 0 12 3 1,grow");

		chkWaterfall = new JCheckBox("Waterfall");

		panelFilter.add(chkWaterfall, "cell 0 6 3 1,grow");

		btnClearAll = new JButton("Clear All");

		btnClearAll.setEnabled(false);

		btnClearAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				parent.sendSpectrumInterrupt(currentProcIP, currentCore);

				clearData();

			}
		});

		panelFilter.add(btnClearAll, "cell 0 13 3 1,grow");

		JLabel lblMax = new JLabel("Max Value : ");
		lblMax.setFont(new Font("Tahoma", Font.PLAIN, 11));

		panelFilter.add(lblMax, "cell 0 15,alignx left,growy");

		JLabel lblMin = new JLabel("Min Value : ");
		lblMin.setFont(new Font("Tahoma", Font.PLAIN, 11));

		panelFilter.add(lblMin, "cell 0 16,alignx center,growy");

		lblMaxVal = new JLabel("");
		lblMaxVal.setFont(new Font("Tahoma", Font.BOLD, 12));

		lblMaxVal.setPreferredSize(new Dimension(45, 25));

		panelFilter.add(lblMaxVal, "cell 2 15,alignx left,aligny top");

		JPanel panelGraph = new JPanel();

		panelGraph.setForeground(Color.WHITE);

		panelGraph.setBackground(Color.BLACK);

		contentPanel.add(panelGraph, BorderLayout.CENTER);
		panelGraph.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panelWaterfall = new JPanel();

		panelWaterfall.setForeground(Color.WHITE);

		panelWaterfall.setBackground(Color.BLACK);

		panelWaterfall.setPreferredSize(new Dimension(10, 350));

		panelWaterfall.setLayout(new BorderLayout(0, 0));

		panelWaterfall.add(newWaterfallGraph, BorderLayout.CENTER);

		panelGraph.add(panelWaterfall);

		JPanel panelAmplitude = new JPanel(new BorderLayout());

		panelAmplitude.setForeground(Color.WHITE);

		panelAmplitude.setBackground(Color.BLACK);

		series = new XYSeries("", true, true);

		dataset = new XYSeriesCollection(series);

		final JFreeChart chart = createChart(dataset);

		final ChartPanel chartPanel = new ChartPanel(chart);

		CrosshairOverlay crosshairOverlay = new CrosshairOverlay();

		this.xCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));

		this.xCrosshair.setLabelBackgroundPaint(Color.WHITE);

		this.xCrosshair.setLabelVisible(true);

		this.yCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));

		this.yCrosshair.setLabelBackgroundPaint(Color.WHITE);

		this.yCrosshair.setLabelVisible(true);

		crosshairOverlay.addDomainCrosshair(xCrosshair);

		crosshairOverlay.addRangeCrosshair(yCrosshair);

		chartPanel.addOverlay(crosshairOverlay);

		chartPanel.setCursor(DrawSurface.getDefaultCrossHairCursor());

		chartPanel.setPopupMenu(null);

		panelAmplitude.add(chartPanel, BorderLayout.CENTER);

		panelGraph.add(panelAmplitude);

		cmbProcessor.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {

				if (arg0.getSource().equals(cmbProcessor)) {

					if (arg0.getStateChange() == ItemEvent.SELECTED) {

						loadGraphObjects();
					}
				}
			}
		});

		loadSubSystems();
	}

	private JFreeChart createChart(final XYDataset dataset) {

		JFreeChart jfreechart = ChartFactory.createXYLineChart("", "", "", dataset, PlotOrientation.VERTICAL, true,
				true, true);

		jfreechart.setAntiAlias(true);

		jfreechart.setBackgroundPaint(Color.BLACK);

		jfreechart.setBorderVisible(true);

		jfreechart.setTextAntiAlias(true);

		xyplot = (XYPlot) jfreechart.getPlot();

		renderer = xyplot.getRenderer();

		renderer.setSeriesPaint(0, Color.YELLOW);

		renderer.setSeriesItemLabelsVisible(0, false);

		renderer.setSeriesVisibleInLegend(0, false, false);

		xyplot.getRangeAxis().setLabelPaint(Color.WHITE);

		xyplot.getRangeAxis().setTickLabelPaint(Color.WHITE);

		xyplot.getDomainAxis().setLabelPaint(Color.WHITE);

		xyplot.getDomainAxis().setTickLabelPaint(Color.WHITE);

		xyplot.setDomainGridlinesVisible(true);

		xyplot.setRangeCrosshairVisible(true);

		xyplot.setBackgroundPaint(Color.BLACK);

		return jfreechart;
	}

	private void updateProcessorDetail() {

	}

	private void loadGraphObjects() {

		cmbGraphObject.removeAllItems();

		for (int i = 0; i < 8; i++) {

			cmbGraphObject.addItem(String.format("Graph Object %d", i));
		}

	}

	private void enableComponents(boolean option) {

		cmbSubSystem.setEnabled(option);

		cmbProcessor.setEnabled(option);

		cmbGraphObject.setEnabled(option);

		btnClearAll.setEnabled(!option);

		btnClear.setEnabled(!option);

		btnStart.setEnabled(option);

	}

	private void startPopulateData() {

		// send command to to populate data

		if (cmbProcessor.getSelectedIndex() > 0) {

			if (!parent.isAlreadyAvailable(cmbProcessor.getSelectedItem().toString(),
					cmbGraphObject.getSelectedIndex())) {

				currentProcIP = cmbProcessor.getSelectedItem().toString();

				currentCore = cmbGraphObject.getSelectedIndex();

				setTitle("Data Graph " + (spectrumID + 1) + " for " + currentProcIP + " Graph Object - " + currentCore);

				doReadData();

				enableComponents(false);

			} else {

				JOptionPane.showMessageDialog(this,
						"Selected Processor or core is already opened.\nPlease select different values.", "Validation",
						JOptionPane.ERROR_MESSAGE);
			}

		} else {

			JOptionPane.showMessageDialog(this, "Please Select processor", "Validation", JOptionPane.ERROR_MESSAGE);

		}
	}

	public void showSpectrumWindow() {

		loadSubSystems();

		cmbSubSystem.setSelectedItem(VPXSessionManager.getCurrentSubSystem());

		cmbProcessor.setSelectedItem(VPXSessionManager.getCurrentProcessor());

		setVisible(true);
	}

	private void loadSubSystems() {

		vpxSystem = VPXSessionManager.getVPXSystem();

		cmbSubSystem.removeAllItems();

		cmbSubSystem.addItem("Select SubSystem");

		List<VPXSubSystem> subsystem = vpxSystem.getSubsystem();

		if (subsystem.size() > 0) {

			for (Iterator<VPXSubSystem> iterator = subsystem.iterator(); iterator.hasNext();) {

				VPXSubSystem vpxSubSystem = iterator.next();

				cmbSubSystem.addItem(vpxSubSystem.getSubSystem());
			}
		}

		List<Processor> unlist = vpxSystem.getUnListed();

		if (unlist.size() > 0) {

			cmbSubSystem.addItem(VPXConstants.VPXUNLIST);
		}

	}

	private void loadProcessors() {

		cmbProcessor.removeAllItems();

		cmbProcessor.addItem("Select Processor");

		if (cmbSubSystem.getSelectedIndex() > 0) {

			VPXSubSystem curProcFilter = vpxSystem.getSubSystemByName(cmbSubSystem.getSelectedItem().toString());

			List<VPXSubSystem> s = vpxSystem.getSubsystem();

			for (Iterator<VPXSubSystem> iterator = s.iterator(); iterator.hasNext();) {

				VPXSubSystem vpxSubSystem = iterator.next();

				if (vpxSubSystem.getSubSystem().equals(cmbSubSystem.getSelectedItem().toString())) {

					curProcFilter = vpxSubSystem;

					cmbProcessor.addItem(vpxSubSystem.getIpDSP1());

					cmbProcessor.addItem(vpxSubSystem.getIpDSP2());

					break;

				}

			}

			if (curProcFilter == null) {

				List<Processor> p = vpxSystem.getUnListed();

				for (Iterator<Processor> iterator = p.iterator(); iterator.hasNext();) {

					Processor processor = iterator.next();

					if (!processor.getName().contains("P2020")) {

						cmbProcessor.addItem(processor.getiP_Addresses());
					}
				}

			}
		}

	}

	private void doReadData() {

		parent.readSpectrum(cmbProcessor.getSelectedItem().toString(), cmbGraphObject.getSelectedIndex(), spectrumID);
	}

	private void clearData() {

		currentCore = -1;

		currentProcIP = "";

		this.series.clear();

		this.dataset.removeSeries(series);

		newWaterfallGraph.clearAlldata();

		this.cmbGraphObject.setSelectedIndex(0);

		enableComponents(true);

		chkWaterfall.setSelected(true);

		lblMaxVal.setText("");

		lblMinVal.setText("");

		setTitle("Data Graph " + (spectrumID + 1));

		cmbSubSystem.setSelectedIndex(0);

	}

	public void loadData(int core, float[] yAxis) {

		// assign first element of an array to largest and smallest

		if (yAxis.length > 0) {
			double smallest = yAxis[0];

			double largest = yAxis[0];

			currentCore = core;

			this.series.clear();

			this.dataset.removeSeries(series);

			for (int i = 0; i < yAxis.length; i++) {

				final double x = i;

				final double y = yAxis[i];

				this.series.addOrUpdate(x, y);

				if (yAxis.length > 0) {

					for (int j = 1; j < yAxis.length; j++) {

						double b = yAxis[j];

						if (b > largest)
							largest = b;
						else if (b < smallest)
							smallest = b;

					}
				}
			}

			this.dataset.addSeries(series);

			xyplot.getDomainAxis().setFixedAutoRange(this.dataset.getDomainUpperBound(true));

			if (chkWaterfall.isSelected()) {

				newWaterfallGraph.addWaterfallData(yAxis, (float) smallest, (float) largest);

			}

			renderer.setSeriesPaint(0, getLineColor(chkColorScale.isSelected(), cmbLineColor.getSelectedIndex()));

			newWaterfallGraph.setColorScale(chkColorScale.isSelected());

			setMinMaxValues(smallest, largest);
		}
	}

	private Color getLineColor(boolean isColorScale, int color) {

		Color clr = Color.YELLOW;

		if (isColorScale) {

			switch (color) {
			case 0:
				clr = Color.RED;
				break;
			case 1:
				clr = Color.GREEN;
				break;
			case 2:
				clr = Color.BLUE;
				break;
			case 3:
				clr = Color.YELLOW;
				break;
			case 4:
				clr = Color.CYAN;
				break;
			default:
				break;
			}

		} else
			clr = Color.WHITE;
		return clr;
	}

	private void setMinMaxValues(double min, double max) {
		lblMinVal.setText("" + String.format("%.02f", min));

		lblMaxVal.setText("" + String.format("%.02f", max));
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {

		parent.reIndexSpectrumWindowIndex(currentProcIP, currentCore);

		currentProcIP = "";

		currentCore = -1;

		clearData();

	}

	public void startLoadtData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {

					float[] ff = new float[1024];

					for (int i = 0; i < ff.length; i++) {
						ff[i] = (float) (Math.random() * 1024);
					}
					loadData(1, ff);
					try {

						Thread.sleep(100);

					} catch (Exception e) { // TODO: handle exception
					}
				}

			}
		}).start();
	}

	@Override
	public void windowClosed(WindowEvent e) {

	}

	public String getCurrentProcIP() {
		return currentProcIP;
	}

	public void setCurrentProcIP(String currentProcIP) {
		this.currentProcIP = currentProcIP;
	}

	public int getCurrentCore() {
		return currentCore;
	}

	public void setCurrentCore(int currentCore) {
		this.currentCore = currentCore;
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
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
