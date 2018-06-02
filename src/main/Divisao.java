package main;

import java.util.ArrayList;

import javax.swing.JComponent;

public class Divisao {

	public static final int HORIZONTAL = 0, VERTICAL = 1;
	
	private Frame frm;
	
	private int iniX, fimX, iniY, fimY;
	private int direcao;
	private int margem = 0;
	
	private ArrayList<JComponent> componentes = new ArrayList<>();
	
	public Divisao(int direcao, Frame frm) {
		
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
	
	public void add(JComponent comp) {
		
		ajustarComp(comp);
		
		ajustarSize(comp);
		
		componentes.add(comp);
	}
	
	private void ajustarComp(JComponent comp) {
		
		int x = (int) comp.getLocation().getX(), y = (int) comp.getLocation().getY();
		
		if (direcao == HORIZONTAL) {
			
			comp.setLocation(x, iniY);
		} else {
			
			comp.setLocation(iniX, y);
		}
		
		
	}
	
	private void ajustarSize(JComponent comp) {
		
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
	
	private void reposicionar(JComponent comp) {
		
		int padding = frm.getPadding();
		
		int x = (int) comp.getLocation().getX(), y = (int) comp.getLocation().getY();
						
		comp.setLocation((int)x + padding, (int)y + padding);
	}
	
	public void atualizar() {
		
		for (JComponent comp : componentes) {
			
			ajustarComp(comp);
			
			reposicionar(comp);
			
			ajustarSize(comp);
		}
	}
	
	public void setIniX(int iniX) {
		
		this.iniX = iniX;
	}
	
	public void setIniY(int iniY) {
		
		this.iniY = iniY;
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
}
