package fr.limsi.negotiate;

/** Interface for a Criterion class (Enum).
 * @see CriterionNegotiation for use.
 *
 */

public interface Criterion {
	Criterion [] getValues();
	String getFrVersion();
	String print(String topic);
}

