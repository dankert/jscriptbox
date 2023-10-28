package de.openrat.jscriptbox.parser;

import de.openrat.jscriptbox.exception.ScriptParserException;

public class Template
{
	public int tagsFound;
	public StringBuffer script;

	public void parseTemplate( String source ) throws ScriptParserException {

		this.script    = new StringBuffer();
		this.tagsFound = 0;

		while( true ) {

			int tagOpen = source.indexOf("<%" );

			if	( tagOpen > 0 ) {
				this.tagsFound++;
				this.addWriteCommand( source.substring(0,tagOpen),true);
				source = source.substring(tagOpen+2);
				int tagClose = source.indexOf("%>" );
				if   ( tagClose == -1 )
					throw new ScriptParserException("Unclosed script tag",0);
				String code = source.substring(0,tagClose);
				if   ( code.charAt(0) == '=' )
					this.addWriteCommand( code.substring(1),true);
				else
					this.script.append( code + "\n" );

				source = source.substring(tagClose+2);
			}
			else{
				this.addWriteCommand(source,true);
				break;
			}
		}
	}

	protected void addWriteCommand( String code, boolean quote ) {

		if   ( quote )
			for ( String line : code.split("\n") )
				this.script.append( "write('"+line.replace("'","\'") + "\');" + "\n");
		else
			this.script.append("write('.code.');"+"\n");
	}
}