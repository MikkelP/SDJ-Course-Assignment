package jysk_shared;

import java.util.Hashtable;
import java.util.Set;

public class PalletCollection {

	private Hashtable<String, Pallet> pallets;
	
	public PalletCollection() {
		pallets = new Hashtable<String, Pallet>();
	}
	
	public void addPallet(String type, Pallet p) {
		pallets.put(type, p);
	}
	
	public Box getBox(String item) {
		Set<String> keys = pallets.keySet();
		for (String key : keys) {
			if (pallets.get(key).getBox(item) != null) {
				return pallets.get(key).getBox(item);
			}
		}
		return null; //Item doesnt exist in the collection
	}
	
	public Pallet getPallet(String type) {
		return pallets.get(type);
	}
	
	public Hashtable<String, Pallet> getCollection() {
		return pallets;
	}
	
	public int size() {
		return pallets.size();
	}
}
