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

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.PropertyModel;

import wicketbox.Resize;

/**
 * @author Sven Meier
 */
public class ResizeExamplePage extends ExamplePage {

	public ResizeExamplePage() {
		WebMarkupContainer resizable = new WebMarkupContainer("resizable");
		resizable.add(new Resize("table.resizable-header",
				"table.resizable-body", new WidthsModel()) {
			protected String getPersist(Component component) {
				return persistInCookie(
						"resize:" + component.getPageRelativePath(),
						30 * 24 * 60 * 60);
			}
		});
		add(resizable);

		resizable.add(new ListView<Foo>("row", new FoosModel()) {
			@Override
			protected void populateItem(ListItem<Foo> item) {
				item.add(new Label("cell1", new PropertyModel<String>(item
						.getModel(), "name")));
				item.add(new Label("cell2", new PropertyModel<String>(item
						.getModel(), "name")));
				item.add(new Label("cell3", new PropertyModel<String>(item
						.getModel(), "name")));
				item.add(new Label("cell4", new PropertyModel<String>(item
						.getModel(), "name")));
			}
		});
	}

	private class FoosModel extends AbstractReadOnlyModel<List<Foo>> {
		@Override
		public List<Foo> getObject() {
			return new FooDataProvider().foos;
		}
	}

	private class WidthsModel extends AbstractReadOnlyModel<List<Integer>> {
		@Override
		public List<Integer> getObject() {
			return Arrays.asList(64, 64, 64, 64);
		}
	}
}