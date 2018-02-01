package com.stmtnode.module;

import java.util.ArrayList;
import java.util.List;

import com.stmtnode.lang.compiler.Token;
import com.stmtnode.lang.cx.CodeCxNode;
import com.stmtnode.primitive.NativeNode;

public abstract class Nodes {

	/**
	 * @param <E>
	 * @param value
	 * @return cast of value
	 */
	@SuppressWarnings("unchecked")
	public static <E> E cast(Object value) {
		return (E) value;
	}

	/**
	 * @param list
	 * @param context
	 * @param <E>
	 * @return nodes linked
	 * @throws LinkException
	 */
	public static <E extends CodeNode> List<E> linkNodes(List<E> list, NodeContext context) throws LinkException {
		int size = list.size();
		List<E> result = new ArrayList<>(size);
		for (int n = 0; n < size; n++) {
			result.add(linkNode(list.get(n), context));
		}
		return result;
	}

	/**
	 * @param node
	 * @param context
	 * @param <E>
	 * @return linked
	 * @throws LinkException
	 */
	public static <E extends CodeNode> E linkNode(E node, NodeContext context) throws LinkException {
		return node == null ? null : node.link(context);
	}

	/**
	 * @param <E>
	 * @param <T>
	 * @param list
	 * @return nodes linked
	 */
	public static <E extends CodeCxNode, T extends NativeNode> List<T> nativeNodes(List<E> list) {
		int size = list.size();
		List<T> result = new ArrayList<>(size);
		for (int n = 0; n < size; n++) {
			result.add(nativeNode(list.get(n)));
		}
		return result;
	}

	/**
	 * @param <E>
	 * @param node
	 * @return native node
	 */
	public static <E extends CodeCxNode, T extends NativeNode> T nativeNode(E node) {
		return node == null ? null : cast(node.toNative());
	}

	/**
	 * @param <E>
	 * @param list
	 * @param context
	 * @throws HeadException
	 */
	public static <E extends CodeCxNode> void headNodes(List<E> list, NodeContext context) throws HeadException {
		int size = list.size();
		for (int n = 0; n < size; n++) {
			headNode(list.get(n), context);
		}
	}

	/**
	 * @param <E>
	 * @param node
	 * @param context
	 * @throws HeadException
	 */
	public static <E extends CodeCxNode> void headNode(E node, NodeContext context) throws HeadException {
		node.head(context);
	}

	public static Token joinTokens(List<Token> tokens) {
		Token token = tokens.get(0);
		for (int n = 1; n < tokens.size(); n++) {
			token = token.join(tokens.get(n));
		}
		return token;
	}

}
