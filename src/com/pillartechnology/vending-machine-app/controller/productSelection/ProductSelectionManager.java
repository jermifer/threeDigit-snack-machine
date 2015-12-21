package controller.productSelection;

import com.pillartechnology.vendingMachine.model.productInventory.ProductCode;
import com.pillartechnology.vendingMachine.model.productInventory.ProductInventoryItem;

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
	public boolean isPurchasable(ProductInventoryItem product) {
		return false;
	}

	@Override
	public ProductCode getProductCodeFrom(Integer input) {
		// TODO Auto-generated method stub
		return null;
	}

}
