package org.openmrs.module.mksreports.definition.data.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.Order;
import org.openmrs.api.OrderService;
import org.openmrs.module.mksreports.data.converter.OrderConverter;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.test.context.ContextConfiguration(locations = { "classpath:moduleApplicationContext.xml" }, inheritLocations = true)
public class OrderConverterTest extends BaseModuleContextSensitiveTest {
	
	protected static final String XML_DATASET_PATH = "";
	
	protected static final String XML_REPORT_TEST_DATASET = "reportingTestDataset.xml";
	
	@Autowired
	OrderService orderService;
	
	private List<Order> orders;
	
	@Before
	public void setUp() throws Exception {
		executeDataSet(XML_DATASET_PATH + XML_REPORT_TEST_DATASET);
		
		orders = new ArrayList<Order>();
		orders.add(orderService.getOrder(1));
		orders.add(orderService.getOrder(2));
		orders.add(orderService.getOrder(3));
		
	}
	
	@Test
	public void convert_shouldReturnFormattedString() {
		
		OrderConverter converter = new OrderConverter();
		
		{
			String formattedOrders = (String) converter.convert(orders);
			assertEquals("ASPIRIN, Triomune-30, Triomune-30", formattedOrders);
		}
		
		// add a non drug order
		orders.add(orderService.getOrder(6));
		{
			String formattedOrders = (String) converter.convert(orders);
			assertEquals("ASPIRIN, Triomune-30, Triomune-30, CD4 COUNT", formattedOrders);
		}
	}
	
	@Test
	public void convert_shouldHandleNullInput() {
		
		OrderConverter converter = new OrderConverter();
		assertNull(converter.convert(null));
	}
	
}
