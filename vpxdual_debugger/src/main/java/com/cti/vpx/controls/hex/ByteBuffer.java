/*
 * Copyright (c) 2008 Robert Futrell
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name "HexEditor" nor the names of its contributors may
 *       be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
 * EVENT SHALL THE CONTRIBUTORS TO THIS SOFTWARE BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.cti.vpx.controls.hex;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ByteBuffer {

	/**
	 * The byte buffer that contains the document content.
	 */
	private byte[] buffer;

	private int length;

	public ByteBuffer(int size) {

		this.length = size;

		buffer = new byte[size];
	}

	public ByteBuffer(String file) throws IOException {
		this(new File(file));
	}

	public ByteBuffer(File file) throws IOException {

		int size = (int) file.length();
		if (size < 0) { // Probably never happens.
			throw new IOException("Negative file length: " + size);
		}

		this.length = size;

		buffer = new byte[size];

		if (size > 0) {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
			int pos = 0;
			int count = 0;
			try {
				while (pos < buffer.length && (count = in.read(buffer, pos, buffer.length - pos)) > -1) {
					pos += count;
				}
			} finally {
				in.close();
			}
		}

	}

	/**
	 * Creates a buffer representing the contents read from an input stream.
	 *
	 * @param in
	 *            The input stream to read from.
	 * @throws IOException
	 *             If an IO error occurs.
	 */
	public ByteBuffer(InputStream in) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		buffer = new byte[4096]; // Use as a temporary buffer.
		int count = 0;
		while ((count = in.read(buffer, 0, buffer.length)) > -1) {
			baos.write(buffer, 0, count);
		}
		buffer = baos.toByteArray();

		this.length = buffer.length;
	}

	/**
	 * Creates a buffer representing the contents read from an input stream.
	 *
	 * @param in
	 *            The input stream to read from.
	 * @throws IOException
	 *             If an IO error occurs.
	 */
	public ByteBuffer(byte[] bytes) throws IOException {

		buffer = new byte[bytes.length]; // Use as a temporary buffer.

		System.arraycopy(bytes, 0, buffer, 0, bytes.length);

		this.length = bytes.length;
	}

	public byte getByte(int offset) {
		return buffer[offset];
	}

	public byte[] getByteByGroup(int offset, int group, boolean isunSigned) {

		int j = offset * group;

		byte[] b = null;

		if (offset < 0)
			return b;

		if (j < buffer.length) {

			b = new byte[group];

			for (int i = b.length - 1; i >= 0; i--) {

				if (j < buffer.length) {

					if (isunSigned)
						b[i] = (byte) (buffer[j] & 0xff);
					else
						b[i] = (byte) (buffer[j]);
				}

				j++;
			}

		}
		return b;
	}

	public int getSize() {
		return buffer.length;
	}

	public void insertByte(int offset, byte b) {
		byte[] buf2 = new byte[buffer.length + 1];
		System.arraycopy(buffer, 0, buf2, 0, offset);
		buf2[offset] = b;
		System.arraycopy(buffer, offset, buf2, offset + 1, buffer.length - offset);
		buffer = buf2;
		this.length = buffer.length;
	}

	public void insertBytes(int offs, byte[] b) {

		int replaceLength = this.length - buffer.length;

		if (b == null || b.length == 0) {
			return;
		}

		byte[] buf2 = new byte[this.length];
		System.arraycopy(buffer, 0, buf2, 0, offs);
		System.arraycopy(b, 0, buf2, offs, replaceLength);
		System.arraycopy(buffer, offs, buf2, offs + replaceLength, buffer.length - offs);
		buffer = buf2;
		this.length = buffer.length;
	}

	public int read(int offset, byte[] buf) {
		if (buf == null) {
			return -1;
		}
		int count = Math.min(buf.length, getSize() - offset);
		System.arraycopy(buffer, offset, buf, 0, count);
		return count;
	}

	public void remove(int offset, int len) {
		remove(offset, len, null);
	}

	public void remove(int offset, int len, byte[] removed) {
		int length = len;

		if (removed != null) {

			if (offset + len > buffer.length) {
				length = buffer.length - offset;

			}

			System.arraycopy(buffer, offset, removed, 0, length);

		}

		byte[] buf = new byte[buffer.length - length];

		System.arraycopy(buffer, 0, buf, 0, offset);

		System.arraycopy(buffer, offset + length, buf, offset, buf.length - offset);

		buffer = buf;
	}

	public void setByte(int offset, byte b) {
		buffer[offset] = b;
	}

}