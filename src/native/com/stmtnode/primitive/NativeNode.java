package com.stmtnode.primitive;

import static com.stmtnode.module.Nodes.cast;

import com.stmtnode.module.CodeNode;
import com.stmtnode.module.LinkContext;
import com.stmtnode.module.LinkException;

/**
 * Nó nativo
 *
 * @author Tecgraf/PUC-Rio
 */
public abstract class NativeNode extends CodeNode {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends CodeNode> E link(LinkContext context) throws LinkException {
		return cast(this);
	}

	/**
	 * Realiza a escrita do objeto que o representa
	 * 
	 * @param output
	 */
	public abstract void writeToC(NativeCodeOutput output);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		NativeCodeOutput output = new NativeCodeOutput();
		writeToC(output);
		return output.toString();
	}

}
