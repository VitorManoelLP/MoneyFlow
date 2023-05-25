package com.moneyflow.moneyflow.enums;

import com.client.common.enums.TipoRendimento;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TipoRendimentoConverter implements AttributeConverter<TipoRendimento, Long> {
	@Override
	public Long convertToDatabaseColumn (TipoRendimento attribute) {
		return attribute.getCodigo();
	}

	@Override
	public TipoRendimento convertToEntityAttribute (Long dbData) {
		return TipoRendimento.getByValue(dbData);
	}
}
