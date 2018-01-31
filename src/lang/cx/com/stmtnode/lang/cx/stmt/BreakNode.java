package com.stmtnode.lang.cx.stmt;

import static com.stmtnode.module.Nodes.cast;

import java.util.ArrayList;
import java.util.List;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkContext;
import com.stmtnode.module.LinkException;
import com.stmtnode.primitive.NativeCodeOutput;

public class BreakNode extends StmtCxNode {

	public final Token token;

	public final List<StmtCxNode> dones;

	public BreakNode(Token token) {
		this.token = token;
		this.dones = new ArrayList<>();
	}

	public BreakNode(Token token, List<StmtCxNode> dones) {
		this.token = token;
		this.dones = dones;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(LinkContext context) throws LinkException {
		List<StmtCxNode> dones = context.peekBlock();
		return cast(new BreakNode(token, dones));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write("break");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.writeDones(dones);
		output.write("break;");
	}

}
