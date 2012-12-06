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
package wicketbox.examples;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.PropertyModel;

import wicketbox.ResizableColumns;

/**
 * @author Sven Meier
 */
public class ResizableColumnsExamplePage extends ExamplePage
{

	public ResizableColumnsExamplePage()
	{
		WebMarkupContainer resizable = new WebMarkupContainer("resizable");
		resizable.add(new ResizableColumns("table.resizable-header", "table.resizable-body",
				new WidthsModel()));
		add(resizable);

		resizable.add(new ListView<Foo>("row", new FoosModel())
		{
			@Override
			protected void populateItem(ListItem<Foo> item)
			{
				item.add(new Label("cell1", new PropertyModel<String>(item.getModel(), "name")));
				item.add(new Label("cell2", new PropertyModel<String>(item.getModel(), "name")));
				item.add(new Label("cell3", new PropertyModel<String>(item.getModel(), "name")));
				item.add(new Label("cell4", new PropertyModel<String>(item.getModel(), "name")));
			}
		});
	}

	private class FoosModel extends AbstractReadOnlyModel<List<Foo>>
	{
		@Override
		public List<Foo> getObject()
		{
			return new FooDataProvider().foos;
		}
	}

	private class WidthsModel extends AbstractReadOnlyModel<List<Integer>>
	{
		@Override
		public List<Integer> getObject()
		{
			return Arrays.asList(64, 64, 64, 64);
		}
	}
}