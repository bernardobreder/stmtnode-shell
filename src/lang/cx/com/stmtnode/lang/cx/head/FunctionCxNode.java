package com.stmtnode.lang.cx.head;

import static com.stmtnode.module.Nodes.cast;
import static com.stmtnode.module.Nodes.headNode;
import static com.stmtnode.module.Nodes.headNodes;
import static com.stmtnode.module.Nodes.linkNode;
import static com.stmtnode.module.Nodes.linkNodes;
import static com.stmtnode.module.Nodes.nativeNode;
import static com.stmtnode.module.Nodes.nativeNodes;

import java.util.List;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.stmt.BlockCxNode;
import com.stmtnode.lang.cx.type.TypeCxNode;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.HeadException;
import com.stmtnode.module.LinkException;
import com.stmtnode.module.NodeContext;
import com.stmtnode.primitive.head.FunctionNativeNode;
import com.stmtnode.primitive.head.HeadNativeNode;

public class FunctionCxNode extends HeadCxNode {

	public final Token token;

	public final TypeCxNode type;

	public final Token name;

	public final List<ArgumentDeclareCxNode> arguments;

	public final BlockCxNode block;

	public FunctionCxNode(Token token, TypeCxNode type, Token name, List<ArgumentDeclareCxNode> arguments, BlockCxNode block) {
		this.token = token;
		this.type = type;
		this.name = name;
		this.arguments = arguments;
		this.block = block;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void head(NodeContext context) throws HeadException {
		headNode(type, context);
		headNodes(arguments, context);
		headNode(block, context);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(NodeContext context) throws LinkException {
		context.pushBlock();
		try {
			return cast(new FunctionCxNode(token, linkNode(type, context), name, linkNodes(arguments, context), linkNode(block, context)));
		} finally {
			context.popBlock();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write("func");
		output.writeSpace();
		type.writeToSource(output);
		output.writeSpace();
		output.write(name);
		output.writeSpace();
		output.write("(");
		output.write(arguments, ", ", e -> e.writeToSource(output));
		output.write(")");
		output.writeSpace();
		block.writeToSource(output);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HeadNativeNode toNative() {
		return new FunctionNativeNode(name, nativeNode(type), nativeNodes(arguments), nativeNode(block));
	}

}
