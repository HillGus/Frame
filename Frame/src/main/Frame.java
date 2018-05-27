package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private Map<String, JComponent> componentes = new HashMap<String, JComponent>();
	private ArrayList<JComponent> arrayComponentes = new ArrayList<>();
	
	private int padding = 0;
	
	public static void main(String[] args) {
		
		Frame frame = new Frame("Teste");
		frame.setSize(300, 300);
		
		frame.add(new JLabel("Teste"), "lblTeste");
		
		System.out.println(frame.get(JLabel.class).get(0).getText());
		
		frame.setVisible(true);
	}
	
	public Frame() {
		
		this("");
	}
	
	public Frame(String titulo) {
		
		super(titulo);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setLocationRelativeTo(null);
	}
	
	@Override
	public void setSize(int width, int height) {
		
		super.setSize(width + 16, height + 39);
	}
	
	public void add(JComponent comp, String key) {
		
		componentes.put(key, comp);
		arrayComponentes.add(comp);
		
		comp.setLocation((int)comp.getLocation().getX() + padding, (int)comp.getLocation().getY() + padding);
		
		add(comp);
	}
	
	public JComponent get(String key) {
		
		return componentes.get(key);
	}
	
	public <T> ArrayList<T> get(Class<T> classe) {
		
		ArrayList<T> comps = new ArrayList<>();
		
		for (JComponent comp : arrayComponentes) {
			
			try {
				comps.add(classe.cast(comp));
			} catch (Exception e) {}
		}
		
		return comps;
	}
}