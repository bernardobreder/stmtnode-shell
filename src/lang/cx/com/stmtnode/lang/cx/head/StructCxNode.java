package com.stmtnode.lang.cx.head;

import static com.stmtnode.module.Nodes.cast;
import static com.stmtnode.module.Nodes.headNodes;
import static com.stmtnode.module.Nodes.linkNodes;
import static com.stmtnode.module.Nodes.nativeNodes;

import java.util.List;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.HeadException;
import com.stmtnode.module.LinkException;
import com.stmtnode.module.NodeContext;
import com.stmtnode.primitive.head.HeadNativeNode;
import com.stmtnode.primitive.head.StructNativeNode;

public class StructCxNode extends HeadCxNode {

	public final Token token;

	public final Token name;

	public final List<StructFieldCxNode> nodes;

	public StructCxNode(Token token, Token name, List<StructFieldCxNode> nodes) {
		this.token = token;
		this.name = name;
		this.nodes = nodes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void head(NodeContext context) throws HeadException {
		headNodes(nodes, context);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(NodeContext context) throws LinkException {
		return cast(new StructCxNode(token, name, linkNodes(nodes, context)));
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
	public HeadNativeNode toNative() {
		return new StructNativeNode(name, nativeNodes(nodes));
	}

}
