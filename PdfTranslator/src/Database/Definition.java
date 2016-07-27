package Database;

/**
 * contains a single definition of a word referenced by its id
 * @author Vlad Herescu
 *
 */
public class Definition{
	
	/**
	 * the id of the definition;
	 */
	Integer m_id;
	
	/**
	 * the value of the definition
	 */
	String m_value;
	
	/**
	 * the id of the word associated
	 */
	int m_idWord;
	
	/**
	 * if its a verb, noun, etc
	 */
	String m_partSpeech;
	
	/**
	 * @param _id
	 * @param _value
	 * @param _idWord
	 */
	public Definition(Integer _id, String _value,String _partSpeech, Integer _idWord) {
		m_id = _id;
		m_value = _value;
		m_idWord = _idWord;
		m_partSpeech = _partSpeech;
	}
	
	public Definition(String _value,String _partSpeech, Integer _idWord)
	{
		m_value = _value;
		m_idWord = _idWord;
		m_partSpeech = _partSpeech;
	}
	
	public Definition(String _value, String _partSpeech)
	{
		m_value = _value;
		m_partSpeech = _partSpeech;
	}
	
	

	/**
	 * @return the m_id
	 */
	public int getM_id() {
		return m_id;
	}

	/**
	 * @param m_id the m_id to set
	 */
	public void setM_id(int m_id) {
		this.m_id = m_id;
	}

	/**
	 * @return the m_value
	 */
	public String getM_value() {
		return m_value;
	}

	/**
	 * @param m_value the m_value to set
	 */
	public void setM_value(String m_value) {
		this.m_value = m_value;
	}

	/**
	 * @return the m_idWord
	 */
	public int getM_idWord() {
		return m_idWord;
	}

	/**
	 * @param m_idWord the m_idWord to set
	 */
	public void setM_idWord(int m_idWord) {
		this.m_idWord = m_idWord;
	}

	/**
	 * @return the m_partSpeech
	 */
	public String getM_partSpeech() {
		return m_partSpeech;
	}

	/**
	 * @param m_partSpeech the m_partSpeech to set
	 */
	public void setM_partSpeech(String m_partSpeech) {
		this.m_partSpeech = m_partSpeech;
	}
	

}
