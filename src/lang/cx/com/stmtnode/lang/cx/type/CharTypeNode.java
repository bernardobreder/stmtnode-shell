package com.stmtnode.lang.cx.type;

import com.stmtnode.lang.cx.CxCodeOutput;
import com.stmtnode.module.CodeOutput;

public class CharTypeNode extends TypeNode {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(CodeOutput output) {
		output.write("char");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CxCodeOutput output) {
		output.write("char");
	}

}
