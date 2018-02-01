package com.stmtnode.module;

public abstract class Node {

	/**
	 * Realiza a leitura do cabeçalho de cada objeto
	 *
	 * @param context
	 * @throws HeadException
	 */
	public abstract void head(NodeContext context) throws HeadException;

	/**
	 * Realiza as ligações do objeto e retorna um novo caso seja necessário
	 *
	 * @param <E>
	 * @param context
	 * @return this
	 * @throws LinkException
	 */
	public abstract <E extends CodeNode> E link(NodeContext context) throws LinkException;

}
