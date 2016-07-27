package modelMainPage;

import java.util.HashMap;

/**
 * contains data used in whole activity
 * @author Vlad Herescu
 *
 */
public class Core {

	/**
	 * contains data about the books started to be read
	 */
	private static HashMap<String,BookProgress> m_MapBetweenMain_Reader;
	
	/**
	 * initializing the data
	 */
	public Core()
	{
		m_MapBetweenMain_Reader = new HashMap<String,BookProgress>();
	}

	/**
	 * @return the m_MapBetweenMain_Reader
	 */
	public static HashMap<String, BookProgress> getM_MapBetweenMain_Reader() {
		return m_MapBetweenMain_Reader;
	}

	/**
	 * @param m_MapBetweenMain_Reader the m_MapBetweenMain_Reader to set
	 */
	public static void setM_MapBetweenMain_Reader(
			HashMap<String, BookProgress> m_MapBetweenMain_Reader) {
		Core.m_MapBetweenMain_Reader = m_MapBetweenMain_Reader;
	}


	
}
