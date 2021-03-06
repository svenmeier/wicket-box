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

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;

import wicketbox.util.CollectionFormattable;

/**
 * Resize table columns.
 * 
 * @author svenmeier
 */
public class ColResize extends AbstractBoxBehavior {

	private static final long serialVersionUID = 1L;

	private IModel<List<Integer>> widths;

	private String headerSelector;

	private String bodySelector;

	public ColResize(String headerSelector, String bodySelector,
			IModel<List<Integer>> widths) {
		Args.notNull(widths, "widths");

		this.headerSelector = headerSelector;
		this.bodySelector = bodySelector;
		this.widths = widths;
	}

	@Override
	public void detach(Component component) {
		super.detach(component);

		this.widths.detach();
	}

	@Override
	public final void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);

		String initJS = String.format(
				"wicketbox.colresize('%s',{header: '%s', body: '%s'},%s,%s);",
				component.getMarkupId(), headerSelector, bodySelector,
				getPersist(component),
				new CollectionFormattable(this.widths.getObject()));
		response.render(OnDomReadyHeaderItem.forScript(initJS));
	}

	/**
	 * Sets the new widths into the model.
	 * 
	 * @see #onResized()
	 */
	@Override
	protected void onPersist(AjaxRequestTarget target, String value) {
		List<Integer> widths = new ArrayList<Integer>();
		for (String string : value.split(":")) {
			try {
				widths.add(Integer.parseInt(string));
			} catch (Exception noInteger) {
				break;
			}
		}

		this.widths.setObject(widths);

		onResized(target);
	}

	/**
	 * Called when the widths have changed and this behavior uses server
	 * persistence.
	 * 
	 * @see #persistToServer(CharSequence, org.apache.wicket.ajax.AjaxChannel)
	 * 
	 * @param value
	 */
	protected void onResized(AjaxRequestTarget target) {
	}
}
