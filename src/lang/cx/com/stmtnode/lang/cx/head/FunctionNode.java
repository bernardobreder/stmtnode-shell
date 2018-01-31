package com.stmtnode.lang.cx.head;

import static com.stmtnode.module.Nodes.cast;
import static com.stmtnode.module.Nodes.linkNode;
import static com.stmtnode.module.Nodes.linkNodes;

import java.util.List;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.stmt.BlockNode;
import com.stmtnode.lang.cx.type.TypeCxNode;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkContext;
import com.stmtnode.module.LinkException;
import com.stmtnode.primitive.NativeCodeOutput;

public class FunctionNode extends HeadCxNode {

	public final Token token;

	public final TypeCxNode type;

	public final Token name;

	public final List<ArgumentDeclareCxNode> arguments;

	public final BlockNode block;

	public FunctionNode(Token token, TypeCxNode type, Token name, List<ArgumentDeclareCxNode> arguments, BlockNode block) {
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
	public <E extends CodeNode> E link(LinkContext context) throws LinkException {
		context.pushBlock();
		try {
			return cast(new FunctionNode(token, linkNode(context, type), name, linkNodes(context, arguments), block.link(context)));
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
	public void writeToC(NativeCodeOutput output) {
		type.writeToC(output);
		output.writeSpace();
		output.write(name);
		output.writeSpace();
		output.write("(");
		output.write(arguments, ", ", e -> e.writeToC(output));
		output.write(")");
		output.writeSpace();
		block.writeToC(output);
	}

}
