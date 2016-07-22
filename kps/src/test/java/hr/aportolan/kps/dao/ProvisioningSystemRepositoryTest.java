package hr.aportolan.kps.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import hr.aportolan.kps.KpsApplication;
import hr.aportolan.kps.entity.ProvisioningSystem;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KpsApplication.class)
@WebAppConfiguration

public class ProvisioningSystemRepositoryTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProvisioningSystemRepositoryTest.class);
	@Autowired
	private ProvisioningSystemRepository provisioningSystemRepository;

	@Test
	public void save() {
		ProvisioningSystem ps = new ProvisioningSystem();
		ps.setIpAddress("localhost:8400");
		provisioningSystemRepository.save(ps);
	}

	@Test
	public void select() {
		List<ProvisioningSystem> provisioningSystemsList = provisioningSystemRepository.findAll();
		LOGGER.debug("provisioningSystemsList:{}", provisioningSystemsList);

	}
}
