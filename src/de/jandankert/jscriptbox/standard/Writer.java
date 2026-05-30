

package de.jandankert.jscriptbox.standard;

import de.jandankert.jscriptbox.context.Invoke;
import de.jandankert.jscriptbox.context.ScriptableFunction;

public class Writer implements ScriptableFunction
{
	private StringBuffer buffer;

	/**
	 * Write something to an output queue.
	 *
	 * @param text
	 */
	@Invoke
	public void write( Object text )
	{
		if   ( this.buffer == null )
			buffer = new StringBuffer();

		if   ( text instanceof CharSequence )
			this.buffer.append(text);
		else
			this.buffer.append(text.toString());
	}

	public String getBuffer() {
		return buffer == null ? null : buffer.toString();
	}

	public void clear() {
		this.buffer = null;
	}
}