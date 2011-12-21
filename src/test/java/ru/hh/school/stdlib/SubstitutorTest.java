package ru.hh.school.stdlib;

import org.junit.Assert;
import org.junit.Test;
import ru.hh.school.stdlib.storage.KeyValueSubstitutor;
import ru.hh.school.stdlib.storage.Substitutor3000;

public class SubstitutorTest {
	@Test
	public void simpleReplacement() {
		Substitutor3000 substitutor = new KeyValueSubstitutor();
		substitutor.put("k1", "one");
		substitutor.put("k2", "two");
		substitutor.put("keys", "1: ${k1}, 2: ${k2}, that's all");

		Assert.assertEquals("1: one, 2: two, that's all", substitutor.get("keys"));
	}

	@Test
	public void overwriteReplacement() {
		Substitutor3000 substitutor = new KeyValueSubstitutor();
		substitutor.put("k1", "one");
		substitutor.put("k2", "nested-${k1}");

		Assert.assertEquals("nested-one", substitutor.get("k2"));

		substitutor.put("k1", "two");

		Assert.assertEquals("nested-two", substitutor.get("k2"));
	}

	@Test
	public void emptyReplacement() {
		Substitutor3000 substitutor = new KeyValueSubstitutor();
		substitutor.put("k", "bla-${inexistent}-bla");

		Assert.assertEquals("bla--bla", substitutor.get("k"));
	}

	@Test
	 public void nestedReplacement() {
		Substitutor3000 substitutor = new KeyValueSubstitutor();
		substitutor.put("k1", "one");
		substitutor.put("k2", "nested-${k1}");
		substitutor.put("k3", "nested-${k2}");

		Assert.assertEquals("nested-nested-one", substitutor.get("k3"));
	}

	@Test
	public void complexReplacement() {
		Substitutor3000 substitutor = new KeyValueSubstitutor();
		substitutor.put("term", "x");
		substitutor.put("factor", "y + ${term}");
		substitutor.put("expression", "(${factor}) * ${term}");

		Assert.assertEquals("(y + x) * x", substitutor.get("expression"));
	}

	@Test
	public void infiniteReplacement() {
		Substitutor3000 substitutor = new KeyValueSubstitutor();
		substitutor.put("k1", "recursive-${k2}");
		substitutor.put("k2", "${k1}");

		Assert.assertEquals(
			"recursive-" + KeyValueSubstitutor.recursionPlaceholder,
			substitutor.get("k2")
		);
	}
}
