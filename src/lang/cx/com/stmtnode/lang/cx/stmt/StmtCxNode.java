package com.stmtnode.lang.cx.stmt;

import com.stmtnode.lang.cx.CodeCxNode;
import com.stmtnode.primitive.stmt.StmtNativeNode;

public abstract class StmtCxNode extends CodeCxNode {

	public abstract StmtNativeNode toNative();

}
