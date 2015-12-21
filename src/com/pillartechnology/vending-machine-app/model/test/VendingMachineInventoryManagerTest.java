package model.test;

import static org.junit.Assert.*;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.pillartechnology.vendingMachine.model.productInventory.ProductCode;
import com.pillartechnology.vendingMachine.model.productInventory.ProductInventoryCollection;
import com.pillartechnology.vendingMachine.model.productInventory.ProductInventoryItem;
import com.pillartechnology.vendingMachine.model.productInventory.ProductInventoryService;

import model.productInventory.ProductInventoryManager;

public class VendingMachineInventoryManagerTest {
	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	
	private ProductInventoryCollection<ProductCode, ProductInventoryItem> products;
	private ProductInventoryService service;
	private ProductInventoryItem product;
	
	@Before
	public final void setup() {
		products = context.mock(ProductInventoryCollection.class);
		service = context.mock(ProductInventoryService.class);
		product = context.mock(ProductInventoryItem.class);
	}

	@Test
	public final void testFindProduct() {
		final ProductCode productCode = ProductCode.CODE111;
		
		context.checking(new Expectations() {
			{
				oneOf(service).getCatalogByProductCode( with(productCode) );
					will( returnValue(products) );
					
				oneOf(products).get(productCode);
					will( returnValue(product) );
				
			}
		});
		
		final ProductInventoryManager manager = new ProductInventoryManager();
		manager.findProduct(productCode);
	}
	
	/*********************************************************/
	@Test
	public final void testProductNotFound() {
		fail("not yet implemented");
	}

}
