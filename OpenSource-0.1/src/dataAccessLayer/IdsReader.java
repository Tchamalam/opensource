package dataAccessLayer;

import org.json.simple.JSONObject;

public class IdsReader {
	
	private static final String path = 	System.getProperty("user.dir") + "/src/database/ids.json";

	private JSONObject ids;
	private JSONObject file;
	
	public IdsReader() {
		 file = ReaderHelper.parse(path);
		 ids =(JSONObject) file.get("ids");
	}
	
	@SuppressWarnings("unchecked")
	public Integer getLastIdForHR() {
		Integer result = Integer.valueOf(ids.get("hr").toString());
		ids.put("hr", String.valueOf(result + 1));
		file.put("ids", ids);
		ReaderHelper.save(file, path);
		return result + 1;
	}
	
	@SuppressWarnings("unchecked")
	public Integer getLastIdForCandidate() {
		Integer result = Integer.valueOf(ids.get("candidate").toString());
		ids.put("candidate", String.valueOf(result + 1));
		file.put("ids", ids);
		ReaderHelper.save(file, path);
		return result + 1;
	}
	
	@SuppressWarnings("unchecked")
	public Integer getLastIdForAdmin() {
		Integer result = Integer.valueOf(ids.get("admin").toString());
		ids.put("admin", String.valueOf(result + 1));
		file.put("admin", ids);
		ReaderHelper.save(file, path);
		return result + 1;
	}
	
	@SuppressWarnings("unchecked")
	public Integer getLastIdForInterview() {
		Integer result = Integer.valueOf(ids.get("interview").toString());
		ids.put("interview", String.valueOf(result + 1));
		file.put("ids", ids);
		ReaderHelper.save(file, path);
		return result + 1;
	}
	
	@SuppressWarnings("unchecked")
	public Integer getLastIdForApplication() {

		String id = ids.get("application").toString();

		Integer result = Integer.valueOf(id);
		ids.put("application", String.valueOf(result + 1));
		file.put("ids", ids);
		ReaderHelper.save(file, path);
		
		return result + 1;
	}

}
