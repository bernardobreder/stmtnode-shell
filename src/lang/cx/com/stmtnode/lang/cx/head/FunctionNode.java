package com.stmtnode.lang.cx.head;

import java.util.List;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.stmt.BlockNode;
import com.stmtnode.lang.cx.type.TypeNode;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkContext;
import com.stmtnode.module.LinkException;

public class FunctionNode extends HeadNode {

	public final Token token;

	public final TypeNode type;

	public final Token name;

	public final List<ArgumentDeclareNode> arguments;

	public final BlockNode block;

	public FunctionNode(Token token, TypeNode type, Token name, List<ArgumentDeclareNode> arguments, BlockNode block) {
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
		return cast(new FunctionNode(token, type.link(context), name, link(context, arguments), block.link(context)));
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
	public void writeToC(CCodeOutput output) {
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
