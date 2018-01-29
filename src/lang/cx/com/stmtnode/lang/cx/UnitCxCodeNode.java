package com.stmtnode.lang.cx;

import java.util.List;

public class UnitCxCodeNode extends CxCodeNode {

	public List<UnitCxCodeNode> nodes;

	public UnitCxCodeNode(List<UnitCxCodeNode> nodes) {
		this.nodes = nodes;
	}

}
