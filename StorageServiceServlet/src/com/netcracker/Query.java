package com.netcracker;

/**
 * Valid query to do. Must be received only as result of {@link QueryHandler#parseQuery(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse) QueryHandler.parseQuery} method.
 * @author Red_Shuhov
 *
 */
public class Query {

	/**
	 * {@code Key:value} relation in query.
	 */
	Entry entry;
	
	/**
	 * Action to be proceeded with {@code entry}.
	 */
	Action action;

	/**
	 * Query constructor, defining, that new query can be formed only with valid {@code action} and {@code entry} fields.
	 * @param action - action to do.
	 * @param entry - entry to work with.
	 */
	public Query(Action action, Entry entry) {
		this.action = action;
		this.entry = entry;
	}
	
	
}