package com.cti.model;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class FileUpload implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5551858100933500562L;

	private MultipartFile file;

	private String desc;

	public FileUpload() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the file
	 */
	public MultipartFile getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(MultipartFile file) {
		this.file = file;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

}
