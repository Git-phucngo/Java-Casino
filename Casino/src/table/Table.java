package table;

import java.util.ArrayDeque;

public class Table {
	private ArrayDeque<Slot> table;
	
	public Table() {
		this.table = new ArrayDeque<Slot>();
	}

	public ArrayDeque<Slot> getTable() {
		return table;
	}

	public void setTable(ArrayDeque<Slot> table) {
		this.table = table;
	}
	
	protected void addSlotToTable(Slot s) {
		table.add(s);
	}
	
	protected Slot getSlotFromTable() {
		return table.remove();
	}
	
}
