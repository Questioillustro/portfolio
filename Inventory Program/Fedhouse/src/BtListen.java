import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BtListen implements ActionListener
{
	private boolean editing = false;
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals("AddItem"))
		{
			InventoryGeneralFields.addNewItem();
			InventoryGeneralFields.clearFields();
		}

		if(e.getActionCommand().equals("DeleteItem"))
		{
			Inventory.deleteItem();
		}
		
		if(e.getActionCommand().equals("SaveItem"))
		{
			if(editing)
				InventoryGeneralFields.updateExisting();
			else
				InventoryGeneralFields.addNewItem();
			
			InventoryGeneralFields.setEditableFields(false);
			editing = false;
		}
		
		if(e.getActionCommand().equals("EditItem"))
		{
			InventoryGeneralFields.setEditableFields(true);
			editing = true;
		}
		
		if(e.getActionCommand().equals("ClearFields"))
		{
			InventoryGeneralFields.clearFields();
		}
		
		if(e.getActionCommand().equals("SaveFile"))
		{
			Main.saveFile();
		}
		
		if(e.getActionCommand().equals("LoadFile"))
		{
			Main.loadFile();
		}
		
		if(e.getActionCommand().equals("Create"))
		{
			Main.createFile();
		}
		
		if(e.getActionCommand().equals("Sort"))
		{
			Inventory.sortList();
		}
		
		
		
	}
}