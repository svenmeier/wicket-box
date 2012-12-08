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

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxNavigationToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.tree.content.Folder;
import org.apache.wicket.extensions.markup.html.repeater.tree.table.TreeColumn;
import org.apache.wicket.extensions.markup.html.repeater.tree.theme.WindowsTheme;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import wicketbox.repeater.TreeBox;
import wicketbox.repeater.theme.BasicTheme;

/**
 * @author Sven Meier
 */
public class TreeBoxExamplePage extends ExamplePage {

	public TreeBoxExamplePage() {
		TreeBox<Foo, Void> tree = new TreeBox<Foo, Void>("tree", columns(),
				new FooTreeProvider(), 10) {
			@Override
			protected Component newContentComponent(String id, IModel<Foo> model) {
				return new Folder<Foo>(id, this, model);
			}
		};
		tree.add(new WindowsTheme());
		tree.getTable().addTopToolbar(
				new HeadersToolbar<Void>(tree.getTable(), null));
		tree.getTable().addBottomToolbar(
				new AjaxNavigationToolbar(tree.getTable()));
		tree.getTable().setOutputMarkupId(true);
		tree.add(new BasicTheme());
		add(tree);
	}

	private List<IColumn<Foo, Void>> columns() {
		List<IColumn<Foo, Void>> columns = new ArrayList<IColumn<Foo, Void>>();
		columns.add(new TreeColumn<Foo, Void>(Model.of("name"), null));
		columns.add(new PropertyColumn<Foo, Void>(Model.of("Name"), null));
		columns.add(new PropertyColumn<Foo, Void>(Model.of("Name"), null));
		columns.add(new PropertyColumn<Foo, Void>(Model.of("Name"), null));
		return columns;
	}
}