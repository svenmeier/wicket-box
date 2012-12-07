/*
 * Copyright 2009 Sven Meier
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

/**
 * @author svenmeier
 */
public class Stretch extends AbstractBoxBehavior {
	private static final long serialVersionUID = 1L;

	private String leadingSelector;

	private String centerSelector;

	private String trailingSelector;

	private Orientation orientation;

	public Stretch(Orientation orientation, String leadingSelector,
			String centerSelector, String trailingSelector) {
		this.orientation = orientation;

		this.leadingSelector = leadingSelector;
		this.centerSelector = centerSelector;
		this.trailingSelector = trailingSelector;
	}

	@Override
	public final void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);

		final String id = component.getMarkupId();

		String initJS = String
				.format("wicketbox.stretch('%s', '%s', {'leading': '%s', 'center': '%s', 'trailing': '%s'});",
						id, orientation, leadingSelector, centerSelector,
						trailingSelector);
		response.render(OnDomReadyHeaderItem.forScript(initJS));
	}

	@Override
	protected void respond(AjaxRequestTarget target) {
	}
}
