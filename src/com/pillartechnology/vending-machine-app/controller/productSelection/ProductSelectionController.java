package controller.productSelection;

import com.pillartechnology.vendingMachine.model.funds.FundsService;
import com.pillartechnology.vendingMachine.model.productInventory.ProductInventoryItem;
import com.pillartechnology.vendingMachine.model.productInventory.ProductInventoryManager;

import com.pillartechnology.vendingMachine.model.productInventory.ProductCode;

import com.pillartechnology.vendingMachine.controller.productSelection.ProductSelectionHandler;
import com.pillartechnology.vendingMachine.controller.productSelection.ProductSelectionLibrary;
import com.pillartechnology.vendingMachine.controller.productSelection.ProductSelectionManager;

public class ProductSelectionController 
	implements com.pillartechnology.vendingMachine.controller.productSelection.ProductSelectionController
{

	private final ProductSelectionManager selection;
	private final FundsService fundsService;
	private final ProductInventoryManager inventory;
	private final ProductSelectionHandler handler;

	public ProductSelectionController(
		ProductSelectionLibrary library, 
		ProductSelectionHandler handler
	) {
		this.selection = library.productSelectionManager();
		this.fundsService = library.fundsService();
		this.inventory = library.inventory();
		this.handler = handler;
	}

	public void onRefund() {
		this.handler.refundDeposit();
	}
	
	public void onClear() {
		Integer deposit = this.fundsService.sumOfFundsOnDeposit(); 

		if( deposit > 0 ) {
			this.handler.clearInputsWithFundsOnDeposit(deposit);

		} else {
			this.handler.clearInputsWithoutFundsOnDeposit();
		}
	}

	public void onInput(Integer input) {
		ProductInventoryItem product;
		
		if( this.selection.isCompleteProductCode(input) ) {
			ProductCode productCode = this.selection.getProductCodeFrom(input);

			//try to find the product in the inventory
			product = inventory.findProduct(productCode);
			
			//product was found and is in stock
			if( this.selection.isPurchasable(product) ) {
				Integer deposit = this.fundsService.sumOfFundsOnDeposit();
				Integer productPrice = product.productPrice();
				
				//not enough funds to buy product
				if( deposit < productPrice ) {
					this.handler.declinePurchaseForNotEnoughFunds(productPrice);
					
				//deposit exceeds cost of product and machine is not able to make change
				} else if( deposit > productPrice && !this.fundsService.isAbleToMakeChange() ) {
					this.handler.declinePurchaseForTooMuchDeposited(productPrice);
						
				//purchase can be completed
				} else {
					String productName = product.productName();
					
					this.handler.completePurchase( productPrice, productName );
					
					//amount of funds on deposit exceeds cost of product
					if( deposit > productPrice ) {
						this.handler.returnChangeDueAfterPurchase();
					}
				}
				
			//product code did not resolve to a product, or else the product is not in stock
			} else {
				this.handler.rejectSelection();
			}
		}
	}
}