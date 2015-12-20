package controller.productSelection;

import com.pillartechnology.vendingMachine.model.vendingMachineInventory.VendingMachineInventoryItem;

public class ProductSelectionManager
	implements  com.pillartechnology.vendingMachine.controller.productSelection.ProductSelectionManager
{

	public void isRefundCode(Integer input) {
	}

	public void clearInputs() {
	}

	public boolean isCompleteProductCode(Integer input) {
		return false;
	}

	//product.quantityInStock() > 0
	public boolean isPurchasable(VendingMachineInventoryItem product) {
		return false;
	}

}
