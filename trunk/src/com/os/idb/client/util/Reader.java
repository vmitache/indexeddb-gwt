package com.os.idb.client.util;

import java.io.IOException;

public abstract class Reader {
	public abstract void close() throws IOException;
	public abstract int read() throws IOException;
	public int read(char[] cbuf) throws IOException {
		return read(cbuf, 0, cbuf.length);
	}
	public abstract int read(char[] cbuf, int off, int len) throws IOException;
}