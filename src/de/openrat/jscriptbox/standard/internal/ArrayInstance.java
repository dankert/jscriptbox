
package de.openrat.jscriptbox.standard.internal;

import de.openrat.jscriptbox.context.BaseScriptable;

import java.util.ArrayList;
import java.util.List;

public class ArrayInstance extends BaseScriptable
{
	private List<Object> value;

	/**
	 * StandardArray constructor.
	 * @param value
	 */
	public ArrayInstance(List value)
	{
		this.value = value;
	}


	public String toString()
	{
		return "[:" + value.size() + "]";
	}

	/**
	 * @return mixed
	 */
	protected List getValue()
	{
		return this.value;
	}

	/**
	 * @return array|null
	 */
	public List getInternalValue()
	{
		return this.value;
	}

	public ArrayInstance concat( ArrayInstance concat )
	{
		List newList = new ArrayList();
		newList.addAll( this.value );
		newList.addAll(concat.getInternalValue() );
		return new ArrayInstance( newList );
	}

	/*
	public function fill( value,start,count)
	{
		return array_fill( value,start,$count );
	}
	public function forEach( $func )
	{
		return array_walk( $this->value, $func );
	}
	public function includes( $search )
	{
		return array_search( $search, $this->value ) !== false;
	}
	public function indexOf( $search )
	{
		return array_search( $search, $this->value );
	}
	public function lastIndexOf( $search )
	{
		$found = array_keys( $this->value,$search );
		return end( $found );
	}
	public function isArray( $val )
	{
		return is_array( $val );
	}
	public function join( $split = ',' )
	{
			return implode( $split,$this->value );
	}
	public function keys()
	{
		return array_keys($this->value);
	}
	public function values() {
		return array_values($this->value);
	}
	public function pop()
	{
		return array_pop( $this->value );
	}
	public function push( $value )
	{
		return array_push( $this->value,$value );
	}
	public function reverse()
	{
		return array_reverse($this->value);
	}
	public function shift()
	{
		return array_shift( $this->value );
	}
	public function unshift($value)
	{
		return array_unshift( $this->value,$value );
	}
	public function slice($from,$end = null )
	{
		if   ( $end )
			return array_slice( $this->value,$from,$end-$from);
		else
			return array_slice( $this->value,$from);
	}
	public function sort()
	{
		return asort( $this->value );
	}
	public function get( $key )
	{
		return @$this->value[ $key ];
	}
*/
}