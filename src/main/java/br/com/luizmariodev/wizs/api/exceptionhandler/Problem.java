package br.com.luizmariodev.wizs.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Problem {

	private Integer status;
	private String type;
	private String title;
	private String detail;
	private LocalDateTime timestamp;
	private String mensageUser;
	private List<Property> properties;
	
	@Setter
	@Getter
	@Builder
	public static class Property {		
		private String name;
		private String mensageUser;		
	}
}
