package Database;

/**
 * contains a single definition of a word referenced by its id
 * @author Vlad Herescu
 *
 */
public class Example {

	/**
	 * the id of the definition;
	 */
	int m_id;
	
	/**
	 * the value of the definition
	 */
	String m_value;
	
	/**
	 * the id of the word associated
	 */
	Integer m_idWord;
	
	/**
	 * @param _id
	 * @param _value
	 * @param _idWord
	 */
	public Example(Integer _id, String _value, int _idWord) {
		m_id = _id;
		m_value = _value;
		m_idWord = _idWord;
	}
	
	public Example(String _value, int _idWord)
	{
		m_value = _value;
		m_idWord = _idWord;
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
}
