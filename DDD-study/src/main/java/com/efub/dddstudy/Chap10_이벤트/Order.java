package com.efub.dddstudy.Chap10_이벤트;

public class Order {
	public void chanegeShippingInfo(ShippingInfo shippinginfo) {
		verifyNotYetShipped();
		setShippingInfo(newShippingInfo);
		Events.raise(new ShippingInfoChangedEvent(number, newShippingInfo));

	}
}
