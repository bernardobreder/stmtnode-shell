package com.stmtnode.module;

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

}
