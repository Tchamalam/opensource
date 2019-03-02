package domainLayer;

/**
 * The Message class is the one used to generate messages for cases when 
 * a candidates applies for a company(message to be sent to the HR or the company),
 *  or an HR schedules an interview(Message to be sent to the candidate).
 *  
 * @author Ben-Malik TCHAMALAM
 *
 */
public class Message {
	
	private String sender;
	private String topic;
	private String content;
	
	public Message(String sender, String topic, String content) {
		this.sender = sender;
		this.topic = topic;
		this.content = content;
	}
	
	public String getSender() {
		return sender;
	}
	
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public String getTopic() {
		return topic;
	}
	
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String toString() {
		return "\n#########  Message  #######\n"
				+ "\n From: " + sender
						+ "\n Topic: " + topic
						+ "\n " + content;
	}
	
}
