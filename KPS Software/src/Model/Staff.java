package Model;

class Staff {
	
	private int UID;
	private String userName;
	private String password;
	
	public Staff(int uid, String name, String pass){
		this.setUID(uid);
		this.setUserName(name);
		this.setPassword(pass);
	}
	
	/**
	 * Checks that a give user name and password match the name and
	 * 	password of this given staff member. 
	 * @param name
	 * @param pass
	 * @return true if name and password match this staff member
	 */
	public boolean checkLogin(String name, String pass){
		if(this.userName.equals(name) 
				&& this.password.equals(pass)){
			return true;
		}
		return false;
	}

	/*
	 * Getter and Setter methods
	 * 
	 */
	public int getUID() {
		return UID;
	}
	public void setUID(int uID) {
		UID = uID;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
