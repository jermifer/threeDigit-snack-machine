package controller.test;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import controller.productSelection.ProductSelectionHandler;

import com.pillartechnology.vendingMachine.controller.productSelection.ProductSelectionLibrary;
import com.pillartechnology.vendingMachine.controller.productSelection.ProductSelectionManager;
import com.pillartechnology.vendingMachine.model.funds.FundsService;
import com.pillartechnology.vendingMachine.view.VendingMachineDisplay;


public class ProductSelectionHandlerTest {
	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();

	private ProductSelectionManager selection;
	private VendingMachineDisplay display;
	private FundsService fundsService;
	private ProductSelectionLibrary library;
	
	@Before
	public final void setup() {
		selection = context.mock(ProductSelectionManager.class);
		display = context.mock(VendingMachineDisplay.class);
		fundsService = context.mock(FundsService.class);
		library = context.mock(ProductSelectionLibrary.class);
	}
	
	@Test
	public final void returnChangeDueAfterPurchase() {
		context.checking(new Expectations() {
			{
				oneOf(library).productSelectionManager();
					will( returnValue(selection) );
				oneOf(library).fundsService();
					will( returnValue(fundsService) );
				oneOf(library).vendingMachineDisplay();
					will( returnValue(display) );
					
				oneOf(fundsService).makeChange();
			}
		});
		
		final ProductSelectionHandler handler = new ProductSelectionHandler(library);
		handler.returnChangeDueAfterPurchase();
	}

	@Test
	public final void completePurchase() {
		final String productName = "M&Ms";
		
		final Integer productPrice = 100;
		context.checking(new Expectations() {
			{
				oneOf(library).productSelectionManager();
					will( returnValue(selection) );
				oneOf(library).fundsService();
					will( returnValue(fundsService) );
				oneOf(library).vendingMachineDisplay();
					will( returnValue(display) );
				
				oneOf(fundsService).addFundsToRespository( productPrice );
				oneOf(selection).clearInputs();
				oneOf(display).promptProductDispensed( with(productName) );
			}
		});
		
		final ProductSelectionHandler handler = new ProductSelectionHandler(library);
			handler.completePurchase(productPrice, productName);
	}
	
	@Test
	public final void declinePurchaseForNotEnoughFunds() {
		final Integer productPrice = 85;
		
		context.checking(new Expectations() {
			{
				oneOf(library).productSelectionManager();
					will( returnValue(selection) );
				oneOf(library).fundsService();
					will( returnValue(fundsService) );
				oneOf(library).vendingMachineDisplay();
					will( returnValue(display) );
					
				oneOf(display).messageProductCost(productPrice);
			}
		});
		
		final ProductSelectionHandler handler = new ProductSelectionHandler(library);
			handler.declinePurchaseForNotEnoughFunds(productPrice);
	}
	
	@Test
	public final void clearInputsWithoutFundsOnDeposit() {
		context.checking(new Expectations() {
			{
				oneOf(library).productSelectionManager();
					will( returnValue(selection) );
				oneOf(library).fundsService();
					will( returnValue(fundsService) );
				oneOf(library).vendingMachineDisplay();
					will( returnValue(display) );
					
				oneOf(selection).clearInputs(); 
				oneOf(display).messageMakeSelection();
			}
		});
		
		final ProductSelectionHandler handler = new ProductSelectionHandler(library);
			handler.clearInputsWithoutFundsOnDeposit();
	}
	
	@Test
	public final void clearInputsWithFundsOnDeposit() {
		final Integer depositAmount = 100;
		
		context.checking(new Expectations() {
			{
				oneOf(library).productSelectionManager();
					will( returnValue(selection) );
				oneOf(library).fundsService();
					will( returnValue(fundsService) );
				oneOf(library).vendingMachineDisplay();
					will( returnValue(display) );
				
				oneOf(selection).clearInputs(); 
				oneOf(display).messageAmountOnDeposit(depositAmount);
			}
		});
		
		final ProductSelectionHandler handler = new ProductSelectionHandler(library);
			handler.clearInputsWithFundsOnDeposit(depositAmount);
	}
	
	@Test
	public final void refundDeposit() {
		context.checking(new Expectations() {
			{
				oneOf(library).productSelectionManager();
					will( returnValue(selection) );
				oneOf(library).fundsService();
					will( returnValue(fundsService) );
				oneOf(library).vendingMachineDisplay();
					will( returnValue(display) );
				
				oneOf(fundsService).refundFundsOnDeposit();
				oneOf(selection).clearInputs();
				oneOf(display).messageMakeSelection();
			}
		});
		
		final ProductSelectionHandler handler = new ProductSelectionHandler(library);
			handler.refundDeposit();
	}

	@Test
	public final void rejectSelection() {
		context.checking(new Expectations() {
			{
				oneOf(library).productSelectionManager();
					will( returnValue(selection) );
				oneOf(library).fundsService();
					will( returnValue(fundsService) );
				oneOf(library).vendingMachineDisplay();
					will( returnValue(display) );
					
				oneOf(display).messageInvalidSelection();
			}
		});
		
		final ProductSelectionHandler handler = new ProductSelectionHandler(library);
			handler.rejectSelection();
	}
	
	@Test
	public final void declinePurchaseForTooMuchDeposited() {
		final Integer productPrice = 110;
		
		context.checking(new Expectations() {
			{
				oneOf(library).productSelectionManager();
					will( returnValue(selection) );
				oneOf(library).fundsService();
					will( returnValue(fundsService) );
				oneOf(library).vendingMachineDisplay();
					will( returnValue(display) );
					
				oneOf(fundsService).refundFundsOnDeposit();
				oneOf(selection).clearInputs();
				oneOf(display).messageExactChangeOnly();
			}
		});
		
		final ProductSelectionHandler handler = new ProductSelectionHandler(library);
			handler.declinePurchaseForTooMuchDeposited(productPrice);
	}

}
