package com.stmtnode.lang.cx;

import java.util.List;

import com.stmtnode.lang.cx.stmt.StmtCxNode;
import com.stmtnode.module.CodeOutput;

public class CCodeOutput extends CodeOutput {

	public void writeSpaceBeforeCommon() {
	}

	public void writeSpaceBeforeArray() {
	}

	public void writeSpaceAfterArray() {
	}

	public void writeDones(List<StmtCxNode> dones) {
		for (StmtCxNode node : dones) {
			node.writeToC(this);
			writeLine();
			writeTab();
		}
	}

}
