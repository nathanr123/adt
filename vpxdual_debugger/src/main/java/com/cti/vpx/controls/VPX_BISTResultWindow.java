package com.cti.vpx.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.cti.vpx.command.ATP;
import com.cti.vpx.model.BIST;
import com.cti.vpx.util.VPXConstants;
import com.cti.vpx.util.VPXUtilities;

public class VPX_BISTResultWindow extends JDialog {

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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VPX_BISTResultWindow dialog = new VPX_BISTResultWindow(new BIST());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VPX_BISTResultWindow() {

		init();

		loadComponents();

		centerFrame();
	}

	public VPX_BISTResultWindow(BIST bist) {

		this();

		setResult(bist);

		loadResult();

	}

	private void init() {

		setModal(true);

		setResizable(false);

		setTitle("Buil In Self Test");

		setBounds(100, 100, 850, 600);

		getContentPane().setLayout(new BorderLayout());

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(contentPanel, BorderLayout.CENTER);

		contentPanel.setLayout(new BorderLayout(0, 0));
	}

	private void loadComponents() {

		createTestPanel();

		createTestResultPanel();

		createControlsPanel();

	}

	private void createTestPanel() {

		detailPanel = new JPanel();

		detailPanel.setPreferredSize(new Dimension(250, 10));

		contentPanel.add(detailPanel, BorderLayout.WEST);

		detailPanel.setLayout(new GridLayout(0, 1, 0, 0));

		createTestDetailPanel();

		createResultDetailPanel();
	}

	private void createTestDetailPanel() {

		JPanel testDetailPanel = new JPanel();

		testDetailPanel.setBorder(new TitledBorder(null, "Test Detail", TitledBorder.LEADING, TitledBorder.TOP, null,
				null));

		detailPanel.add(testDetailPanel);

		testDetailPanel.setLayout(new GridLayout(8, 2, 0, 0));

		JLabel lblTestDetailTest = new JLabel("Test");

		testDetailPanel.add(lblTestDetailTest);

		lblTestDetailTestVal = new JLabel("Built In Self Test");

		lblTestDetailTestVal.setFont(VPXConstants.BISTRESULTFONT);

		testDetailPanel.add(lblTestDetailTestVal);

		JLabel lblTestDetailTestType = new JLabel("Test Type");

		testDetailPanel.add(lblTestDetailTestType);

		lblTestDetailTestTypeVal = new JLabel("Full Test");

		lblTestDetailTestTypeVal.setFont(VPXConstants.BISTRESULTFONT);

		testDetailPanel.add(lblTestDetailTestTypeVal);

		JLabel lblTestDetailSubSystem = new JLabel("Sub System");

		testDetailPanel.add(lblTestDetailSubSystem);

		lblTestDetailSubSystemVal = new JLabel("Sub_1");

		lblTestDetailSubSystemVal.setFont(VPXConstants.BISTRESULTFONT);

		testDetailPanel.add(lblTestDetailSubSystemVal);

		JLabel lblTestDetailP2020IP = new JLabel("P2020 IP");

		testDetailPanel.add(lblTestDetailP2020IP);

		lblTestDetailP2020IPVal = new JLabel("172.17.1.1");

		lblTestDetailP2020IPVal.setFont(VPXConstants.BISTRESULTFONT);

		testDetailPanel.add(lblTestDetailP2020IPVal);

		JLabel lblTestDetailDSP1IP = new JLabel("DSP 1 IP");

		testDetailPanel.add(lblTestDetailDSP1IP);

		lblTestDetailDSP1IPVal = new JLabel("172.17.1.2");

		lblTestDetailDSP1IPVal.setFont(VPXConstants.BISTRESULTFONT);

		testDetailPanel.add(lblTestDetailDSP1IPVal);

		JLabel lblTestDetailDSP2IP = new JLabel("DSP 2 IP");

		testDetailPanel.add(lblTestDetailDSP2IP);

		lblTestDetailDSP2IPVal = new JLabel("172.17.1.3");

		lblTestDetailDSP2IPVal.setFont(VPXConstants.BISTRESULTFONT);

		testDetailPanel.add(lblTestDetailDSP2IPVal);

		JLabel lblTestDetailTestDate = new JLabel("Test Date");

		testDetailPanel.add(lblTestDetailTestDate);

		lblTestDetailTestDateVal = new JLabel("18-07-2015");

		lblTestDetailTestDateVal.setFont(VPXConstants.BISTRESULTFONT);

		testDetailPanel.add(lblTestDetailTestDateVal);

		JLabel lblTestDetailTestTime = new JLabel("Test Time");

		testDetailPanel.add(lblTestDetailTestTime);

		lblTestDetailTestTimeVal = new JLabel("08:12:04");

		lblTestDetailTestTimeVal.setFont(VPXConstants.BISTRESULTFONT);

		testDetailPanel.add(lblTestDetailTestTimeVal);
	}

	private void createResultDetailPanel() {

		JPanel resultDetailPanel = new JPanel();

		resultDetailPanel.setBorder(new TitledBorder(null, "Result Tetail", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));

		detailPanel.add(resultDetailPanel);

		resultDetailPanel.setLayout(new GridLayout(7, 2, 0, 0));

		JLabel lblResultDetailNofTests = new JLabel("Total No.oF Tests");

		resultDetailPanel.add(lblResultDetailNofTests);

		lblResultDetailNofTestsVal = new JLabel("14 Tests");

		lblResultDetailNofTestsVal.setFont(VPXConstants.BISTRESULTFONT);

		resultDetailPanel.add(lblResultDetailNofTestsVal);

		JLabel lblResultDetailTestsPassed = new JLabel("Tests Passed");

		resultDetailPanel.add(lblResultDetailTestsPassed);

		lblResultDetailTestsPassedVal = new JLabel("9 Tests");

		lblResultDetailTestsPassedVal.setFont(VPXConstants.BISTRESULTFONT);

		resultDetailPanel.add(lblResultDetailTestsPassedVal);

		JLabel lblResultDetailTestsFailed = new JLabel("Tests Failed");

		resultDetailPanel.add(lblResultDetailTestsFailed);

		lblResultDetailTestsFailedVal = new JLabel("5 Tests");

		lblResultDetailTestsFailedVal.setFont(VPXConstants.BISTRESULTFONT);

		resultDetailPanel.add(lblResultDetailTestsFailedVal);

		JLabel lblResultDetailTestStartedAt = new JLabel("Test Started at");

		resultDetailPanel.add(lblResultDetailTestStartedAt);

		lblResultDetailTestStartedAtVal = new JLabel("08:12:04");

		lblResultDetailTestStartedAtVal.setFont(VPXConstants.BISTRESULTFONT);

		resultDetailPanel.add(lblResultDetailTestStartedAtVal);

		JLabel lblResultDetailTestCompletedAt = new JLabel("Tests Completed at");

		resultDetailPanel.add(lblResultDetailTestCompletedAt);

		lblResultDetailTestCompletedAtVal = new JLabel("08:14:04");

		lblResultDetailTestCompletedAtVal.setFont(VPXConstants.BISTRESULTFONT);

		resultDetailPanel.add(lblResultDetailTestCompletedAtVal);

		JLabel lblResultDetailTestDuration = new JLabel("Test Duration");

		resultDetailPanel.add(lblResultDetailTestDuration);

		lblResultDetailTestDurationVal = new JLabel("2 Minutes");

		lblResultDetailTestDurationVal.setFont(VPXConstants.BISTRESULTFONT);

		resultDetailPanel.add(lblResultDetailTestDurationVal);

		JLabel lblResultDetailTestStatus = new JLabel("Test Status");

		resultDetailPanel.add(lblResultDetailTestStatus);

		lblResultDetailTestStatusVal = new JLabel("Success!");

		lblResultDetailTestStatusVal.setFont(VPXConstants.BISTRESULTFONT);

		resultDetailPanel.add(lblResultDetailTestStatusVal);
	}

	private void createTestResultPanel() {

		resultPanel = new JPanel();

		resultPanel
				.setBorder(new TitledBorder(null, "Test Result", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		contentPanel.add(resultPanel, BorderLayout.CENTER);

		resultPanel.setLayout(new BorderLayout(0, 0));

		dspTestResultBasePanel = new JPanel();

		dspTestResultBasePanel.setPreferredSize(new Dimension(10, 200));

		resultPanel.add(dspTestResultBasePanel, BorderLayout.SOUTH);

		dspTestResultBasePanel.setLayout(new GridLayout(0, 2, 0, 0));

		createDSP1TestResultPanel();

		createDSP2TestResultPanel();

		createP2020TestResultPanel();
	}

	private void createP2020TestResultPanel() {

		JPanel p2020TestResultPanel = new JPanel();

		p2020TestResultPanel.setBorder(new TitledBorder(null, "P2020 Test Result", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));

		resultPanel.add(p2020TestResultPanel, BorderLayout.CENTER);

		p2020TestResultPanel.setLayout(new GridLayout(8, 4, 0, 0));

		JLabel lblP2020TestResultProcessor = new JLabel("Processor");

		p2020TestResultPanel.add(lblP2020TestResultProcessor);

		lblP2020TestResultProcessorVal = new JLabel("PASS");

		lblP2020TestResultProcessorVal.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultProcessorVal);

		JLabel lblP2020TestResultTemprature3 = new JLabel("Temprature 3");

		p2020TestResultPanel.add(lblP2020TestResultTemprature3);

		lblP2020TestResultTemprature3Val = new JLabel("PASS");

		lblP2020TestResultTemprature3Val.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultTemprature3Val);

		JLabel lblP2020TestResultDDR3 = new JLabel("DDR3");

		p2020TestResultPanel.add(lblP2020TestResultDDR3);

		lblP2020TestResultDDR3Val = new JLabel("PASS");

		lblP2020TestResultDDR3Val.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultDDR3Val);

		JLabel lblP2020TestResultVoltage1 = new JLabel("Voltage 1");

		p2020TestResultPanel.add(lblP2020TestResultVoltage1);

		lblP2020TestResultVoltage1Val = new JLabel("PASS");

		lblP2020TestResultVoltage1Val.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultVoltage1Val);

		JLabel lblP2020TestResultNORFlash = new JLabel("NOR Flash");

		p2020TestResultPanel.add(lblP2020TestResultNORFlash);

		lblP2020TestResultNORFlashVal = new JLabel("PASS");

		lblP2020TestResultNORFlashVal.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultNORFlashVal);

		JLabel lblP2020TestResultVoltage2 = new JLabel("Voltage 2");

		p2020TestResultPanel.add(lblP2020TestResultVoltage2);

		lblP2020TestResultVoltage2Val = new JLabel("PASS");

		lblP2020TestResultVoltage2Val.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultVoltage2Val);

		JLabel lblP2020TestResultEthernet = new JLabel("Ethernet");

		p2020TestResultPanel.add(lblP2020TestResultEthernet);

		lblP2020TestResultEthernetVal = new JLabel("PASS");

		lblP2020TestResultEthernetVal.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultEthernetVal);

		JLabel lblP2020TestResultVoltage3 = new JLabel("Voltage 3");

		p2020TestResultPanel.add(lblP2020TestResultVoltage3);

		lblP2020TestResultVoltage3Val = new JLabel("PASS");

		lblP2020TestResultVoltage3Val.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultVoltage3Val);

		JLabel lblP2020TestResultPCIE = new JLabel("PCIe");

		p2020TestResultPanel.add(lblP2020TestResultPCIE);

		lblP2020TestResultPCIEVal = new JLabel("PASS");

		lblP2020TestResultPCIEVal.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultPCIEVal);

		JLabel lblP2020TestResultVoltage4 = new JLabel("Voltage 4");

		p2020TestResultPanel.add(lblP2020TestResultVoltage4);

		lblP2020TestResultVoltage4Val = new JLabel("PASS");

		lblP2020TestResultVoltage4Val.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultVoltage4Val);

		JLabel lblP2020TestResultSRIO = new JLabel("SRIO");

		p2020TestResultPanel.add(lblP2020TestResultSRIO);

		lblP2020TestResultSRIOVal = new JLabel("PASS");

		lblP2020TestResultSRIOVal.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultSRIOVal);

		JLabel lblP2020TestResultVoltage5 = new JLabel("Voltage 5");

		p2020TestResultPanel.add(lblP2020TestResultVoltage5);

		lblP2020TestResultVoltage5Val = new JLabel("PASS");

		lblP2020TestResultVoltage5Val.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultVoltage5Val);

		JLabel lblP2020TestResultTemprature1 = new JLabel("Temprature 1");

		p2020TestResultPanel.add(lblP2020TestResultTemprature1);

		lblP2020TestResultTemprature1Val = new JLabel("PASS");

		lblP2020TestResultTemprature1Val.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultTemprature1Val);

		JLabel lblP2020TestResultVoltage6 = new JLabel("Voltage 6");

		p2020TestResultPanel.add(lblP2020TestResultVoltage6);

		lblP2020TestResultVoltage6Val = new JLabel("PASS");

		lblP2020TestResultVoltage6Val.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultVoltage6Val);

		JLabel lblP2020TestResultTemprature2 = new JLabel("Temprature 2");

		p2020TestResultPanel.add(lblP2020TestResultTemprature2);

		lblP2020TestResultTemprature2Val = new JLabel("PASS");

		lblP2020TestResultTemprature2Val.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultTemprature2Val);

		JLabel lblP2020TestResultVoltage7 = new JLabel("Voltage 7");

		p2020TestResultPanel.add(lblP2020TestResultVoltage7);

		lblP2020TestResultVoltage7Val = new JLabel("PASS");

		lblP2020TestResultVoltage7Val.setFont(VPXConstants.BISTRESULTFONT);

		p2020TestResultPanel.add(lblP2020TestResultVoltage7Val);
	}

	private void createDSP1TestResultPanel() {

		JPanel dsp1TestResultPanel = new JPanel();

		dsp1TestResultPanel.setBorder(new TitledBorder(null, "DSP 1 Test Result", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		dspTestResultBasePanel.add(dsp1TestResultPanel);

		dsp1TestResultPanel.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lblDSP1TestResultProcessor = new JLabel("Processor");

		dsp1TestResultPanel.add(lblDSP1TestResultProcessor);

		lblDSP1TestResultProcessorVal = new JLabel("PASS");

		lblDSP1TestResultProcessorVal.setFont(VPXConstants.BISTRESULTFONT);

		dsp1TestResultPanel.add(lblDSP1TestResultProcessorVal);

		JLabel lblDSP1TestResultDDR3 = new JLabel("DDR3");

		dsp1TestResultPanel.add(lblDSP1TestResultDDR3);

		lblDSP1TestResultDDR3Val = new JLabel("PASS");

		lblDSP1TestResultDDR3Val.setFont(VPXConstants.BISTRESULTFONT);

		dsp1TestResultPanel.add(lblDSP1TestResultDDR3Val);

		JLabel lblDSP1TestResultNAND = new JLabel("NAND");

		dsp1TestResultPanel.add(lblDSP1TestResultNAND);

		lblDSP1TestResultNANDVal = new JLabel("PASS");

		lblDSP1TestResultNANDVal.setFont(VPXConstants.BISTRESULTFONT);

		dsp1TestResultPanel.add(lblDSP1TestResultNANDVal);

		JLabel lblDSP1TestResultNOR = new JLabel("NOR");

		dsp1TestResultPanel.add(lblDSP1TestResultNOR);

		lblDSP1TestResultNORVal = new JLabel("PASS");

		lblDSP1TestResultNORVal.setFont(VPXConstants.BISTRESULTFONT);

		dsp1TestResultPanel.add(lblDSP1TestResultNORVal);

	}

	private void createDSP2TestResultPanel() {

		JPanel dsp2TestResultPanel = new JPanel();

		dsp2TestResultPanel.setBorder(new TitledBorder(null, "DSP 2 Test Result", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));

		dspTestResultBasePanel.add(dsp2TestResultPanel);

		dsp2TestResultPanel.setLayout(new GridLayout(4, 2, 0, 0));

		JLabel lblDSP2TestResultProcessor = new JLabel("Processor");

		dsp2TestResultPanel.add(lblDSP2TestResultProcessor);

		lblDSP2TestResultProcessorVal = new JLabel("PASS");

		lblDSP2TestResultProcessorVal.setFont(VPXConstants.BISTRESULTFONT);

		dsp2TestResultPanel.add(lblDSP2TestResultProcessorVal);

		JLabel lblDSP2TestResultDDR3 = new JLabel("DDR3");

		dsp2TestResultPanel.add(lblDSP2TestResultDDR3);

		lblDSP2TestResultDDR3Val = new JLabel("PASS");

		lblDSP2TestResultDDR3Val.setFont(VPXConstants.BISTRESULTFONT);

		dsp2TestResultPanel.add(lblDSP2TestResultDDR3Val);

		JLabel lblDSP2TestResultNAND = new JLabel("NAND");

		dsp2TestResultPanel.add(lblDSP2TestResultNAND);

		lblDSP2TestResultNANDVal = new JLabel("PASS");

		lblDSP2TestResultNANDVal.setFont(VPXConstants.BISTRESULTFONT);

		dsp2TestResultPanel.add(lblDSP2TestResultNANDVal);

		JLabel lblDSP2TestResultNOR = new JLabel("NOR");

		dsp2TestResultPanel.add(lblDSP2TestResultNOR);

		lblDSP2TestResultNORVal = new JLabel("PASS");

		lblDSP2TestResultNORVal.setFont(VPXConstants.BISTRESULTFONT);

		dsp2TestResultPanel.add(lblDSP2TestResultNORVal);

	}

	private void createControlsPanel() {

		JPanel controlsPanel = new JPanel();

		controlsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		getContentPane().add(controlsPanel, BorderLayout.SOUTH);

		JButton btnSave = new JButton("Save");

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				savetoFile();
			}
		});

		btnSave.setActionCommand("OK");

		controlsPanel.add(btnSave);

		getRootPane().setDefaultButton(btnSave);

		JButton btnClose = new JButton("Close");

		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				VPX_BISTResultWindow.this.dispose();
			}
		});

		btnClose.setActionCommand("Cancel");

		controlsPanel.add(btnClose);
	}

	public void setResult(BIST result) {

		this.testResult = result;

		loadResult();
	}

	private void loadResult() {
		// "<html>41 &deg;C</html>" // for degree represntation

		// Test Detail
		lblTestDetailTestTimeVal.setText(testResult.getTestTime());

		lblTestDetailTestDateVal.setText(testResult.getTest());

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

		lblP2020TestResultTemprature1Val.setText(String.format("<html>%s &deg;C</html>",
				testResult.getResultP2020Temprature1()));

		lblP2020TestResultTemprature2Val.setText(String.format("<html>%s &deg;C</html>",
				testResult.getResultP2020Temprature2()));

		lblP2020TestResultTemprature3Val.setText(String.format("<html>%s &deg;C</html>",
				testResult.getResultP2020Temprature3()));

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

	}

	private void centerFrame() {

		Dimension windowSize = getSize();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - windowSize.width / 2;

		int dy = centerPoint.y - windowSize.height / 2;

		setLocation(dx, dy);
	}

	private void savetoFile() {
		PrintWriter writer;
		try {

			String fileName = String.format("%s\\%s_%s_%s.txt", System.getProperty("user.home"), testResult.getTest()
					.replaceAll(" ", "_"), testResult.getTestSubSystem(), VPXUtilities.getCurrentTime(3));

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

			writer.println(String.format("Processor : %s", testResult.getResultP2020Processor()));

			writer.println(String.format("DDR3 : %s", testResult.getResultP2020DDR3()));

			writer.println(String.format("NOR Flash : %s", testResult.getResultP2020NORFlash()));

			writer.println(String.format("Ethernet : %s", testResult.getResultP2020Ethernet()));

			writer.println(String.format("PCIe : %s", testResult.getResultP2020PCIe()));

			writer.println(String.format("SRIO : %s", testResult.getResultP2020SRIO()));

			writer.println(String.format("Temprature 1 : %s", testResult.getResultP2020Temprature1()));

			writer.println(String.format("Temprature 2 : %s", testResult.getResultP2020Temprature2()));

			writer.println(String.format("Temprature 3 : %s", testResult.getResultP2020Temprature3()));

			writer.println(String.format("Voltage 1 : %s", testResult.getResultP2020Voltage1()));

			writer.println(String.format("Voltage 2 : %s", testResult.getResultP2020Voltage2()));

			writer.println(String.format("Voltage 3 : %s", testResult.getResultP2020Voltage3()));

			writer.println(String.format("Voltage 4 : %s", testResult.getResultP2020Voltage4()));

			writer.println(String.format("Voltage 5 : %s", testResult.getResultP2020Voltage5()));

			writer.println(String.format("Voltage 6 : %s", testResult.getResultP2020Voltage6()));

			writer.println(String.format("Voltage 7 : %s", testResult.getResultP2020Voltage7()));

			writer.println("");

			writer.println("---------------------------------------");

			writer.println("DSP 1 Test Result Detail");

			writer.println("---------------------------------------");

			writer.println("");

			writer.println(String.format("Processor : %s", testResult.getResultDSP1Processor()));

			writer.println(String.format("DDR3 : %s", testResult.getResultDSP1DDR3()));

			writer.println(String.format("NAND : %s", testResult.getResultDSP1NAND()));

			writer.println(String.format("NOR : %s", testResult.getResultDSP1NOR()));

			writer.println("");

			writer.println("---------------------------------------");

			writer.println("DSP 2 Test Result Detail");

			writer.println("---------------------------------------");

			writer.println(String.format("Processor : %s", testResult.getResultDSP2Processor()));

			writer.println(String.format("DDR3 : %s", testResult.getResultDSP2DDR3()));

			writer.println(String.format("NAND : %s", testResult.getResultDSP2NAND()));

			writer.println(String.format("NOR : %s", testResult.getResultDSP2NOR()));

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
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}
}
