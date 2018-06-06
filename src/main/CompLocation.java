package main;

import javax.swing.JComponent;

public class CompLocation {

	public static final int LEFT = 10, TOP = 11, CENTER = 12, BOTTOM = 13, RIGHT = 14;
	
	public static final int LEFT_TOP = 1,	 MIDDLE_TOP = 2,    RIGHT_TOP = 3,
							LEFT_CENTER = 4, MIDDLE_CENTER = 5, RIGHT_CENTER = 6,
							LEFT_BOTTOM = 7, MIDDLE_BOTTOM = 8, RIGHT_BOTTOM = 9;
	
	private DivBounds divisao;
	private JComponent comp;
	private int location;
	
	public CompLocation(JComponent comp, int location, DivBounds divisao) {
		
		this.comp = comp;
		this.location = location;
		this.divisao = divisao;
	}
	
	
	public void atualizar() {
		
		JComponent comp = getComp();
		int local = getLocation();
		
		int compWidth = (int) comp.getSize().getWidth(), compHeight = (int) comp.getSize().getHeight();
		int compX = (int) comp.getLocation().getX(), compY = (int) comp.getLocation().getY();
		
		int divWidth = divisao.getWidth(), divHeight = divisao.getHeight();			
		int divX = divisao.getX(), divY = divisao.getY();
		
		switch (local) {
		
			case LEFT_TOP: comp.setLocation(0, 0); break;
			case MIDDLE_TOP: comp.setLocation(divWidth / 2 - compWidth / 2, 0); break;
			case RIGHT_TOP: comp.setLocation(divWidth - compWidth, 0); break;
			
			case LEFT_CENTER: comp.setLocation(0 , divHeight / 2 - compHeight / 2); break;
			case MIDDLE_CENTER: comp.setLocation(divWidth / 2 - compWidth / 2, divHeight / 2 - compHeight / 2); break;
			case RIGHT_CENTER: comp.setLocation(divWidth - compWidth, divHeight / 2 - compHeight / 2); break;
			
			case LEFT_BOTTOM: comp.setLocation(0, divHeight - compHeight); break;
			case MIDDLE_BOTTOM: comp.setLocation(divWidth / 2 - compWidth / 2, divHeight - compHeight); break;
			case RIGHT_BOTTOM: comp.setLocation(divWidth - compWidth, divHeight - compHeight); break;
			
			case LEFT: comp.setLocation(divX, compY); break;
			case TOP:
			case CENTER:
			case BOTTOM:
			case RIGHT:
		}
	}
	
	
	public JComponent getComp() {
		
		return this.comp;
	}
	
	public int getLocation() {
		
		return this.location;
	}
	
}
