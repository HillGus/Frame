package panel;

import javax.swing.JComponent;

public class CompLocation {

	public static final int LEFT = 10, TOP = 11, CENTER = 12, BOTTOM = 13, RIGHT = 14;
	
	public static final int LEFT_TOP = 1,	 MIDDLE_TOP = 2,    RIGHT_TOP = 3,
							LEFT_CENTER = 4, MIDDLE_CENTER = 5, RIGHT_CENTER = 6,
							LEFT_BOTTOM = 7, MIDDLE_BOTTOM = 8, RIGHT_BOTTOM = 9;
	
	private DivBounds divisao;
	private JComponent comp;
	private int location;
	
	private int firstCompX, firstCompY;
	
	public CompLocation(JComponent comp, int location, DivBounds divisao) {
		
		this.comp = comp;
		
		this.firstCompX = (int) comp.getLocation().getX();
		this.firstCompY = (int) comp.getLocation().getY();
		
		this.location = location;
		this.divisao = divisao;
	}
	
	
	public void atualizar() {
		
		JComponent comp = getComp();
		int local = getLocation();
		
		int compWidth = (int) comp.getSize().getWidth(), compHeight = (int) comp.getSize().getHeight();
		
		int divWidth = divisao.getWidth(), divHeight = divisao.getHeight();	
		int divX = divisao.getX(), divY = divisao.getY();
		
		switch (local) {
		
			case LEFT_TOP: comp.setLocation(divX, divY); break;
			case MIDDLE_TOP: comp.setLocation(divWidth / 2 - compWidth / 2 + divX, divY); break;
			case RIGHT_TOP: comp.setLocation(divWidth - compWidth + divX, divY); break;
			
			case LEFT_CENTER: comp.setLocation(divX , divHeight / 2 - compHeight / 2 + divY); break;
			case MIDDLE_CENTER: comp.setLocation(divWidth / 2 - compWidth / 2 + divX, divHeight / 2 - compHeight / 2 + divY); break;
			case RIGHT_CENTER: comp.setLocation(divWidth - compWidth+ divX, divHeight / 2 - compHeight / 2 + divY); break;
			
			case LEFT_BOTTOM: comp.setLocation(divX, divHeight - compHeight + divY); break;
			case MIDDLE_BOTTOM: comp.setLocation(divWidth / 2 - compWidth / 2+ divX, divHeight - compHeight + divY); break;
			case RIGHT_BOTTOM: comp.setLocation(divWidth - compWidth+ divX, divHeight - compHeight + divY); break;
			
			case LEFT: comp.setLocation(divX, firstCompY + divY); break;
			case TOP: comp.setLocation(firstCompX + divX, divY); break;
			case CENTER: comp.setLocation(divWidth / 2 - compWidth / 2 + divX, divHeight / 2 - compHeight / 2 + divY); break;
			case BOTTOM: comp.setLocation(firstCompX + divX, divHeight - compHeight + divY); break;
			case RIGHT: comp.setLocation(divWidth - compWidth + divX, firstCompY + divY); break;
		}
	}
	
	public JComponent getComp() {
		
		return this.comp;
	}
	
	public int getLocation() {
		
		return this.location;
	}
	
}
