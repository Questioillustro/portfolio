import java.util.ArrayList;

class Profile implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	private ArrayList<String> tableColumns;
	private ArrayList<String> extraFields;
	private String[] defaultColumnNames = {"Product", "ID", "Cost",
										   "Status","Quantity", "Asking Price", "Low Price"};
	private String[] defaultExtraFields = {"CPU", "RAM", "HDD", "LCD", "Battery", "OS"};
	private int numFields;
	private boolean notes;
	private int[] properties;
		
	public Profile()
	{
		//Build default column/field names
		for(int i = 0; i < defaultColumnNames.length; i++)
			tableColumns.add(defaultColumnNames[i]);
		for(int i = 0; i < defaultExtraFields.length; i++)
			extraFields.add(defaultExtraFields[i]);
		
		//Enables a notes text area
		notes = true;
		//Counts the total number of fields
		numFields = tableColumns.size() + extraFields.size();
		//Builds boolean arrays for searchable and sortable fields
		properties = new int[numFields];

	}

	public ArrayList<String> getTableColumns() {
		return tableColumns;
	}

	public void setTableColumns(ArrayList<String> tableColumns) {
		this.tableColumns = tableColumns;
	}

	public ArrayList<String> getExtraFields() {
		return extraFields;
	}

	public void setExtraFields(ArrayList<String> extraFields) {
		this.extraFields = extraFields;
	}

	public int getNumFields() {
		return numFields;
	}

	public void setNumFields(int numFields) {
		this.numFields = numFields;
	}
}