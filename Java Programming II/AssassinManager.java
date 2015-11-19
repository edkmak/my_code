/*Edwin Mak 1/17/13
 * Victoria Wagner Section BE
 * HW #3 Assassin
 * In this assignment we create a manager to play 
 * a game of Assassin.
 */

import java.util.*;

public class AssassinManager{
	
	private AssassinNode killring;
	private AssassinNode graveyard;
	
	// creates a list and adds the passed names into the
	// list in order given
	// throws an illegal argument exception if passed a null list 
	//      or the size is 0
	public AssassinManager (ArrayList<String> names){
		if(names == null || names.size() == 0){
			throw new IllegalArgumentException("List is null or list size is 0");
		}
		Iterator<String> i = names.iterator();
		killring = new AssassinNode(i.next());
		AssassinNode current = killring;
		while(i.hasNext()){
			current.next = new AssassinNode(i.next());
			current = current.next;
		}
	}
	
	//prints a list of the stalkers and the intended victims
	public void printKillRing(){
		AssassinNode current = killring;
		String assassinOne = current.name;
		while(current.next != null){
			System.out.println("  " + current.name + " is stalking " + current.next.name);
			current = current.next;
		}
		System.out.println("  " + current.name + " is stalking " + assassinOne);
	}
	
	//prints a list of the poeple who died and who killed them
	public void printGraveyard(){
		AssassinNode current = graveyard;
		if (graveyard != null){
			while(current != null){
				System.out.println("   " + current.name + " was killed by " + current.killer);
				current = current.next;
			}
		}
	}
	
	// returns true if name passed in is still alive
	public boolean killRingContains(String name){
		AssassinNode current = killring;
		return contains(current, name);
	}
	
	// returns true if named passed in is dead
	public boolean graveyardContains(String name){
		AssassinNode current = graveyard;
		return contains(current, name);
	}
	
	// returns true when one person is left in the game
	public boolean isGameOver(){
		return killring.next == null;
	}
	
	// returns the name of the winner of the game
	public String winner(){
		if(isGameOver()){
			return killring.name;	
		}
		return null;
	}

	// removes name from the killring to the graveyard
	// throws exception if game is over or if name is not in the killring
	public void kill(String name){
      if(isGameOver())
         throw new IllegalStateException("Game over");
		if(!killRingContains(name))
         throw new IllegalArgumentException("Name is not in the kill ring");
		AssassinNode currentRing = killring;
      AssassinNode target = null;
		AssassinNode currentGrave = graveyard;
      if(killring.name.equalsIgnoreCase(name)) {
         target = currentRing;
         while(currentRing.next != null){
            currentRing = currentRing.next;
			}
			currentRing.killer = currentRing.name;
         killring = killring.next;
      }else {
         while(!currentRing.next.name.equalsIgnoreCase(name)) {
            currentRing = currentRing.next;
			}
         target = currentRing.next;
         currentRing.next.killer = currentRing.name;
			if(currentRing.next.next != null) {
            currentRing.next = currentRing.next.next;
         }else{
            currentRing.next = null;
			}
      }
		if(currentGrave == null) {               //adds name to graveyard
         graveyard = target;
      }else{
         while(currentGrave.next != null){ 
            currentGrave = currentGrave.next;
         }
			currentGrave.next = target;
      }
		if(target.next != null){
         target.next = null;
		}
	}

	//checks list to see if name given is inside passed list of
	//either the graveyard or killring
	private boolean contains(AssassinNode current, String name){
		while(current != null){	
			String assassinOne = current.name;
			if(assassinOne.equalsIgnoreCase(name)){
				return true;
			}
			current = current.next;	
		}
		return false;
	}
}