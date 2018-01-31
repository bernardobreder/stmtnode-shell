package com.stmtnode.lang.cx.head;

import static com.stmtnode.module.Nodes.cast;
import static com.stmtnode.module.Nodes.linkNode;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.type.TypeCxNode;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkContext;
import com.stmtnode.module.LinkException;
import com.stmtnode.primitive.NativeCodeOutput;

public class ArgumentDeclareCxNode extends HeadCxNode {

	public final Token name;

	public final TypeCxNode type;

	public ArgumentDeclareCxNode(Token name, TypeCxNode type) {
		this.name = name;
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(LinkContext context) throws LinkException {
		return cast(new ArgumentDeclareCxNode(name, linkNode(context, type)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		type.writeToSource(output);
		output.writeSpace();
		output.write(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		type.writeToC(output);
		output.writeSpace();
		output.write(name);
	}

}
