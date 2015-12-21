package model.productInventory;

import java.util.HashMap;
import java.util.Map;

public class ProductInventoryCollection<ProductCode, ProductInventoryItem> 
	implements com.pillartechnology.vendingMachine.model.productInventory.ProductInventoryCollection<ProductCode, ProductInventoryItem> 
{
	
	private final Map<ProductCode, ProductInventoryItem> map = new HashMap<ProductCode, ProductInventoryItem>();
	
	/* (non-Javadoc)
	 * @see model.vendingMachineInventory.VendingMachineInventoryCollectionInterface#put(ProductCode, VendingMachineInventoryItem)
	 */
	@Override
	public void put(ProductCode productCode, ProductInventoryItem product) {
		map.put(productCode, product);
	}
	
	/* (non-Javadoc)
	 * @see model.vendingMachineInventory.VendingMachineInventoryCollectionInterface#get(ProductCode)
	 */
	@Override
	public ProductInventoryItem get(ProductCode productCode) {
		return map.get(productCode);
	}

}
