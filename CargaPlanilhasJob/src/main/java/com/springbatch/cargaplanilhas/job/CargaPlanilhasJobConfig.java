package com.springbatch.cargaplanilhas.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@EnableBatchProcessing
public class CargaPlanilhasJobConfig {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Bean
	public Job cargaPlanilahsJob(
			@Qualifier("cargaClientesStep") Step cargaClientesStep,
			@Qualifier("cargaAcessosStep") Step cargaAcessosStep,
			@Qualifier("cargaComprasStep") Step cargaComprasStep,
			@Qualifier("cargaDevolucoesStep") Step cargaDevolucoesStep) {
		return jobBuilderFactory
				.get("cargaPlanilahsJob")
				.start(stepsParalelos(cargaClientesStep, cargaAcessosStep, cargaComprasStep, cargaDevolucoesStep))
				.end()
				.incrementer(new RunIdIncrementer())
				.build();		
	}
	
	private Flow cargaClientesFlow(Step cargaClientesStep) {
		return new FlowBuilder<Flow>("cargaClientesFlow")
				.start(cargaClientesStep)
				.build();
	}
	
	private Flow cargaAcessosFlow(Step cargaAcessosStep) {
		return new FlowBuilder<Flow>("cargaAcessosFlow")
				.start(cargaAcessosStep)
				.build();
	}

	private Flow cargaComprasFlow(Step cargaComprasStep) {
		return new FlowBuilder<Flow>("cargaComprasFlow")
				.start(cargaComprasStep)
				.build();
	}

	private Flow cargaDevolucoesFlow(Step cargaDevolucoesStep) {
		return new FlowBuilder<Flow>("cargaDevolucoesFlow")
				.start(cargaDevolucoesStep)
				.build();
	}
	
	private Flow stepsParalelos(Step cargaClientesStep, 
			Step cargaAcessosStep, 
			Step cargaComprasStep, 
			Step cargaDevolucoesStep) {
		return new FlowBuilder<Flow>("stepsParalelos")
				.start(cargaClientesFlow(cargaClientesStep))
				.split(new SimpleAsyncTaskExecutor())
				.add(cargaAcessosFlow(cargaAcessosStep), 
						cargaComprasFlow(cargaComprasStep),
						cargaDevolucoesFlow(cargaDevolucoesStep))
				.build();
	}
}
