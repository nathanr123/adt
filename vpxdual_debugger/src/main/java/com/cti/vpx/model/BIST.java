package com.cti.vpx.model;

import java.io.Serializable;

import com.cti.vpx.util.VPXUtilities;

public class BIST implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5873062208817223775L;

	public static final String NA = "<html><font color='gray'>NA</font></html>";

	// Test Detail
	private String test = "Built In Self Test";

	private String testType = "Full Test";

	private String testSubSystem = "";

	private String testP2020IP = NA;

	private String testDSP1IP = NA;

	private String testDSP2IP = NA;

	private String testDate = VPXUtilities.getCurrentTime(1);

	private String testTime = VPXUtilities.getCurrentTime(2);

	// Result detail
	private String resultTestNoofTests = NA;

	private String resultTestPassed = NA;

	private String resultTestFailed = NA;

	private String resultTestStartedAt = VPXUtilities.getCurrentTime(2);

	private String resultTestCompletedAt = VPXUtilities.getCurrentTime(2);

	private String resultTestDuration = NA;

	private String resultTestStatus = NA;

	// P2020 Test Result
	private String resultP2020Processor = NA;

	private String resultP2020DDR3 = NA;

	private String resultP2020NORFlash = NA;

	private String resultP2020Ethernet = NA;

	private String resultP2020PCIe = NA;

	private String resultP2020SRIO = NA;

	private String resultP2020Temprature1 = NA;

	private String resultP2020Temprature2 = NA;

	private String resultP2020Temprature3 = NA;

	private String resultP2020Voltage1 = NA;

	private String resultP2020Voltage2 = NA;

	private String resultP2020Voltage3 = NA;

	private String resultP2020Voltage4 = NA;

	private String resultP2020Voltage5 = NA;

	private String resultP2020Voltage6 = NA;

	private String resultP2020Voltage7 = NA;

	private boolean isP2020Completed = false;

	// DSP 1 Test Result
	private String resultDSP1Processor = NA;

	private String resultDSP1DDR3 = NA;

	private String resultDSP1NAND = NA;

	private String resultDSP1NOR = NA;

	private boolean isDSP1Completed = false;

	// DSP 2 Test Result
	private String resultDSP2Processor = NA;

	private String resultDSP2DDR3 = NA;

	private String resultDSP2NAND = NA;

	private String resultDSP2NOR = NA;

	private boolean isDSP2Completed = false;

	private boolean isP2020Available = true;

	private boolean isDSP1Available = true;

	private boolean isDSP2Available = true;

	private long startTime;

	private int totalProcessor = 0;

	public BIST() {

		startTime = System.currentTimeMillis();

		testDate = VPXUtilities.getCurrentTime(1);

		testTime = VPXUtilities.getCurrentTime(2);

		resultTestStartedAt = VPXUtilities.getCurrentTime(2);

	}

	public BIST(boolean isP2020, boolean isDSP1, boolean isDSP2) {

		this.isP2020Available = isP2020;

		this.isDSP1Available = isDSP1;

		this.isDSP2Available = isDSP2;

		if (isP2020Available)
			totalProcessor++;
		if (isDSP1Available)
			totalProcessor++;
		if (isDSP2Available)
			totalProcessor++;

		startTime = System.currentTimeMillis();

		testDate = VPXUtilities.getCurrentTime(1);

		testTime = VPXUtilities.getCurrentTime(2);

		resultTestStartedAt = VPXUtilities.getCurrentTime(2);

	}

	public BIST(String test, String testType, String testSubSystem, String testP2020IP, String testDSP1IP,
			String testDSP2IP, String testDate, String testTime, String resultNoofTests, String resultTestPassed,
			String resultTestFailed, String resultTestStartedAt, String resultTestCompletedAt,
			String resultTestDuration, String resultTestStatus, String resultresultP2020020Processor,
			String resultP2020DDR3, String resultP2020NORFlash, String resultP2020Ethernet, String resultP2020PCIe,
			String resultP2020SRIO, String resultP2020Temprature1, String resultP2020Temprature2,
			String resultP2020Temprature3, String resultP2020Voltage1, String resultP2020Voltage2,
			String resultP2020Voltage3, String resultP2020Voltage4, String resultP2020Voltage5,
			String resultP2020Voltage6, String resultP2020Voltage7, String resultDSP1Processor, String resultDSP1DDR3,
			String resultDSP1BAND, String resultDSP1NOR, String resultDSP2Processor, String resultDSP2DDR3,
			String resultDSP2BAND, String resultDSP2NOR) {

		super();

		this.test = test;

		this.testType = testType;

		this.testSubSystem = testSubSystem;

		this.testP2020IP = testP2020IP;

		this.testDSP1IP = testDSP1IP;

		this.testDSP2IP = testDSP2IP;

		this.testDate = testDate;

		this.testTime = testTime;

		this.resultTestNoofTests = resultNoofTests;

		this.resultTestPassed = resultTestPassed;

		this.resultTestFailed = resultTestFailed;

		this.resultTestStartedAt = resultTestStartedAt;

		this.resultTestCompletedAt = resultTestCompletedAt;

		this.resultTestDuration = resultTestDuration;

		this.resultTestStatus = resultTestStatus;

		this.resultP2020Processor = resultresultP2020020Processor;

		this.resultP2020DDR3 = resultP2020DDR3;

		this.resultP2020NORFlash = resultP2020NORFlash;

		this.resultP2020Ethernet = resultP2020Ethernet;

		this.resultP2020PCIe = resultP2020PCIe;

		this.resultP2020SRIO = resultP2020SRIO;

		this.resultP2020Temprature1 = resultP2020Temprature1;

		this.resultP2020Temprature2 = resultP2020Temprature2;

		this.resultP2020Temprature3 = resultP2020Temprature3;

		this.resultP2020Voltage1 = resultP2020Voltage1;

		this.resultP2020Voltage2 = resultP2020Voltage2;

		this.resultP2020Voltage3 = resultP2020Voltage3;

		this.resultP2020Voltage4 = resultP2020Voltage4;

		this.resultP2020Voltage5 = resultP2020Voltage5;

		this.resultP2020Voltage6 = resultP2020Voltage6;

		this.resultP2020Voltage7 = resultP2020Voltage7;

		this.resultDSP1Processor = resultDSP1Processor;

		this.resultDSP1DDR3 = resultDSP1DDR3;

		this.resultDSP1NAND = resultDSP1BAND;

		this.resultDSP1NOR = resultDSP1NOR;

		this.resultDSP2Processor = resultDSP2Processor;

		this.resultDSP2DDR3 = resultDSP2DDR3;

		this.resultDSP2NAND = resultDSP2BAND;

		this.resultDSP2NOR = resultDSP2NOR;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public String getTestSubSystem() {
		return testSubSystem;
	}

	public void setTestSubSystem(String testSubSystem) {
		this.testSubSystem = testSubSystem;
	}

	public String getTestP2020IP() {
		return testP2020IP;
	}

	public void setTestP2020IP(String testP2020IP) {
		this.testP2020IP = testP2020IP;
	}

	public String getTestDSP1IP() {
		return testDSP1IP;
	}

	public void setTestDSP1IP(String testDSP1IP) {
		this.testDSP1IP = testDSP1IP;
	}

	public String getTestDSP2IP() {
		return testDSP2IP;
	}

	public void setTestDSP2IP(String testDSP2IP) {
		this.testDSP2IP = testDSP2IP;
	}

	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public String getTestTime() {
		return testTime;
	}

	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}

	public String getResultTestNoofTests() {
		return resultTestNoofTests;
	}

	public void setResultTestNoofTests(String resultTestNoofTests) {
		this.resultTestNoofTests = resultTestNoofTests;
	}

	public String getResultTestPassed() {
		return resultTestPassed;
	}

	public void setResultTestPassed(String resultTestPassed) {
		this.resultTestPassed = resultTestPassed;
	}

	public String getResultTestFailed() {
		return resultTestFailed;
	}

	public void setResultTestFailed(String resultTestFailed) {
		this.resultTestFailed = resultTestFailed;
	}

	public String getResultTestStartedAt() {
		return resultTestStartedAt;
	}

	public void setResultTestStartedAt(String resultTestStartedAt) {
		this.resultTestStartedAt = resultTestStartedAt;
	}

	public String getResultTestCompletedAt() {
		return resultTestCompletedAt;
	}

	public void setResultTestCompletedAt(String resultTestCompletedAt) {
		this.resultTestCompletedAt = resultTestCompletedAt;
	}

	public String getResultTestDuration() {
		return resultTestDuration;
	}

	public void setResultTestDuration(String resultTestDuration) {
		this.resultTestDuration = resultTestDuration;
	}

	public String getResultTestStatus() {
		return resultTestStatus;
	}

	public void setResultTestStatus(String resultTestStatus) {
		this.resultTestStatus = resultTestStatus;
	}

	public String getResultP2020Processor() {
		return resultP2020Processor;
	}

	public void setResultP2020Processor(String resultP2020Processor) {
		this.resultP2020Processor = resultP2020Processor;
	}

	public String getResultP2020DDR3() {
		return resultP2020DDR3;
	}

	public void setResultP2020DDR3(String resultP2020DDR3) {
		this.resultP2020DDR3 = resultP2020DDR3;
	}

	public String getResultP2020NORFlash() {
		return resultP2020NORFlash;
	}

	public void setResultP2020NORFlash(String resultP2020NORFlash) {
		this.resultP2020NORFlash = resultP2020NORFlash;
	}

	public String getResultP2020Ethernet() {
		return resultP2020Ethernet;
	}

	public void setResultP2020Ethernet(String resultP2020Ethernet) {
		this.resultP2020Ethernet = resultP2020Ethernet;
	}

	public String getResultP2020PCIe() {
		return resultP2020PCIe;
	}

	public void setResultP2020PCIe(String resultP2020PCIe) {
		this.resultP2020PCIe = resultP2020PCIe;
	}

	public String getResultP2020SRIO() {
		return resultP2020SRIO;
	}

	public void setResultP2020SRIO(String resultP2020SRIO) {
		this.resultP2020SRIO = resultP2020SRIO;
	}

	public String getResultP2020Temprature1() {
		return resultP2020Temprature1;
	}

	public void setResultP2020Temprature1(String resultP2020Temprature1) {
		this.resultP2020Temprature1 = resultP2020Temprature1;
	}

	public String getResultP2020Temprature2() {
		return resultP2020Temprature2;
	}

	public void setResultP2020Temprature2(String resultP2020Temprature2) {
		this.resultP2020Temprature2 = resultP2020Temprature2;
	}

	public String getResultP2020Temprature3() {
		return resultP2020Temprature3;
	}

	public void setResultP2020Temprature3(String resultP2020Temprature3) {
		this.resultP2020Temprature3 = resultP2020Temprature3;
	}

	public String getResultP2020Voltage1() {
		return resultP2020Voltage1;
	}

	public void setResultP2020Voltage1(String resultP2020Voltage1) {
		this.resultP2020Voltage1 = resultP2020Voltage1;
	}

	public String getResultP2020Voltage2() {
		return resultP2020Voltage2;
	}

	public void setResultP2020Voltage2(String resultP2020Voltage2) {
		this.resultP2020Voltage2 = resultP2020Voltage2;
	}

	public String getResultP2020Voltage3() {
		return resultP2020Voltage3;
	}

	public void setResultP2020Voltage3(String resultP2020Voltage3) {
		this.resultP2020Voltage3 = resultP2020Voltage3;
	}

	public String getResultP2020Voltage4() {
		return resultP2020Voltage4;
	}

	public void setResultP2020Voltage4(String resultP2020Voltage4) {
		this.resultP2020Voltage4 = resultP2020Voltage4;
	}

	public String getResultP2020Voltage5() {
		return resultP2020Voltage5;
	}

	public void setResultP2020Voltage5(String resultP2020Voltage5) {
		this.resultP2020Voltage5 = resultP2020Voltage5;
	}

	public String getResultP2020Voltage6() {
		return resultP2020Voltage6;
	}

	public void setResultP2020Voltage6(String resultP2020Voltage6) {
		this.resultP2020Voltage6 = resultP2020Voltage6;
	}

	public String getResultP2020Voltage7() {
		return resultP2020Voltage7;
	}

	public void setResultP2020Voltage7(String resultP2020Voltage7) {
		this.resultP2020Voltage7 = resultP2020Voltage7;
	}

	public String getResultDSP1Processor() {
		return resultDSP1Processor;
	}

	public void setResultDSP1Processor(String resultDSP1Processor) {
		this.resultDSP1Processor = resultDSP1Processor;
	}

	public String getResultDSP1DDR3() {
		return resultDSP1DDR3;
	}

	public void setResultDSP1DDR3(String resultDSP1DDR3) {
		this.resultDSP1DDR3 = resultDSP1DDR3;
	}

	public String getResultDSP1NAND() {
		return resultDSP1NAND;
	}

	public void setResultDSP1NAND(String resultDSP1NAND) {
		this.resultDSP1NAND = resultDSP1NAND;
	}

	public String getResultDSP2NAND() {
		return resultDSP2NAND;
	}

	public void setResultDSP2NAND(String resultDSP2NAND) {
		this.resultDSP2NAND = resultDSP2NAND;
	}

	public String getResultDSP1NOR() {
		return resultDSP1NOR;
	}

	public void setResultDSP1NOR(String resultDSP1NOR) {
		this.resultDSP1NOR = resultDSP1NOR;
	}

	public String getResultDSP2Processor() {
		return resultDSP2Processor;
	}

	public void setResultDSP2Processor(String resultDSP2Processor) {
		this.resultDSP2Processor = resultDSP2Processor;
	}

	public String getResultDSP2DDR3() {
		return resultDSP2DDR3;
	}

	public void setResultDSP2DDR3(String resultDSP2DDR3) {
		this.resultDSP2DDR3 = resultDSP2DDR3;
	}

	public String getResultDSP2NOR() {
		return resultDSP2NOR;
	}

	public void setResultDSP2NOR(String resultDSP2NOR) {
		this.resultDSP2NOR = resultDSP2NOR;
	}

	public boolean isP2020Completed() {
		return isP2020Completed;
	}

	public void setP2020Completed(boolean isP2020Completed) {
		this.isP2020Completed = isP2020Completed;
	}

	public boolean isDSP1Completed() {
		return isDSP1Completed;
	}

	public void setDSP1Completed(boolean isDSP1Completed) {
		this.isDSP1Completed = isDSP1Completed;
	}

	public boolean isDSP2Completed() {
		return isDSP2Completed;
	}

	public void setDSP2Completed(boolean isDSP2Completed) {
		this.isDSP2Completed = isDSP2Completed;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public boolean isP2020Available() {
		return isP2020Available;
	}

	public void setP2020Available(boolean isP2020Available) {
		this.isP2020Available = isP2020Available;
	}

	public boolean isDSP1Available() {
		return isDSP1Available;
	}

	public void setDSP1Available(boolean isDSP1Available) {
		this.isDSP1Available = isDSP1Available;
	}

	public boolean isDSP2Available() {
		return isDSP2Available;
	}

	public void setDSP2Available(boolean isDSP2Available) {
		this.isDSP2Available = isDSP2Available;
	}

	public boolean isAllCompleted() {

		if ((isP2020Available && isP2020Completed) && (isDSP1Available && isDSP1Completed)
				&& (isDSP2Available && isDSP2Completed)) {

			return true;
		}

		return false;

	}

	public int getTotalProcessor() {

		return this.totalProcessor;
	}
}
