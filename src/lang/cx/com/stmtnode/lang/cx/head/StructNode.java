package com.stmtnode.lang.cx.head;

import java.util.List;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CCodeOutput;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkContext;
import com.stmtnode.module.LinkException;

public class StructNode extends HeadNode {

	public final Token token;

	public final Token name;

	public final List<StructFieldNode> nodes;

	public StructNode(Token token, Token name, List<StructFieldNode> nodes) {
		this.token = token;
		this.name = name;
		this.nodes = nodes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(LinkContext context) throws LinkException {
		return cast(new StructNode(token, name, link(context, nodes)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write("struct ");
		output.write(name);
		output.write(" {");
		output.writeLine();
		output.incTab();
		output.writeTab();
		output.writeLines(nodes, e -> e.writeToSource(output));
		output.writeLine();
		output.decTab();
		output.write("}");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(CCodeOutput output) {
		output.write("struct ");
		output.write(name);
		output.write(" {");
		output.writeLine();
		output.incTab();
		output.writeTab();
		output.writeLines(nodes, e -> e.writeToC(output));
		output.writeLine();
		output.decTab();
		output.write("};");
	}

}
