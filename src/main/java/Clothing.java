package main.java;

/*
 * clothing to wear for the weather 
 */

public class Clothing {
	
	// levels of clothing (1-7, least clothing to most clothing) for the weather:
	private enum Level { ONE, TWO, THREE, FOUR,	FIVE, SIX, SEVEN; }
	private Clothing.Level level = Clothing.Level.ONE;
	
	// items of clothing and their svg icon path and name
	private enum Item {
		NONE      ("/clothing/none.svg",       "nothing"),
		JACKET    ("/clothing/jacket.svg",     "jacket"),
		PANTS     ("/clothing/pants.svg",      "pants"),
		PEACOAT   ("/clothing/peacoat.svg",    "peacoat"),
		SHORTS    ("/clothing/shorts.svg",     "shorts"),
		SWEATER   ("/clothing/sweater.svg",    "sweater"),
		TSHIRT    ("/clothing/tshirt.svg",     "tshirt"),
		TUQUE     ("/clothing/tuque.svg",      "tuque"),
		TUQUESCARF("/clothing/tuquescarf.svg", "tuque and scarf");
	
		private String icon; // icon of this clothing item
		private String name; // name of this clothing item   
		
		private Item (String icon, String name) {	
			this.icon = icon;
			this.name = name;
		}	
		
		private String getIcon()  { return this.icon; }
		private String getName()  { return this.name; }
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
			case 7: this.level = Clothing.Level.SEVEN; break;
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
	public String getHeadIcon() throws Exception {
		switch(this.level) {
			case ONE : case TWO : case THREE : case FOUR : 
				return Clothing.Item.NONE.getIcon();
			case FIVE : case SIX : 
				return Clothing.Item.TUQUE.getIcon();
			case SEVEN:
				return Clothing.Item.TUQUESCARF.getIcon();
			default :
				throw new Exception("clothing level did not return clothing for head");
		}
	}
	
	/*
	 * return the clothing icon for torso
	 */
	public String getTorsoIcon() throws Exception {
		switch(this.level) {
			case ONE : case TWO :
				return Clothing.Item.TSHIRT.getIcon();
			case THREE :
				return Clothing.Item.SWEATER.getIcon();
			case FOUR : case FIVE :
				return Clothing.Item.JACKET.getIcon();
			case SIX : case SEVEN :
				return Clothing.Item.PEACOAT.getIcon();
			default :
				throw new Exception("clothing level did not return clothing for torso");
		}
	}
	
	/*
	 * return the clothing icon for legs
	 */
	public String getLegsIcon() throws Exception {
		switch(this.level) {
			case ONE :
				return Clothing.Item.SHORTS.getIcon();
			case TWO : case THREE : case FOUR : case FIVE : case SIX : case SEVEN :
				return Clothing.Item.PANTS.getIcon();
			default :
				throw new Exception("clothing level did not return clothing for legs");
		}
	}
	
	/*
	 * return the clothing item for head
	 */
	public String getHeadName() throws Exception {
		switch(this.level) {
			case ONE : case TWO : case THREE : case FOUR : 
				return Clothing.Item.NONE.getName();
			case FIVE : case SIX : 
				return Clothing.Item.TUQUE.getName();
			case SEVEN:
				return Clothing.Item.TUQUESCARF.getName();
			default :
				throw new Exception("clothing level did not return clothing for head");
		}
	}
	
	/*
	 * return the clothing item for torso
	 */
	public String getTorsoName() throws Exception {
		switch(this.level) {
			case ONE : case TWO :
				return Clothing.Item.TSHIRT.getName();
			case THREE :
				return Clothing.Item.SWEATER.getName();
			case FOUR : case FIVE :
				return Clothing.Item.JACKET.getName();
			case SIX : case SEVEN :
				return Clothing.Item.PEACOAT.getName();
			default :
				throw new Exception("clothing level did not return clothing for torso");
		}
	}
	
	/*
	 * return the clothing item for legs
	 */
	public String getLegsName() throws Exception {
		switch(this.level) {
			case ONE :
				return Clothing.Item.SHORTS.getName();
			case TWO : case THREE : case FOUR : case FIVE : case SIX : case SEVEN :
				return Clothing.Item.PANTS.getName();
			default :
				throw new Exception("clothing level did not return clothing for legs");
		}
	}
}
