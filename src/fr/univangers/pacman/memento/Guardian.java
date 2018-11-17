package fr.univangers.pacman.memento;

import java.util.ArrayList;

public class Guardian {
	
	private ArrayList<Memento> intList = new ArrayList<Memento>();

	public void addMemento(Memento mem) {
		intList.add(mem);
	}
	
	public Memento getMemento(int index) {
		return intList.get(index);
	}
	
}



