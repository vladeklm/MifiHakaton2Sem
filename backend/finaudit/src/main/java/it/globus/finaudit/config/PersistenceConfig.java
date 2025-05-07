@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "it.globus.finaudit.repository")
@EnableJpaAuditing
public class PersistenceConfig {
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
