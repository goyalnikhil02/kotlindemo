package codemwnci.bootdemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:jobConfiguration.properties")
public class GlobalProperties {
	
	@Value("${job1.days}")
	public String daysType1;
	
	

	@Value("${job2.days}")
	public String daysType2;



	public String getDaysType1() {
		return daysType1;
	}



	public void setDaysType1(String daysType1) {
		this.daysType1 = daysType1;
	}



	public String getDaysType2() {
		return daysType2;
	}



	public void setDaysType2(String daysType2) {
		this.daysType2 = daysType2;
	}
	
	
	
	

}
