package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.cti.vpx.command.ATP;
import com.cti.vpx.model.BIST;
import com.cti.vpx.model.VPX.PROCESSOR_LIST;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXLogger;
import com.cti.vpx.util.VPXUtilities;
import com.cti.vpx.view.VPX_ETHWindow;

import net.miginfocom.swing.MigLayout;

public class VPX_BISTResultWindow extends JDialog implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7924321598422417103L;

	private final JPanel contentPanel = new JPanel();

	private JPanel resultPanel;

	private JPanel dspTestResultBasePanel;

	private JPanel detailPanel;

	private BIST testResult;

	private JLabel lblDSP2TestResultNORVal;

	private JLabel lblDSP2TestResultNANDVal;

	private JLabel lblDSP2TestResultDDR3Val;

	private JLabel lblDSP2TestResultProcessorVal;

	private JLabel lblDSP1TestResultProcessorVal;

	private JLabel lblDSP1TestResultDDR3Val;

	private JLabel lblDSP1TestResultNANDVal;

	private JLabel lblDSP1TestResultNORVal;

	private JLabel lblP2020TestResultVoltage7Val;

	private JLabel lblP2020TestResultTemprature2Val;

	private JLabel lblP2020TestResultVoltage6Val;

	private JLabel lblP2020TestResultProcessorVal;

	private JLabel lblP2020TestResultDDR3Val;

	private JLabel lblP2020TestResultNORFlashVal;

	private JLabel lblP2020TestResultEthernetVal;

	private JLabel lblP2020TestResultPCIEVal;

	private JLabel lblP2020TestResultSRIOVal;

	private JLabel lblP2020TestResultTemprature1Val;

	private JLabel lblP2020TestResultTemprature3Val;

	private JLabel lblP2020TestResultVoltage1Val;

	private JLabel lblP2020TestResultVoltage2Val;

	private JLabel lblP2020TestResultVoltage3Val;

	private JLabel lblP2020TestResultVoltage4Val;

	private JLabel lblP2020TestResultVoltage5Val;

	private JLabel lblResultDetailTestStatusVal;

	private JLabel lblResultDetailTestDurationVal;

	private JLabel lblResultDetailTestCompletedAtVal;

	private JLabel lblResultDetailTestStartedAtVal;

	private JLabel lblResultDetailTestsFailedVal;

	private JLabel lblResultDetailTestsPassedVal;

	private JLabel lblResultDetailNofTestsVal;

	private JLabel lblTestDetailTestTimeVal;

	private JLabel lblTestDetailTestDateVal;

	private JLabel lblTestDetailDSP2IPVal;

	private JLabel lblTestDetailDSP1IPVal;

	private JLabel lblTestDetailP2020IPVal;

	private JLabel lblTestDetailSubSystemVal;

	private JLabel lblTestDetailTestTypeVal;

	private JLabel lblTestDetailTestVal;

	private VPX_TestProgressWindow progress;

	private VPX_ETHWindow parent;

	private JButton btnReboot;

	/**
	 * Create the dialog.
	 */
	public VPX_BISTResultWindow() {

		init();

		loadComponents();

		centerFrame();
	}

	public VPX_BISTResultWindow(VPX_ETHWindow parent, BIST bist) {

		this();

		this.parent = parent;

		setResult(bist);

		loadResult();

	}

	private void init() {

		getContentPane().setBackground(Color.WHITE);

		setBackground(Color.WHITE);

		setModal(true);

		setResizable(false);

		setTitle("Buil In Self Test");

		setIconImage(VPXConstants.Icons.ICON_BIST.getImage());

		setBounds(100, 100, 900, 600);

		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBackground(Color.WHITE);

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.CENTER);

		contentPanel.setLayout(new BorderLayout(0, 0));

		progress = new VPX_TestProgressWindow(VPX_BISTResultWindow.this, VPXConstants.MAX_PROCESSOR);
	}

	private void loadComponents() {

		createTestPanel();

		createTestResultPanel();

		createControlsPanel();

	}

	private void createTestPanel() {

		detailPanel = new JPanel();

		detailPanel.setBackground(Color.WHITE);

		detailPanel.setPreferredSize(new Dimension(300, 10));

		contentPanel.add(detailPanel, BorderLayout.WEST);

		detailPanel.setLayout(new GridLayout(0, 1, 0, 0));

		createTestDetailPanel();

		createResultDetailPanel();
	}

	private void createTestDetailPanel() {

		JPanel testDetailPanel = new JPanel();

		testDetailPanel.setBackground(Color.WHITE);

		testDetailPanel
				.setBorder(new TitledBorder(null, "Test Detail", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		detailPanel.add(testDetailPanel);

		testDetailPanel.setLayout(new GridLayout(8, 2, 0, 0));

		JLabel lblTestDetailTest = new JLabel("Test");

		lblTestDetailTest.setBackground(Color.WHITE);

		testDetailPanel.add(lblTestDetailTest);

		lblTestDetailTestVal = new JLabel("Built In Self Test");

		lblTestDetailTestVal.setBackground(Color.WHITE);

		lblTestDetailTestVal.setFont(VPXConstants.BISTRESULTFONT);

		testDetailPanel.add(lblTestDetailTestVal);

		JLabel lblTestDetailTestType = new JLabel("Test Type");

		lblTestDetailTestType.setBackground(Color.WHITE);

		testDetailPanel.add(lblTestDetailTestType);

		lblTestDetailTestTypeVal = new JLabel("Full Test");

		lblTestDetailTestTypeVal.setBackground(Color.WHITE);

		lblTestDetailTestTypeVal.setFont(VPXConstants.BISTRESULTFONT);

		testDetailPanel.add(lblTestDetailTestTypeVal);

		JLabel lblTestDetailSubSystem = new JLabel("Sub System");

		lblTestDetailSubSystem.setBackground(Color.WHITE);

		testDetailPanel.add(lblTestDetailSubSystem);

		lblTestDetailSubSystemVal = new JLabel("");

		lblTestDetailSubSystemVal.setBackground(Color.WHITE);

		lblTestDetailSubSystemVal.setFont(VPXConstants.BISTRESULTFONT);

		testDetailPanel.add(lblTestDetailSubSystemVal);

		JLabel lblTestDetailP2020IP = new JLabel("P2020 IP");

		lblTestDetailP2020IP.setBackground(Color.WHITE);

		testDetailPanel.add(lblTestDetailP2020IP);

		lblTestDetailP2020IPVal = new JLabel("");

		lblTestDetailP2020IPVal.setBackground(Color.WHITE);

		lblTestDetailP2020IPVal.setFont(VPXConstants.BISTRESULTFONT);

		testDetailPanel.add(lblTestDetailP2020IPVal);

		JLabel lblTestDetailDSP1IP = new JLabel("DSP 1 IP");

		lblTestDetailDSP1IP.setBackground(Color.WHITE);

		testDetailPanel.add(lblTestDetailDSP1IP);

		lblTestDetailDSP1IPVal = new JLabel("");

		lblTestDetailDSP1IPVal.setBackground(Color.WHITE);

		lblTestDetailDSP1IPVal.setFont(VPXConstants.BISTRESULTFONT);

		testDetailPanel.add(lblTestDetailDSP1IPVal);

		JLabel lblTestDetailDSP2IP = new JLabel("DSP 2 IP");

		lblTestDetailDSP2IP.setBackground(Color.WHITE);

		testDetailPanel.add(lblTestDetailDSP2IP);

		lblTestDetailDSP2IPVal = new JLabel("");

		lblTestDetailDSP2IPVal.setBackground(Color.WHITE);

		lblTestDetailDSP2IPVal.setFont(VPXConstants.BISTRESULTFONT);

		testDetailPanel.add(lblTestDetailDSP2IPVal);

		JLabel lblTestDetailTestDate = new JLabel("Test Date");

		lblTestDetailTestDate.setBackground(Color.WHITE);

		testDetailPanel.add(lblTestDetailTestDate);

		lblTestDetailTestDateVal = new JLabel("");

		lblTestDetailTestDateVal.setBackground(Color.WHITE);

		lblTestDetailTestDateVal.setFont(VPXConstants.BISTRESULTFONT);

		testDetailPanel.add(lblTestDetailTestDateVal);

		JLabel lblTestDetailTestTime = new JLabel("Test Time");

		lblTestDetailTestTime.setBackground(Color.WHITE);

		testDetailPanel.add(lblTestDetailTestTime);

		lblTestDetailTestTimeVal = new JLabel("");

		lblTestDetailTestTimeVal.setBackground(Color.WHITE);

		lblTestDetailTestTimeVal.setFont(VPXConstants.BISTRESULTFONT);

		testDetailPanel.add(lblTestDetailTestTimeVal);
	}

	private void createResultDetailPanel() {

		JPanel resultDetailPanel = new JPanel();

		resultDetailPanel.setBackground(Color.WHITE);

		resultDetailPanel
				.setBorder(new TitledBorder(null, "Result Tetail", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		detailPanel.add(resultDetailPanel);

		resultDetailPanel.setLayout(new GridLayout(7, 2, 0, 0));

		JLabel lblResultDetailNofTests = new JLabel("Total No.oF Tests");

		lblResultDetailNofTests.setBackground(Color.WHITE);

		resultDetailPanel.add(lblResultDetailNofTests);

		lblResultDetailNofTestsVal = new JLabel("");

		lblResultDetailNofTestsVal.setBackground(Color.WHITE);

		lblResultDetailNofTestsVal.setFont(VPXConstants.BISTRESULTFONT);

		resultDetailPanel.add(lblResultDetailNofTestsVal);

		JLabel lblResultDetailTestsPassed = new JLabel("Tests Passed");

		lblResultDetailTestsPassed.setBackground(Color.WHITE);

		resultDetailPanel.add(lblResultDetailTestsPassed);

		lblResultDetailTestsPassedVal = new JLabel("");

		lblResultDetailTestsPassedVal.setBackground(Color.WHITE);

		lblResultDetailTestsPassedVal.setFont(VPXConstants.BISTRESULTFONT);

		resultDetailPanel.add(lblResultDetailTestsPassedVal);

		JLabel lblResultDetailTestsFailed = new JLabel("Tests Failed");

		lblResultDetailTestsFailed.setBackground(Color.WHITE);

		resultDetailPanel.add(lblResultDetailTestsFailed);

		lblResultDetailTestsFailedVal = new JLabel("");

		lblResultDetailTestsFailedVal.setBackground(Color.WHITE);

		lblResultDetailTestsFailedVal.setFont(VPXConstants.BISTRESULTFONT);

		resultDetailPanel.add(lblResultDetailTestsFailedVal);

		JLabel lblResultDetailTestStartedAt = new JLabel("Test Started at");

		lblResultDetailTestStartedAt.setBackground(Color.WHITE);

		resultDetailPanel.add(lblResultDetailTestStartedAt);

		lblResultDetailTestStartedAtVal = new JLabel("");

		lblResultDetailTestStartedAtVal.setBackground(Color.WHITE);

		lblResultDetailTestStartedAtVal.setFont(VPXConstants.BISTRESULTFONT);

		resultDetailPanel.add(lblResultDetailTestStartedAtVal);

		JLabel lblResultDetailTestCompletedAt = new JLabel("Tests Completed at");

		lblResultDetailTestCompletedAt.setBackground(Color.WHITE);

		resultDetailPanel.add(lblResultDetailTestCompletedAt);

		lblResultDetailTestCompletedAtVal = new JLabel("");

		lblResultDetailTestCompletedAtVal.setBackground(Color.WHITE);

		lblResultDetailTestCompletedAtVal.setFont(VPXConstants.BISTRESULTFONT);

		resultDetailPanel.add(lblResultDetailTestCompletedAtVal);

		JLabel lblResultDetailTestDuration = new JLabel("Test Duration");

		lblResultDetailTestDuration.setBackground(Color.WHITE);

		resultDetailPanel.add(lblResultDetailTestDuration);

		lblResultDetailTestDurationVal = new JLabel("");

		lblResultDetailTestDurationVal.setBackground(Color.WHITE);

		lblResultDetailTestDurationVal.setFont(VPXConstants.BISTRESULTFONT);

		resultDetailPanel.add(lblResultDetailTestDurationVal);

		JLabel lblResultDetailTestStatus = new JLabel("Test Status");

		lblResultDetailTestStatus.setBackground(Color.WHITE);

		resultDetailPanel.add(lblResultDetailTestStatus);

		lblResultDetailTestStatusVal = new JLabel("");

		lblResultDetailTestStatusVal.setBackground(Color.WHITE);

		lblResultDetailTestStatusVal.setFont(VPXConstants.BISTRESULTFONT);

		resultDetailPanel.add(lblResultDetailTestStatusVal);
	}

	private void createTestResultPanel() {

		resultPanel = new JPanel();

		resultPanel.setBackground(Color.WHITE);

		resultPanel
				.setBorder(new TitledBorder(null, "Test Result", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		contentPanel.add(resultPanel, BorderLayout.CENTER);

		resultPanel.setLayout(new BorderLayout(0, 0));

		dspTestResultBasePanel = new JPanel();

		dspTestResultBasePanel.setBackground(Color.WHITE);

		dspTestResultBasePanel.setPreferredSize(new Dimension(10, 200));

		resultPanel.add(dspTestResultBasePanel, BorderLayout.SOUTH);

		dspTestResultBasePanel.setLayout(new GridLayout(0, 2, 0, 0));

		createDSP1TestResultPanel();

		createDSP2TestResultPanel();

		createP2020TestResultPanel();
	}

	private void createP2020TestResultPanel() {

		JPanel p2020TestResultPanel = new JPanel();

		p2020TestResultPanel.setBackground(Color.WHITE);

		p2020TestResultPanel.setBorder(
				new TitledBorder(null, "P2020 Test Result", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		resultPanel.add(p2020TestResultPanel, BorderLayout.CENTER);

		p2020TestResultPanel.setLayout(new GridLayout(8, 4, 0, 0));

		JLabel lblP2020TestResultProcessor = new JLabel("Processor");

		lblP2020TestResultProcessor.setBackground(Color.WHITE);

		p2020TestResultPanel.add(lblP2020TestResultProcessor);

		lblP2020TestResultProcessorVal = new JLabel("");

		lblP2020TestResultProcessorVal.setBackground(Color.WHITE);

		lblP2020TestResultProcessorVal.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultProcessorVal);

		JLabel lblP2020TestResultTemprature3 = new JLabel("Bottom Temp");

		lblP2020TestResultTemprature3.setBackground(Color.WHITE);

		p2020TestResultPanel.add(lblP2020TestResultTemprature3);

		lblP2020TestResultTemprature3Val = new JLabel("");

		lblP2020TestResultTemprature3Val.setBackground(Color.WHITE);

		lblP2020TestResultTemprature3Val.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultTemprature3Val);

		JLabel lblP2020TestResultDDR3 = new JLabel("DDR3");

		lblP2020TestResultDDR3.setBackground(Color.WHITE);

		p2020TestResultPanel.add(lblP2020TestResultDDR3);

		lblP2020TestResultDDR3Val = new JLabel("");

		lblP2020TestResultDDR3Val.setBackground(Color.WHITE);

		lblP2020TestResultDDR3Val.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultDDR3Val);

		JLabel lblP2020TestResultVoltage1 = new JLabel("Voltage 3.3");

		lblP2020TestResultVoltage1.setBackground(Color.WHITE);

		p2020TestResultPanel.add(lblP2020TestResultVoltage1);

		lblP2020TestResultVoltage1Val = new JLabel("");

		lblP2020TestResultVoltage1Val.setBackground(Color.WHITE);

		lblP2020TestResultVoltage1Val.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultVoltage1Val);

		JLabel lblP2020TestResultNORFlash = new JLabel("NOR Flash");

		lblP2020TestResultNORFlash.setBackground(Color.WHITE);

		p2020TestResultPanel.add(lblP2020TestResultNORFlash);

		lblP2020TestResultNORFlashVal = new JLabel("");

		lblP2020TestResultNORFlashVal.setBackground(Color.WHITE);

		lblP2020TestResultNORFlashVal.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultNORFlashVal);

		JLabel lblP2020TestResultVoltage2 = new JLabel("Voltage 2.5");

		lblP2020TestResultVoltage2.setBackground(Color.WHITE);

		p2020TestResultPanel.add(lblP2020TestResultVoltage2);

		lblP2020TestResultVoltage2Val = new JLabel("");

		lblP2020TestResultVoltage2Val.setBackground(Color.WHITE);

		lblP2020TestResultVoltage2Val.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultVoltage2Val);

		JLabel lblP2020TestResultEthernet = new JLabel("Ethernet");

		lblP2020TestResultEthernet.setBackground(Color.WHITE);

		p2020TestResultPanel.add(lblP2020TestResultEthernet);

		lblP2020TestResultEthernetVal = new JLabel("");

		lblP2020TestResultEthernetVal.setBackground(Color.WHITE);

		lblP2020TestResultEthernetVal.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultEthernetVal);

		JLabel lblP2020TestResultVoltage3 = new JLabel("Voltage 1.8");

		lblP2020TestResultVoltage3.setBackground(Color.WHITE);

		p2020TestResultPanel.add(lblP2020TestResultVoltage3);

		lblP2020TestResultVoltage3Val = new JLabel("");

		lblP2020TestResultVoltage3Val.setBackground(Color.WHITE);

		lblP2020TestResultVoltage3Val.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultVoltage3Val);

		JLabel lblP2020TestResultPCIE = new JLabel("PCIe");

		lblP2020TestResultPCIE.setBackground(Color.WHITE);

		p2020TestResultPanel.add(lblP2020TestResultPCIE);

		lblP2020TestResultPCIEVal = new JLabel("");

		lblP2020TestResultPCIEVal.setBackground(Color.WHITE);

		lblP2020TestResultPCIEVal.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultPCIEVal);

		JLabel lblP2020TestResultVoltage4 = new JLabel("Voltage 1.5");

		lblP2020TestResultVoltage4.setBackground(Color.WHITE);

		p2020TestResultPanel.add(lblP2020TestResultVoltage4);

		lblP2020TestResultVoltage4Val = new JLabel("");

		lblP2020TestResultVoltage4Val.setBackground(Color.WHITE);

		lblP2020TestResultVoltage4Val.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultVoltage4Val);

		JLabel lblP2020TestResultSRIO = new JLabel("SRIO");

		lblP2020TestResultSRIO.setBackground(Color.WHITE);

		p2020TestResultPanel.add(lblP2020TestResultSRIO);

		lblP2020TestResultSRIOVal = new JLabel("");

		lblP2020TestResultSRIOVal.setBackground(Color.WHITE);

		lblP2020TestResultSRIOVal.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultSRIOVal);

		JLabel lblP2020TestResultVoltage5 = new JLabel("Voltage 1.2");

		lblP2020TestResultVoltage5.setBackground(Color.WHITE);

		p2020TestResultPanel.add(lblP2020TestResultVoltage5);

		lblP2020TestResultVoltage5Val = new JLabel("");

		lblP2020TestResultVoltage5Val.setBackground(Color.WHITE);

		lblP2020TestResultVoltage5Val.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultVoltage5Val);

		JLabel lblP2020TestResultTemprature1 = new JLabel("Local Temp");

		lblP2020TestResultTemprature1.setBackground(Color.WHITE);

		p2020TestResultPanel.add(lblP2020TestResultTemprature1);

		lblP2020TestResultTemprature1Val = new JLabel("");

		lblP2020TestResultTemprature1Val.setBackground(Color.WHITE);

		lblP2020TestResultTemprature1Val.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultTemprature1Val);

		JLabel lblP2020TestResultVoltage6 = new JLabel("Voltage 1.0");

		lblP2020TestResultVoltage6.setBackground(Color.WHITE);

		p2020TestResultPanel.add(lblP2020TestResultVoltage6);

		lblP2020TestResultVoltage6Val = new JLabel("");

		lblP2020TestResultVoltage6Val.setBackground(Color.WHITE);

		lblP2020TestResultVoltage6Val.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultVoltage6Val);

		JLabel lblP2020TestResultTemprature2 = new JLabel("Top Temp");

		lblP2020TestResultTemprature2.setBackground(Color.WHITE);

		p2020TestResultPanel.add(lblP2020TestResultTemprature2);

		lblP2020TestResultTemprature2Val = new JLabel("");

		lblP2020TestResultTemprature2Val.setBackground(Color.WHITE);

		lblP2020TestResultTemprature2Val.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultTemprature2Val);

		JLabel lblP2020TestResultVoltage7 = new JLabel("Voltage 1.05");

		lblP2020TestResultVoltage7.setBackground(Color.WHITE);

		p2020TestResultPanel.add(lblP2020TestResultVoltage7);

		lblP2020TestResultVoltage7Val = new JLabel("");

		lblP2020TestResultVoltage7Val.setBackground(Color.WHITE);

		lblP2020TestResultVoltage7Val.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultVoltage7Val);
	}

	private void createDSP1TestResultPanel() {

		JPanel dsp1TestResultPanel = new JPanel();

		dsp1TestResultPanel.setBackground(Color.WHITE);

		dsp1TestResultPanel.setBorder(
				new TitledBorder(null, "DSP 1 Test Result", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		dspTestResultBasePanel.add(dsp1TestResultPanel);

		dsp1TestResultPanel.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lblDSP1TestResultProcessor = new JLabel("Processor");

		lblDSP1TestResultProcessor.setBackground(Color.WHITE);

		dsp1TestResultPanel.add(lblDSP1TestResultProcessor);

		lblDSP1TestResultProcessorVal = new JLabel("");

		lblDSP1TestResultProcessorVal.setBackground(Color.WHITE);

		lblDSP1TestResultProcessorVal.setFont(VPXConstants.BISTRESULTFONT);

		dsp1TestResultPanel.add(lblDSP1TestResultProcessorVal);

		JLabel lblDSP1TestResultDDR3 = new JLabel("DDR3");

		lblDSP1TestResultDDR3.setBackground(Color.WHITE);

		dsp1TestResultPanel.add(lblDSP1TestResultDDR3);

		lblDSP1TestResultDDR3Val = new JLabel("");

		lblDSP1TestResultDDR3Val.setBackground(Color.WHITE);

		lblDSP1TestResultDDR3Val.setFont(VPXConstants.BISTRESULTFONT);

		dsp1TestResultPanel.add(lblDSP1TestResultDDR3Val);

		JLabel lblDSP1TestResultNAND = new JLabel("NAND");

		lblDSP1TestResultNAND.setBackground(Color.WHITE);

		dsp1TestResultPanel.add(lblDSP1TestResultNAND);

		lblDSP1TestResultNANDVal = new JLabel("");

		lblDSP1TestResultNANDVal.setBackground(Color.WHITE);

		lblDSP1TestResultNANDVal.setFont(VPXConstants.BISTRESULTFONT);

		dsp1TestResultPanel.add(lblDSP1TestResultNANDVal);

		JLabel lblDSP1TestResultNOR = new JLabel("NOR");

		lblDSP1TestResultNOR.setBackground(Color.WHITE);

		dsp1TestResultPanel.add(lblDSP1TestResultNOR);

		lblDSP1TestResultNORVal = new JLabel("");

		lblDSP1TestResultNORVal.setBackground(Color.WHITE);

		lblDSP1TestResultNORVal.setFont(VPXConstants.BISTRESULTFONT);

		dsp1TestResultPanel.add(lblDSP1TestResultNORVal);

	}

	private void createDSP2TestResultPanel() {

		JPanel dsp2TestResultPanel = new JPanel();

		dsp2TestResultPanel.setBackground(Color.WHITE);

		dsp2TestResultPanel.setBorder(
				new TitledBorder(null, "DSP 2 Test Result", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		dspTestResultBasePanel.add(dsp2TestResultPanel);

		dsp2TestResultPanel.setLayout(new GridLayout(4, 2, 0, 0));

		JLabel lblDSP2TestResultProcessor = new JLabel("Processor");

		lblDSP2TestResultProcessor.setBackground(Color.WHITE);

		dsp2TestResultPanel.add(lblDSP2TestResultProcessor);

		lblDSP2TestResultProcessorVal = new JLabel("");

		lblDSP2TestResultProcessorVal.setBackground(Color.WHITE);

		lblDSP2TestResultProcessorVal.setFont(VPXConstants.BISTRESULTFONT);

		dsp2TestResultPanel.add(lblDSP2TestResultProcessorVal);

		JLabel lblDSP2TestResultDDR3 = new JLabel("DDR3");

		lblDSP2TestResultDDR3.setBackground(Color.WHITE);

		dsp2TestResultPanel.add(lblDSP2TestResultDDR3);

		lblDSP2TestResultDDR3Val = new JLabel("");

		lblDSP2TestResultDDR3Val.setBackground(Color.WHITE);

		lblDSP2TestResultDDR3Val.setFont(VPXConstants.BISTRESULTFONT);

		dsp2TestResultPanel.add(lblDSP2TestResultDDR3Val);

		JLabel lblDSP2TestResultNAND = new JLabel("NAND");

		lblDSP2TestResultNAND.setBackground(Color.WHITE);

		dsp2TestResultPanel.add(lblDSP2TestResultNAND);

		lblDSP2TestResultNANDVal = new JLabel("");

		lblDSP2TestResultNANDVal.setBackground(Color.WHITE);

		lblDSP2TestResultNANDVal.setFont(VPXConstants.BISTRESULTFONT);

		dsp2TestResultPanel.add(lblDSP2TestResultNANDVal);

		JLabel lblDSP2TestResultNOR = new JLabel("NOR");

		lblDSP2TestResultNOR.setBackground(Color.WHITE);

		dsp2TestResultPanel.add(lblDSP2TestResultNOR);

		lblDSP2TestResultNORVal = new JLabel("");

		lblDSP2TestResultNORVal.setBackground(Color.WHITE);

		lblDSP2TestResultNORVal.setFont(VPXConstants.BISTRESULTFONT);

		dsp2TestResultPanel.add(lblDSP2TestResultNORVal);

	}

	private void createControlsPanel() {

		JPanel controlsPanel = new JPanel();

		controlsPanel.setBackground(Color.WHITE);

		getContentPane().add(controlsPanel, BorderLayout.SOUTH);
		controlsPanel.setLayout(new MigLayout("", "[296.00px,center][447px]", "[33px]"));

		JButton btnSave = new JButton("Save");
		controlsPanel.add(btnSave, "flowx,cell 0 0");

		btnSave.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				savetoFile();
			}
		});

		btnSave.setActionCommand("OK");

		getRootPane().setDefaultButton(btnSave);

		JLabel lblNote = new JLabel("Please wait until desired processors to complete test.");
		lblNote.setVisible(false);

		controlsPanel.add(lblNote, "cell 1 0");

		btnReboot = new JButton("Reboot");

		btnReboot.setEnabled(false);

		btnReboot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!lblTestDetailP2020IPVal.getText().trim().equals(BIST.NA)) {

					int option = JOptionPane.showConfirmDialog(VPX_BISTResultWindow.this,
							"Do you want to reboot the system?", "Confirmation", JOptionPane.YES_NO_OPTION);

					if (option == JOptionPane.YES_OPTION) {

						parent.setReboot(lblTestDetailP2020IPVal.getText().trim(), 0, ATP.FLASH_DEVICE_NAND, 0);
					}
				} else {

					JOptionPane.showMessageDialog(VPX_BISTResultWindow.this, "No P2020 found", "Not found",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		controlsPanel.add(btnReboot, "cell 0 0");

		JButton btnClose = new JButton("Close");
		controlsPanel.add(btnClose, "cell 0 0,alignx center,aligny center");

		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				VPX_BISTResultWindow.this.dispose();
			}
		});

		btnClose.setActionCommand("Cancel");
	}

	public void showBISTWindow(VPX_ETHWindow prnt, int count) {

		this.parent = prnt;

		clearAllFields();

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				setVisible(true);

			}
		});

		th.start();

		// progress.showProgressWindow(count);
	}

	public void setResult(BIST result) {

		this.testResult = result;

		loadResult();
	}

	private void loadResult() {

		// Test Detail
		lblTestDetailTestTimeVal.setText(testResult.getTestTime());

		lblTestDetailTestDateVal.setText(testResult.getTestDate());

		lblTestDetailDSP2IPVal.setText(testResult.getTestDSP2IP());

		lblTestDetailDSP1IPVal.setText(testResult.getTestDSP1IP());

		lblTestDetailP2020IPVal.setText(testResult.getTestP2020IP());

		lblTestDetailSubSystemVal.setText(testResult.getTestSubSystem());

		lblTestDetailTestTypeVal.setText(testResult.getTestType());

		lblTestDetailTestVal.setText(testResult.getTest());

		// Result Detail
		lblResultDetailTestStatusVal.setText(testResult.getResultTestStatus());

		lblResultDetailTestDurationVal.setText(testResult.getResultTestDuration());

		lblResultDetailTestCompletedAtVal.setText(testResult.getResultTestCompletedAt());

		lblResultDetailTestStartedAtVal.setText(testResult.getResultTestStartedAt());

		lblResultDetailTestsFailedVal.setText(testResult.getResultTestFailed());

		lblResultDetailTestsPassedVal.setText(testResult.getResultTestPassed());

		lblResultDetailNofTestsVal.setText(testResult.getResultTestNoofTests());

		// P2020 Result

		lblP2020TestResultProcessorVal.setText(testResult.getResultP2020Processor());

		lblP2020TestResultDDR3Val.setText(testResult.getResultP2020DDR3());

		lblP2020TestResultNORFlashVal.setText(testResult.getResultP2020NORFlash());

		lblP2020TestResultEthernetVal.setText(testResult.getResultP2020Ethernet());

		lblP2020TestResultPCIEVal.setText(testResult.getResultP2020PCIe());

		lblP2020TestResultSRIOVal.setText(testResult.getResultP2020SRIO());

		lblP2020TestResultTemprature1Val.setText(testResult.getResultP2020Temprature1());

		lblP2020TestResultTemprature2Val.setText(testResult.getResultP2020Temprature2());

		lblP2020TestResultTemprature3Val.setText(testResult.getResultP2020Temprature3());

		lblP2020TestResultVoltage1Val.setText(testResult.getResultP2020Voltage1());

		lblP2020TestResultVoltage2Val.setText(testResult.getResultP2020Voltage2());

		lblP2020TestResultVoltage3Val.setText(testResult.getResultP2020Voltage3());

		lblP2020TestResultVoltage4Val.setText(testResult.getResultP2020Voltage4());

		lblP2020TestResultVoltage5Val.setText(testResult.getResultP2020Voltage5());

		lblP2020TestResultVoltage6Val.setText(testResult.getResultP2020Voltage6());

		lblP2020TestResultVoltage7Val.setText(testResult.getResultP2020Voltage7());

		// DSP1 Result
		lblDSP1TestResultProcessorVal.setText(testResult.getResultDSP1Processor());

		lblDSP1TestResultDDR3Val.setText(testResult.getResultDSP1DDR3());

		lblDSP1TestResultNANDVal.setText(testResult.getResultDSP1NAND());

		lblDSP1TestResultNORVal.setText(testResult.getResultDSP1NOR());

		// DSP2 Result
		lblDSP2TestResultNORVal.setText(testResult.getResultDSP2NOR());

		lblDSP2TestResultNANDVal.setText(testResult.getResultDSP2NAND());

		lblDSP2TestResultDDR3Val.setText(testResult.getResultDSP2DDR3());

		lblDSP2TestResultProcessorVal.setText(testResult.getResultDSP2Processor());

		//lblResultDetailTestCompletedAtVal.setText(testResult.getResultTestCompletedAt());

	//	lblResultDetailTestDurationVal.setText(testResult.getResultTestDuration());

		if (testResult.isP2020Completed()) {

			btnReboot.setEnabled(true);
		}
	}

	private void centerFrame() {

		Dimension windowSize = getSize();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - windowSize.width / 2;

		int dy = centerPoint.y - windowSize.height / 2;

		setLocation(dx, dy);
	}

	private String getAsPlainText(String text) {

		String str = "";
		// '></

		if (text.contains("&deg")) {

			str = text.substring(text.indexOf("'>") + 2, text.indexOf("&deg")).trim();

		} else {

			str = text.substring(text.indexOf("'>") + 2, text.indexOf("</")).trim();

		}

		return str;
	}

	private void savetoFile() {

		PrintWriter writer;

		JFileChooser chooser;

		try {

			chooser = new JFileChooser();

			chooser.setCurrentDirectory(new java.io.File("."));

			chooser.setDialogTitle("Select folder to save");

			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {

				String fileName = String.format("%s/%s_%s_%s.txt", VPXUtilities.getPathAsLinuxStandard(chooser.getSelectedFile().getPath()),
						testResult.getTest().replaceAll(" ", "_"), testResult.getTestSubSystem(),
						VPXUtilities.getCurrentTime(3));

				writer = new PrintWriter(fileName, "UTF-8");

				writer.println("---------------------------------------");

				writer.println("Test Detail");

				writer.println("---------------------------------------");

				writer.println(String.format("Test : %s", testResult.getTest()));

				writer.println(String.format("Test Type : %s", testResult.getTestType()));

				writer.println(String.format("Sub System : %s", testResult.getTestSubSystem()));

				writer.println(String.format("P2020 IP : %s", testResult.getTestP2020IP()));

				writer.println(String.format("DSP 1 IP : %s", testResult.getTestDSP1IP()));

				writer.println(String.format("DSP 2 IP : %s", testResult.getTestDSP2IP()));

				writer.println(String.format("Test Date : %s", testResult.getTestDate()));

				writer.println(String.format("Test Time : %s", testResult.getTestTime()));

				writer.println("");

				writer.println("---------------------------------------");

				writer.println("P2020 Test Result Detail");

				writer.println("---------------------------------------");

				writer.println("");

				writer.println(String.format("Processor : %s", getAsPlainText(testResult.getResultP2020Processor())));

				writer.println(String.format("DDR3 : %s", getAsPlainText(testResult.getResultP2020DDR3())));

				writer.println(String.format("NOR Flash : %s", getAsPlainText(testResult.getResultP2020NORFlash())));

				writer.println(String.format("Ethernet : %s", getAsPlainText(testResult.getResultP2020Ethernet())));

				writer.println(String.format("PCIe : %s", getAsPlainText(testResult.getResultP2020PCIe())));

				writer.println(String.format("SRIO : %s", getAsPlainText(testResult.getResultP2020SRIO())));

				writer.println(
						String.format("Local Temp : %s", getAsPlainText(testResult.getResultP2020Temprature1())));

				writer.println(String.format("Top Temp : %s", getAsPlainText(testResult.getResultP2020Temprature2())));

				writer.println(
						String.format("Bottom Temp : %s", getAsPlainText(testResult.getResultP2020Temprature3())));

				writer.println(String.format("Voltage 3.3 : %s", getAsPlainText(testResult.getResultP2020Voltage1())));

				writer.println(String.format("Voltage 2.5 : %s", getAsPlainText(testResult.getResultP2020Voltage2())));

				writer.println(String.format("Voltage 1.8 : %s", getAsPlainText(testResult.getResultP2020Voltage3())));

				writer.println(String.format("Voltage 1.5 : %s", getAsPlainText(testResult.getResultP2020Voltage4())));

				writer.println(String.format("Voltage 1.2 : %s", getAsPlainText(testResult.getResultP2020Voltage5())));

				writer.println(String.format("Voltage 1.0 : %s", getAsPlainText(testResult.getResultP2020Voltage6())));

				writer.println(String.format("Voltage 1.05 : %s", getAsPlainText(testResult.getResultP2020Voltage7())));

				writer.println("");

				writer.println("---------------------------------------");

				writer.println("DSP 1 Test Result Detail");

				writer.println("---------------------------------------");

				writer.println("");

				writer.println(String.format("Processor : %s", getAsPlainText(testResult.getResultDSP1Processor())));

				writer.println(String.format("DDR3 : %s", getAsPlainText(testResult.getResultDSP1DDR3())));

				writer.println(String.format("NAND : %s", getAsPlainText(testResult.getResultDSP1NAND())));

				writer.println(String.format("NOR : %s", getAsPlainText(testResult.getResultDSP1NOR())));

				writer.println("");

				writer.println("---------------------------------------");

				writer.println("DSP 2 Test Result Detail");

				writer.println("---------------------------------------");

				writer.println(String.format("Processor : %s", getAsPlainText(testResult.getResultDSP2Processor())));

				writer.println(String.format("DDR3 : %s", getAsPlainText(testResult.getResultDSP2DDR3())));

				writer.println(String.format("NAND : %s", getAsPlainText(testResult.getResultDSP2NAND())));

				writer.println(String.format("NOR : %s", getAsPlainText(testResult.getResultDSP2NOR())));

				writer.println("");

				writer.println("---------------------------------------");

				writer.println("Result Detail");

				writer.println("---------------------------------------");

				writer.println("");

				writer.println(String.format("Total No.of Tests : %s", testResult.getResultTestNoofTests()));

				writer.println(String.format("Tests Passed : %s", testResult.getResultTestPassed()));

				writer.println(String.format("Tests Failed : %s", testResult.getResultTestFailed()));

				writer.println(String.format("Test Started At : %s", testResult.getResultTestStartedAt()));

				writer.println(String.format("Test Completed At : %s", testResult.getResultTestCompletedAt()));

				writer.println(String.format("Test Duration : %s", testResult.getResultTestDuration()));

				writer.println(String.format("Test Status : %s", testResult.getResultTestStatus()));

				writer.close();

				JOptionPane.showMessageDialog(VPX_BISTResultWindow.this, "File Saved at " + fileName, "Result File",
						JOptionPane.NO_OPTION);
			}

		} catch (FileNotFoundException | UnsupportedEncodingException e) {

			VPXLogger.updateError(e);

			e.printStackTrace();
		}

	}

	public void updateTestProgress(PROCESSOR_LIST pType, int val) {

		// progress.updateTestProgress(pType, val);

	}

	public class VPX_TestProgressWindow extends JDialog {

		/**
		 * 
		 */
		private static final long serialVersionUID = -7250292684798353091L;

		private JProgressBar progressFileSent;

		private JDialog parent;

		private int maxVal;

		private JLabel lblExitingApplication;

		/**
		 * Create the dialog.
		 */
		public VPX_TestProgressWindow(JDialog prnt, int max) {

			super(prnt);

			this.maxVal = max;

			this.parent = prnt;

			init();

			loadComponents();

		}

		private void init() {

			setTitle("Built in Self Test");

			setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

			setSize(500, 150);

			setLocationRelativeTo(parent);

			getContentPane().setLayout(new BorderLayout(0, 0));

		}

		private void loadComponents() {

			JPanel progressPanel = new JPanel();

			progressPanel.setPreferredSize(new Dimension(10, 35));

			getContentPane().add(progressPanel, BorderLayout.SOUTH);

			progressPanel.setLayout(new BorderLayout(0, 0));

			progressFileSent = new JProgressBar();

			progressFileSent.setStringPainted(true);

			progressFileSent.setMaximum(maxVal);

			progressFileSent.setMinimum(0);

			progressPanel.add(progressFileSent, BorderLayout.SOUTH);

			progressFileSent.setValue(0);

			JPanel detailPanel = new JPanel();

			detailPanel.setBorder(null);

			detailPanel.setPreferredSize(new Dimension(25, 10));

			getContentPane().add(detailPanel, BorderLayout.CENTER);

			detailPanel.setLayout(new BorderLayout(0, 0));

			lblExitingApplication = new JLabel("Built in Self Testing in progress...");

			lblExitingApplication.setFont(VPXConstants.BISTRESULTFONT);

			lblExitingApplication.setPreferredSize(new Dimension(30, 0));

			detailPanel.add(lblExitingApplication);
		}

		public void showProgressWindow(int count) {

			setVisible(true);

			progressFileSent.setMaximum(count);

			progressFileSent.setValue(0);
		}

		public void updateTestProgress(PROCESSOR_LIST pType, int value) {

			if (value == -1) {

				// VPX_TestProgressWindow.this.dispose();

			} else {

				if (pType == PROCESSOR_LIST.PROCESSOR_P2020) {

					lblExitingApplication
							.setText("<html>Built in Self Testing in progress...<br><br>P2020 Test Completed</html>");

				} else if (pType == PROCESSOR_LIST.PROCESSOR_DSP1) {

					lblExitingApplication
							.setText("<html>Built in Self Testing in progress...<br><br>DSP1 Test Completed</html>");

				} else if (pType == PROCESSOR_LIST.PROCESSOR_DSP2) {

					lblExitingApplication
							.setText("<html>Built in Self Testing in progress...<br><br>DSP2 Test Completed</html>");
				}

				progressFileSent.setValue(value);
			}
		}

	}

	private void clearAllFields() {

		btnReboot.setEnabled(false);

		progress.lblExitingApplication.setText("Built in Self Testing in progress...");

		// Test Detail
		lblTestDetailTestTimeVal.setText("");

		lblTestDetailTestDateVal.setText("");

		lblTestDetailDSP2IPVal.setText("");

		lblTestDetailDSP1IPVal.setText("");

		lblTestDetailP2020IPVal.setText("");

		lblTestDetailSubSystemVal.setText("");

		lblTestDetailTestTypeVal.setText("");

		lblTestDetailTestVal.setText("");

		// Result Detail
		lblResultDetailTestStatusVal.setText("");

		lblResultDetailTestDurationVal.setText("");

		lblResultDetailTestCompletedAtVal.setText("");

		lblResultDetailTestStartedAtVal.setText("");

		lblResultDetailTestsFailedVal.setText("");

		lblResultDetailTestsPassedVal.setText("");

		lblResultDetailNofTestsVal.setText("");

		// P2020 Result

		lblP2020TestResultProcessorVal.setText("");

		lblP2020TestResultDDR3Val.setText("");

		lblP2020TestResultNORFlashVal.setText("");

		lblP2020TestResultEthernetVal.setText("");

		lblP2020TestResultPCIEVal.setText("");

		lblP2020TestResultSRIOVal.setText("");

		lblP2020TestResultTemprature1Val.setText("");

		lblP2020TestResultTemprature2Val.setText("");

		lblP2020TestResultTemprature3Val.setText("");

		lblP2020TestResultVoltage1Val.setText("");

		lblP2020TestResultVoltage2Val.setText("");

		lblP2020TestResultVoltage3Val.setText("");

		lblP2020TestResultVoltage4Val.setText("");

		lblP2020TestResultVoltage5Val.setText("");

		lblP2020TestResultVoltage6Val.setText("");

		lblP2020TestResultVoltage7Val.setText("");

		// DSP1 Result
		lblDSP1TestResultProcessorVal.setText("");

		lblDSP1TestResultDDR3Val.setText("");

		lblDSP1TestResultNANDVal.setText("");

		lblDSP1TestResultNORVal.setText("");

		// DSP2 Result
		lblDSP2TestResultNORVal.setText("");

		lblDSP2TestResultNANDVal.setText("");

		lblDSP2TestResultDDR3Val.setText("");

		lblDSP2TestResultProcessorVal.setText("");

		lblResultDetailTestCompletedAtVal.setText("");

		lblResultDetailTestDurationVal.setText("");

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {

		if (!lblTestDetailP2020IPVal.getText().trim().equals(BIST.NA)) {

			int option = JOptionPane.showConfirmDialog(VPX_BISTResultWindow.this,
					"Do you want to reboot the system before close?", "Confirmation", JOptionPane.YES_NO_OPTION);

			if (option == JOptionPane.YES_OPTION) {

				parent.setReboot(lblTestDetailP2020IPVal.getText().trim(), 0, ATP.FLASH_DEVICE_NAND, 0);

				VPX_BISTResultWindow.this.dispose();
			}
		}

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
