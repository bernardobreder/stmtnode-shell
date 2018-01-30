package com.stmtnode.module;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {

	/**
	 * Realiza as ligações do objeto e retorna um novo caso seja necessário
	 *
	 * @param <E>
	 * @param context
	 * @return this
	 * @throws LinkException
	 */
	public abstract <E extends CodeNode> E link(LinkContext context) throws LinkException;

	/**
	 * @param value
	 * @return cast of value
	 */
	@SuppressWarnings("unchecked")
	protected static <E> E cast(Object value) {
		return (E) value;
	}

	/**
	 * @param context
	 * @param list
	 * @return nodes linked
	 * @throws LinkException
	 */
	protected static <E extends Node> List<E> link(LinkContext context, List<E> list) throws LinkException {
		List<E> result = new ArrayList<>(list.size());
		for (int n = 0; n < list.size(); n++) {
			result.add(cast(list.get(n).link(context)));
		}
		return result;
	}

}
