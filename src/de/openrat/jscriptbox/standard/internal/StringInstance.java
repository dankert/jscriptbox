


package de.openrat.jscriptbox.standard.internal;


import de.openrat.jscriptbox.context.BaseScriptable;
import de.openrat.jscriptbox.context.ContextPrimitive;

public class StringInstance extends BaseScriptable implements ContextPrimitive
{
	private String value;

	/**
	 * Number constructor.
	 * @param $value
	 */
	public StringInstance(String value)
	{
		/*
		if   ( value instanceof BaseScriptableObject )
			this->value = $value->__toString();

		elseif   ( is_object( value ) )
			this->value = get_class($value);

		elseif   ( is_bool( $value ) )
			this->value = $value ? 'true' : 'false';
		else*/
			this.value = value;
	}

	public String toString()
	{
		return this.value;
	}


	/*
	public function __invoke( $value )
	{
		return new StringInstance( $value );
	}
	*/


/*
	public function split( $splitChar = null ) {
		if   ( $splitChar == '' )
			return str_split( $this->value );
		if   ( $splitChar == null )
			return array( $this->value );

		return explode( $splitChar, $this->value );
	}


	public function trim() {
		return trim( $this->value);
	}
	public function trimEnd() {
		return rtrim( $this->value);

	}
	public function trimStart() {
		return ltrim( $this->value);
	}

	public function valueOf( $val ) {
		return new StringInstance( $val );
	}

	public function charAt( $pos ) {
		return substr( $this->value,$pos,1 );
	}

	public function concat( $str ) {
		return $this->value.$str;
	}

	public function startsWith($str) {
		return substr( $this->value, 0,strlen($str) );
	}

	public function endsWith($str ) {
		return substr( $this->value,strlen($str)*-1,strlen($str)) == $str;
	}

	public function indexOf( $search ) {
		return strpos( $this->value, $search );
	}

	public function lastIndexOf( $search) {
		return strrpos( $this->value, $search );
	}

	public function padStart($length,$pad=null) {
		$this->value = str_pad( $this->value,$length, STR_PAD_LEFT);
	}

	public function padEnd( $length,$pad=null) {
		$this->value = str_pad( $this->value,$length, STR_PAD_RIGHT);
	}

	public function repeat( $count) {
		return str_repeat( $this->value,$count);
	}

	public function replace( $search,$replaceWith ) {
		$c = 1;
		return str_replace( $search,$replaceWith,$this->value,$c );
	}

	public function replaceAll( $search,$replaceWith ) {
		return str_replace( $search,$replaceWith,$this->value );
	}

	public function slice( $begin,$end=null) {
		return array_slice( $this->value,$begin,$end-$begin);
	}

	public function substring( $start,$end) {
		return substr( $this->value, $start,$end-$start+1 );
	}

	public function toLowerCase() {
		return strtolower($this->value);
	}

	public function toUpperCase() {
		return strtoupper($this->value);
	}

//	public function length() {
//		return strlen($this->value);
//	}
*/
}