package ru.hh.school.stdlib;

import junit.framework.Assert;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

public class ServerImplementationTest extends BaseFunctionalTest {
	@Test
	public void nonexistentGet() throws IOException {
		Socket s = connect();
		Writer out = new PrintWriter(s.getOutputStream());
		out.append("GET k1\n").flush();
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

		Assert.assertEquals("VALUE", in.readLine());
		Assert.assertEquals("", in.readLine());
		s.close();
	}

	@Test
	public void templateGet() throws IOException {
		// PUT k1 value1
		Socket putk1Socket = connect();
		Writer putk1Writer = new PrintWriter(putk1Socket.getOutputStream());
		putk1Writer.append("PUT k1 value1\n").flush();
		BufferedReader putk1Reader = new BufferedReader(new InputStreamReader(putk1Socket.getInputStream()));

		Assert.assertEquals("OK", putk1Reader.readLine());
		putk1Socket.close();

		// GET k1
		Socket getk1Socket = connect();
		Writer getk1Writer = new PrintWriter(getk1Socket.getOutputStream());
		getk1Writer.append("GET k1\n").flush();
		BufferedReader getk1Reader = new BufferedReader(new InputStreamReader(getk1Socket.getInputStream()));

		Assert.assertEquals("VALUE", getk1Reader.readLine());
		Assert.assertEquals("value1", getk1Reader.readLine());
		getk1Socket.close();

		// PUT k2 tpl-${k1}
		Socket putk2Socket = connect();
		Writer putk2Writer = new PrintWriter(putk2Socket.getOutputStream());
		putk2Writer.append("PUT k2 tpl-${k1}\n").flush();
		BufferedReader putk2Reader = new BufferedReader(new InputStreamReader(putk2Socket.getInputStream()));

		Assert.assertEquals("OK", putk2Reader.readLine());
		putk2Socket.close();

		// GET k2
		Socket getk2Socket = connect();
		Writer getk2Writer = new PrintWriter(getk2Socket.getOutputStream());
		getk2Writer.append("GET k2\n").flush();
		BufferedReader getk2Reader = new BufferedReader(new InputStreamReader(getk2Socket.getInputStream()));

		Assert.assertEquals("VALUE", getk2Reader.readLine());
		Assert.assertEquals("tpl-value1", getk2Reader.readLine());
		getk2Socket.close();
	}

	@Test
	public void whitespacesGet() throws IOException {
		// PUT k1 value1
		Socket putk1Socket = connect();
		Writer putk1Writer = new PrintWriter(putk1Socket.getOutputStream());
		putk1Writer.append("PUT k1    value1   \n").flush();
		BufferedReader putk1Reader = new BufferedReader(new InputStreamReader(putk1Socket.getInputStream()));

		Assert.assertEquals("OK", putk1Reader.readLine());
		putk1Socket.close();

		// GET k1
		Socket getk1Socket = connect();
		Writer getk1Writer = new PrintWriter(getk1Socket.getOutputStream());
		getk1Writer.append("GET k1\n").flush();
		BufferedReader getk1Reader = new BufferedReader(new InputStreamReader(getk1Socket.getInputStream()));

		Assert.assertEquals("VALUE", getk1Reader.readLine());
		Assert.assertEquals("   value1   ", getk1Reader.readLine());
		getk1Socket.close();
	}

	@Test
	public void invalidGet() throws IOException {
		Socket s = connect();
		Writer out = new PrintWriter(s.getOutputStream());
		out.append("GET some-invalid-key\n").flush();
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

		Assert.assertEquals("ERROR", in.readLine());
		s.close();
	}

	@Test
	public void setSleep() throws IOException {
		Socket s = connect();
		Writer out = new PrintWriter(s.getOutputStream());
		out.append("SET SLEEP 1000\n").flush();
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

		Assert.assertEquals("OK", in.readLine());
		s.close();
	}
}
