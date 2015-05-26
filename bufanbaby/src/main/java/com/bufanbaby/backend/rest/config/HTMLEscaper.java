package com.bufanbaby.backend.rest.config;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;

@SuppressWarnings("serial")
public class HTMLEscaper extends CharacterEscapes
{
	private final int[] asciiEscapes;

	public HTMLEscaper()
	{
		int[] esc = CharacterEscapes.standardAsciiEscapesForJSON();
		esc['<'] = CharacterEscapes.ESCAPE_STANDARD;
		esc['>'] = CharacterEscapes.ESCAPE_STANDARD;
		esc['&'] = CharacterEscapes.ESCAPE_STANDARD;
		esc['\''] = CharacterEscapes.ESCAPE_STANDARD;
		asciiEscapes = esc;
	}

	@Override
	public int[] getEscapeCodesForAscii() {
		return asciiEscapes;
	}

	@Override
	public SerializableString getEscapeSequence(int ch) {
		return null;
	}
}
