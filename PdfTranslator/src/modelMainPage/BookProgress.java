package modelMainPage;

/**
 * used to show in the main activity the progress the reader has done so far
 * @author Vlad Herescu
 *
 */
public class BookProgress{
	
	/**
	 * the total number of files the book contains
	 */
	int m_nrTotalFiles;
	
	/**
	 * the progress the reader has done, how many pages he has read
	 */
	int m_progress; 
	
	/**
	 * @param _nrTotalFiles
	 * @param _progress
	 */
	public BookProgress(int _nrTotalFiles, int _progress) {
		m_nrTotalFiles = _nrTotalFiles;
		m_progress = _progress;
	}

	/**
	 * @return the m_nrTotalFiles
	 */
	public int getM_nrTotalFiles() {
		return m_nrTotalFiles;
	}

	/**
	 * @return the m_progress
	 */
	public int getM_progress() {
		return m_progress;
	}

	/**
	 * @param m_nrTotalFiles the m_nrTotalFiles to set
	 */
	public void setM_nrTotalFiles(int m_nrTotalFiles) {
		this.m_nrTotalFiles = m_nrTotalFiles;
	}

	/**
	 * @param m_progress the m_progress to set
	 */
	public void setM_progress(int m_progress) {
		this.m_progress = m_progress;
	}

}
