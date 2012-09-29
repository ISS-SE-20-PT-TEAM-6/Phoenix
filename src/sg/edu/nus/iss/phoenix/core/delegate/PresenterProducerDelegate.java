package sg.edu.nus.iss.phoenix.core.delegate;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import sg.edu.nus.iss.phoenix.core.service.PresenterProducerService;

import sg.edu.nus.iss.phoenix.user.entity.User;

public class PresenterProducerDelegate {
	PresenterProducerService pps;
	
	public PresenterProducerDelegate() {
		pps = new PresenterProducerService();
	}
	
	
	
	public List <User> searchPresenterProducer()throws ParseException, SQLException {
		return pps.searchPresenterProducer();
		
	}

	


}
