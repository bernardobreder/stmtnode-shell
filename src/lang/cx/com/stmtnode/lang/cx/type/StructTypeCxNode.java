package com.stmtnode.lang.cx.type;

import static com.stmtnode.module.Nodes.cast;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkException;
import com.stmtnode.module.NodeContext;
import com.stmtnode.primitive.type.StructTypeNativeNode;
import com.stmtnode.primitive.type.TypeNativeNode;

public class StructTypeCxNode extends TypeCxNode {

	public final Token name;

	public StructTypeCxNode(Token name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(NodeContext context) throws LinkException {
		return cast(new StructTypeCxNode(name));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		output.write(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TypeNativeNode toNative() {
		return new StructTypeNativeNode(name);
	}

	public String getStartConstructorName() {
		String name = this.name.word;
		if (!name.endsWith("_t")) {
			return name;
		}
		return name.substring(0, name.length() - 2) + "_new";
	}

}
