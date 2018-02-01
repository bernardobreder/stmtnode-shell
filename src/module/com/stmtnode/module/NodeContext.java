package com.stmtnode.module;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.stmtnode.lang.cx.stmt.StmtCxNode;

public class NodeContext {

	public final LinkedList<LinkedList<StmtCxNode>> blocks = new LinkedList<>();

	public void pushBlock() {
		blocks.addFirst(new LinkedList<>());
	}

	public void addBlock(StmtCxNode node) {
		blocks.peek().addFirst(node);
	}

	public List<StmtCxNode> peekBlock() {
		return new ArrayList<>(blocks.getFirst());
	}

	public List<StmtCxNode> peekFunction() {
		LinkedList<StmtCxNode> list = new LinkedList<>();
		for (LinkedList<StmtCxNode> block : blocks) {
			for (StmtCxNode node : block) {
				list.addLast(node);
			}
		}
		return new ArrayList<>(list);
	}

	public void popBlock() {
		blocks.removeFirst();
	}

}
