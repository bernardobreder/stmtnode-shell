package com.stmtnode.module;

import java.util.ArrayList;
import java.util.List;

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
	 * @param <E>
	 * @param context
	 * @param list
	 * @return nodes linked
	 * @throws LinkException
	 */
	public static <E extends CodeNode> List<E> linkNodes(LinkContext context, List<E> list) throws LinkException {
		List<E> result = new ArrayList<>(list.size());
		for (int n = 0; n < list.size(); n++) {
			result.add(list.get(n).link(context));
		}
		return result;
	}

	/**
	 * @param <E>
	 * @param context
	 * @param node
	 * @return
	 * @throws LinkException
	 */
	public static <E extends CodeNode> E linkNode(LinkContext context, E node) throws LinkException {
		return node == null ? null : node.link(context);
	}

}
