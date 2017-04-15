/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emotion;

import java.io.IOException;
import java.io.Writer;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLWriter;

/**
 *
 * @author user
 */
public class HteWriter extends HTMLWriter {

	public HteWriter(Writer w, Document doc, int pos, int len) {
		super(new CodeWriter(w), (HTMLDocument) doc, pos, len);
	}
}

class CodeWriter extends Writer {

	protected Writer w;

	public CodeWriter(Writer w) {
		this.w = w;
	}

	/**
	 * バッファに保存中かどうか
	 */
	private boolean code;

	/**
	 * コード文字列を保持する為のバッファ
	 */
	private StringBuffer sb = new StringBuffer();

	private char[] c = new char[1];

	/**
	 * <p>コード文字列が分割して呼ばれても、必ず先頭は&amp;#で、末尾が;で来るのが前提</p>
	 */
	public void write(char[] cbuf, int off, int len) throws IOException {
		if (len >= 2 && cbuf[off] == '&' && cbuf[off + 1] == '#') {
			sb.setLength(0); //バッファをクリア
			code = true;
		}
		if (code) {
			sb.append(cbuf, off, len);
			len = sb.length();
			if (sb.charAt(len - 1) != ';') return;

			String str = sb.substring(2, len - 1);
			cbuf = c;
			cbuf[off = 0] = (char) Integer.parseInt(str);
			len = 1;
			code = false;
		}

		w.write(cbuf, off, len);
	}

	public void flush() throws IOException {
		w.flush();
	}

	public void close() throws IOException {
		w.close();
	}
}
