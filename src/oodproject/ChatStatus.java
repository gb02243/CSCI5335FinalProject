package oodproject;

public abstract class ChatStatus {
	public abstract String identify(String name);
	public final void send() {
		String name = "";
		identify(name);
	}
}
