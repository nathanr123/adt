package com.cti.vpx.controls.hex;

public class MemoryViewFilter {

	private String subsystem;

	private String processor;

	private String core;

	private boolean isAutoRefresh;

	private int timeinterval;

	private boolean isUseMapFile;

	private String mapPath;

	private String memoryName;

	private boolean isDirectMemory;

	private String memoryAddress;

	private String memoryLength;

	private String memoryStride;

	public MemoryViewFilter() {

	}

	public MemoryViewFilter(String subsystem, String processor, String core, boolean isAutoRefresh, int timeinterval,
			boolean isUseMapFile, String mapPath, String memoryName, boolean isDirectMemory, String memoryAddress,
			String memoryLength, String memoryStride) {

		super();

		this.subsystem = subsystem;

		this.processor = processor;

		this.core = core;

		this.isAutoRefresh = isAutoRefresh;

		this.timeinterval = timeinterval;

		this.isUseMapFile = isUseMapFile;

		this.mapPath = mapPath;

		this.memoryName = memoryName;

		this.isDirectMemory = isDirectMemory;

		this.memoryAddress = memoryAddress;

		this.memoryLength = memoryLength;

		this.memoryStride = memoryStride;
	}

	public String getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(String subsystem) {
		this.subsystem = subsystem;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public String getCore() {
		return core;
	}

	public void setCore(String core) {
		this.core = core;
	}

	public boolean isAutoRefresh() {
		return isAutoRefresh;
	}

	public void setAutoRefresh(boolean isAutoRefresh) {
		this.isAutoRefresh = isAutoRefresh;
	}

	public int getTimeinterval() {
		return timeinterval;
	}

	public void setTimeinterval(int timeinterval) {
		this.timeinterval = timeinterval;
	}

	public boolean isUseMapFile() {
		return isUseMapFile;
	}

	public void setUseMapFile(boolean isUseMapFile) {

		this.isUseMapFile = isUseMapFile;

	}

	public String getMapPath() {
		return mapPath;
	}

	public void setMapPath(String mapPath) {
		this.mapPath = mapPath;
	}

	public String getMemoryName() {
		return memoryName;
	}

	public void setMemoryName(String memoryName) {
		this.memoryName = memoryName;
	}

	public boolean isDirectMemory() {
		return isDirectMemory;
	}

	public void setDirectMemory(boolean isDirectMemory) {
		this.isDirectMemory = isDirectMemory;
	}

	public String getMemoryAddress() {
		return memoryAddress;
	}

	public void setMemoryAddress(String memoryAddress) {
		this.memoryAddress = memoryAddress;
	}

	public String getMemoryLength() {
		return memoryLength;
	}

	public void setMemoryLength(String memoryLength) {
		this.memoryLength = memoryLength;
	}

	public String getMemoryStride() {
		return memoryStride;
	}

	public void setMemoryStride(String memoryStride) {
		this.memoryStride = memoryStride;
	}

}
