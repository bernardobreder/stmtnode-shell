package com.stmtnode.lang.cx.type;

import static com.stmtnode.module.Nodes.cast;

import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkContext;
import com.stmtnode.module.LinkException;

public class CharTypeNode extends TypeNode {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(LinkContext context) throws LinkException {
		return cast(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write("char");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("char");
	}

}
