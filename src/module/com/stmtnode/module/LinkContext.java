package com.stmtnode.module;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.stmtnode.lang.cx.stmt.StmtNode;

public class LinkContext {

	public final LinkedList<LinkedList<StmtNode>> blocks = new LinkedList<>();

	public void pushBlock() {
		blocks.addFirst(new LinkedList<>());
	}

	public void addBlock(StmtNode node) {
		blocks.peek().addFirst(node);
	}

	public List<StmtNode> peekBlock() {
		return new ArrayList<>(blocks.getFirst());
	}

	public List<StmtNode> peekFunction() {
		LinkedList<StmtNode> list = new LinkedList<>();
		for (LinkedList<StmtNode> block : blocks) {
			for (StmtNode node : block) {
				list.addLast(node);
			}
		}
		return new ArrayList<>(list);
	}

	public void popBlock() {
		blocks.removeFirst();
	}

}
