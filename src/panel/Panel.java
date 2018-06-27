package panel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Panel extends JPanel implements DivBounds {

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
		
		JFrame frame = new JFrame("Cadastro Usuário");
		
		Panel pnl = new Panel();
		pnl.setBackground(Color.BLUE);
		
		frame.setContentPane(pnl);
		frame.setSize(316, 339);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		pnl.setPadding(25);
		pnl.setRowMargin(-50);
		
		JLabel lbl = new JLabel("Teste");
		lbl.setSize(100, 25);
		lbl.setLocation(0, 0);
		
		pnl.add(lbl, 1, 1);
		
		JButton btn = new JButton("TEASD");
		btn.setSize(100, 25);
		
		pnl.add(btn, 2, 1);
		
		//pnl.add(new JLabel(), 2, 1);
		
		frame.setVisible(true);
	}
	
	
	public Panel() {
		
		setLayout(null);
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
	
	@Override
	public void setSize(int width, int height) {
		
		super.setSize(width, height);
		
		ajustarLocalizacoes();
	}
		
	public void add(JComponent comp) {
		
		reposicionar(comp);
		
		resizeIfNeeded(comp);
		
		ajustarLocalizacoes();
		
		super.add(comp);
	}
	
	public void add(JComponent comp, String key) {
		
		componentes.put(key, comp);
		arrayComponentes.add(comp);
		
		add(comp);
	}
	
	public void add(JComponent comp, int local) {
		
		arrayCompLocation.add(new CompLocation(comp, local, this));
		
		add(comp);
	}
	
	public void add(JComponent comp, int linha, int coluna) {
		
		add(comp, linha, coluna, null);
	}
	
	public void add(JComponent comp, int linha, int coluna, String key) {
		
		if (key != null) {
			
			componentes.put(key,  comp);
			arrayComponentes.add(comp);
		}
		
		add(comp, linha, coluna, TOP, LEFT, key);
	}
	
	public void add(JComponent comp, int linha, int coluna, int posLinha, int posColuna) {	
		
		add(comp, linha, coluna, posLinha, posColuna, null);
	}
	
	public void add(JComponent comp, int linha, int coluna, int posLinha, int posColuna, String key) {
		
		if (linha == linhas.size() + 1) {		
			addLinha();
		}
		
		if (coluna == colunas.size() + 1) {
			addColuna();
		}
			
		linhas.get(linha - 1).add(new CompLocation(comp, posLinha, linhas.get(linha-1)));
		colunas.get(coluna - 1).add(new CompLocation(comp, posColuna, colunas.get(coluna-1)));
		
		atualizarDivisoes();
		
		add(comp, key);
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
		
	@SuppressWarnings("unchecked")
	public <T extends JComponent> T get(String key) {
		
		return (T) componentes.get(key);
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
			
			resizeIfNeeded(compLocal.getComp());
		}
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
	
	private void resizeIfNeeded(JComponent comp) {
		
		int x = (int) comp.getLocation().getX();
		int y = (int) comp.getLocation().getY();
		int width = (int) comp.getSize().getWidth();
		int height = (int) comp.getSize().getHeight();
		
		//Aumenta o tamanho do Frame caso ele não seja grande o suficiente para os componentes
		this.width = this.width < x + width ? x + width : this.width;
		this.height = this.height < y + height ? y + height : this.height;
	}


	@Override
	public int getX() {
		
		return (int) this.getLocation().getX();
	}

	@Override
	public int getY() {
		
		return (int) this.getLocation().getY();
	}

	@Override
	public int getWidth() {
		
		return (int) this.getSize().getWidth();
	}

	@Override
	public int getHeight() {
		
		return (int) this.getSize().getHeight();
	}
}