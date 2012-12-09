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

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;

import wicketbox.Orientation;
import wicketbox.ResizableColumns;
import wicketbox.Stretch;
import wicketbox.SynchronizedScroll;

/**
 * A boxed {@link DataTable}-
 * 
 * @author Sven Meier
 */
public class DataBox<T, S> extends DataTable<T, S> {

	private static final int DEFAULT_WIDTH = 64;

	public DataBox(String id, List<? extends IColumn<T, S>> columns,
			IDataProvider<T> dataProvider, long rowsPerPage) {
		super(id, columns, dataProvider, rowsPerPage);

		add(new Stretch(Orientation.VERTICAL, ".top", ".body", ".bottom"));
		add(new ResizableColumns(".top table", ".body table", new WidthsModel()));
		add(new SynchronizedScroll(Orientation.HORIZONTAL, ".top, .body"));
	}

	protected int getWidth(IColumn<?, ?> column) {
		return DEFAULT_WIDTH;
	}

	private class WidthsModel implements IModel<List<Integer>> {

		public void detach() {
		}

		public List<Integer> getObject() {
			List<Integer> widths = new ArrayList<Integer>();

			for (IColumn<?, ?> column : getColumns()) {
				widths.add(DataBox.this.getWidth(column));
			}

			return widths;
		}

		public void setObject(List<Integer> object) {
		}
	}
}