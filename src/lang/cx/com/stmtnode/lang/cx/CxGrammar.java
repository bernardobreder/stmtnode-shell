package com.stmtnode.lang.cx;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.stmtnode.lang.compiler.Grammar;
import com.stmtnode.lang.compiler.Token;

public class CxGrammar extends Grammar {

	public CxGrammar(Token[] tokens) {
		super(tokens);
	}

	public UnitCxCodeNode parseUnit() throws ParseException {
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

	protected UnitCxCodeNode parsePrecompiler() throws ParseException {
		if (is("include")) {
			return parsePreprocessorInclude();
		} else if (is("define")) {
			return parsePreprocessorDefine();
		} else if (is("typedef")) {
			return parsePreprocessorTypedef();
		} else {
			throw error("expected pre-processor");
		}
	}

	protected UnitCxCodeNode parsePreprocessorInclude() {
		// TODO Auto-generated method stub
		return null;
	}

	protected UnitCxCodeNode parsePreprocessorDefine() {
		// TODO Auto-generated method stub
		return null;
	}

	protected UnitCxCodeNode parsePreprocessorTypedef() {
		// TODO Auto-generated method stub
		return null;
	}

	protected UnitCxCodeNode parseUnitItem() throws SyntaxException {
		if (is("func")) {
			return parseFunction();
		} else if (is("let")) {
			return parseDeclare();
		} else {
			throw error("expected unit item");
		}
	}

	private UnitCxCodeNode parseDeclare() {
		// TODO Auto-generated method stub
		return null;
	}

	private UnitCxCodeNode parseFunction() throws SyntaxException {
		Token token = read("func", "expected func keyword");
		parseType();
	}

	private void parseType() {
		if (can("int")) {

		}
	}

}
