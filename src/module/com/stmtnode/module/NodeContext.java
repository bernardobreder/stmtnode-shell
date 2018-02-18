package com.stmtnode.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CodeCxNode;
import com.stmtnode.lang.cx.stmt.StmtCxNode;
import com.stmtnode.lang.cx.stmt.VariableCxNode;
import com.stmtnode.lang.cx.type.PointerTypeCxNode;
import com.stmtnode.lang.cx.type.TypeCxNode;
import com.stmtnode.lang.cx.type.UnkownTypeCxNode;
import com.stmtnode.lang.cx.type.VoidTypeCxNode;

public class NodeContext {

	public final List<CodeCxNode> stmts = new LinkedList<>();

	public final LinkedList<LinkedList<StmtCxNode>> blocks = new LinkedList<>();

	public final LinkedList<LinkedList<VariableCxNode>> variables = new LinkedList<>();

	public final Map<String, TypeCxNode> typeMap = new HashMap<>();

	public NodeContext() {
		typeMap.put("malloc", new PointerTypeCxNode(VoidTypeCxNode.GET));
		typeMap.put("free", VoidTypeCxNode.GET);
		typeMap.put("setbuf", VoidTypeCxNode.GET);
	}

	public void pushStmt(CodeCxNode node) {
		stmts.add(node);
	}

	public void popStmt() {
		stmts.remove(stmts.size() - 1);
	}

	public CodeCxNode peekStmt() {
		return stmts.get(stmts.size() - 1);
	}

	public void pushBlock() {
		blocks.addFirst(new LinkedList<>());
		variables.addFirst(new LinkedList<>());
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
		variables.removeFirst();
	}

	public void clearBlock() {
		blocks.getFirst().clear();
	}

	public TypeCxNode findType(Token token) throws LinkException {
		String name = token.word;
		TypeCxNode type = typeMap.get(name);
		if (type != null) {
			return type;
		}
		List<VariableCxNode> variables = findVariable(name);
		if (!variables.isEmpty()) {
			if (variables.size() == 1) {
				return variables.get(0).type;
			} else {
				throw new LinkException(variables.toString());
			}
		}
		return new UnkownTypeCxNode(token);
	}

	public void declareVariable(Token name, TypeCxNode type) {
		variables.peek().addFirst(new VariableCxNode(type, name));
	}

	public List<VariableCxNode> findVariable(String name) {
		int hashCode = name.hashCode();
		LinkedList<VariableCxNode> list = new LinkedList<>();
		for (LinkedList<VariableCxNode> block : variables) {
			for (VariableCxNode node : block) {
				if (node.name.word.hashCode() == hashCode && node.name.word.equals(name)) {
					list.addLast(node);
				}
			}
		}
		return new ArrayList<>(list);
	}

}
