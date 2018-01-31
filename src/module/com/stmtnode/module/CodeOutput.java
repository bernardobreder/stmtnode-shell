package com.stmtnode.module;

import java.util.List;
import java.util.function.Consumer;

import com.stmtnode.lang.compiler.Token;

public class CodeOutput {

	private StringBuilder sb = new StringBuilder();

	private int tab;

	public void writeSpace() {
		write(" ");
	}

	public CodeOutput write(String text) {
		sb.append(text);
		return this;
	}

	public void write(Token token) {
		write(token.word);
	}

	public void writeLine() {
		sb.append('\n');
	}

	public <E> void write(List<E> list, String separator, Consumer<E> consumer) {
		if (list.isEmpty()) {
			return;
		}
		consumer.accept(list.get(0));
		for (int i = 1; i < list.size(); i++) {
			if (separator != null) {
				write(separator);
			}
			consumer.accept(list.get(i));
		}
	}

	public <E> void writeLines(List<E> list, Consumer<E> consumer) {
		if (list.isEmpty()) {
			return;
		}
		consumer.accept(list.get(0));
		for (int i = 1; i < list.size(); i++) {
			writeLine();
			writeTab();
			consumer.accept(list.get(i));
		}
	}

	public void writeTab() {
		for (int n = 0; n < tab; n++) {
			sb.append('\t');
		}
	}

	public void incTab() {
		tab++;
	}

	public void decTab() {
		tab--;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return sb.toString();
	}

}
