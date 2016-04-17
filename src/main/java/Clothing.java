package main.java;

/*
 * clothing to wear for the weather 
 */

public class Clothing {
	
	// levels of clothing (1-6, least clothing to most clothing) for the weather:
	private enum Level { ONE, TWO, THREE, FOUR,	FIVE, SIX; }
	private Clothing.Level level = Clothing.Level.ONE;
	
	// items of clothing
	private enum Item {
		NONE("nothing"),
		JACKET("jacket"),			// TODO: change string for loaded icon
		PANTS("pants"),
		PEACOAT("peacoat"),
		SHORTS("shorts"),
		SWEATER("sweater"),
		TSHIRT("tshirt"),
		TUQUE("tuque");
	
		private String icon;    // icon of this clothing item
		
		private Item (String icon) {	
			this.icon = icon;
		}	
		
		private String getItem()  { return this.icon; }
	}
	
	/*
	 * set the clothing level
	 */
	public boolean setLevel(int level) {
		switch (level) {
			case 1: this.level = Clothing.Level.ONE;   break;
			case 2: this.level = Clothing.Level.TWO;   break;
			case 3: this.level = Clothing.Level.THREE; break;
			case 4: this.level = Clothing.Level.FOUR;  break;
			case 5: this.level = Clothing.Level.FIVE;  break;
			case 6: this.level = Clothing.Level.SIX;   break;
			default: return false;
		}
		return true;
	}
	
	/* 
	 * can have three items of clothing to suggest for weather:
	 * head, torso, legs
	 */
	
	/*
	 * return the clothing icon for head
	 */
	public String getHead() throws Exception {
		switch(this.level) {
			case ONE : case TWO : case THREE : case FOUR : 
				return Clothing.Item.NONE.getItem();
			case FIVE : case SIX : 
				return Clothing.Item.TUQUE.getItem();
			default :
				throw new Exception("clothing level did not return clothing for legs");
		}
	}
	
	/*
	 * return the clothing icon for torso
	 */
	public String getTorso() throws Exception {
		switch(this.level) {
			case ONE : case TWO :
				return Clothing.Item.TSHIRT.getItem();
			case THREE :
				return Clothing.Item.SWEATER.getItem();
			case FOUR : case FIVE :
				return Clothing.Item.JACKET.getItem();
			case SIX :
				return Clothing.Item.PEACOAT.getItem();
			default :
				throw new Exception("clothing level did not return clothing for torso");
		}
	}
	
	/*
	 * return the clothing icon for legs
	 */
	public String getLegs() throws Exception {
		switch(this.level) {
			case ONE :
				return Clothing.Item.SHORTS.getItem();
			case TWO : case THREE : case FOUR : case FIVE : case SIX :
				return Clothing.Item.PANTS.getItem();
			default :
				throw new Exception("clothing level did not return clothing for legs");
		}
	}
}
