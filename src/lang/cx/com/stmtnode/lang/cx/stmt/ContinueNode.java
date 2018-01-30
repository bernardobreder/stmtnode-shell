package com.stmtnode.lang.cx.stmt;

import java.util.ArrayList;
import java.util.List;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkContext;
import com.stmtnode.module.LinkException;

public class ContinueNode extends StmtNode {

	public final Token token;

	public final List<StmtNode> dones;

	public ContinueNode(Token token) {
		this.token = token;
		this.dones = new ArrayList<>();
	}

	public ContinueNode(Token token, List<StmtNode> dones) {
		this.token = token;
		this.dones = dones;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(LinkContext context) throws LinkException {
		List<StmtNode> dones = context.peekBlock();
		return cast(new ContinueNode(token, dones));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write("continue");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.writeDones(dones);
		output.write("continue;");
	}

}
