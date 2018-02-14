package com.stmtnode.module;

import java.nio.file.Path;

public class SourceData {

	public final Path path;

	public final String content;

	public CodeNode node;

	/**
	 * @param path
	 * @param content
	 * @param node
	 */
	public SourceData(Path path, String content, CodeNode node) {
		super();
		this.path = path;
		this.content = content;
		this.node = node;
	}

}
