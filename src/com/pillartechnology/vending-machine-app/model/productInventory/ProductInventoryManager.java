package model.productInventory;

import com.pillartechnology.vendingMachine.model.productInventory.ProductCode;
import com.pillartechnology.vendingMachine.model.productInventory.ProductCode;
import com.pillartechnology.vendingMachine.model.productInventory.ProductInventoryCollection;
import com.pillartechnology.vendingMachine.model.productInventory.ProductInventoryItem;

import model.productInventory.ProductInventoryService;

/*********************************************************/
public class ProductInventoryManager
	implements com.pillartechnology.vendingMachine.model.productInventory.ProductInventoryManager
{
	final ProductInventoryCollection products;

	public ProductInventoryManager() {
		final ProductInventoryService service = new ProductInventoryService();
		this.products = service.getCatalogByProductCode();
	}

	@Override
	public ProductInventoryItem findProduct(ProductCode productCode) {
		return this.products.get(productCode);
	}

}