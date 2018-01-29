package com.stmtnode.lang.cx;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.stmtnode.lang.compiler.Grammar;
import com.stmtnode.lang.compiler.Token;

public class CXGrammar extends Grammar {

	public CXGrammar(Token[] tokens) {
		super(tokens);
	}

	public UnitCxCodeNode parseUnit() {
		List<UnitCxCodeNode> nodes = new ArrayList<>();
		while (!eof()) {
			if (can('#')) {
				nodes.add(parsePrecompiler());
			} else {
				nodes.add(parseUnitItem());
			}
		}
		return new UnitCxCodeNode(nodes);
	}

	private UnitCxCodeNode parsePrecompiler() throws ParseException {
		if (is("include")) {

		} else if (is("define")) {

		} else if (is("typedef")) {

		} else {
			throw error("expected pre-processor");
		}
	}

	private UnitCxCodeNode parseUnitItem() {
		// TODO Auto-generated method stub
		return null;
	}

}
