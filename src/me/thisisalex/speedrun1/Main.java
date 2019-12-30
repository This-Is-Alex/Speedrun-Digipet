package me.thisisalex.speedrun1;

import me.thisisalex.speedrun1.logic.Pet;
import me.thisisalex.speedrun1.menus.MenuManager;

public class Main {

	public static void main(String[] args) {
		
		System.out.println("Welcome to the super duper virtual pet simuator 2000");
		System.out.println("What would you like to name your pet?");
		String petName = MenuManager.askQuestion();
		
		Pet pet = new Pet(petName);
		MenuManager.dialog("A wild "+petName+" appeared!");
		
		int t = 0;
		while (true) {
			long startTime = System.currentTimeMillis();
			MenuManager.printSummary(pet);
			
			t++;
			if (t % 5 == 0)
				pet.tick();
			
			if (MenuManager.shouldActivateMenu()) {
				if (pet.getMood() == 3) {
					MenuManager.dialog(petName+" is asleep and cannot do anything");
				} else {
					System.out.println("Actions:");
					System.out.println("1. Give apple       8. Toilet break");
					System.out.println("2. Give cake        9. Give treat");
					System.out.println("3. Give wine        10.Give water");
					System.out.println("4. Give beer        11.Cancel");
					System.out.println("5. Give pizza       ");
					System.out.println("6. Give bath        ");
					System.out.println("7. Take to park     ");
					
					long timeToAnswer = System.currentTimeMillis();
					
					int option = MenuManager.askIntegerRangeQuestion("What would you like to do?", 1, 11);
					for (int i = 0; i < ((System.currentTimeMillis()-timeToAnswer)/1000); i++) {
						pet.tick();
					}
					
					switch (option) {
					case 1:
						if (pet.getStat("stomachfood") < 20) {
							MenuManager.dialog("You offer "+petName+" an apple but they are not hungry");
						} else if (pet.getStat("diet") > 80) {
							MenuManager.dialog("You offer "+petName+" an apple but they aren't in the mood for an apple");
						} else {
							pet.addStat("stomachfood", -10);
							pet.addStat("diet", 5);
							MenuManager.dialog(petName+" ate an apple!");
						}
						break;
					case 2:
						if (pet.getStat("stomachfood") < 30) {
							MenuManager.dialog("You offer "+petName+" some cake but they are not hungry");
						} else if (pet.getStat("diet") < 30) {
							MenuManager.dialog("You offer "+petName+" some cake but they aren't in the mood for any");
						} else {
							pet.addStat("diet", -10);
							pet.addStat("stomachfood", -20);
							MenuManager.dialog(petName+" ate some cake!");
						}
						break;
					case 3:
						if (pet.getStat("thirst") > 480) {
							MenuManager.dialog("You offer "+petName+" a glass of wine but they are not thirsty");
						} else {
							pet.addStat("thirst", 20);
							pet.addStat("drunk", 25);
							MenuManager.dialog(petName+" drank a glass of wine!");
						}
						break;
					case 4:
						if (pet.getStat("thirst") > 450) {
							MenuManager.dialog("You offer "+petName+" a bottle of beer but they are not thirsty");
						} else {
							pet.addStat("thirst", 50);
							pet.addStat("drunk", 10);
							MenuManager.dialog(petName+" drank a bottle of beer!");
						}
						break;
					case 5:
						if (pet.getStat("stomachfood") < 70) {
							MenuManager.dialog("You offer "+petName+" a pizza but they are not hungry");
						} else if (pet.getStat("diet") < 30) {
							MenuManager.dialog("You offer "+petName+" a pizza but they aren't in the mood for it");
						} else {
							pet.addStat("diet", -20);
							pet.addStat("stomachfood", -60);
							MenuManager.dialog(petName+" ate an entire pizza!");
						}
						break;
					case 6:
						pet.setStat("clean", 1000);
						pet.addStat("mood", 50);
						MenuManager.dialog("You gave "+petName+" a bath!");
						break;
					case 7:
						if (pet.getStat("energy") < 200) {
							MenuManager.dialog(petName+" isn't feeling very energetic");
						} else {
							pet.addStat("energy", -200);
							pet.addStat("thirst", -50);
							pet.addStat("social", 200);
							MenuManager.dialog(petName+" went to the park!");
						}
						break;
					case 8:
						if (pet.getStat("piss") < 10) {
							MenuManager.dialog(petName+" is concerned with why you're taking them to the bathroom so often");
						} else {
							pet.setStat("piss", 0);
							MenuManager.dialog(petName+" went for a piss!");
						}
						break;
					case 9:
						if (pet.getStat("stomachfood") < 10) {
							MenuManager.dialog("You offer "+petName+" a treat but they are not hungry");
						} else if (pet.getStat("diet") < 60) {
							MenuManager.dialog("You offer "+petName+" a treat but they aren't in the mood for it");
						} else {
							pet.addStat("diet", -10);
							pet.addStat("stomachfood", -10);
							pet.addStat("mood", 10);
							MenuManager.dialog("You gave "+petName+" a treat!");
						}
						break;
					case 10:
						if (pet.getStat("thirst") > 400) {
							MenuManager.dialog("You offer "+petName+" water, but they are not thirsty");
						} else {
							pet.addStat("thirst", 100);
							MenuManager.dialog(petName+" drank water!");
						}
						break;
					default:
						break;
					}
				}
			}
			
			try {
				if (System.currentTimeMillis() - startTime < 200)
					Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
