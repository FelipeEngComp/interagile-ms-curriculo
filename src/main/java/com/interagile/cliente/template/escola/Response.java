package com.interagile.cliente.template.escola;

import java.util.List;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Response<T> {
	private int status;
	private T data;
	private List<String> erros;
}
