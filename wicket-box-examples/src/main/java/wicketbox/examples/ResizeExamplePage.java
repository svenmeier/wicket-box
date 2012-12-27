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

import wicketbox.Orientation;
import wicketbox.Resize;

/**
 * @author Sven Meier
 */
public class ResizeExamplePage extends ExamplePage {

	public ResizeExamplePage() {
		WebMarkupContainer resize = new WebMarkupContainer("resize");
		resize.add(new Resize(Orientation.VERTICAL, ".initiator") {
			@Override
			protected String getPersist(Component component) {
				return persistInCookie(
						"resize:" + component.getPageRelativePath(),
						30 * 24 * 60 * 60);
			}
		});
		add(resize);

		resize.add(new Label("body", new StringResourceModel("lorem", null)));
	}
}