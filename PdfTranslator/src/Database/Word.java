package Database;

import java.util.ArrayList;
import java.util.List;

import com.wordnik.client.model.Definition;
import com.wordnik.client.model.Example;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * the word from the local dictionary
 * @author admin
 *
 */
public class Word  implements Parcelable{

	/**
	 * the id of the word in database
	 */
	int m_id;

	/**
	 * the string, how it is written
	 */
	String m_value;
	
	/**
	 * the definition fom wordnik
	 */
	List<String> m_definition;
	
	/**
	 * how it is being translated in romanian
	 */
	String m_translation;
	
	/**
	 * the example from wordnik
	 */
	List<String> m_example;
	
	
	/**
	 * the part of speech for each definition
	 */
	List<String> m_partSpeech;
	
	
	
	
	/**
	 * which part of sentence is the word
	 */
//	String m_part;
	
	/**
	 * specified above
	 * @param id
	 * @param value
	 * @param translation
	 * @param _definition
	 * @param _example
	 * @param part
	 */
	public Word(int id,String value, String translation, List<String> _definition, List<String> _example){
		
		
		this.m_id = id;
		this.m_value = value;
		this.m_definition = _definition;
		this.m_translation = translation;
		this.m_example = _example;
	//	this.m_part = part;
		
		
	}
	
	/**
	 * @param value
	 * @param translation
	 * @param definition
	 * @param example
	 * @param part
	 */
	public Word(String value,String translation, List<String> definition, List<String> example){
		
		this.m_id = -1;
		this.m_value = value;
		this.m_definition = definition;
		this.m_translation = translation;
		this.m_example = example;
		//this.m_part = part;
		
		
	}
	
	public Word( Integer _id, String value,String translation, List<Database.Definition> definition, List<String> example){
		
		m_id = _id;
		m_value = value;
		m_translation = translation;
		m_example = example;
		
		m_definition = new ArrayList<String>();
		m_partSpeech = new ArrayList<>();
		if(definition != null)
			for(Database.Definition def : definition)
			{
				m_definition.add(def.getM_value());
				
				
				if(def.getM_partSpeech() == null)
				{
					Log.i("message", "nu are parte de vorbre");
				}
				else
				{
					Log.i("message", "Are parte de vorbre");
					m_partSpeech.add(def.getM_partSpeech());
				}
			}
		
		
	}
			
			
	
	private Word(Parcel in){
	//	if(in.readInt() != -1)
		m_id = in.readInt();
        m_value = in.readString();
        m_translation = in.readString();
        m_definition = new ArrayList<String>();
        m_example = new ArrayList<String>();
        m_partSpeech = new ArrayList<String>();
        in.readStringList(m_definition);
        in.readStringList(m_example);
        in.readStringList(m_partSpeech);

    }
	
 	/**
 	 * 
 	 */
 	public Word()
	{
		m_definition = new ArrayList<String>();
		m_example = new ArrayList<String>();
		m_partSpeech = new ArrayList<String>();
	}
	



	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		 dest.writeInt(m_id);
		 dest.writeString(m_value);
	     dest.writeString(m_translation);
	     dest.writeStringList(m_definition);
	     dest.writeStringList(m_example);
	     dest.writeStringList(m_partSpeech);
		
	}
	
	/**
	 * used to send the data between two activities
	 */
	public static final Parcelable.Creator<Word> CREATOR = new Parcelable.Creator<Word>() {

        @Override
        public Word createFromParcel(Parcel source) {
            return new Word(source);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

	/**
	 * @return the m_definition
	 */
	public List<String> getM_definition() {
		return m_definition;
	}

	/**
	 * @param m_definition the m_definition to set
	 */
	public void setM_definition(List<String> m_definition) { 
		this.m_definition = m_definition;
	}

	/**
	 * @return the m_example
	 */
	public List<String> getM_example() {
		return m_example;
	}

	/**
	 * @param m_example the m_example to set
	 */
	public void setM_example(List<String> m_example) {
		this.m_example = m_example;
	}

	/**
	 * reating the list of examples from the data retrieved from Wordnik
	 * @param _definitions 
	 * 
	 */
	public void setM_definitionFromWordnik(List<Definition> _definitions) {
		
		
		List<String> definitions = new ArrayList<String>();
		List<String> partSpeech = new ArrayList<String>();
		for(Definition def : _definitions)
		{
			definitions.add(def.getText());
			partSpeech.add(def.getPartOfSpeech());
			
		}
		setM_definition(definitions);
		setM_partSpeech(partSpeech);
		
	}

	/**
	 * creating the list of examples from the data retrieved from Wordnik
	 * @param exampleResult : the list of resuults from WordNik
	 */
	public void setM_exampleFromWordnik(List<Example> exampleResult) {
		List<String> examples = new ArrayList<String>();
		for(Example example : exampleResult)
		{
			examples.add(example.getText());
		}
		setM_example(examples);
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
	 * @return the m_translation
	 */
	public String getM_translation() {

		return m_translation;
	}

	/**
	 * @param m_translation the m_translation to set
	 */
	public void setM_translation(String m_translation) {
		this.m_translation = m_translation;
	}



	/**
	 * @return the m_partSpeech
	 */
	public List<String> getM_partSpeech() {
		return m_partSpeech;
	}

	/**
	 * @param m_partSpeech the m_partSpeech to set
	 */
	public void setM_partSpeech(List<String> m_partSpeech) {
		this.m_partSpeech = m_partSpeech;
	}

	
}
