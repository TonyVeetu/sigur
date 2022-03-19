package ru.uteev.Sigur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.uteev.Sigur.service.EmployeeManager;
import ru.uteev.Sigur.service.GuestManager;
import ru.uteev.Sigur.service.PassEmulator;
import ru.uteev.Sigur.service.VirtualTime;

@SpringBootApplication
@EnableScheduling
public class SigurApplication {

	@Autowired
	private EmployeeManager employeeManager;

	@Autowired
	private GuestManager guestManager;

	@Autowired
	private PassEmulator passEmulator;

	@Autowired
	private VirtualTime virtualTime;

	public static void main(String[] args) {
		SpringApplication.run(SigurApplication.class, args);
	}

	@Scheduled(fixedRate = 10_000)
	public void workForAllComponent() {
		employeeManager.work();
		guestManager.work();
		passEmulator.work();
		virtualTime.work();
	}

}
