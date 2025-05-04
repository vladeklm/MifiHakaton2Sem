package it.globus.finaudit.config;

import it.globus.finaudit.repository.OperationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@Profile("test")
public class TestDataLoader implements CommandLineRunner {
    private final OperationRepository operationRepository;
    private final DataSource dataSource;

    public TestDataLoader(OperationRepository operationRepository, DataSource dataSource) {
        this.operationRepository = operationRepository;
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        if (operationRepository.count() == 0) {
            executeSqlScript();
        }
    }

    private void executeSqlScript() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            ClassPathResource resource = new ClassPathResource("test.sql");
            ScriptUtils.executeSqlScript(connection, resource);
        }
    }
}
