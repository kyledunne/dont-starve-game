package entities;

import aamain.Main;

public class CraftingRecipe {
	public static CraftingRecipe CAMPFIRE_RECIPE = new CraftingRecipe(new Entity[] {new Log(), new Twig()}, new int[] {3, 3});
	public static CraftingRecipe AXE_RECIPE = new CraftingRecipe(new Entity[] {new Log(), new Twig()}, new int[] {1, 2});
	
	Entity[] ingredients;
	int[] numberOfIngredients;
	
	public CraftingRecipe(Entity[] ingredients, int[] numberOfIngredients) {
		this.ingredients = ingredients;
		this.numberOfIngredients = numberOfIngredients;
	}
	
	public boolean isCurrentlyCraftable() {
		for (int i = 0; i < ingredients.length; i++) {
			if (!(Main.MAIN_GAME_INSTANCE.inventoryBar.isInInventory(ingredients[i], numberOfIngredients[i]))) {
				return false;
			}
		}
		
		return true;
	}
	
	public void consumeIngredients() {
		
		for (int i = 0; i < ingredients.length; i++) {
			Main.MAIN_GAME_INSTANCE.inventoryBar.removeFromInventory(ingredients[i], numberOfIngredients[i]);
		}
	}
}
