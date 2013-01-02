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
package wicketbox.examples;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.util.time.Duration;

import wicketbox.Orientation;
import wicketbox.Scroll;

/**
 * @author Sven Meier
 */
public class ScrollExamplePage extends ExamplePage {

	public ScrollExamplePage() {
		WebMarkupContainer scroll = new WebMarkupContainer("scroll");
		scroll.add(new Scroll(Orientation.VERTICAL, ".scroll") {
			@Override
			protected String getPersist(Component component) {
				return persistToCookie(
						"scroll:" + component.getPageRelativePath(),
						Duration.days(1));
			}
		});
		add(scroll);

		scroll.add(new Label("left", new StringResourceModel("lorem", null)));
		scroll.add(new Label("right", new StringResourceModel("lorem", null)));
	}
}