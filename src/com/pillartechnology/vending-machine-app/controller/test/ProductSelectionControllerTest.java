package controller.test;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import controller.productSelection.ProductSelectionController;

import com.pillartechnology.vendingMachine.controller.productSelection.ProductSelectionHandler;
import com.pillartechnology.vendingMachine.controller.productSelection.ProductSelectionLibrary;
import com.pillartechnology.vendingMachine.model.funds.FundsService;
import com.pillartechnology.vendingMachine.model.vendingMachineInventory.VendingMachineInventoryManager;
import com.pillartechnology.vendingMachine.model.vendingMachineInventory.VendingMachineInventoryItem;
import com.pillartechnology.vendingMachine.controller.productSelection.ProductSelectionManager;
import com.pillartechnology.vendingMachine.view.VendingMachineDisplay;

import org.jmock.integration.junit4.JUnitRuleMockery;

import org.jmock.Expectations;

public class ProductSelectionControllerTest {
	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	
	private ProductSelectionManager selection;
	private VendingMachineDisplay display;
	private FundsService fundsService;
	private VendingMachineInventoryManager inventory;
	private VendingMachineInventoryItem product;
	private ProductSelectionHandler handler;
	private ProductSelectionLibrary library;
	
	@Before
	public final void setup() {
		selection = context.mock(ProductSelectionManager.class);
		display = context.mock(VendingMachineDisplay.class);
		fundsService = context.mock(FundsService.class);
		inventory = context.mock(VendingMachineInventoryManager.class);
		product = context.mock(VendingMachineInventoryItem.class);
		library = context.mock(ProductSelectionLibrary.class);
		handler = context.mock(ProductSelectionHandler.class);
	}
	

	/**********************************************************************************************
	 * @author jennifer.mankin
	 *
	 */
	@Test
	public final void testMakeSelectionProductInStockPriceExceedsDeposit() {
		final Integer input = 111;
		final Integer productPrice = 100;
		
		context.checking(new Expectations() {
			{
				oneOf(library).productSelectionManager();
					will( returnValue(selection) );
				oneOf(library).fundsService();
					will( returnValue(fundsService) );
				oneOf(library).inventory();
					will( returnValue(inventory) );
				
				oneOf(selection).isCompleteProductCode(input); 
 					will( returnValue(true) );
 				
 				oneOf(inventory).findProduct( with(input) );
 					will(returnValue(product));
 					
 				oneOf(selection).isPurchasable( with(any(VendingMachineInventoryItem.class)) );
 					will( returnValue(true) );
 				
 				oneOf(product).productPrice();
 					will( returnValue(productPrice) );
 					
 				oneOf(fundsService).sumOfFundsOnDeposit();
 					will( returnValue(productPrice-10) );
 					
 				oneOf(handler).declinePurchaseForNotEnoughFunds(productPrice);
 				
 				never(fundsService).makeChange();
 				never(fundsService).addFundsToRespository(productPrice);
 				never(selection).clearInputs();
			}
		});
		
		final ProductSelectionController controller = new ProductSelectionController(library, handler);
		controller.onInput(input);
	}

	/**********************************************************************************************
	 * @author jennifer.mankin
	 *
	 */
	@Test
	public final void testMakeSelectionProductInStockFundsMatchPrice() {
		final Integer input = 111;
		final String productName = "M&Ms";
		final Integer productPrice = 100;
		
		context.checking(new Expectations() {
			{
				oneOf(library).productSelectionManager();
					will( returnValue(selection) );
				oneOf(library).fundsService();
					will( returnValue(fundsService) );
				oneOf(library).inventory();
					will( returnValue(inventory) );
				
				oneOf(selection).isCompleteProductCode(input); 
					will( returnValue(true) );

				oneOf(inventory).findProduct( with(input) );
					will(returnValue(product));
					
				oneOf(selection).isPurchasable( with(any(VendingMachineInventoryItem.class)) );
					will( returnValue(true) );

				oneOf(product).productPrice();
					will( returnValue(productPrice) );
					
				oneOf(fundsService).sumOfFundsOnDeposit();
					will( returnValue(productPrice) );
				
				oneOf(product).productName();
					will( returnValue(productName) );
					
				oneOf(handler).completePurchase(productPrice, productName);
					
				never(fundsService).makeChange();
			}
		});
		
		final ProductSelectionController controller = new ProductSelectionController(library, handler);
		controller.onInput(111);
	}
	
	/**********************************************************************************************
	 * @author jennifer.mankin
	 *
	 */
	@Test
	public final void testMakeSelectionProductInStockFundsExceedPriceAbleToMakeChange() {
		final Integer input = 111;
		final String productName = "M&Ms";
		final Integer productPrice = 100;
		
		context.checking(new Expectations() {
			{
				oneOf(library).productSelectionManager();
					will( returnValue(selection) );
				oneOf(library).fundsService();
					will( returnValue(fundsService) );
				oneOf(library).inventory();
					will( returnValue(inventory) );
				
				oneOf(selection).isCompleteProductCode(input); 
					will( returnValue(true) );
				
				oneOf(inventory).findProduct( with(input) );
					will(returnValue(product));
				
				oneOf(selection).isPurchasable( with(any(VendingMachineInventoryItem.class)) );
					will( returnValue(true) );
				
				oneOf(product).productPrice();
					will( returnValue(productPrice) );
				
				oneOf(fundsService).sumOfFundsOnDeposit();
					will( returnValue(productPrice+10) );
					
				oneOf(fundsService).isAbleToMakeChange();
					will( returnValue(true) );
					
				oneOf(product).productName();
					will( returnValue(productName) );
				
				oneOf(handler).completePurchase(productPrice, productName);
				oneOf(handler).returnChangeDueAfterPurchase();
				
				never(fundsService).refundFundsOnDeposit();
			}
		});
		
		final ProductSelectionController controller = new ProductSelectionController(library, handler);
		controller.onInput(111);
	}
	
	/**********************************************************************************************
	 * @author jennifer.mankin
	 *
	 */
	@Test
	public final void testMakeSelectionProductInStockFundsExceedPriceExactChangeOnly() {
		final Integer input = 111;
		final String productName = "M&Ms";
		final Integer productPrice = 100;
		
		context.checking(new Expectations() {
			{
				oneOf(library).productSelectionManager();
					will( returnValue(selection) );
				oneOf(library).fundsService();
					will( returnValue(fundsService) );
				oneOf(library).inventory();
					will( returnValue(inventory) );
					
				oneOf(selection).isCompleteProductCode(input); 
					will( returnValue(true) );
				
				oneOf(inventory).findProduct( with(input) );
					will(returnValue(product));
				
				oneOf(selection).isPurchasable( with(any(VendingMachineInventoryItem.class)) );
					will( returnValue(true) );
				
				oneOf(product).productPrice();
					will( returnValue(productPrice) );
				
				oneOf(fundsService).sumOfFundsOnDeposit();
					will( returnValue(productPrice+10) );
					
				oneOf(fundsService).isAbleToMakeChange();
					will( returnValue(false) );
					
				oneOf(handler).declinePurchaseForTooMuchDeposited(productPrice);
				
				never(fundsService).makeChange();
				never(fundsService).addFundsToRespository( productPrice );
				never(display).promptProductDispensed( with(productName) );
			}
		});
		
		final ProductSelectionController controller = new ProductSelectionController(library, handler); 
		controller.onInput(111);
	}
	
	/**********************************************************************************************
	 * @author jennifer.mankin
	 *
	 */
	@Test
	public final void testMakeSelectionProductCodeIsIncomplete() {
		context.checking(new Expectations() {
			{
				oneOf(library).productSelectionManager();
					will( returnValue(selection) );
				oneOf(library).fundsService();
					will( returnValue(fundsService) );
				oneOf(library).inventory();
					will( returnValue(inventory) );
				
				//make sure that this method is always called
				oneOf(selection).isCompleteProductCode( with(1) );
					will( returnValue(false) );
			}
		});
		
		final ProductSelectionController controller = new ProductSelectionController(library, handler); 
		controller.onInput(1);
	}
	
	/**********************************************************************************************
	 * @author jennifer.mankin
	 *
	 */
	@Test
	public final void testMakeSelectionProductCodeIsComplete() {
		final Integer input = 123;
		
		context.checking(new Expectations() {
			{
				oneOf(library).productSelectionManager();
					will( returnValue(selection) );
				oneOf(library).fundsService();
					will( returnValue(fundsService) );
				oneOf(library).inventory();
					will( returnValue(inventory) );
					
				oneOf(selection).isCompleteProductCode( with(input) );
					will( returnValue(true) );
					
				oneOf(inventory).findProduct( with(input) );
					
				oneOf(selection).isPurchasable( with(any(VendingMachineInventoryItem.class)) );
				
				allowing(fundsService);
				allowing(handler);
			}
		});
		
		final ProductSelectionController controller = new ProductSelectionController(library, handler); 
		controller.onInput(123);
	}
	
	/**********************************************************************************************
	 * @author jennifer.mankin
	 *
	 */
	@Test
	public final void testMakeSelectionProductFound() {
		final Integer input = 111;
		
		context.checking(new Expectations() {
			{
			oneOf(library).productSelectionManager();
				will( returnValue(selection) );
			oneOf(library).fundsService();
				will( returnValue(fundsService) );
			oneOf(library).inventory();
				will( returnValue(inventory) );
				
				oneOf(selection).isCompleteProductCode(input); 
					will( returnValue(true) );
				
				oneOf(inventory).findProduct( with(input) );
					will(returnValue(product));
					
				oneOf(selection).isPurchasable( with(any(VendingMachineInventoryItem.class)) );
				
				allowing(product);
				allowing(handler);
				allowing(fundsService);
			}
		});
		
		final ProductSelectionController controller = new ProductSelectionController(library, handler); 
		controller.onInput(111);
	}
	
	/**********************************************************************************************
	 * @author jennifer.mankin
	 *
	 */
	@Test
	public final void testMakeSelectionProductNotFound() {
		final Integer input = 300;
		
		context.checking(new Expectations() {
			{
				oneOf(library).productSelectionManager();
					will( returnValue(selection) );
				oneOf(library).fundsService();
					will( returnValue(fundsService) );
				oneOf(library).inventory();
					will( returnValue(inventory) );
					
				oneOf(selection).isCompleteProductCode(input); 
					will( returnValue(true) );
				
				oneOf(inventory).findProduct( with(input) );
					will( returnValue(null) );
					
				oneOf(selection).isPurchasable( with(aNull(VendingMachineInventoryItem.class)) );
					//b/c product wasn't found
					will( returnValue(false) );
				
				oneOf(handler).rejectSelection();
			}
		});
		
		final ProductSelectionController controller = new ProductSelectionController(library, handler); 
		controller.onInput(input);
	}

	/**********************************************************************************************
	 * @author jennifer.mankin
	 *
	 */
	@Test
	public final void testMakeSelectionProductOutOfStock() {
		final Integer input = 300;
		
		context.checking(new Expectations() {
			{
				oneOf(library).productSelectionManager();
					will( returnValue(selection) );
				oneOf(library).fundsService();
					will( returnValue(fundsService) );
				oneOf(library).inventory();
					will( returnValue(inventory) );
					
				oneOf(selection).isCompleteProductCode(input); 
					will( returnValue(true) );
				
				oneOf(inventory).findProduct( with(input) );
					will(returnValue(product));
					
				oneOf(selection).isPurchasable( with(any(VendingMachineInventoryItem.class)) );
					//b/c quantity of product in inventory is 0
					will( returnValue(false) );
				
				oneOf(handler).rejectSelection();
			}
		});
		
		final ProductSelectionController controller = new ProductSelectionController(library, handler); 
		controller.onInput(input);
	}

	/**********************************************************************************************
	 * @author jennifer.mankin
	 *
	 */
	@Test
	public final void testRefund() {
		//same behavior regardless of whether or not there is money on deposit
		context.checking(new Expectations() {
			{
				oneOf(library).productSelectionManager();
					will( returnValue(selection) );
				oneOf(library).fundsService();
					will( returnValue(fundsService) );
				oneOf(library).inventory();
					will( returnValue(inventory) );
				
				oneOf(handler).refundDeposit();
			}
		});
		
		final ProductSelectionController controller = new ProductSelectionController(library, handler); 
		controller.onRefund();
	}
	
	/**********************************************************************************************
	 * @author jennifer.mankin
	 *
	 */
	@Test
	public final void testClear() {
		context.checking(new Expectations() {
			{
				oneOf(library).productSelectionManager();
					will( returnValue(selection) );
				oneOf(library).fundsService();
					will( returnValue(fundsService) );
				oneOf(library).inventory();
					will( returnValue(inventory) );
				
				oneOf(fundsService).sumOfFundsOnDeposit(); 
					
				oneOf(handler); 
			}
		});
		
		final ProductSelectionController controller = new ProductSelectionController(library, handler); 
		controller.onClear();
	}
	
	/**********************************************************************************************
	 * @author jennifer.mankin
	 *
	 */
	@Test
	public final void testClearWhenNoFundsOnDeposit() {
		context.checking(new Expectations() {
			{
				oneOf(library).productSelectionManager();
					will( returnValue(selection) );
				oneOf(library).fundsService();
					will( returnValue(fundsService) );
				oneOf(library).inventory();
					will( returnValue(inventory) );
					
				oneOf(fundsService).sumOfFundsOnDeposit(); 
					will(returnValue(0));
				
				oneOf(handler).clearInputsWithoutFundsOnDeposit();
			}
		});
		
		final ProductSelectionController controller = new ProductSelectionController(library, handler); 
		controller.onClear();
	}
	
	/**********************************************************************************************
	 * @author jennifer.mankin
	 *
	 */
	@Test
	public final void testClearWhenFundsOnDeposit() {
		final Integer depositAmount = 1;
		
		context.checking(new Expectations() {
			{
				oneOf(library).productSelectionManager();
					will( returnValue(selection) );
				oneOf(library).fundsService();
					will( returnValue(fundsService) );
				oneOf(library).inventory();
					will( returnValue(inventory) );
					
				oneOf(fundsService).sumOfFundsOnDeposit(); 
					will(returnValue(depositAmount));
					
				oneOf(handler).clearInputsWithFundsOnDeposit(depositAmount);	
			}
		});

		final ProductSelectionController controller = new ProductSelectionController(library, handler); 
		controller.onClear();
	}
	
}
