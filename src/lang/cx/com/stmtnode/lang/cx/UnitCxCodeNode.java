package com.stmtnode.lang.cx;

import java.util.List;

import com.stmtnode.module.CodeNode;

public class UnitCxCodeNode extends CodeNode {

	public List<UnitCxCodeNode> nodes;

	public UnitCxCodeNode(List<UnitCxCodeNode> nodes) {
		this.nodes = nodes;
	}

}
