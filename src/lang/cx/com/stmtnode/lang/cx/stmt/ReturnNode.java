package com.stmtnode.lang.cx.stmt;

import static com.stmtnode.module.Nodes.cast;
import static com.stmtnode.module.Nodes.linkNode;

import java.util.ArrayList;
import java.util.List;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.value.ValueCxNode;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkContext;
import com.stmtnode.module.LinkException;

public class ReturnNode extends StmtCxNode {

	public final Token token;

	public final ValueCxNode value;

	public final List<StmtCxNode> dones;

	public ReturnNode(Token token, ValueCxNode value) {
		this.token = token;
		this.value = value;
		this.dones = new ArrayList<>();
	}

	public ReturnNode(Token token, ValueCxNode value, List<StmtCxNode> dones) {
		this.token = token;
		this.value = value;
		this.dones = dones;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(LinkContext context) throws LinkException {
		List<StmtCxNode> dones = context.peekFunction();
		return cast(new ReturnNode(token, linkNode(context, value), dones));
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
