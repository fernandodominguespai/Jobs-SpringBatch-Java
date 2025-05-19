package com.springbatch.bdpartitionerlocal.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.bdpartitionerlocal.dominio.DadosBancarios;

@Configuration
public class MigrarDadosBancariosStepConfig {
  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Autowired
  @Qualifier("transactionManagerApp")
  private PlatformTransactionManager transactionManagerApp;
  
  @Bean
	public Step migrarDadosBancariosManager(
			ItemReader<DadosBancarios>dadosBancariosReader,
			ItemWriter<DadosBancarios> dadosBancariosWriter,
			@Qualifier("dadosBancariosPartitioner")Partitioner partitioner,
			TaskExecutor taskExecutor) {
		
		return stepBuilderFactory
				.get("migrarDadosBancariosManager")
				.partitioner("migrarDadosBancarios.Manager", partitioner)
				.step(migrarDadosBancariosStep(dadosBancariosReader,dadosBancariosWriter))
				.gridSize(10)
				.taskExecutor(taskExecutor)
				.build();
	}
		
	private Step migrarDadosBancariosStep(
	  ItemReader<DadosBancarios> dadosBancariosReader,
      ItemWriter<DadosBancarios> dadosBancariosWriter) {
    return stepBuilderFactory
        .get("migrarDadosBancariosStep")
        .<DadosBancarios, DadosBancarios>chunk(2000)
        .reader(dadosBancariosReader)
        .writer(dadosBancariosWriter)
        .transactionManager(transactionManagerApp)
        .build();
  }
}
