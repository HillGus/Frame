package panel;

import java.util.ArrayList;

import javax.swing.JComponent;

public class Divisao implements DivBounds {

	public static final int HORIZONTAL = 0, VERTICAL = 1;
	
	private Panel frm;
	
	private int iniX, fimX, iniY, fimY;
	private int direcao;
	private int margem = 0;
	
	private ArrayList<CompLocation> componentes = new ArrayList<>();
	
	public Divisao(int direcao, Panel frm) {
		
		this.frm = frm;
		
		this.iniX = 0;
		this.fimX = 0;
		this.iniY = 0;
		this.fimY = 0;
		
		this.direcao = direcao;
	}
	
	public void setMargem(int margem) {
		
		this.margem = margem;
	}
	
	public void add(CompLocation compLocal) {
		
		componentes.add(compLocal);
		
		atualizar();
	}
	
	private void ajustarComp(CompLocation compLocal) {
		
		JComponent comp = compLocal.getComp();
		
		int x = (int) comp.getLocation().getX(), y = (int) comp.getLocation().getY();
		
		if (direcao == HORIZONTAL) {
			
			comp.setLocation(x, iniY);
		} else {
			
			comp.setLocation(iniX, y);
		}
	}
	
	private void ajustarSize(CompLocation compLocal) {
		
		JComponent comp = compLocal.getComp();
		
		int width = (int) comp.getSize().getWidth(), height = (int) comp.getSize().getHeight();
		
		if (direcao == HORIZONTAL) {
					
			int alturaAtual = fimY;
			int alturaNova = iniY + height + margem;
			
			fimY = alturaNova > alturaAtual ? alturaNova : alturaAtual;
		} else {
				
			int larguraAtual = fimX;
			int larguraNova = iniX + width + margem;
			
			fimX = larguraNova > larguraAtual ? larguraNova : larguraAtual;
		}
	}
	
	private void reposicionar(CompLocation compLocal) {
		
		compLocal.atualizar();
	}
		
	public void atualizar() {
		
		for (CompLocation comp : componentes) {
			
			ajustarComp(comp);
			
			ajustarSize(comp);
			
			reposicionar(comp);
		}
	}
	
	public void setIniX(int iniX) {
		
		this.iniX = iniX + frm.getPadding();
	}
	
	public void setIniY(int iniY) {
		
		this.iniY = iniY + frm.getPadding();
	}
	
	public int getIniX() {
		
		return this.iniX;
	}
	
	public int getFimX() {
		
		return this.fimX;
	}
	
	public int getIniY() {
		
		return this.iniY;
	}
	
	public int getFimY() {
		
		return this.fimY;
	}

	
	@Override
	public int getX() {
		
		return getIniX();
	}

	@Override
	public int getY() {
		
		return getIniY();
	}

	@Override
	public int getWidth() {
		
		return getFimX() - getIniX();
	}

	@Override
	public int getHeight() {
		
		return getFimY() - getIniY();
	}
}
