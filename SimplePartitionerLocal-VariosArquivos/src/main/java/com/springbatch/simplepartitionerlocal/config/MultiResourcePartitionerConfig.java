package com.springbatch.simplepartitionerlocal.config;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class MultiResourcePartitionerConfig {
	
	@StepScope
	@Bean
	public MultiResourcePartitioner pessoaPartitioner(
			@Value("file:files/pessoas-tmp*") Resource[] resource) {
		MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
		partitioner.setKeyName("file");
		partitioner.setResources(resource);
		return partitioner;
	}
	
	@StepScope
	@Bean
	public MultiResourcePartitioner dadosBancariosPartitioner(
			@Value("file:files/dadosBancarios-tmp*") Resource[] resource) {
		MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
		partitioner.setKeyName("file");
		partitioner.setResources(resource);
		return partitioner;
	}
}
