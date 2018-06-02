package main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private Map<String, JComponent> componentes = new HashMap<String, JComponent>();
	private ArrayList<JComponent> arrayComponentes = new ArrayList<>();
	
	private ArrayList<Divisao> linhas = new ArrayList<>();
	private ArrayList<Divisao> colunas = new ArrayList<>();
	
	private int padding = 0, width = 0, height = 0, divMargem = 0;
	
	public static void main(String args[]) {
		
		Frame frm = new Frame();
		frm.setPadding(15);
		
		JLabel lbl1 = new JLabel();
		lbl1.setSize(100, 100);
		lbl1.setOpaque(true);
		lbl1.setBackground(Color.BLACK);
		
		JLabel lbl2 = new JLabel();
		lbl2.setSize(100, 100);
		lbl2.setOpaque(true);
		lbl2.setBackground(Color.BLUE);
		
		JLabel lbl3 = new JLabel();
		lbl3.setSize(100, 100);
		lbl3.setOpaque(true);
		lbl3.setBackground(Color.RED);
		
		JLabel lbl4 = new JLabel();
		lbl4.setSize(100, 100);
		lbl4.setOpaque(true);
		lbl4.setBackground(Color.GREEN);
		
		JLabel lbl5 = new JLabel();
		lbl5.setSize(150, 150);
		lbl5.setOpaque(true);
		lbl5.setBackground(Color.CYAN);
		
		frm.add(lbl1, 1, 1);		
		frm.add(lbl2, 2, 1);
		frm.add(lbl3, 2, 2);
		frm.add(lbl5, 2, 1);
		
		frm.setVisible(true);
		
		try {
			Thread.sleep(1000);
		} catch (Exception e) {}
		
		frm.add(lbl4, 3, 2);
		
		frm.setVisible(true);
	}
	
	public Frame() {
		
		this("");
		
		setDivMargem(15);
	}
	
	public Frame(String titulo) {
		
		//Cria um JFrame
		super(titulo);
		
		//Configura o JFrame
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//Define a margem interna do frame
	public void setPadding(int padding) {
		
		this.padding = padding;
	}

	public int getPadding() {
		
		return this.padding;
	}
	
	public void setDivMargem(int margem) {
		
		this.divMargem = margem;
	}

	private void addLinha() {
		
		int iniY = 0;
		if (linhas.size() > 0) {
			
			Divisao d = linhas.get(linhas.size() - 1);
			iniY = d.getFimY();
		}
		
		Divisao div = new Divisao(Divisao.HORIZONTAL, this);
		div.setMargem(divMargem);
		div.setIniY(iniY);
		
		linhas.add(div);
	}
	
	private void addColuna() {
		
		int iniX = 0;
		
		if (colunas.size() > 0) {
			
			Divisao d = colunas.get(colunas.size() - 1);
			iniX = d.getFimX();
		}
		
		Divisao div = new Divisao(Divisao.VERTICAL, this);
		div.setMargem(divMargem);
		div.setIniX(iniX);
		
		colunas.add(div);
	}
	
	@Override
	public void setSize(int width, int height) {
		
		this.width = width;
		this.height = height;
		
		//Define a dimensão do frame do tamanho que o pane interno fique com os parâmetros informados
		super.setSize(width + 16, height + 39);
	}
	
	public void setVisible(boolean visivel) {
		
		//Adiciona a margem interna ao frame
		this.setSize(width + padding * 2, height + padding * 2);
		
		super.setVisible(visivel);
		
		setLocationRelativeTo(null);
	}
	
	private void reposicionar(JComponent comp) {
		
		//Obtem x e y do componente
		int x = (int) comp.getLocation().getX(), y = (int) comp.getLocation().getY();
				
		//define a localização do componente de acordo com a margem interna
		comp.setLocation((int)x + padding, (int)y + padding);
	}
	
	public void add(JComponent comp) {
		
		resizeIfNeeded(comp);
		
		reposicionar(comp);
		
		super.add(comp);
	}
	
	public void add(JComponent comp, String key) {
		
		//Adiciona o componente no dicionário e no array
		componentes.put(key, comp);
		arrayComponentes.add(comp);
		
		resizeIfNeeded(comp);
		
		//Obtem x e y do componente
		int x = (int) comp.getLocation().getX(), y = (int) comp.getLocation().getY();
		
		//Define a localização do componente de acordo com a margem interna
		comp.setLocation((int)x + padding, (int)y + padding);
		
		add(comp);
	}
	
	public void add(JComponent comp, int linha, int coluna) {	
		
		if (linha == linhas.size() + 1) {		
			addLinha();
		}
		
		if (coluna == colunas.size() + 1) {
			addColuna();
		}
			
		atualizarDivisoes();
		
		linhas.get(linha - 1).add(comp);
		colunas.get(coluna - 1).add(comp);
		
		add(comp);
	}
	
	private void atualizarDivisoes() {
		
		if (colunas.size() > 0) {
			for (int i = 1; i < colunas.size(); i++) {
				
				colunas.get(i).setIniX(colunas.get(i - 1).getFimX());
				colunas.get(i).atualizar();
			}
		}
	
		if (linhas.size() > 0) {
			for (int i = 1; i < linhas.size(); i++) {
				
				linhas.get(i).setIniY(colunas.get(i - 1).getFimY());
				linhas.get(i).atualizar();
			}
		}
	}
	
	public JComponent get(String key) {
		
		return componentes.get(key);
	}
	
	public <T> ArrayList<T> get(Class<T> classe) {
		
		//Cria um arrayList da classe informada
		ArrayList<T> comps = new ArrayList<>();
		
		//Adiciona os componentes que forem daquela classe ao arraylist
		for (JComponent comp : arrayComponentes) {
			
			try {
				comps.add(classe.cast(comp));
			} catch (Exception e) {}
		}
		
		return comps;
	}

	private void resizeIfNeeded(JComponent comp) {
		
		int x = (int) comp.getLocation().getX();
		int y = (int) comp.getLocation().getY();
		int width = (int) comp.getSize().getWidth();
		int height = (int) comp.getSize().getHeight();
		
		//Aumenta o tamanho do Frame caso ele não seja grande o suficiente para os componentes
		this.width = this.width < x + width ? x + width : this.width;
		this.height = this.height < y + height ? y + height : this.height;
	}
}