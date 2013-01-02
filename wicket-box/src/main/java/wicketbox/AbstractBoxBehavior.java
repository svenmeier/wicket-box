/*
 * Copyright 2012 Sven Meier
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicketbox;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxChannel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.AbstractTextComponent;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.time.Duration;

/**
 * Base class for all box behaviors.
 * 
 * @author svenmeier
 */
public abstract class AbstractBoxBehavior extends AbstractDefaultAjaxBehavior {

	public static final ResourceReference JS = new JavaScriptResourceReference(
			AbstractBoxBehavior.class, "wicket-box.js");

	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);

		response.render(JavaScriptHeaderItem.forReference(JS));
	}

	/**
	 * Hook method to decide about persistence, by default not persisted.
	 * 
	 * @return does not persist
	 * 
	 * @see #persistNot(Component)
	 */
	protected String getPersist(Component component) {
		return persistNot();
	}

	/**
	 * Handle persistence.
	 * 
	 * @see #onPersist(AjaxRequestTarget, String)
	 */
	@Override
	protected final void respond(AjaxRequestTarget target) {
		final RequestCycle requestCycle = RequestCycle.get();

		final String value = requestCycle.getRequest().getRequestParameters()
				.getParameterValue("value").toString();

		onPersist(target, value);
	}

	/**
	 * Persist a new value.
	 * 
	 * @param target
	 * @param value
	 */
	protected void onPersist(AjaxRequestTarget target, String value) {
	}

	/**
	 * Not persisted.
	 */
	public static String persistNot() {
		return String.format("wicketbox.persistNot()");
	}

	/**
	 * Persist to a cookie.
	 * 
	 * @param key
	 *            key of cookie
	 * @param maxAge
	 *            maximum age for cookie
	 */
	public static String persistToCookie(String key, Duration maxAge) {
		return String.format("wicketbox.persistToCookie('%s',%s)", key,
				maxAge.seconds());
	}

	/**
	 * Persist to the containing document.
	 * 
	 * @param key
	 *            key for jQuery data
	 */
	public static String persistToDocument(String key) {
		return String.format("wicketbox.persistToDocument('%s')", key);
	}

	/**
	 * Persist to the given input component.
	 * 
	 * @param callbackUrl
	 *            url for callback
	 * 
	 * @see #onEvent(Component, org.apache.wicket.event.IEvent)
	 */
	public static String persistToText(AbstractTextComponent<?> text) {
		return String.format("wicketbox.persistToInput('%s')",
				text.getInputName());
	}

	/**
	 * Persist to the server.
	 * 
	 * @param callbackUrl
	 *            url for callback
	 * 
	 * @see #onEvent(Component, org.apache.wicket.event.IEvent)
	 */
	public static String persistToServer(CharSequence callbackUrl,
			AjaxChannel channel) {
		return String.format("wicketbox.persistToServer('%s', '%s')",
				callbackUrl, channel);
	}
}