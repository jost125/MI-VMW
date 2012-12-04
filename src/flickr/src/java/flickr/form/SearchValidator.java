package flickr.form;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author Ondrej Karas
 */
public class SearchValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Search.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "term", "term.required");
	}

}
