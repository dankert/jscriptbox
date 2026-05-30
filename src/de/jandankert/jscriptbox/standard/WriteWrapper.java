

package de.jandankert.jscriptbox.standard;

import de.jandankert.jscriptbox.context.ScriptableFunction;

public class WriteWrapper implements ScriptableFunction
{
	/**
	 * Writer.
	 */
	private Writer writer;

	/**
	 * Prefix.
	 */
	private String prefix;

	/**
	 * WriteWrapper constructor.
	 * @param writer the Writer
	 * @param prefix Prefix
	 */
	public WriteWrapper( Writer writer,String prefix )
	{
		this.writer = writer;
		this.prefix = prefix;
	}

	/**
	 * Write something to an output queue.
	 *
	 * @param text
	 */
	public void invoke( String text )
	{
		this.writer.write(text  );
		this.writer.write(this.prefix );
	}
}