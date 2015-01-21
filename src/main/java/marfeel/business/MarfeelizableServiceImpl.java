package marfeel.business;

import java.util.ArrayList;
import java.util.List;

import marfeel.business.thread.CheckMarfeelizableSiteTask;
import marfeel.controller.dto.Site;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class MarfeelizableServiceImpl implements MarfeelizableService {

	private static final Logger log = LoggerFactory
			.getLogger(MarfeelizableServiceImpl.class);

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	private ApplicationContext context;

	@Override
	public void checkSites(List<Site> sites) {
//		for (Site site : sites) {
//			log.debug(site.toString());
//		}
		
		int numberOfSitesPerTask = calculateNumberOfSitesPerTask(sites.size());
		List<CheckMarfeelizableSiteTask> tasks = createTasks(numberOfSitesPerTask,sites);
		executeTasks(tasks);
		
		while (true) {
			// taskExecutor.initialize();
			int count = taskExecutor.getActiveCount();
			log.debug("Active Threads : " + count);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (count == 0) {
				// taskExecutor.shutdown();
				break;
			}
		}

	}

	private void executeTasks(List<CheckMarfeelizableSiteTask> tasks) {
		for (CheckMarfeelizableSiteTask task : tasks) {
			taskExecutor.execute(task);
		}
	}

	private List<CheckMarfeelizableSiteTask> createTasks(
			int numberOfSitesPerTask, List<Site> sites) {
		log.info("Creating tasks with "+numberOfSitesPerTask+" maximum sites each one");
		List<CheckMarfeelizableSiteTask> tasks = new ArrayList<CheckMarfeelizableSiteTask>();
		
		int fromIndex = 0, toIndex = fromIndex + numberOfSitesPerTask;
		
		while (fromIndex < sites.size()) {
			List<Site> subSites = sites.subList(fromIndex, toIndex);
			CheckMarfeelizableSiteTask task = (CheckMarfeelizableSiteTask) context
					.getBean("checkMarfeelizableSiteTask", subSites);
			fromIndex = toIndex;
			toIndex=fromIndex+numberOfSitesPerTask;
			toIndex = (toIndex > sites.size() ? sites.size() : toIndex);
			tasks.add(task);
		}
		
		log.info("End creating " + tasks.size() + " tasks");

		return tasks;
	}

	private int calculateNumberOfSitesPerTask(int sites) {
		int max = this.taskExecutor.getMaxPoolSize();
		return sites > max ? sites / max : 1;
	}

}
