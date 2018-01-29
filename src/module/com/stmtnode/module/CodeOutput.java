package com.stmtnode.module;

import java.util.List;
import java.util.function.Consumer;

public class CodeOutput {

	public void writeSpace() {
		write(" ");
	}

	public void write(String text) {
		// TODO Auto-generated method stub

	}

	public void writeLine() {
		// TODO Auto-generated method stub

	}

	public <E extends CodeNode> void write(List<E> list, String separator, Consumer<E> consumer) {
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

	public <E extends CodeNode> void writeLines(List<E> list, Consumer<E> consumer) {
		if (list.isEmpty()) {
			return;
		}
		consumer.accept(list.get(0));
		for (int i = 1; i < list.size(); i++) {
			writeLine();
			consumer.accept(list.get(i));
		}
	}

}
