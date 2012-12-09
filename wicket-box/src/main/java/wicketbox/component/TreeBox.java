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
package wicketbox.component;

import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.extensions.markup.html.repeater.tree.TableTree;
import org.apache.wicket.markup.repeater.data.IDataProvider;

/**
 * A boxed {@link TableTree}.
 * 
 * @author Sven Meier
 */
public abstract class TreeBox<T, S> extends TableTree<T, S> {

	private static final int DEFAULT_WIDTH = 64;

	public TreeBox(String id, List<? extends IColumn<T, S>> columns,
			ITreeProvider<T> treeProvider, long rowsPerPage) {
		super(id, columns, treeProvider, rowsPerPage);
	}

	@Override
	protected DataTable<T, S> newDataTable(String id,
			List<? extends IColumn<T, S>> columns,
			IDataProvider<T> dataProvider, long rowsPerPage) {
		return new DataBox<T, S>(id, columns, dataProvider, rowsPerPage) {
			@Override
			protected int getWidth(IColumn<?, ?> column) {
				return TreeBox.this.getWidth(column);
			};
		};
	}

	protected int getWidth(IColumn<?, ?> column) {
		return DEFAULT_WIDTH;
	}
}