package com.stmtnode.lang.cx.value.unary;

import static com.stmtnode.module.Nodes.cast;
import static com.stmtnode.module.Nodes.linkNode;
import static com.stmtnode.module.Nodes.linkNodes;
import static com.stmtnode.module.Nodes.nativeNodes;

import java.util.List;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.SourceCodeOutput;
import com.stmtnode.lang.cx.type.TypeCxNode;
import com.stmtnode.lang.cx.value.ValueCxNode;
import com.stmtnode.module.CodeNode;
import com.stmtnode.module.HeadException;
import com.stmtnode.module.LinkException;
import com.stmtnode.module.NodeContext;
import com.stmtnode.primitive.value.ValueNativeNode;
import com.stmtnode.primitive.value.unary.CallNativeNode;

public class CallCxNode extends UnaryCxNode {

	public final List<ValueCxNode> values;

	public CallCxNode(Token token, ValueCxNode left, List<ValueCxNode> values) {
		this(token, null, left, values);
	}

	public CallCxNode(Token token, TypeCxNode type, ValueCxNode left, List<ValueCxNode> values) {
		super(token, type, left);
		this.values = values;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void head(NodeContext context) throws HeadException {
		super.head(context);
		for (ValueCxNode argument : values) {
			argument.head(context);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(NodeContext context) throws LinkException {
		ValueCxNode left = linkNode(this.left, context);
		List<ValueCxNode> values = linkNodes(this.values, context);
		TypeCxNode type = null;
		return cast(new CallCxNode(token, type, left, values));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeToSource(SourceCodeOutput output) {
		left.writeToSource(output);
		output.write("(");
		output.write(values, ", ", e -> e.writeToSource(output));
		output.write(")");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ValueNativeNode toNative() {
		return new CallNativeNode(left.toNative(), nativeNodes(values));
	}

}
