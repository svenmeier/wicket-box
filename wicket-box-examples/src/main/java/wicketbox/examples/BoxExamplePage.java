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

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxNavigationToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.Model;

import wicketbox.table.DataBox;
import wicketbox.table.theme.BasicTheme;

/**
 * @author Sven Meier
 */
public class BoxExamplePage extends ExamplePage
{

	public BoxExamplePage()
	{
		DataBox<Foo, Void> table = new DataBox<Foo, Void>("table", columns(),
				new FooDataProvider(), 20);
		table.addTopToolbar(new HeadersToolbar<Void>(table, null));
		table.addBottomToolbar(new AjaxNavigationToolbar(table));
		table.add(new BasicTheme());
		add(table);
	}

	private List<IColumn<Foo, Void>> columns()
	{
		List<IColumn<Foo, Void>> columns = new ArrayList<IColumn<Foo, Void>>();
		columns.add(new PropertyColumn<Foo, Void>(Model.of("Name"), "name"));
		columns.add(new PropertyColumn<Foo, Void>(Model.of("Name"), "name"));
		columns.add(new PropertyColumn<Foo, Void>(Model.of("Name"), "name"));
		return columns;
	}
}