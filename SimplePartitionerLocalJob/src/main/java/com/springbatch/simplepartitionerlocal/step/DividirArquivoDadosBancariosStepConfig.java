package com.springbatch.simplepartitionerlocal.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.simplepartitionerlocal.dominio.DadosBancarios;
import com.springbatch.simplepartitionerlocal.dominio.Pessoa;

@Configuration
public class DividirArquivoDadosBancariosStepConfig {
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step dividirArquivoDadosBancariosStep(
			FlatFileItemReader<DadosBancarios> dadosBancariosReader,
			MultiResourceItemWriter<DadosBancarios> dadosBancariosWriter) {
		
		return stepBuilderFactory
				.get("dividirArquivoDadosBancariosStep")
				.<DadosBancarios, DadosBancarios>chunk(2000)
				.reader(dadosBancariosReader)
				.writer(dadosBancariosWriter)
				.stream(dadosBancariosWriter)
				.build();
		
	}

}
