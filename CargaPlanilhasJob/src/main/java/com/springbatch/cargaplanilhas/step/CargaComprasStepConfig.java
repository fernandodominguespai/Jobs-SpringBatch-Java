package com.springbatch.cargaplanilhas.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.cargaplanilhas.dominio.Compra;

@Configuration
public class CargaComprasStepConfig {
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	@Qualifier("transactionManagerApp")
	private PlatformTransactionManager transactionManagerApp;
	
	@Bean
	public Step cargaComprasStep(ItemReader<Compra> reader, ItemWriter<Compra> writer) {
		return stepBuilderFactory
				.get("cargaComprasStep")
				.<Compra, Compra>chunk(1000)
				.reader(reader)
				.writer(writer)
				.transactionManager(transactionManagerApp)
				.build();
	}
}
