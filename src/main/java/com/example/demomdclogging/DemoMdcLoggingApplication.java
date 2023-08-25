package com.example.demomdclogging;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demomdclogging.business.TransferService;
import com.example.demomdclogging.business.TransferServiceFactory;
import com.example.demomdclogging.config.TransferServiceConfig;
import com.example.demomdclogging.model.Transfer;
import com.example.demomdclogging.task.TransferTask;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class DemoMdcLoggingApplication implements CommandLineRunner {

	@Autowired
	private TransferServiceFactory transferServiceFactory;

	@Autowired
	private TransferServiceConfig transferServiceConfig;

	public static void main(String[] args) {
		SpringApplication.run(DemoMdcLoggingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		TransferService transferService = transferServiceFactory.get(transferServiceConfig.getServiceName());

		final int transfersPageSize = 50;
		List<Transfer> transfers = transferService.getTransferList(transfersPageSize);

		Scanner scanner = new Scanner(System.in);
		boolean running = true;
		while (running) {
			System.out.println("Concurrent processing options");
			System.out.println("1 -> User ThreadPool with sequential task creation");
			System.out.println("2 -> User ThreadPool with parallel task creation");
			System.out.println("3 -> Common ThreadPool with parallel task creation using the ForkJoin framework");
			System.out.println("4 -> Default or Internal ThreadPool with parallel task creation");
			System.out.println("Enter an option or (x) to exit:");

			String option = scanner.nextLine();

			int optionValue = processInput(option);
			if (optionValue < 0) {
				System.out.println("Invalid option");
			} else if (optionValue == 0) {
				running = false;
			} else {
				log.info("Processing transactions...");
				Instant startTime = Instant.now();

				switch (optionValue) {
					case 1:
					case 2:
						ExecutorService executorService = Executors
								.newFixedThreadPool(transferServiceConfig.getTasksPoolSize());

						if (optionValue == 1) {
							// Option 1: this approach starts tasks that runs concurrently on the
							// ThreadPool, but in a sequencial way
							transfers.stream().forEach(
									transfer -> executorService.submit(new TransferTask(transfer, transferService)));
						} else {
							// Option 2: this approach starts tasks that runs concurrently on the
							// ThreadPool, but in a parallel/concurrent way
							transfers.parallelStream().forEach(
									transfer -> executorService.execute(new TransferTask(transfer, transferService)));
						}

						executorService.shutdown();
						executorService.awaitTermination(10L, TimeUnit.MINUTES);
						break;
					case 3:
						// Option 3: this approach starts tasks concurrently on a Common ThreadPool
						// where we can control the parallelism level
						ForkJoinPool customThreadPool = new ForkJoinPool(transferServiceConfig.getTasksPoolSize());
						customThreadPool.submit(() -> transfers.parallelStream()
								.forEach(transfer -> new TransferTask(transfer, transferService).run())).get();
						customThreadPool.shutdown();
						break;
					case 4:
						// Option 4: this approach starts tasks concurrently but without a user created
						// ThreadPool control,
						// and parallelisim/concurrency depends on CPU cores available and managed by
						// the ForkJoinPool
						transfers.parallelStream()
								.forEach(transfer -> new TransferTask(transfer, transferService).run());
						break;
					default:
						break;
				}

				Instant finishTime = Instant.now();
				long timeElapsed = Duration.between(startTime, finishTime).toMillis();
				log.info("Finished! it took: " + timeElapsed / 1000 + " seconds");
			}
		}

		scanner.close();
	}

	private int processInput(String option) {
		if (option == null || option.isEmpty()) {
			return -1;
		}

		int optionValue = 0;
		if (!option.equals("x")) {

			try {
				optionValue = Integer.parseInt(option);
			} catch (NumberFormatException e) {
				return -1;
			}

			if (optionValue < 0 || optionValue > 4) {
				return -1;
			}
		}
		return optionValue;
	}

}
