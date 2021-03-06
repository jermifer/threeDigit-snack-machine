package controller.productSelection;

import com.pillartechnology.vendingMachine.model.vendingMachineInventory.VendingMachineInventoryManager;
import com.pillartechnology.vendingMachine.model.funds.FundsService;
import com.pillartechnology.vendingMachine.controller.productSelection.ProductSelectionManager;
import com.pillartechnology.vendingMachine.view.VendingMachineDisplay;

public class ProductSelectionLibrary
	implements com.pillartechnology.vendingMachine.controller.productSelection.ProductSelectionLibrary
{
	
	private final ProductSelectionManager selection;
	private final VendingMachineDisplay display;
	private final FundsService fundsService;
	private final VendingMachineInventoryManager inventory;
	
	public ProductSelectionLibrary(
		ProductSelectionManager selection, 
		VendingMachineDisplay display, 
		FundsService fundsService, 
		VendingMachineInventoryManager inventory
	) {
		this.display = display;
		this.selection = selection;
		this.fundsService = fundsService;
		this.inventory = inventory;
	}
	
	public VendingMachineDisplay vendingMachineDisplay() {
		return this.display;
	}
	
	public ProductSelectionManager productSelectionManager() {
		return this.selection;
	}
	
	public FundsService fundsService() {
		return this.fundsService;
	}
	
	public VendingMachineInventoryManager inventory() {
		return this.inventory;
	}
	
}
