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
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * Synchronize scrolling between markup.
 * 
 * @author svenmeier
 */
public class SynchronizedScroll extends AbstractBoxBehavior {

	private static final long serialVersionUID = 1L;

	private Orientation orientation;

	private IModel<Integer> scroll;

	private String selector;

	public SynchronizedScroll(Orientation orientation, String selector) {
		this(orientation, selector, Model.of(0));
	}

	public SynchronizedScroll(Orientation orientation, String selector,
			IModel<Integer> scroll) {
		this.orientation = orientation;
		this.selector = selector;

		this.scroll = scroll;
	}

	@Override
	public void detach(Component component) {
		super.detach(component);

		scroll.detach();
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);

		final String id = component.getMarkupId();

		final String persist = getPersist(component);

		final int scroll = this.scroll.getObject();

		String initJS = String.format(
				"wicketbox.synchronizedScroll('%s','%s','%s',%s,%s);", id,
				orientation.name(), selector, persist, scroll);

		response.render(OnDomReadyHeaderItem.forScript(initJS));
	}

	/**
	 * @see AbstractBoxBehavior#persistInDocument(String)
	 */
	protected String getPersist(Component component) {
		return persistInDocument("synchronizedScroll:"
				+ component.getPageRelativePath());
	}

	/**
	 */
	@Override
	protected void respond(AjaxRequestTarget target) {
		final RequestCycle requestCycle = RequestCycle.get();

		final int scroll = requestCycle.getRequest().getRequestParameters()
				.getParameterValue("value").toInt();

		this.scroll.setObject(scroll);

		onScrolled();
	}

	/**
	 * See
	 * {@link AbstractBoxBehavior#persistOnServer(String, java.nio.channels.Channel)}
	 * 
	 * @param value
	 */
	protected void onScrolled() {
	}
}
