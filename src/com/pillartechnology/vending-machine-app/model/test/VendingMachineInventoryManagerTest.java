package model.test;

import static org.junit.Assert.*;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import com.pillartechnology.vendingMachine.model.vendingMachineInventory.VendingMachineInventoryItem;

public class VendingMachineInventoryManagerTest {
	/*********************************************************/
	public interface VendingMachineInventoryCatalog {
		
	}
	
	/*********************************************************/
	public class VendingMachineInventoryManager
		implements com.pillartechnology.vendingMachine.model.vendingMachineInventory.VendingMachineInventoryManager
	{

		private VendingMachineInventoryCatalog catalog;

		public VendingMachineInventoryManager(VendingMachineInventoryCatalog catalog) {
			this.catalog = catalog;
		}

		@Override
		public VendingMachineInventoryItem findProduct(Integer productCode) {
			return null;
		}

	}

	
	/*********************************************************/
	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();

	@Test
	public final void testFindProduct() {
		final Integer productCode = 111;
		final VendingMachineInventoryCatalog catalog = context.mock(VendingMachineInventoryCatalog.class);
		
		context.checking(new Expectations() {
			{
				
			}
		});
		
		final VendingMachineInventoryManager manager = new VendingMachineInventoryManager(catalog);
		manager.findProduct(productCode);
	}

}
