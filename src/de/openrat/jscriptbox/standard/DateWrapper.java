
package de.openrat.jscriptbox.standard;

import de.openrat.jscriptbox.context.BaseScriptable;

public class DateWrapper extends BaseScriptable
{
	/**
	 * Date.now()
	 *
	 * milliseconds since 1970.
	 *
	 * @return int
	 */
	public long now() {

		return java.lang.System.currentTimeMillis();
	}


	/**
	 * @param int year year
	 * @param int month month 0-based
	 * @param int day day of month
	 * @param int hour hour
	 * @param int minute minute
	 * @param int second second
	 * @return DateInstance
	 *
	public function __invoke( year = 0,month = 0,day = 0,hour = 0,minute = 0,second = 0 )
	{
		if  ( is_string(year) )
			return new DateInstance( strtotime(year) );

		if   ( is_numeric(year) && year && !month )
			return new DateInstance( floor(year/1000) );

		month++; // month in JS is 0-based, but in PHP 1-based.

		if   ( is_numeric(year) && year )
			return new DateInstance( mktime( hour, minute, second, month, day, year ) );

		return new DateInstance();
	}*/


	/**
	 * Gets the current date object.
	 * @return DateInstance
	 */
	/*
	public function getDate( date = null ) {

		return new DateInstance( date );
	}*/



	public String toString()
	{
		return "Date";
	}

	/*
	public function parse( dateAsString ) {

		return strtotime( dateAsString ) * 1000;
	}*/
}