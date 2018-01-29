package com.stmtnode.module;

public abstract class Node {

	/**
	 * Realiza as ligações do objeto e retorna um novo caso seja necessário
	 * 
	 * @param <E>
	 * @param context
	 * @return
	 * @throws LinkException
	 */
	public <E extends CodeNode> E link(LinkContext context) throws LinkException {
		return cast(this);
	}

	@SuppressWarnings("unchecked")
	protected static <E> E cast(Object value) {
		return (E) value;
	}

}
