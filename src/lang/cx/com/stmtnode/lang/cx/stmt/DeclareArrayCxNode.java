package com.stmtnode.lang.cx.stmt;

import static com.stmtnode.module.Nodes.cast;
import static com.stmtnode.module.Nodes.linkNode;
import static com.stmtnode.module.Nodes.nativeNode;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.type.TypeCxNode;
import com.stmtnode.lang.cx.value.ValueCxNode;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.HeadException;
import com.stmtnode.module.LinkException;
import com.stmtnode.module.NodeContext;
import com.stmtnode.primitive.stmt.DeclareValueNativeNode;
import com.stmtnode.primitive.stmt.StmtNativeNode;

public class DeclareArrayCxNode extends StmtCxNode {

	public final Token token;

	public final TypeCxNode type;

	public final Token name;

	public final ValueCxNode count;

	public DeclareArrayCxNode(Token token, TypeCxNode type, Token name, ValueCxNode count) {
		this.token = token;
		this.type = type;
		this.name = name;
		this.count = count;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void head(NodeContext context) throws HeadException {
		type.head(context);
		count.head(context);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(NodeContext context) throws LinkException {
		return cast(new DeclareArrayCxNode(token, linkNode(type, context), name, linkNode(count, context)));
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
	public StmtNativeNode toNative() {
		return new DeclareValueNativeNode(name, nativeNode(type), nativeNode(count));
	}

}
