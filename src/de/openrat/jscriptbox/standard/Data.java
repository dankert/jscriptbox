
package de.openrat.jscriptbox.standard;

import de.openrat.jscriptbox.context.BaseScriptable;

public class Data extends BaseScriptable
{
	/**
	 * @var mixed|null
	 *
	private value;

	/**
	 * StandardArray constructor.
	 * @param value
	 * *
	 *
	public function __construct(value=null)
	{
		this->value = value;
	}

	public function getData() {

		return array_map(

			function( val ) {
				if   ( is_array(val) )
					return new Data( val );
				else
					return val;
			}
			,this->value);
	}


	public function __toString()
	{
		return '['.implode(',',array_map(

				function( val ) {
					if   ( is_object(val) )
						return (new Data( get_object_vars(val) ))->__toString();
					if   ( is_array(val) )
						return (new Data( val ))->__toString();
					else
						return val;
				}
				,this->value)).']';
	}*/
}