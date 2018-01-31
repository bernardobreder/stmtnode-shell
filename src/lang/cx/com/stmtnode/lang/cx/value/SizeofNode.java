package com.stmtnode.lang.cx.value;

import static com.stmtnode.module.Nodes.cast;
import static com.stmtnode.module.Nodes.linkNode;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.type.TypeNode;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkContext;
import com.stmtnode.module.LinkException;

public class SizeofNode extends ValueNode {

	public final TypeNode type;

	public SizeofNode(Token token, TypeNode type) {
		super(token);
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(LinkContext context) throws LinkException {
		return cast(new SizeofNode(token, linkNode(context, type)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write("sizeof(");
		type.writeToSource(output);
		output.write(")");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("sizeof(");
		type.writeToC(output);
		output.write(")");
	}

}
