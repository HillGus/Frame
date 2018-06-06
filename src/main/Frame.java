package main;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Frame extends JFrame implements DivBounds {

	private static final long serialVersionUID = 1L;
	
	public static final int CENTER = CompLocation.CENTER, TOP = CompLocation.TOP, 
							BOTTOM = CompLocation.BOTTOM, LEFT = CompLocation.LEFT, RIGHT = CompLocation.RIGHT;
	
	public static final int LEFT_TOP = 1,	 MIDDLE_TOP = 2,    RIGHT_TOP = 3,
							LEFT_CENTER = 4, MIDDLE_CENTER = 5, RIGHT_CENTER = 6,
							LEFT_BOTTOM = 7, MIDDLE_BOTTOM = 8, RIGHT_BOTTOM = 9;
	
	private Map<String, JComponent> componentes = new HashMap<String, JComponent>();
	private ArrayList<JComponent> arrayComponentes = new ArrayList<>();
	private ArrayList<CompLocation> arrayCompLocation = new ArrayList<>();
	
	private ArrayList<Divisao> linhas = new ArrayList<>();
	private ArrayList<Divisao> colunas = new ArrayList<>();
	
	private int padding = 0, width = 0, height = 0;
	
	private int rowMargin = 0, colMargin = 0; 
	
	public static void main(String args[]) {
		
		Frame frm = new Frame();
		frm.setPadding(15);
		frm.setColMargin(15);
		frm.setRowMargin(15);
		
		JLabel lbl = new JLabel("Teste");
		lbl.setSize((int) lbl.getPreferredSize().getWidth(), (int) lbl.getPreferredSize().getHeight());
		
		JLabel lbl1 = new JLabel();
		lbl1.setSize(200, 200);
		
		frm.add(lbl1);
		frm.add(lbl, MIDDLE_CENTER);
		
		frm.setVisible(true);
	}
	
	public Frame() {
		
		this("");
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
	
	public void setRowMargin(int rowMargin) {
		
		this.rowMargin = rowMargin;
	}
	
	public void setColMargin(int colMargin) {
		
		this.colMargin = colMargin;
	}

	private void addLinha() {
		
		int iniY = 0;
		if (linhas.size() > 0) {
			
			Divisao d = linhas.get(linhas.size() - 1);
			iniY = d.getFimY();
		}
		
		Divisao div = new Divisao(Divisao.HORIZONTAL, this);
		div.setMargem(rowMargin);
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
		div.setMargem(colMargin);
		div.setIniX(iniX);
		
		colunas.add(div);
	}
	
	@Override
	public void setSize(int width, int height) {
		
		this.width = width;
		this.height = height;
		
		//Define a dimensão do frame do tamanho que o pane interno fique com os parâmetros informados
		super.setSize(width + 16, height + 39);
		
		ajustarLocalizacoes();
	}
	
	@Override
	public int getWidth() {
		
		return super.getWidth() - padding * 2 - 16;
	}
	
	@Override
	public int getHeight() {
		
		return super.getHeight() - padding * 2 - 39;
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
	
	private void ajustarLocalizacoes() {
		
		for (CompLocation compLocal : arrayCompLocation) {
			
			compLocal.atualizar();
			
			reposicionar(compLocal.getComp());
		}
	}
	
	public void add(JComponent comp) {
		
		resizeIfNeeded(comp);
		
		reposicionar(comp);
		
		ajustarLocalizacoes();
		
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
	
	public void add(JComponent comp, int local) {
		
		arrayCompLocation.add(new CompLocation(comp, local, this));
		
		add(comp);
	}
	
	public void add(JComponent comp, int linha, int coluna) {
		
		add(comp, linha, coluna, TOP, LEFT);
	}
	
	public void add(JComponent comp, int linha, int coluna, int posLinha, int posColuna) {	
		
		if (linha == linhas.size() + 1) {		
			addLinha();
		}
		
		if (coluna == colunas.size() + 1) {
			addColuna();
		}
			
		atualizarDivisoes();
		
		linhas.get(linha - 1).add(new CompLocation(comp, posLinha, this));
		colunas.get(coluna - 1).add(new CompLocation(comp, posColuna, this));
		
		add(comp);
	}
	
	public void addAtRow(JComponent comp, int linha) {
		
		addAtRow(comp, linha, TOP);
	}
	
	public void addAtRow(JComponent comp, int linha, int posLinha) {
		
		if (linha == linhas.size() + 1) {
			addLinha();
		}
		
		atualizarDivisoes();
		
		linhas.get(linha - 1).add(new CompLocation(comp, posLinha, this));
		
		add(comp);
	}
	
	public void addAtCol(JComponent comp, int coluna) {
		
		addAtCol(comp, coluna, LEFT);
	}
	
	public void addAtCol(JComponent comp, int coluna, int posColuna) {
		
		if (coluna == colunas.size() + 1) {
			addColuna();
		}
		
		atualizarDivisoes();
		
		colunas.get(coluna - 1).add(new CompLocation(comp, posColuna, this));
		
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
				
				linhas.get(i).setIniY(linhas.get(i - 1).getFimY());
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