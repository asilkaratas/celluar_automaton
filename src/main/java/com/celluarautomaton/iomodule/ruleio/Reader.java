package com.celluarautomaton.iomodule.ruleio;

import java.io.BufferedReader;
import java.io.IOException;

public interface Reader<T> {
	T read(BufferedReader reader) throws IOException;
}
