package ru.hh.school.stdlib;

import junit.framework.Assert;
import org.junit.Test;
import ru.hh.school.stdlib.methods.GetMethod;
import ru.hh.school.stdlib.methods.MethodParser;
import ru.hh.school.stdlib.methods.PutMethod;
import ru.hh.school.stdlib.methods.SetSleepMethod;

public class MethodParserTest {
	@Test
	public void getTest() {
		MethodParser parser = new GetMethod();

		Assert.assertTrue(parser.tryParse("GET x"));
		Assert.assertTrue(parser.tryParse("GET _x"));
		Assert.assertTrue(parser.tryParse(" GET   $x  "));
		Assert.assertTrue(parser.tryParse("GET $x123_test"));

		Assert.assertFalse(parser.tryParse("get x"));
		Assert.assertFalse(parser.tryParse("GET x-y"));
		Assert.assertFalse(parser.tryParse("GET 0"));
	}

	@Test
	public void putTest() {
		MethodParser parser = new PutMethod();

		Assert.assertTrue(parser.tryParse("PUT x 11"));
		Assert.assertTrue(parser.tryParse("PUT _x ${16}"));
		Assert.assertTrue(parser.tryParse("PUT x    "));

		Assert.assertFalse(parser.tryParse("put x"));
		Assert.assertFalse(parser.tryParse("PUT x"));
	}

	@Test
	public void setSleepTest() {
		MethodParser parser = new SetSleepMethod();

		Assert.assertTrue(parser.tryParse("SET SLEEP 1000"));
		Assert.assertTrue(parser.tryParse(" SET SLEEP   1000000"));

		Assert.assertFalse(parser.tryParse("SET SLEEP x"));
		Assert.assertFalse(parser.tryParse("SETSLEEP 300"));
		Assert.assertFalse(parser.tryParse("SET SLEEP3000"));
	}
}
