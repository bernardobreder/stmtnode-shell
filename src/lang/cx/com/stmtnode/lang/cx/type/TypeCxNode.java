package com.stmtnode.lang.cx.type;

import com.stmtnode.lang.cx.CodeCxNode;
import com.stmtnode.module.HeadException;
import com.stmtnode.module.NodeContext;
import com.stmtnode.primitive.type.TypeNativeNode;

public abstract class TypeCxNode extends CodeCxNode {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void head(NodeContext context) throws HeadException {
	}

	public abstract TypeNativeNode toNative();

}
