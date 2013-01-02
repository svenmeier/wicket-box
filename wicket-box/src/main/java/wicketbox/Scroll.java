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

/**
 * Scroll markup.
 * 
 * @author svenmeier
 */
public class Scroll extends AbstractBoxBehavior {

	private static final long serialVersionUID = 1L;

	private Orientation orientation;

	private IModel<Integer> position;

	private String selector;

	public Scroll(Orientation orientation, String selector) {
		this(orientation, selector, Model.of(0));
	}

	public Scroll(Orientation orientation, String selector,
			IModel<Integer> position) {
		this.orientation = orientation;
		this.selector = selector;

		this.position = position;
	}

	@Override
	public void detach(Component component) {
		super.detach(component);

		position.detach();
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);

		final String id = component.getMarkupId();

		final String persist = getPersist(component);

		final int position = this.position.getObject();

		String initJS = String.format(
				"wicketbox.scroll('%s','%s','%s',%s,%s);", id,
				orientation.name(), selector, persist, position);

		response.render(OnDomReadyHeaderItem.forScript(initJS));
	}

	/**
	 * Sets the new position into the model.
	 * 
	 * @see #onScrolled()
	 */
	@Override
	protected void onPersist(AjaxRequestTarget target, String value) {
		final int position = Integer.parseInt(value);

		this.position.setObject(position);

		onScrolled();
	}

	/**
	 * Called when the scroll position has changed and this behavior uses server
	 * persistence.
	 * 
	 * @see #persistToServer(CharSequence, org.apache.wicket.ajax.AjaxChannel)
	 * 
	 * @param value
	 */
	protected void onScrolled() {
	}
}
