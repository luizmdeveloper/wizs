package br.com.luizmariodev.wizs.domain.infrastructure.specifactions;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import br.com.luizmariodev.wizs.api.model.filter.CustomerFilter;
import br.com.luizmariodev.wizs.domain.model.Customer;

public class CustomerSpecification {

	
	public static Specification<Customer> find(CustomerFilter filter) {
		return (root, query, builder) -> {
			
			var predicates = new ArrayList<Predicate>();
			
			if (!StringUtils.isEmpty(filter.getName()))
				predicates.add(builder.like(builder.upper(root.get("name")), "%" + filter.getName().toUpperCase() + "%"));

			if (!StringUtils.isEmpty(filter.getDocumentNumber()))
				predicates.add(builder.equal(root.get("documentNumber"), filter.getDocumentNumber()));			
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
	
}
