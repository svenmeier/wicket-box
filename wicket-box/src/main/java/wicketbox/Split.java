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
 * Split markup.
 * 
 * @author svenmeier
 */
public class Split extends AbstractBoxBehavior {
	private static final long serialVersionUID = 1L;

	private String mainSelector;

	private String dividerSelector;

	private String remainderSelector;

	private Orientation orientation;

	private IModel<Integer> size;

	public Split(Orientation orientation, String mainSelector,
			String dividerSelector, String remainderSelector) {
		this(orientation, mainSelector, dividerSelector, remainderSelector,
				Model.of(64));
	}

	public Split(Orientation orientation, String mainSelector,
			String dividerSelector, String remainderSelector,
			IModel<Integer> size) {
		this.orientation = orientation;

		this.mainSelector = mainSelector;
		this.dividerSelector = dividerSelector;
		this.remainderSelector = remainderSelector;

		this.size = size;
	}

	@Override
	public void detach(Component component) {
		super.detach(component);

		size.detach();
	}

	@Override
	public final void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);

		final String id = component.getMarkupId();

		final String persist = getPersist(component);

		final int size = this.size.getObject();

		String initJS = String
				.format("wicketbox.split('%s','%s',{main: '%s', divider: '%s', remainder: '%s'}, %s, %s);",
						id, orientation, mainSelector, dividerSelector,
						remainderSelector, persist, size);
		response.render(OnDomReadyHeaderItem.forScript(initJS));
	}

	/**
	 * Hook method to decide where position should be persisted.
	 * 
	 * @return does not persist
	 * @see #persistNot(Component)
	 */
	protected String getPersist(Component component) {
		return persistNot(component);
	}

	/**
	 * Sets the new size into the model.
	 * 
	 * @see #onSplit()
	 */
	@Override
	protected final void respond(AjaxRequestTarget target) {
		final RequestCycle requestCycle = RequestCycle.get();

		final int size = requestCycle.getRequest().getRequestParameters()
				.getParameterValue("value").toInt();

		this.size.setObject(size);

		onSplit();
	}

	/**
	 * Called when the split has changed and this behavior uses server
	 * persistence.
	 * 
	 * @see #persistOnServer(CharSequence, org.apache.wicket.ajax.AjaxChannel)
	 * 
	 * @param value
	 */
	protected void onSplit() {
	}
}
