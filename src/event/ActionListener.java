package event;

public abstract class ActionListener implements EventListener {
	public abstract void actionPerformed(ActionEvent e);
	public void mousedOver() {}
	public void unMousedOver() {}
	public void pressed() {}
	public void released() {}
}
