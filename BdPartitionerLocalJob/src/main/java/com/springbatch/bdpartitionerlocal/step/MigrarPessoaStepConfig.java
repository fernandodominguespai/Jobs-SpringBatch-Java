package com.springbatch.bdpartitionerlocal.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.bdpartitionerlocal.dominio.Pessoa;

@Configuration
public class MigrarPessoaStepConfig {
  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Autowired
  @Qualifier("transactionManagerApp")
  private PlatformTransactionManager transactionManagerApp;

  @Bean
	public Step migrarPessoaManager(
			ItemReader<Pessoa> pessoaReader,
			ItemWriter<Pessoa> pessoaWriter,
			@Qualifier("pessoaPartitioner")Partitioner partitioner,
			TaskExecutor taskExecutor) {
		
		return stepBuilderFactory
				.get("migrarPessoaManager")
				.partitioner("migrarPessoa.Manager", partitioner)
				.step(migrarPessoaStep(pessoaReader,pessoaWriter))
				.gridSize(10)
				.taskExecutor(taskExecutor)
				.build();
		
	}
	
	private Step migrarPessoaStep(
      ItemReader<Pessoa> pessoaReader, 
      ItemWriter<Pessoa> pessoaWriter) {
    return stepBuilderFactory
        .get("migrarPessoaStep")
        .<Pessoa, Pessoa>chunk(2000)
        .reader(pessoaReader)
        .writer(pessoaWriter)
        .transactionManager(transactionManagerApp)
        .build();
  }
}
