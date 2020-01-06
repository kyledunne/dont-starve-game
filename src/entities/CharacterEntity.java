package entities;

import aamain.*;

public class CharacterEntity extends Entity {
	public static final Image characterImage = new Image("res/images/entities/characterEntity.png");
	public static float width = 150;
	public static float height = 225;
	
	CharacterModel characterModel;
	int currentSpeed;
	
	public CharacterEntity() {
		super(characterImage, false);
	}

}
