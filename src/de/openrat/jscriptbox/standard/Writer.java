

package de.openrat.jscriptbox.standard;

import de.openrat.jscriptbox.context.Invoke;
import de.openrat.jscriptbox.context.ScriptableFunction;

public class Writer implements ScriptableFunction
{
	public StringBuffer buffer = new StringBuffer();

	/**
	 * Write something to an output queue.
	 *
	 * @param text
	 */
	@Invoke
	public void write( Object text )
	{
		if   ( text instanceof CharSequence )

			this.buffer.append(text);
		else
			this.buffer.append(text.toString());
	}

	public String getBuffer() {
		return buffer.toString();
	}
}