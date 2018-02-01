package com.stmtnode.primitive.head;

import java.util.List;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.primitive.NativeCodeOutput;

public class StructNativeNode extends HeadNativeNode {

	public final Token name;

	public final List<StructFieldNativeNode> fields;

	/**
	 * @param name
	 * @param fields
	 */
	public StructNativeNode(Token name, List<StructFieldNativeNode> fields) {
		super();
		this.name = name;
		this.fields = fields;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToC(NativeCodeOutput output) {
		output.write("struct ");
		output.write(name);
		output.write(" {");
		output.writeLine();
		output.incTab();
		output.writeTab();
		output.writeLines(fields, e -> e.writeToC(output));
		output.writeLine();
		output.decTab();
		output.write("};");
	}

}
