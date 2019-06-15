package codemwnci.bootdemo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	@Scheduled(cron = "${job1.timeexpression}")
	public void job1() throws ParseException {

		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

		Date currentdate = format.parse(LocalDateTime.now().toString());


		///////////////creating a list of accepted date it can taken from any file also ///////////

		List<Date> dateList = new ArrayList<>();
		Date date1 = format.parse("2019-06-10");
		Date date2 = format.parse("2019-06-13");
		Date date3 = format.parse("2019-06-11");
		dateList.add(date1);
		dateList.add(date2);
		dateList.add(date3);

		///////////////created a list of date excepted///////////
		
		
		if (dateList.contains(currentdate)) {

			System.out.println("###" + currentdate);
			
			//here call the rest api

			log.info("Job Runs at time {}", format.format(new Date()));

		} else {
			System.out.println("#Not running ##" + currentdate);

			log.info("Job will not trigger at time {}", format.format(new Date()));
		}
	}
}
