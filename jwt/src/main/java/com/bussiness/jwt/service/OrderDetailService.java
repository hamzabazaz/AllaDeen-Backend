package com.bussiness.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bussiness.jwt.entity.OrderProductQuantity;
import com.bussiness.jwt.entity.Product;
import com.bussiness.jwt.configuration.JwtRequestFilter;
import com.bussiness.jwt.dao.CartDao;
import com.bussiness.jwt.dao.OrderDetailDao;
import com.bussiness.jwt.dao.ProductDao;
import com.bussiness.jwt.dao.UserDao;
import com.bussiness.jwt.entity.OrderInput;
import java.util.List;
import java.util.ArrayList;
import com.bussiness.jwt.entity.OrderDetail;
import com.bussiness.jwt.entity.User;
import com.bussiness.jwt.entity.TransactionDetails;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;

import com.bussiness.jwt.entity.Cart;

@Service
public class OrderDetailService {

	private static String ORDER_PLACED="Placed";
	
	private static final String KEY="rzp_test_UqghXA6rjqWnzB";
	
	private static final String KEY_SECRET="CXXtcJNN3MtkJ5SgSjDlbO1d";
	
	private static final String CURRENCY="INR";
	
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private OrderDetailDao orderDetailDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CartDao cartDao;
	
	
	
	
	public List<OrderDetail> getAllOrderDetails(String status) {
		List<OrderDetail>orderDetails=new ArrayList<>();
		
		if(status.equals("All")) {
			orderDetailDao.findAll().forEach(
					x->orderDetails.add(x));
		}else {
			orderDetailDao.findByOrderStatus(status).forEach(
					x->orderDetails.add(x)
					);
		}
		
		return orderDetails;
	}
	
	public List<OrderDetail> getOrderDetails() {
		String currentUser=JwtRequestFilter.CURRENT_USER;
		 User user=userDao.findById(currentUser).get();
		 return orderDetailDao.findByUser(user);
	}
	
	
	public void placeOrder(OrderInput orderInput,boolean isSingleProductCheckout) {
		 List<OrderProductQuantity>productQuantityList= orderInput.getOrderProductQuantityList();
		 
		 for(OrderProductQuantity o:productQuantityList) {
			 
			 Product product=productDao.findById(o.getProductId()).get();
			 
			 String currentUser=JwtRequestFilter.CURRENT_USER;
			 
			 User user=userDao.findById(currentUser).get();
			 
			 OrderDetail orderDetail=new OrderDetail(
					 orderInput.getFullName(),
					 orderInput.getFullAddress(),
					 orderInput.getContactNumber(),
					 orderInput.getAlternateContactNumber(),
					 ORDER_PLACED,product.getProductDiscountedPrice()*o.getQuantity(),product,user,
					 orderInput.getTransactionId()
					 
					 );
			 
			 //empty the cart
			 if(!isSingleProductCheckout) {
				 List<Cart> carts=cartDao.findByUser(user);
				 carts.stream().forEach(x->cartDao.deleteById(x.getCartId()));
			 }
			 
			 orderDetailDao.save(orderDetail);
		 } 
	}
	
	public void markOrderAsDelivered(Integer orderId) {
		OrderDetail orderDetail=orderDetailDao.findById(orderId).get();
		if(orderDetail!=null) {
			orderDetail.setOrderStatus("Delivered");
			orderDetailDao.save(orderDetail);
		}
	}
	
	public TransactionDetails createTransaction(Double amount) {
		//amount
		//currency
		//key
		//secret key
		try {
		
			JSONObject jsonObject=new JSONObject(); 
			jsonObject.put("amount",(amount*100));
			jsonObject.put("currency",CURRENCY);
			
			
			
		RazorpayClient razorpayClient= new RazorpayClient(KEY,KEY_SECRET);
		
		Order order=razorpayClient.orders.create(jsonObject);
		
		TransactionDetails transactionDetails= prepareTransactionDetails(order);
		
		return transactionDetails;
		
		
		} catch(Exception e){
			System.out.println(e.getMessage());
			
		}
		return null;
	}
	private TransactionDetails prepareTransactionDetails(Order order) {
		String orderId=order.get("id");
		String currency=order.get("currency");
		Integer amount = order.get("amount");
		
		TransactionDetails transactionDetails=new TransactionDetails(orderId,currency,amount,KEY); 
		
		return transactionDetails;
	}
}
