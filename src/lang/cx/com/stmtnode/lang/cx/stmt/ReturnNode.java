package com.stmtnode.lang.cx.stmt;

import java.util.ArrayList;
import java.util.List;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.value.ValueNode;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkContext;
import com.stmtnode.module.LinkException;

public class ReturnNode extends StmtNode {

	public final Token token;

	public final ValueNode value;

	public final List<StmtNode> dones;

	public ReturnNode(Token token, ValueNode value) {
		this.token = token;
		this.value = value;
		this.dones = new ArrayList<>();
	}

	public ReturnNode(Token token, ValueNode value, List<StmtNode> dones) {
		this.token = token;
		this.value = value;
		this.dones = dones;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(LinkContext context) throws LinkException {
		List<StmtNode> dones = context.peekFunction();
		return cast(new ReturnNode(token, link(context, value), dones));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write("return ");
		value.writeToSource(output);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.writeDones(dones);
		output.write("return ");
		value.writeToC(output);
		output.write(";");
	}

}
