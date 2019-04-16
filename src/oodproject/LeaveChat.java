package oodproject;

class LeaveChat extends ChatStatus{

	public String identify(String name) {
		return "--- " + name + " is leaving the chat---";
	}
}