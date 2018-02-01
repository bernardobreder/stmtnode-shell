package com.stmtnode.lang.cx.head;

import com.stmtnode.lang.cx.CodeCxNode;
import com.stmtnode.primitive.head.HeadNativeNode;

public abstract class HeadCxNode extends CodeCxNode {

	public abstract HeadNativeNode toNative();

}
