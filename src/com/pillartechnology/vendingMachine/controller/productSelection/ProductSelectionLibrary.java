package com.pillartechnology.vendingMachine.controller.productSelection;

import com.pillartechnology.vendingMachine.model.funds.FundsService;
import com.pillartechnology.vendingMachine.model.vendingMachineInventory.VendingMachineInventory;
import com.pillartechnology.vendingMachine.view.VendingMachineDisplay;

public class ProductSelectionLibrary {
	
	public final ProductSelectionManager selection;
	public final VendingMachineDisplay display;
	public final FundsService fundsService;
	public final VendingMachineInventory inventory;
	
	public ProductSelectionLibrary(
		ProductSelectionManager selection, 
		VendingMachineDisplay display, 
		FundsService fundsService, 
		VendingMachineInventory inventory
	) {
		this.display = display;
		this.selection = selection;
		this.fundsService = fundsService;
		this.inventory = inventory;
	}
	
}
