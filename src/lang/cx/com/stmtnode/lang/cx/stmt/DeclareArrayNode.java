package com.stmtnode.lang.cx.stmt;

import static com.stmtnode.module.Nodes.cast;
import static com.stmtnode.module.Nodes.linkNode;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.type.TypeCxNode;
import com.stmtnode.lang.cx.value.ValueCxNode;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkContext;
import com.stmtnode.module.LinkException;
import com.stmtnode.primitive.NativeCodeOutput;

public class DeclareArrayNode extends StmtCxNode {

	public final Token token;

	public final TypeCxNode type;

	public final Token name;

	public final ValueCxNode count;

	public DeclareArrayNode(Token token, TypeCxNode type, Token name, ValueCxNode count) {
		this.token = token;
		this.type = type;
		this.name = name;
		this.count = count;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(LinkContext context) throws LinkException {
		return cast(new DeclareArrayNode(token, linkNode(context, type), name, linkNode(context, count)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write("let local ");
		type.writeToSource(output);
		output.writeSpace();
		output.write(name);
		output.write("[");
		count.writeToSource(output);
		output.write("]");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		type.writeToC(output);
		output.writeSpace();
		output.write(name);
		output.write("[");
		count.writeToC(output);
		output.write("];");
	}

}
