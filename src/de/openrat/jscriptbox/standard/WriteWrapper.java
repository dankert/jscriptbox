

package de.openrat.jscriptbox.standard;

import de.openrat.jscriptbox.context.ScriptableFunction;

public class WriteWrapper implements ScriptableFunction
{
	/**
	 * @var Writer
	 */
	private Writer writer;
	private String prefix;

	/**
	 * WriteWrapper constructor.
	 * @param Writer writer
	 * @param string prefix Prefix
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