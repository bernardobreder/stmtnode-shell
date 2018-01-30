package com.stmtnode.lang.cx;

import java.util.List;

import com.stmtnode.lang.cx.stmt.StmtNode;
import com.stmtnode.module.CodeOutput;

public class CCodeOutput extends CodeOutput {

	public void writeDones(List<StmtNode> dones) {
		for (StmtNode node : dones) {
			node.writeToC(this);
			writeLine();
			writeTab();
		}
	}

}
