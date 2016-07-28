package hr.aportolan.kps.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import hr.aportolan.kps.KpsApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KpsApplication.class)
@WebAppConfiguration

public class ProvisioningSystemRepositoryTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProvisioningSystemRepositoryTest.class);
	@Autowired
	private ProvisioningSystemRequestRepository provisioningSystemRequestRepository;

	@Test
	public void countByRequestId() {
		long count = provisioningSystemRequestRepository.countByRequestId("ObjectId('5797ec5a4418c32c0fecb410')");
		System.out.println("Number of provisioningSystemRequestList:" + count);

	}
}
