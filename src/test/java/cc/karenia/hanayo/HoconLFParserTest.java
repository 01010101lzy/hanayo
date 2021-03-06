package cc.karenia.hanayo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cc.karenia.hanayo.types.HoconParseException;

/*
	I don't know why the file is named like this... 
	It's named by the other team member.

	Anyway, tests work.
	- Rynco
*/
public class HoconLFParserTest {
	static {
		HoconParseException.shouldGatherStacktrace = true;
	}

	@Test
	public void testSkipComments() {
		String str1 = "test #Comments";
		HoconParser parseParse = HoconParser.of(str1);
		int skipptr = parseParse.skipComments(5);
		assertEquals(skipptr, 14);

		String str2 = "test //Comments";
		HoconParser parseParse1 = HoconParser.of(str2);
		skipptr = parseParse1.skipComments(5);
		assertEquals(skipptr, 15);

		String str3 = "#new:Comm}";
		HoconParser parseParse3 = HoconParser.of(str3);
		skipptr = parseParse3.skipComments(0);
		assertEquals(skipptr, 10);
	}

	@Test
	public void testParseUnquotedString() throws HoconParseException {
		String str1 = "test:Comments";
		HoconParser parseParse = HoconParser.of(str1);
		var parseResult = parseParse.parseUnquotedString(0,
				HoconParser.KeyDelimiters);
		assertEquals(parseResult.result, "test");
		// assertEquals(parseResult.success(0),5);
		String str2 = "testnew.Comments";
		HoconParser parseParse2 = HoconParser.of(str2);
		var parseResult2 = parseParse2.parseUnquotedString(0,
				HoconParser.KeyDelimiters);
		assertEquals(parseResult2.result, "testnew");

	}

	@Test
	public void testParseQuotedString() throws HoconParseException {
		String str1 = "\"test_new\":Comments";
		HoconParser parseParse1 = HoconParser.of(str1);
		var parseResult1 = parseParse1.parseUnquotedString(0,
				HoconParser.KeyDelimiters);
		assertEquals(parseResult1.result, "\"test_new\"");

		String str2 = "\"tes\\nt_new\":Comments";
		HoconParser parseParse2 = HoconParser.of(str2);
		var parseResult2 = parseParse2.parseUnquotedString(0,
				HoconParser.KeyDelimiters);
		assertEquals(parseResult2.result, "\"tes\\nt_new\"");

	}

	@Test
	public void testParseMultilineString() throws HoconParseException {
		String str1 = "\"\"\"test a \nmultiline \nstring\n \"\"\"";
		HoconParser parseParse1 = HoconParser.of(str1);
		var parseResult1 = parseParse1.parseMultilineString(0);
		assertEquals(parseResult1.result, "test a \nmultiline \nstring\n ");
	}

	@Test
	public void testParseHoconString() throws HoconParseException {
		String str1 = "test:Comments";
		HoconParser parseHocon1 = HoconParser.of(str1);
		var parseResult1 = parseHocon1.parseHoconString(0, false, false);
		assertEquals(parseResult1.result.value, "test");
	}

}
